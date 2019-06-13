package com.nekobitlz.devfest_spb.baseMvp;

import android.arch.lifecycle.Lifecycle;
import android.os.Bundle;

public interface BaseContact {

    interface View {

    }

    interface Presenter<V extends BaseContact.View> {

        void attachLifecycle(Lifecycle lifecycle);

        void detachLifecycle(Lifecycle lifecycle);

        void attachView(V view);

        void detachView();

        V getView();

        boolean isViewAttached();

        Bundle getStateBundle();

        void onPresenterCreated();

        void onPresenterDestroy();
    }
}
