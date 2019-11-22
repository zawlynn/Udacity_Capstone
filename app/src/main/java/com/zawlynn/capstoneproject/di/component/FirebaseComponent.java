package com.zawlynn.capstoneproject.di.component;

import com.zawlynn.capstoneproject.di.module.FirebaseModule;
import com.zawlynn.capstoneproject.ui.login.LoginActivity;
import com.zawlynn.capstoneproject.ui.splash.SplashActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = { FirebaseModule.class})
public interface FirebaseComponent {
    void inject(LoginActivity activity);
    void inject(SplashActivity activity);
}
