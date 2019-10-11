package com.zawlynn.udacitycapstoneproject.di.component;

import com.zawlynn.udacitycapstoneproject.di.module.FirebaseModule;
import com.zawlynn.udacitycapstoneproject.ui.login.LoginActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = { FirebaseModule.class})
public interface FirebaseComponent {
    void inject(LoginActivity activity);
}
