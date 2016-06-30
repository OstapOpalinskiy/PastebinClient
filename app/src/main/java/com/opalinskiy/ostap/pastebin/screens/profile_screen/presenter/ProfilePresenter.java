package com.opalinskiy.ostap.pastebin.screens.profile_screen.presenter;

import android.content.SharedPreferences;

import com.opalinskiy.ostap.pastebin.interactor.models.User;
import com.opalinskiy.ostap.pastebin.screens.profile_screen.IProfileScreen;
import com.opalinskiy.ostap.pastebin.screens.main_screen.IMainScreen;
import com.opalinskiy.ostap.pastebin.screens.main_screen.presenter.MainScreenPresenter;


public class ProfilePresenter implements IProfileScreen.IPresenter {
    IMainScreen.IPresenter mainPresenter;
    IProfileScreen.IProfileView view;

    public ProfilePresenter( IProfileScreen.IProfileView view
            , IMainScreen.IView mainView, SharedPreferences preferences) {
        mainPresenter = MainScreenPresenter.getInstance(mainView, preferences);
        this.view = view;
    }

    @Override
    public void onLogout(SharedPreferences preferences) {
        mainPresenter.onLogout();
    }



    @Override
    public void loadData() {
       // User user = mainPresenter.getUser();
        boolean isRegistered = mainPresenter.isRegistered();
        if(isRegistered){
        //    view.showUser(user);
        }else {
            view.showGuest();
        }
    }

    @Override
    public void onDestroy() {
        view = null;
    }
}
