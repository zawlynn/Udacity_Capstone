package com.zawlynn.udacitycapstoneproject.data.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public abstract class NetworkBoundResource<ResultType, RequestType> {
    private Flowable<Resource<ResultType>> result;
    protected NetworkBoundResource(Context context) {

        Flowable<ResultType> diskObservable= Flowable.defer(() -> loadFromDb()
                .subscribeOn(Schedulers.io()));

        Flowable<ResultType> networkObservable = Flowable.defer(() -> createCall()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .doOnNext(request -> {
                    saveCallResult(processResponse(request));
                })
                .onErrorReturn(throwable -> {
                    throw Exceptions.propagate(throwable);
                })
                .flatMap(requestTypeResponse -> loadFromDb()));

        if(isNetworkStatusAvailable(context)){
           result= networkObservable.map(Resource::success)
                    .onErrorReturn(throwable -> Resource.error(throwable.getMessage(), null))
                    .observeOn(AndroidSchedulers.mainThread())
                    .startWith(Resource.loading(null));
        }else {
            result= diskObservable.map(Resource::success)
                    .onErrorReturn(throwable -> Resource.error(throwable.getMessage(), null))
                    .observeOn(AndroidSchedulers.mainThread())
                    .startWith(Resource.loading(null));
        }
    }

    private RequestType processResponse(Response<RequestType> response) {
        return response.body();
    }

    private boolean isNetworkStatusAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public Flowable<Resource<ResultType>> asFlowable() {
        return result;
    }

    protected abstract void saveCallResult(RequestType request);

    protected abstract Flowable<ResultType> loadFromDb();

    protected abstract Flowable<Response<RequestType>> createCall();
}
