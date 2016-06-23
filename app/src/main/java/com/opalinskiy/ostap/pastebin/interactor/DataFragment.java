package com.opalinskiy.ostap.pastebin.interactor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.opalinskiy.ostap.pastebin.Constants;
import com.opalinskiy.ostap.pastebin.interactor.models.User;

public class DataFragment extends Fragment {
    private User user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            user = (User) savedInstanceState.getSerializable(Constants.SAVE_USER_KEY);
        }
        setRetainInstance(true);
        return null;
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(Constants.SAVE_USER_KEY, user);

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}