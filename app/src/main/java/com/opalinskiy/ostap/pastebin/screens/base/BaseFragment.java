package com.opalinskiy.ostap.pastebin.screens.base;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;


public class BaseFragment extends Fragment implements IBaseFragment {
    private ProgressDialog ringProgress;


    @Override
    public void startProgress(String title, String msg) {
        ringProgress = ProgressDialog.show(getActivity(), title, msg);
    }

    @Override
    public void stopProgress() {
        ringProgress.cancel();
    }
}
