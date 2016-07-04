package com.opalinskiy.ostap.pastebin.screens.profile_screen;

import android.content.SharedPreferences;

import com.opalinskiy.ostap.pastebin.interactor.models.User;
import com.opalinskiy.ostap.pastebin.screens.base.IBaseFragment;


public interface IProfileScreen {


    interface IPresenter {
        void onLogout(SharedPreferences preferences);
        void loadData(SharedPreferences prefs);
        void onDestroy();
    }


    interface IProfileView extends IBaseFragment  {
        void showUser(User user);
        void showGuest();
    }
}
