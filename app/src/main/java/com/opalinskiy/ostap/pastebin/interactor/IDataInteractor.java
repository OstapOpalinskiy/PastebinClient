package com.opalinskiy.ostap.pastebin.interactor;


import com.opalinskiy.ostap.pastebin.interactor.models.PasteList;
import com.opalinskiy.ostap.pastebin.interactor.models.User;

import java.util.Map;


public interface IDataInteractor {

    void postPaste(Map<String, String> parameters, OnLoadFinishedListener <String> listener);

    void getUserKey(Map<String, String> parameters, final OnLoadFinishedListener <String> listener);

    void getRawPasteCode(String pasteUrl, final OnLoadFinishedListener <String> listener);

    void getPastes(Map<String, String> parameters, OnLoadFinishedListener <PasteList> listener);

    void deletePaste(Map<String, String> parameters, final OnLoadFinishedListener <String> listener);

    void getUser(Map<String, String> parameters, final OnLoadFinishedListener <User> listener);

}
