package com.opalinskiy.ostap.pastebin.screens.main_screen;

import android.content.SharedPreferences;

import com.opalinskiy.ostap.pastebin.interactor.models.User;


public interface IMainScreen {

    interface IPresenter {
        void loadUser(SharedPreferences prefs, boolean userChanged);

        void loadUserKey(SharedPreferences prefs, boolean userChanged);

        void onDestroy();

        void setUserInfo(User user);

        void setData(SharedPreferences prefs);

        void onLogout(SharedPreferences preferences);

        void onLogin(String login, String password, SharedPreferences preferences);
    }

    interface IView {

        void setAvatar(String url);

        void setUserName(String name);

        void setLoginScreen();

        void setProfileScreen();

        void onWrongLogin();

    }
}
