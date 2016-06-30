package com.opalinskiy.ostap.pastebin.screens.my_pastes_screen.presenter;

import android.content.SharedPreferences;

import com.opalinskiy.ostap.pastebin.global.Constants;
import com.opalinskiy.ostap.pastebin.interactor.ConnectProvider;
import com.opalinskiy.ostap.pastebin.interactor.IDataInteractor;
import com.opalinskiy.ostap.pastebin.interactor.DataInteractor;
import com.opalinskiy.ostap.pastebin.interactor.OnLoadFinishedListener;
import com.opalinskiy.ostap.pastebin.interactor.models.Paste;
import com.opalinskiy.ostap.pastebin.interactor.models.PasteList;
import com.opalinskiy.ostap.pastebin.interactor.models.User;
import com.opalinskiy.ostap.pastebin.screens.main_screen.IMainScreen;
import com.opalinskiy.ostap.pastebin.screens.main_screen.presenter.MainScreenPresenter;
import com.opalinskiy.ostap.pastebin.screens.my_pastes_screen.IMyPastesScreen;
import com.opalinskiy.ostap.pastebin.utils.ConverterUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class MyPastesPresenter implements IMyPastesScreen.IPresenter {
    private IDataInteractor model;
    private IMyPastesScreen.IView view;
    private int myOrTrending;
    private IMainScreen.IPresenter mainPresenter;


    public MyPastesPresenter(IMyPastesScreen.IView view
            , IMainScreen.IView mainView, int myOrTrending, SharedPreferences preferences) {
        this.view = view;
        mainPresenter = MainScreenPresenter.getInstance(mainView, preferences);
        model = new DataInteractor(ConnectProvider.getInstance().getRetrofit(), new ConverterUtils());
        this.myOrTrending = myOrTrending;
    }


    @Override
    public void showMyPastes() {
       // User user = mainPresenter.getUser();
        boolean isRegistered = mainPresenter.isRegistered();
        if(myOrTrending == Constants.MY_PASTES){
            if (isRegistered) {
           //     getMyPastes(user.getUserKey());
            } else {
                mainPresenter.onLogout();
                view.showMessage();
            }
        }else{
            getTrends();
        }

    }

    @Override
    public void getMyPastes(String userKey) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("api_dev_key", Constants.API_DEV_KEY);
        parameters.put("api_user_key", userKey);
        parameters.put("api_option", "list");
        final List<Paste> myPastes = new LinkedList<>();
        model.getUsersPastes(parameters, new OnLoadFinishedListener() {
            @Override
            public void onSuccess(Object object) {
                PasteList pasteList = (PasteList) object;
                myPastes.addAll(pasteList.getPasteList());
                view.setUsersList(myPastes);
            }

            @Override
            public void onFailure(Object object) {
            }
        });
    }

    @Override
    public void getTrends() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("api_dev_key", Constants.API_DEV_KEY);
        parameters.put("api_option", "trends");
        final List<Paste> myPastes = new LinkedList<>();

        model.getListOfTrendingPastes(parameters, new OnLoadFinishedListener() {

            @Override
            public void onSuccess(Object object) {
                PasteList pasteList = (PasteList) object;
                myPastes.addAll(pasteList.getPasteList());
                view.setUsersList(myPastes);
            }

            @Override
            public void onFailure(Object object) {

            }
        });
    }

    @Override
    public void onDestroy() {
        view = null;
    }
}
