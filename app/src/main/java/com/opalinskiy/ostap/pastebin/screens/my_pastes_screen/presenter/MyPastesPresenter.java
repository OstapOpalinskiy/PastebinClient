package com.opalinskiy.ostap.pastebin.screens.my_pastes_screen.presenter;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.Log;

import com.opalinskiy.ostap.pastebin.Application;
import com.opalinskiy.ostap.pastebin.R;
import com.opalinskiy.ostap.pastebin.global.Constants;
import com.opalinskiy.ostap.pastebin.interactor.ConnectProvider;
import com.opalinskiy.ostap.pastebin.interactor.DataInteractor;
import com.opalinskiy.ostap.pastebin.interactor.IDataInteractor;
import com.opalinskiy.ostap.pastebin.interactor.OnLoadFinishedListener;
import com.opalinskiy.ostap.pastebin.interactor.RequestParams;
import com.opalinskiy.ostap.pastebin.interactor.models.Paste;
import com.opalinskiy.ostap.pastebin.interactor.models.PasteList;
import com.opalinskiy.ostap.pastebin.screens.my_pastes_screen.IMyPastesScreen;
import com.opalinskiy.ostap.pastebin.utils.ConverterUtils;

import java.util.LinkedList;
import java.util.List;


public class MyPastesPresenter implements IMyPastesScreen.IPresenter {
    private IDataInteractor model;
    private IMyPastesScreen.IView view;
    private int myOrTrending;
    private RequestParams parameters;


    public MyPastesPresenter(IMyPastesScreen.IView view, int myOrTrending) {
        this.view = view;
        model = DataInteractor.getInstance(ConnectProvider.getInstance().getRetrofit(), new ConverterUtils());
        this.myOrTrending = myOrTrending;
        parameters = new RequestParams();
    }

    @Override
    public void showMyPastes(SharedPreferences prefs) {
        String userKey = prefs.getString(Constants.USER_KEY_TAG, "");
        boolean isRegistered = prefs.getBoolean(Constants.IS_REGISTERED_KEY, false);
        loadData(userKey, isRegistered);
    }

    private void loadData(String userKey, boolean isRegistered) {
        if (myOrTrending == Constants.MY_PASTES) {
            if (isRegistered) {
                getMyPastes(userKey);
            } else {
                view.showMessage();
            }
        } else {
            getTrends();
        }
    }

    @Override
    public void choseTitle(int myOrTrending) {
        Resources r = Application.getContext().getResources();
        if (myOrTrending == Constants.MY_PASTES) {
            view.setTitle(r.getString(R.string.my_pastes));
        } else {
            view.setTitle(r.getString(R.string.trendings));
        }
    }

    @Override
    public void getMyPastes(String userKey) {
        view.startProgress("Please wait...", "Pastes is loading.");
        final List<Paste> myPastes = new LinkedList<>();
        model.getPastes(parameters.getMyPastesParams(), new OnLoadFinishedListener<PasteList>() {
            @Override
            public void onSuccess(PasteList pasteList) {
                myPastes.addAll(pasteList.getPasteList());
                showPastes(myPastes);
            }

            @Override
            public void onFailure(String msg) {
                showErrorMsg(msg);
            }
        });
    }

    private void showPastes(List<Paste> myPastes) {
        if (view != null) {
            setUsersList(myPastes);
            view.stopProgress();
        }
    }

    @Override
    public void getTrends() {
        Log.d(Constants.TAG1, "TRENDING PRESENTER getTrends()");
        view.startProgress("Please wait...", "Pastes is loading.");
        final List<Paste> myPastes = new LinkedList<>();
        model.getPastes(parameters.getTrendsParams(), new OnLoadFinishedListener<PasteList>() {

            @Override
            public void onSuccess(PasteList pasteList) {
                myPastes.addAll(pasteList.getPasteList());
                Log.d(Constants.TAG1, "TRENDING PRESENTER callback onSuccess()");
                showPastes(myPastes);
            }

            @Override
            public void onFailure(String msg) {
                showErrorMsg(msg);
            }
        });
    }

    private void showErrorMsg(String msg) {
        if (view != null) {
            view.showMessage(msg);
            view.stopProgress();
        }
    }

    @Override
    public void setUsersList(List<Paste> myPastes) {
        Log.d(Constants.TAG1, "TRENDING PRESENTER setUserList()");
        if (myPastes.size() > 0) {
            view.setDataToRecyclerView(myPastes);
        } else {
            view.showMessage(Application.getContext().getString(R.string.no_pastes_yet));
        }
    }

    @Override
    public void onDestroy() {
        view = null;
    }
}
