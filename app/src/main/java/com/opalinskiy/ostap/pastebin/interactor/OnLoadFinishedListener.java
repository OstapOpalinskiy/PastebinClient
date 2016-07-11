package com.opalinskiy.ostap.pastebin.interactor;



public interface OnLoadFinishedListener<T> {
    void onSuccess(T t);
    void onFailure(String string);
}
