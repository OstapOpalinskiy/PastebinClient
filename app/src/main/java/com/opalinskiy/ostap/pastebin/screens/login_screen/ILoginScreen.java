package com.opalinskiy.ostap.pastebin.screens.login_screen;

import android.content.SharedPreferences;

/**
 * Created by Evronot on 15.06.2016.
 */
public interface ILoginScreen {

    interface IPresenter {
        void onLogin(String login, String password, SharedPreferences preferences);
    }

    interface ILoginView {}
}
