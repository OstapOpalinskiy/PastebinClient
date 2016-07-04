package com.opalinskiy.ostap.pastebin.screens.my_pastes_screen;

import android.content.SharedPreferences;

import com.opalinskiy.ostap.pastebin.interactor.models.Paste;
import com.opalinskiy.ostap.pastebin.screens.base.IBaseFragment;

import java.util.List;


public interface IMyPastesScreen {

    interface IPresenter {
        void getMyPastes(String userKey);

        void getTrends();

        void showMyPastes(SharedPreferences prefs);

        void onDestroy();
    }

    interface IView extends IBaseFragment {
        void setUsersList(List<Paste> list);
        void showMessage();
    }

    interface IItemClickHandler {
        void onItemClick(String url);
    }

}
