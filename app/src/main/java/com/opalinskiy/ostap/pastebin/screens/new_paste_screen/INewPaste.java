package com.opalinskiy.ostap.pastebin.screens.new_paste_screen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


public interface INewPaste {

    interface IView {
        void setText(String pasteUrl);

        void showMessage();

        void showLink(String pasteUrl);

        void clearLink();
    }

    interface IPresenter {
        void restoreState(Bundle bundle);

        void onPostPaste(String pasteCode, String name, String syntax, String expiration, String exposure);

        void onClearLink();

        void onDestroy();

        void openLink(Context context, Intent intent);
    }


}