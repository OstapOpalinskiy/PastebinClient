package com.opalinskiy.ostap.pastebin.screens.loginScreen.presenter;

import android.content.SharedPreferences;

import com.opalinskiy.ostap.pastebin.screens.loginScreen.ILoginScreen;
import com.opalinskiy.ostap.pastebin.screens.mainScreen.IMainScreen;
import com.opalinskiy.ostap.pastebin.screens.mainScreen.presenter.MainScreenPresenter;

/**
 * Created by Evronot on 23.06.2016.
 */
public class LoginPresenter implements ILoginScreen.IPresenter {
    IMainScreen.IPresenter mainPresenter;

    public LoginPresenter(IMainScreen.IView view, SharedPreferences preferences) {
        mainPresenter = MainScreenPresenter.getInstance(view, preferences);
    }

    @Override
    public void onLogin(String login, String password, SharedPreferences preferences) {
        mainPresenter.onLogin(login, password, preferences);
    }
}
