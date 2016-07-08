package com.opalinskiy.ostap.pastebin.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opalinskiy.ostap.pastebin.global.Constants;
import com.opalinskiy.ostap.pastebin.interactor.models.Paste;
import com.opalinskiy.ostap.pastebin.interactor.models.PasteList;
import com.opalinskiy.ostap.pastebin.interactor.models.User;
import com.opalinskiy.ostap.pastebin.interactor.models.UserResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.lang.reflect.Type;


public  class ConverterUtils {

    public  PasteList responseBodyToPasteList(String response){

        JSONObject xmlJSONObj = null;
        try {
            xmlJSONObj = XML.toJSONObject(response);
            Log.d(Constants.TAG, "XML: ");
        } catch (JSONException je) {
            je.printStackTrace();
            Log.d(Constants.TAG, "Error");
        }

        Gson gson = new Gson();
        PasteList pasteList = null;
        try{
            Type type = new TypeToken<PasteList>() {}.getType();
            if (xmlJSONObj != null) {
                pasteList = gson.fromJson(xmlJSONObj.toString(), type);
            }
        }catch (Exception e){
            e.printStackTrace();
            Type type = new TypeToken<Paste>() {}.getType();
            String jsonString = null;
            if (xmlJSONObj != null) {
                jsonString = xmlJSONObj.toString().substring(9, xmlJSONObj.toString().length() - 1);
            }
            Paste paste = gson.fromJson(jsonString, type);
            pasteList = new PasteList();
            pasteList.addPaste(paste);
        }

        return pasteList;
    }


    public  User responseBodyToUser(String response){

        JSONObject xmlJSONObj = null;
        try {
            xmlJSONObj = XML.toJSONObject(response);
        } catch (JSONException je) {
            je.printStackTrace();
            Log.d(Constants.TAG, "Error");
        }

        Gson gson = new Gson();
        Type type = new TypeToken<UserResponse>() {}.getType();
        UserResponse userResponse = null;
        if (xmlJSONObj != null) {
            userResponse = gson.fromJson(xmlJSONObj.toString(), type);
        }
        return userResponse.getUser();
    }
}
