package com.opalinskiy.ostap.pastebin.screens.profileScreen.presenter;

import android.content.SharedPreferences;

import com.opalinskiy.ostap.pastebin.interactor.models.User;
import com.opalinskiy.ostap.pastebin.screens.profileScreen.IProfileScreen;
import com.opalinskiy.ostap.pastebin.screens.mainScreen.IMainScreen;
import com.opalinskiy.ostap.pastebin.screens.mainScreen.presenter.MainScreenPresenter;


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
        User user = mainPresenter.getUser();
        boolean isRegistered = mainPresenter.isRegistered();
        if(isRegistered){
            view.showUser(user);
        }else {
            view.showGuest();
        }
    }

    @Override
    public void onDestroy() {
        view = null;
    }
}
