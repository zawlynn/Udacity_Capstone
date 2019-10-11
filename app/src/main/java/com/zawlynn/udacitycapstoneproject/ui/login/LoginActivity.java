package com.zawlynn.udacitycapstoneproject.ui.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.linecorp.linesdk.LoginDelegate;
import com.linecorp.linesdk.LoginListener;
import com.linecorp.linesdk.Scope;
import com.linecorp.linesdk.auth.LineAuthenticationParams;
import com.linecorp.linesdk.auth.LineLoginApi;
import com.linecorp.linesdk.auth.LineLoginResult;
import com.zawlynn.udacitycapstoneproject.PodcastApplication;
import com.zawlynn.udacitycapstoneproject.R;
import com.zawlynn.udacitycapstoneproject.databinding.LoginBinding;
import com.zawlynn.udacitycapstoneproject.ui.genre.GenreActivity;
import com.zawlynn.udacitycapstoneproject.ui.login.viewmodel.LoginViewModel;
import com.zawlynn.udacitycapstoneproject.ui.main.MainActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.inject.Inject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    @Inject
    public FirebaseAuth mAuth;
    @Inject
    public CallbackManager mCallbackManager;

    private static final String TAG = "LoginActivity_";
    @Inject
    public LoginManager fbLoginManager;
    LoginBinding binding;
    LoginViewModel viewmodel;
    private LoginDelegate loginDelegate = LoginDelegate.Factory.create();

    @SuppressLint("PackageManagerGetSignatures")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        viewmodel = ViewModelProviders.of(this).get(LoginViewModel.class);
        PodcastApplication.getInstance().getFirebaseComponent().inject(this);
        binding.loginButton.setOnClickListener(this);
        Signature[] sigs = new Signature[0];
        try {
            sigs = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES).signatures;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        for (Signature sig : sigs) {
            Log.d("MyApp", "Signature hashcode : " + sig.hashCode());
        }
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

        //       binding.lineLoginBtn.setChannelId("1653323716");
//        binding.lineLoginBtn.enableLineAppAuthentication(true);
//        binding.lineLoginBtn.setAuthenticationParams(new LineAuthenticationParams.Builder()
//                .scopes(Arrays.asList(Scope.PROFILE,Scope.OC_EMAIL,Scope.OC_PHONE_NUMBER,Scope.OC_REAL_NAME))
//                .build()
//        );
//        binding.lineLoginBtn.setLoginDelegate(loginDelegate);
//        binding.lineLoginBtn.addLoginListener(new LoginListener() {
//            @Override
//            public void onLoginSuccess(@NonNull LineLoginResult result) {
//                Toast.makeText(getApplicationContext(), "Login success "+ result.getLineProfile().getUserId(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onLoginFailure(@Nullable LineLoginResult result) {
//                Toast.makeText(getApplicationContext(), "Login failure", Toast.LENGTH_SHORT).show();
//            }
//        });
//        binding.signUp.setOnClickListener(view -> {
//            try{
//                // App-to-app login
//                Intent loginIntent = LineLoginApi.getLoginIntent(
//                        view.getContext(),
//                        "1653323716",
//                        new LineAuthenticationParams.Builder()
//                                .scopes(Arrays.asList(Scope.PROFILE,Scope.OC_PHONE_NUMBER,Scope.OC_EMAIL))
//                                .build());
//                startActivityForResult(loginIntent, 1);
//            }
//            catch(Exception e) {
//                Log.e("ERROR", e.toString());
//            }
//        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    //    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode != 1) {
//            Log.e("ERROR", "Unsupported Request");
//            return;
//        }
//
//        LineLoginResult result = LineLoginApi.getLoginResultFromIntent(data);
//
//        switch (result.getResponseCode()) {
//
//            case SUCCESS:
//                // Login successful
//                String accessToken = result.getLineCredential().getAccessToken().getTokenString();
//                Log.d(TAG, "onActivityResult: "+result.getLineProfile().getUserId());
////
////                Intent transitionIntent = new Intent(this, PostLoginActivity.class);
////                transitionIntent.putExtra("line_profile", result.getLineProfile());
////                transitionIntent.putExtra("line_credential", result.getLineCredential());
////                startActivity(transitionIntent);
//                break;
//
//            case CANCEL:
//                // Login canceled by user
//                Log.e("ERROR", "LINE Login Canceled by user.");
//                break;
//
//            default:
//                // Login canceled due to other error
//                Log.e("ERROR", "Login FAILED!");
//                Log.e("ERROR", result.getErrorData().toString());
//        }
//    }
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent i = new Intent(LoginActivity.this, GenreActivity.class);
                            startActivity(i);
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if (AccessToken.getCurrentAccessToken() != null) {
            fbLoginManager.logOut();
        } else {
            fbLoginManager.logInWithReadPermissions(LoginActivity.this,
                    Arrays.asList("email", "public_profile"));
        }

    }
}
