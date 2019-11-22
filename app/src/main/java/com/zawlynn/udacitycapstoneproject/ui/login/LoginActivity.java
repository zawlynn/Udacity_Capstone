package com.zawlynn.udacitycapstoneproject.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.auth.GoogleAuthProvider;
import com.zawlynn.udacitycapstoneproject.PodcastApplication;
import com.zawlynn.udacitycapstoneproject.R;
import com.zawlynn.udacitycapstoneproject.databinding.LoginBinding;
import com.zawlynn.udacitycapstoneproject.ui.genre.GenreActivity;
import com.zawlynn.udacitycapstoneproject.ui.login.viewmodel.LoginViewModel;
import com.zawlynn.udacitycapstoneproject.utils.NetworkUtils;

import java.util.Arrays;

import javax.inject.Inject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    @Inject
    public FirebaseAuth mAuth;
    @Inject
    public CallbackManager mCallbackManager;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "LoginActivity_";
    @Inject
    public LoginManager fbLoginManager;
    LoginBinding binding;
    LoginViewModel viewmodel;

    @SuppressLint("PackageManagerGetSignatures")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        viewmodel = ViewModelProviders.of(this).get(LoginViewModel.class);
        PodcastApplication.getInstance().getFirebaseComponent().inject(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        binding.loginButton.setOnClickListener(this);
        binding.googleButton.setOnClickListener(this);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
            onSuccess();
        }else {
            fbLoginManager.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    handleFacebookAccessToken(loginResult.getAccessToken());
                    Log.d(TAG, "onSuccess: ");
                }

                @Override
                public void onCancel() {
                    Log.d(TAG, "onCancel: ");
                }

                @Override
                public void onError(FacebookException e) {
                    // here write code when get error
                    Log.d(TAG, "onError: ");
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult();
                firebaseAuthWithGoogle(account);
            } catch (Exception e) {
                Log.w(TAG, "Google sign in failed", e);
            }
        }else {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }

    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            onSuccess();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void onSuccess(){
        Intent i = new Intent(LoginActivity.this, GenreActivity.class);
        startActivity(i);
        finish();
    }
    // [START auth_with_google]
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        onSuccess();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        Snackbar.make(binding.container, getResources().getString(R.string.login_failed),
                                Snackbar.LENGTH_SHORT).show();
                    }
                });
    }
    @Override
    public void onClick(View view) {

        if(NetworkUtils.getInstance().isNetworkStatusAvailable(this)){
            if (AccessToken.getCurrentAccessToken() != null) {
                fbLoginManager.logOut();
            } else {
                if(view.getId()==R.id.login_button){
                    fbLoginManager.logInWithReadPermissions(LoginActivity.this,
                            Arrays.asList("email", "public_profile"));
                }else {
                    Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                    startActivityForResult(signInIntent, RC_SIGN_IN);
                }
            }
        }else {
            Snackbar.make(binding.container,getResources().getString(R.string.no_internet),
                    Snackbar.LENGTH_SHORT).show();
        }

    }
}
