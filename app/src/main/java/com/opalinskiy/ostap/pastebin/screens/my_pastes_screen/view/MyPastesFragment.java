package com.opalinskiy.ostap.pastebin.screens.my_pastes_screen.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.opalinskiy.ostap.pastebin.R;
import com.opalinskiy.ostap.pastebin.global.Constants;
import com.opalinskiy.ostap.pastebin.interactor.models.Paste;
import com.opalinskiy.ostap.pastebin.screens.base.BaseFragment;
import com.opalinskiy.ostap.pastebin.screens.main_screen.view.NavigationDrawerActivity;
import com.opalinskiy.ostap.pastebin.screens.my_pastes_screen.IMyPastesScreen;
import com.opalinskiy.ostap.pastebin.screens.my_pastes_screen.presenter.MyPastesPresenter;
import com.opalinskiy.ostap.pastebin.screens.paste_code_screen.view.PasteCodeFragment;

import java.util.List;

public class MyPastesFragment extends BaseFragment
        implements IMyPastesScreen.IView, IMyPastesScreen.IItemClickHandler {
    private IMyPastesScreen.IPresenter presenter;
    private RecyclerView recyclerView;
    private int myOrTrending;
    private SharedPreferences prefs;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_pastes_fragment, container, false);
        init(view);
        presenter.showMyPastes(prefs);
        return view;
    }

    private void init(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_MPF);
        myOrTrending = getArguments().getInt(Constants.MY_OR_TRANDING_KEY);
        prefs = getActivity().getSharedPreferences(Constants.PREFS_NAME, 0);
        presenter = new MyPastesPresenter(this, myOrTrending, getActivity());
    }

    @Override
    public void onResume() {
        if (myOrTrending == Constants.MY_PASTES) {
            getActivity().setTitle(getResources().getString(R.string.my_pastes));
        } else {
            getActivity().setTitle(getResources().getString(R.string.trendings));
        }
        super.onResume();
    }

    @Override
    public void setUsersList(List<Paste> myPastes) {
        setDataToRecyclerView(myPastes);
    }

    @Override
    public void showMessage() {
        Toast.makeText(getActivity(), R.string.you_need_login, Toast.LENGTH_SHORT).show();
    }

    private void setDataToRecyclerView(List<Paste> myPastes) {
        if (myPastes.size() > 0) {
            MyPastesAdapter adapter = new MyPastesAdapter(myPastes, this);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(linearLayoutManager);
        } else {
            Toast.makeText(getActivity(), R.string.no_pastes_yet, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(String url) {
        PasteCodeFragment fragment = new PasteCodeFragment();
        Bundle args = new Bundle();
        args.putString(Constants.URL_KEY, url);
        args.putInt(Constants.MY_OR_TRANDING_KEY, myOrTrending);
        fragment.setArguments(args);
        ((NavigationDrawerActivity) getActivity()).commitFragment(fragment
                , Constants.MY_PASTES_CODE_FRAGMENT_TAG, true, false);
    }

    @Override
    public void onDestroy() {
        if (presenter != null) {
            presenter.onDestroy();
        }
        super.onDestroy();
    }
}
