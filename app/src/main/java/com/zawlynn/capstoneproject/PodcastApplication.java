package com.zawlynn.capstoneproject;

import android.app.Application;

import com.zawlynn.capstoneproject.di.component.DaggerDataComponent;
import com.zawlynn.capstoneproject.di.component.DaggerFirebaseComponent;
import com.zawlynn.capstoneproject.di.component.DataComponent;
import com.zawlynn.capstoneproject.di.component.FirebaseComponent;
import com.zawlynn.capstoneproject.di.module.ApplicationContextModule;



public class PodcastApplication extends Application {
    DataComponent component;
    FirebaseComponent firebaseComponent;
    static PodcastApplication instance;
//    @Nullable
//    private EkoIdlingResource mIdlingResource;
//
//    @VisibleForTesting
//    @NonNull
//    private void initializeIdlingResource() {
//        if (mIdlingResource == null) {
//            mIdlingResource = new EkoIdlingResource();
//        }
//    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
//        if (BuildConfig.DEBUG) {
//            initializeIdlingResource();
//        }
        component = DaggerDataComponent.builder()
                .applicationContextModule(new ApplicationContextModule(this)).build();
        firebaseComponent = DaggerFirebaseComponent.builder().build();
    }

    public static PodcastApplication getInstance() {
        return instance;
    }

    public DataComponent getArticleComponent() {
        return component;
    }

    public FirebaseComponent getFirebaseComponent() {
        return firebaseComponent;
    }

//    public void setIdleState(boolean state) {
//        if (mIdlingResource != null)
//            mIdlingResource.setIdleState(state);
//    }
//
//    @Nullable
//    public EkoIdlingResource getIdlingResource() {
//        return mIdlingResource;
//    }
}
