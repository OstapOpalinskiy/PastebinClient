package com.opalinskiy.ostap.pastebin.screens.base;


public interface IBaseFragment {
    void startProgress(String title,String msg);
    void stopProgress();
    void showMessage(String msg);
    void setTitle(String title);
}
