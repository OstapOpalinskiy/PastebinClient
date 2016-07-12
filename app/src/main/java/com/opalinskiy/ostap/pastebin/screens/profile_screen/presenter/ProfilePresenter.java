package com.opalinskiy.ostap.pastebin.screens.profile_screen.presenter;

import android.content.SharedPreferences;
import android.util.Log;

import com.opalinskiy.ostap.pastebin.global.Constants;
import com.opalinskiy.ostap.pastebin.interactor.ConnectProvider;
import com.opalinskiy.ostap.pastebin.interactor.DataInteractor;
import com.opalinskiy.ostap.pastebin.interactor.IDataInteractor;
import com.opalinskiy.ostap.pastebin.interactor.OnLoadFinishedListener;
import com.opalinskiy.ostap.pastebin.interactor.RequestParams;
import com.opalinskiy.ostap.pastebin.interactor.models.User;
import com.opalinskiy.ostap.pastebin.screens.main_screen.IMainScreen;
import com.opalinskiy.ostap.pastebin.screens.main_screen.presenter.MainScreenPresenter;
import com.opalinskiy.ostap.pastebin.screens.profile_screen.IProfileScreen;
import com.opalinskiy.ostap.pastebin.utils.ConverterUtils;


public class ProfilePresenter implements IProfileScreen.IPresenter {
    private IMainScreen.IPresenter mainPresenter;
    private IProfileScreen.IProfileView view;
    private IDataInteractor model;
    private RequestParams parameters;

    public ProfilePresenter(IProfileScreen.IProfileView view, IMainScreen.IView mainView) {
        this.model = DataInteractor.getInstance(ConnectProvider.getInstance().getRetrofit(), new ConverterUtils());
        this.view = view;
        mainPresenter = new MainScreenPresenter(mainView);
        parameters = new RequestParams();
    }

    @Override
    public void onLogout(SharedPreferences preferences) {
        mainPresenter.onLogout(preferences);
    }


    private void loadUser(final SharedPreferences prefs) {
        view.startProgress("Please wait...", "User data is loading");
        model.getUser(parameters.getUserParams(), new OnLoadFinishedListener<User>() {
            @Override
            public void onSuccess(User user) {
                if (user != null) {
                    view.showUser(user);
                    SharedPreferences.Editor ed = prefs.edit();
                    ed.putBoolean(Constants.IS_REGISTERED_KEY, true);
                    ed.apply();
                    view.stopProgress();
                }
            }

            @Override
            public void onFailure(String msg) {
                view.stopProgress();
                view.showMessage(msg);
            }
        });
    }

    @Override
    public void loadData(SharedPreferences prefs) {
        boolean isRegistered = prefs.getBoolean(Constants.IS_REGISTERED_KEY, false);
        Log.d(Constants.TAG, "is Registered:  in profile:" + isRegistered);
        if (isRegistered) {
            loadUser(prefs);
        } else {
            view.showGuest();
        }
    }

    @Override
    public void onDestroy() {
        view = null;
    }
}
