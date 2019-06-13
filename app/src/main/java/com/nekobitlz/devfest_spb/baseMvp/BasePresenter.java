package com.nekobitlz.devfest_spb.baseMvp;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.os.Bundle;

public abstract class BasePresenter<V extends BaseContact.View>
        implements LifecycleObserver, BaseContact.Presenter<V> {

    private Bundle stateBundle;
    private V view;

   @Override
    public final V getView() {
        return view;
    }

    @Override
    public void attachLifecycle(Lifecycle lifecycle) {
        lifecycle.addObserver(this);
    }

    @Override
    public void detachLifecycle(Lifecycle lifecycle) {
        lifecycle.removeObserver(this);
    }

    @Override
    public void attachView(V view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public boolean isViewAttached() {
        return view != null;
    }

    @Override
    public final Bundle getStateBundle() {
       return stateBundle == null ? stateBundle = new Bundle() : stateBundle;
    }

    @Override
    public void onPresenterCreated() {
        getView();
    }

    @Override
    public void onPresenterDestroy() {
        if (stateBundle != null && !stateBundle.isEmpty()) {
            stateBundle.clear();
        }
    }
}
