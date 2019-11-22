package com.zawlynn.capstoneproject.di.component;




import com.zawlynn.capstoneproject.data.repository.ApiRepository;
import com.zawlynn.capstoneproject.di.module.RoomModule;
import com.zawlynn.capstoneproject.di.module.ApplicationContextModule;
import com.zawlynn.capstoneproject.di.module.RetrofitModule;
import com.zawlynn.capstoneproject.data.repository.DatabaseRepository;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationContextModule.class, RetrofitModule.class, RoomModule.class})
public interface DataComponent {
   void inject(ApiRepository repository);
   void inject(DatabaseRepository repository);
}
