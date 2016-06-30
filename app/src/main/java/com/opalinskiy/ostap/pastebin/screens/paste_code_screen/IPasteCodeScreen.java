package com.opalinskiy.ostap.pastebin.screens.paste_code_screen;


public interface IPasteCodeScreen {

    interface IPresenter {
        void getCode(String url);

        void deletePaste(String url);

        void onDestroy();
    }

    interface IView {
        void setCode(String code);

        void onDeletePaste(String msg);
    }
}
