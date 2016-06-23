package com.opalinskiy.ostap.pastebin.interactor;

/**
 * Created by Evronot on 10.06.2016.
 */
public interface OnLoadFinishedListener {
    void onSuccess(Object object);
    void onFailure(Object object);
}
