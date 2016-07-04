package com.opalinskiy.ostap.pastebin.screens.login_screen;

import android.content.SharedPreferences;

import com.opalinskiy.ostap.pastebin.screens.base.IBaseFragment;


public interface ILoginScreen {

    interface IPresenter {
        void onLogin(String login, String password, SharedPreferences preferences);
    }

    interface ILoginView extends IBaseFragment {}
}
