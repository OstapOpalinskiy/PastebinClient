package com.opalinskiy.ostap.pastebin.screens.profile_screen.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.opalinskiy.ostap.pastebin.global.Constants;
import com.opalinskiy.ostap.pastebin.interactor.ConnectProvider;
import com.opalinskiy.ostap.pastebin.interactor.DataInteractor;
import com.opalinskiy.ostap.pastebin.interactor.IDataInteractor;
import com.opalinskiy.ostap.pastebin.interactor.OnLoadFinishedListener;
import com.opalinskiy.ostap.pastebin.interactor.models.User;
import com.opalinskiy.ostap.pastebin.screens.main_screen.IMainScreen;
import com.opalinskiy.ostap.pastebin.screens.main_screen.presenter.MainScreenPresenter;
import com.opalinskiy.ostap.pastebin.screens.profile_screen.IProfileScreen;
import com.opalinskiy.ostap.pastebin.utils.ConverterUtils;

import java.util.HashMap;
import java.util.Map;


public class ProfilePresenter implements IProfileScreen.IPresenter {
    private IMainScreen.IPresenter mainPresenter;
    private IProfileScreen.IProfileView view;
    private IDataInteractor model;

    public ProfilePresenter(IProfileScreen.IProfileView view, IMainScreen.IView mainView, Context context) {
        mainPresenter = MainScreenPresenter.getInstance(mainView, context);
        this.model = new DataInteractor(ConnectProvider.getInstance().getRetrofit(), new ConverterUtils(), context);
        this.view = view;
    }

    @Override
    public void onLogout(SharedPreferences preferences) {
        mainPresenter.onLogout(preferences);
    }


    private void loadUser(final SharedPreferences prefs) {
        view.startProgress("Please wait...", "User data is loading");
        String userKey = prefs.getString(Constants.USER_KEY_TAG, "");

        Map<String, String> parameters = new HashMap<>();
        parameters.put("api_dev_key", Constants.API_DEV_KEY);
        parameters.put("api_user_key", userKey);
        parameters.put("api_option", "userdetails");

        model.getUser(parameters, new OnLoadFinishedListener() {
            @Override
            public void onSuccess(Object object) {
                Log.d(Constants.TAG, "loadUser() onSuccess():  " + object);
                User user = (User) object;
                if (user != null) {
                    view.showUser(user);
                    SharedPreferences.Editor ed = prefs.edit();
                    ed.putBoolean(Constants.IS_REGISTERED_KEY, true);
                    ed.apply();
                    view.stopProgress();
                }
            }

            @Override
            public void onFailure(Object object) {
                view.stopProgress();
                view.showMessage(object.toString());
                Log.d(Constants.TAG, "onFailure: " + object);
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
