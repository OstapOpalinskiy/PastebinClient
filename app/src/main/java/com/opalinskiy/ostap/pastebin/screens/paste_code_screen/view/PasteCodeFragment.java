package com.opalinskiy.ostap.pastebin.screens.paste_code_screen.view;

import android.app.ApplicationErrorReport;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.opalinskiy.ostap.pastebin.R;
import com.opalinskiy.ostap.pastebin.global.Constants;
import com.opalinskiy.ostap.pastebin.screens.base.BaseFragment;
import com.opalinskiy.ostap.pastebin.screens.paste_code_screen.IPasteCodeScreen;
import com.opalinskiy.ostap.pastebin.screens.paste_code_screen.presenter.PasteCodePresenter;

public class PasteCodeFragment extends BaseFragment implements IPasteCodeScreen.IView {
    private TextView tvCode;
    private IPasteCodeScreen.IPresenter presenter;
    private String url;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.paste_code_fragment, container, false);
        tvCode = (TextView) view.findViewById(R.id.tv_code_PCF);
        presenter = new PasteCodePresenter(this, getActivity());
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
        presenter = new PasteCodePresenter(this, getActivity());
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
        if(isAdded()){
            getActivity().setTitle(getResources().getString(R.string.paste_code));
        }else{
            Log.d(Constants.TAG, "Error!!!!! Fragment NOT Added yet!!!!");
        }
        tvCode.setText(code);
    }

    @Override
    public void onDeletePaste(String s) {
        getActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onStop() {
        if (presenter != null) {
            presenter.onDestroy();
        }
        super.onStop();
    }

}
