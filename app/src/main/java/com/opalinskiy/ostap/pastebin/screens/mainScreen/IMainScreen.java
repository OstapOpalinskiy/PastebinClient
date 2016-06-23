package com.opalinskiy.ostap.pastebin.screens.mainScreen;

import android.content.SharedPreferences;

import com.opalinskiy.ostap.pastebin.interactor.models.User;

/**
 * Created by Evronot on 10.06.2016.
 */
public interface IMainScreen {

    interface IPresenter {
        void loadUser(SharedPreferences prefs, boolean userChanged);

        void loadUserKey(SharedPreferences prefs, boolean userChanged);

        User getUser();

        void onDestroy();

        void setUserInfo(User user);

        void setData(SharedPreferences prefs);

        void onLogout();

        void onLogin(String login, String password, SharedPreferences preferences);

        boolean isRegistered();

        void setIsRegistered(boolean isRegistered);
    }


    interface IView {
        void setUser(User user);

        void setAvatar(String url);

        void setUserName(String name);

        void setLoginScreen();

        void setProfileScreen();

        void onWrongLogin();

    }

    interface IViewFragment {
        void setText(String pasteUrl);
    }

    interface IPresenterFragment {
        void onPostPaste(String pasteCode, String name, String syntax, String expiration, String exposure);

        void onDestroy();
    }


}
