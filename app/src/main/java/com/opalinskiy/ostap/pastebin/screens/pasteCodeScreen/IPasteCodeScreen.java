package com.opalinskiy.ostap.pastebin.screens.pasteCodeScreen;


public interface IPasteCodeScreen {

    interface IPresenter {
        void getCode(String url);

        void deletePaste(String url, String userKey);

        void onDestroy();
    }

    interface IView {
        void setCode(String title, String code);

        void onDeletePaste(String msg);
    }
}
