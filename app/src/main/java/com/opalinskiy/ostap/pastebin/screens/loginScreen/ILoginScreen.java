package com.opalinskiy.ostap.pastebin.screens.loginScreen;

import android.content.SharedPreferences;

import com.opalinskiy.ostap.pastebin.screens.mainScreen.IMainScreen;

/**
 * Created by Evronot on 15.06.2016.
 */
public interface ILoginScreen {

    interface IPresenter {
        void onLogin(String login, String password, SharedPreferences preferences);
    }

    interface ILoginView {

    }
}
