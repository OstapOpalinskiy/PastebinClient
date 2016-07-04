package com.opalinskiy.ostap.pastebin.screens.paste_code_screen;


import android.content.SharedPreferences;

import com.opalinskiy.ostap.pastebin.screens.base.IBaseFragment;

public interface IPasteCodeScreen {

    interface IPresenter {
        void getCode(String url);

        void deletePaste(String url, SharedPreferences preferences);

        void onDestroy();
    }

    interface IView extends IBaseFragment {
        void setCode(String code);

        void onDeletePaste(String msg);
    }
}
