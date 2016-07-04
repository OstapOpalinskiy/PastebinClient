package com.opalinskiy.ostap.pastebin.screens.paste_code_screen.view;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
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

import com.opalinskiy.ostap.pastebin.R;
import com.opalinskiy.ostap.pastebin.global.Constants;
import com.opalinskiy.ostap.pastebin.screens.paste_code_screen.IPasteCodeScreen;
import com.opalinskiy.ostap.pastebin.screens.paste_code_screen.presenter.PasteCodePresenter;

public class PasteCodeFragment extends Fragment implements IPasteCodeScreen.IView {
    private TextView tvCode;
    private IPasteCodeScreen.IPresenter presenter;
    private String url;
    private ProgressDialog ringProgress;

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
        SharedPreferences prefs = getActivity().getSharedPreferences(Constants.PREFS_NAME, 0);
        presenter.deletePaste(url, prefs);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setCode(String code) {
        getActivity().setTitle(getResources().getString(R.string.paste_code));
        tvCode.setText(code);
    }

    @Override
    public void onDeletePaste(String s) {
        getActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public void startProgress() {
        ringProgress = ProgressDialog.show(getActivity(), 
                getActivity().getString(R.string.please_wait), getActivity().getString(R.string.paste_code_loading));
    }

    @Override
    public void stopProgress() {
        ringProgress.cancel();
    }

    @Override
    public void onDestroy() {
        if (presenter != null) {
            presenter.onDestroy();
        }
        super.onDestroy();
    }
}
