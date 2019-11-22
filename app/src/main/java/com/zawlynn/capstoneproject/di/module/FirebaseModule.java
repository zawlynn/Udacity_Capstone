package com.zawlynn.capstoneproject.di.module;

import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class FirebaseModule {

    @Provides
    @Singleton
    public FirebaseAuth provideFirebaseAuth(){
       return FirebaseAuth.getInstance();
    }
    @Provides
    @Singleton
    public CallbackManager provideCallbackManager(){
        return CallbackManager.Factory.create();
    }
    @Provides
    @Singleton
    public LoginManager provideLoginManager(){
        return LoginManager.getInstance();
    }
}
