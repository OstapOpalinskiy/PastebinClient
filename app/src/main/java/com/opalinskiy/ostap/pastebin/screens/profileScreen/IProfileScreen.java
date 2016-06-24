package com.opalinskiy.ostap.pastebin.screens.profileScreen;

import android.content.SharedPreferences;

import com.opalinskiy.ostap.pastebin.interactor.models.User;

/**
 * Created by Evronot on 15.06.2016.
 */
public interface IProfileScreen {


    interface IPresenter {
        void onLogout(SharedPreferences preferences);
        void loadData();
        void onDestroy();
    }


    interface IProfileView {
        void showUser(User user);
        void showGuest();
    }
}
