package com.opalinskiy.ostap.pastebin.interactor;


import java.util.Map;


public interface IDataInteractor {

    void postPaste(Map<String, String> parameters, OnLoadFinishedListener listener);

    void getUserKey(Map<String, String> parameters, final OnLoadFinishedListener listener);

    void getRawPasteCode(String pasteUrl, final OnLoadFinishedListener listener);

    void getPastes(Map<String, String> parameters, OnLoadFinishedListener listener);

    void deletePaste(Map<String, String> parameters, final OnLoadFinishedListener listener);

    void getUser(Map<String, String> parameters, final OnLoadFinishedListener listener);

}
