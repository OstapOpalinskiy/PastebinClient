package com.opalinskiy.ostap.pastebin.screens.myPastesScreen;

import com.opalinskiy.ostap.pastebin.interactor.models.Paste;

import java.util.List;


public interface IMyPastesScreen {

    interface IPresenter {
        void getMyPastes(String userKey);

        void getTrends();

        void showMyPastes();

        void onDestroy();
    }

    interface IView {
        void setUsersList(List<Paste> list);
        void showMessage();
    }

    interface IItemClickHandler {
        void onItemClick(String url);
    }

}
