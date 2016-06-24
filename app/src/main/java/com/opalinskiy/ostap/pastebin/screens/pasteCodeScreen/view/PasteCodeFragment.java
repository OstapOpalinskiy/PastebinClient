package com.opalinskiy.ostap.pastebin.screens.pasteCodeScreen.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.opalinskiy.ostap.pastebin.Constants;
import com.opalinskiy.ostap.pastebin.R;
import com.opalinskiy.ostap.pastebin.interactor.models.User;
import com.opalinskiy.ostap.pastebin.screens.mainScreen.view.NavigationDrawerActivity;
import com.opalinskiy.ostap.pastebin.screens.pasteCodeScreen.IPasteCodeScreen;
import com.opalinskiy.ostap.pastebin.screens.pasteCodeScreen.presenter.PasteCodePresenter;

public class PasteCodeFragment extends Fragment implements IPasteCodeScreen.IView {
    private TextView tvCode;
    private IPasteCodeScreen.IPresenter presenter;
    private String url;
    private String userKey;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.paste_code_fragment, container, false);
        tvCode = (TextView) view.findViewById(R.id.tv_code_PCF);
        presenter = new PasteCodePresenter(this);
        url = getArguments().getString(Constants.URL_KEY);
        int myOrTrending = getArguments().getInt(Constants.MY_OR_TRANDING_KEY);
        if (myOrTrending == Constants.MY_PASTES) {
            setHasOptionsMenu(true);
        }
        presenter.getCode(url);
        return view;
    }

    @Override
    public void onResume() {
        User user = ((NavigationDrawerActivity)getActivity()).getUser();
        userKey = user.getUserKey();
        getActivity().setTitle(getResources().getString(R.string.paste_code));
        super.onResume();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_my_pastes, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        presenter.deletePaste(url, userKey);
        getActivity().getSupportFragmentManager().popBackStack();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setCode(String code) {
        getActivity().setTitle(getResources().getString(R.string.paste_code));
        tvCode.setText(code);
    }

    @Override
    public void onDeletePaste(String s) {

    }

    @Override
    public void onDestroy() {
        if(presenter != null){
            presenter.onDestroy();
        }
        super.onDestroy();
    }
}
