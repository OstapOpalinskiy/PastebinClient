package com.opalinskiy.ostap.pastebin.screens.paste_code_screen;


import android.content.SharedPreferences;

public interface IPasteCodeScreen {

    interface IPresenter {
        void getCode(String url);

        void deletePaste(String url, SharedPreferences preferences);

        void onDestroy();
    }

    interface IView {
        void setCode(String code);

        void onDeletePaste(String msg);

        void startProgress();

        void stopProgress();
    }
}
