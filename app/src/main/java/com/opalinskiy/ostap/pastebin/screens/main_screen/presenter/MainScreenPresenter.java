package com.opalinskiy.ostap.pastebin.screens.main_screen.presenter;

import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.opalinskiy.ostap.pastebin.global.Constants;
import com.opalinskiy.ostap.pastebin.interactor.ConnectProvider;
import com.opalinskiy.ostap.pastebin.interactor.DataInteractor;
import com.opalinskiy.ostap.pastebin.interactor.IDataInteractor;
import com.opalinskiy.ostap.pastebin.interactor.OnLoadFinishedListener;
import com.opalinskiy.ostap.pastebin.interactor.RequestParams;
import com.opalinskiy.ostap.pastebin.interactor.models.User;
import com.opalinskiy.ostap.pastebin.screens.main_screen.IMainScreen;
import com.opalinskiy.ostap.pastebin.utils.ConverterUtils;

public class MainScreenPresenter implements IMainScreen.IPresenter {
    private IMainScreen.IView view;
    private IDataInteractor model;
    private RequestParams parameters;

    public MainScreenPresenter(final IMainScreen.IView view) {
        this.view = view;
        this.model =DataInteractor.getInstance(ConnectProvider.getInstance().getRetrofit(), new ConverterUtils());
        parameters = new RequestParams();
    }

    @Override
    public void loadUser(final SharedPreferences prefs, final boolean userChanged) {
        model.getUser(parameters.getUserParams(), new OnLoadFinishedListener <User>() {
            @Override
            public void onSuccess(User user) {
                Log.d(Constants.TAG, "loadUser() onSuccess():  " + user);
                if (user != null) {
                    setUserInfo(user);
                }
                if (userChanged) {
                    view.setProfileScreen();
                }
                SharedPreferences.Editor ed = prefs.edit();
                ed.putBoolean(Constants.IS_REGISTERED_KEY, true);
                ed.apply();
            }

            @Override
            public void onFailure(String msg) {
                view.showMessage(msg);
            }
        });
    }


    @Override
    public void loadUserKey(final SharedPreferences prefs, final boolean userChanged) {
        model.getUserKey(parameters.getUserKeyParams(), new OnLoadFinishedListener<String>()  {
            @Override
            public void onSuccess(String userKey) {
                if (userKey.equals(Constants.WRONG_PASSWORD_RESPONSE)) {
                    view.showMessage(userKey);
                } else {
                    SharedPreferences.Editor ed = prefs.edit();
                    ed.putString(Constants.USER_KEY_TAG, userKey);
                    ed.apply();
                    loadUser(prefs, userChanged);
                }
            }

            @Override
            public void onFailure(String msg) {
                view.showMessage(msg);
            }
        });
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void setUserInfo(User user) {
            view.setAvatar(user.getUserAvatarUrl());
            view.setUserName(user.getUserName());
    }

    @Override
    public void setData(SharedPreferences prefs) {
        if(prefs.getBoolean(Constants.IS_REGISTERED_KEY, false)){
            loadUser(prefs, false);
        } else{
            view.setGuest();
        }
    }

    @Override
    public void onLogout(SharedPreferences prefs) {
        Log.d(Constants.TAG, "onLogout");
        view.setLoginScreen();
        SharedPreferences.Editor ed = prefs.edit();
        ed.putBoolean(Constants.IS_REGISTERED_KEY, false);
        ed.apply();

    }

    @Override
    public void onLogin(String login, String password, SharedPreferences prefs) {
        if (!(TextUtils.isEmpty(login) && TextUtils.isEmpty(password))) {
            SharedPreferences.Editor ed = prefs.edit();
            ed.putString(Constants.LOGIN_KEY, login);
            ed.putString(Constants.PASSWORD_KEY, password);
            ed.putBoolean(Constants.IS_REGISTERED_KEY, true);
            ed.apply();
        }

        loadUserKey(prefs, true);
    }
}
