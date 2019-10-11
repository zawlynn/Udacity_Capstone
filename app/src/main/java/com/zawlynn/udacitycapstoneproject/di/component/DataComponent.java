package com.zawlynn.udacitycapstoneproject.di.component;




import com.zawlynn.udacitycapstoneproject.di.module.ApplicationContextModule;
import com.zawlynn.udacitycapstoneproject.di.module.RetrofitModule;
import com.zawlynn.udacitycapstoneproject.di.module.RoomModule;
import com.zawlynn.udacitycapstoneproject.repository.ApiRepository;
import com.zawlynn.udacitycapstoneproject.repository.DatabaseRepository;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationContextModule.class, RetrofitModule.class, RoomModule.class})
public interface DataComponent {
   void inject(ApiRepository repository);
   void inject(DatabaseRepository repository);
}
