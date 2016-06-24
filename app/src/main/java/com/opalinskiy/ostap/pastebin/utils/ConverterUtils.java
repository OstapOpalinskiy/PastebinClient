package com.opalinskiy.ostap.pastebin.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opalinskiy.ostap.pastebin.Constants;
import com.opalinskiy.ostap.pastebin.interactor.models.Paste;
import com.opalinskiy.ostap.pastebin.interactor.models.PasteList;
import com.opalinskiy.ostap.pastebin.interactor.models.User;
import com.opalinskiy.ostap.pastebin.interactor.models.UserResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Scanner;

import okhttp3.ResponseBody;
import retrofit2.Response;


public  class ConverterUtils {

    public  PasteList responseBodyToPasteList(Response<ResponseBody> response){
        InputStream is = response.body().byteStream();
        String myString = new Scanner(is, "UTF-8").useDelimiter("\\A").next();

        JSONObject xmlJSONObj = null;
        try {
            xmlJSONObj = XML.toJSONObject(myString);
            Log.d(Constants.TAG, "XML: ");
        } catch (JSONException je) {
            je.printStackTrace();
            Log.d(Constants.TAG, "Error");
        }

        Gson gson = new Gson();
        PasteList pasteList = null;
        try{
            Type type = new TypeToken<PasteList>() {}.getType();
            pasteList = gson.fromJson(xmlJSONObj.toString(), type);
        }catch (Exception e){
            e.printStackTrace();
            Type type = new TypeToken<Paste>() {}.getType();
            String jsonString = xmlJSONObj.toString().substring(9, xmlJSONObj.toString().length() - 1);
            Paste paste = gson.fromJson(jsonString, type);
            pasteList = new PasteList();
            pasteList.addPaste(paste);
        }

        return pasteList;
    }


    public  User responseBodyToUser(Response<ResponseBody> response){
        InputStream is = response.body().byteStream();
        String myString = new Scanner(is, "UTF-8").useDelimiter("\\A").next();

        JSONObject xmlJSONObj = null;
        try {
            xmlJSONObj = XML.toJSONObject(myString);
        } catch (JSONException je) {
            je.printStackTrace();
            Log.d(Constants.TAG, "Error");
        }

        Gson gson = new Gson();
        Type type = new TypeToken<UserResponse>() {}.getType();
        UserResponse userResponse = gson.fromJson(xmlJSONObj.toString(), type);
        return userResponse.getUser();
    }
}
