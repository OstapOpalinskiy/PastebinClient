package com.opalinskiy.ostap.pastebin.screens.login_screen.presenter;


import android.content.Context;
import android.content.SharedPreferences;

import com.opalinskiy.ostap.pastebin.screens.login_screen.ILoginScreen;
import com.opalinskiy.ostap.pastebin.screens.main_screen.IMainScreen;
import com.opalinskiy.ostap.pastebin.screens.main_screen.presenter.MainScreenPresenter;


public class LoginPresenter implements ILoginScreen.IPresenter {
    IMainScreen.IPresenter mainPresenter;

    public LoginPresenter(IMainScreen.IView view, Context context) {
        mainPresenter = MainScreenPresenter.getInstance(view, context);
    }

    @Override
    public void onLogin(String login, String password, SharedPreferences preferences) {
        mainPresenter.onLogin(login, password, preferences);
    }
}
