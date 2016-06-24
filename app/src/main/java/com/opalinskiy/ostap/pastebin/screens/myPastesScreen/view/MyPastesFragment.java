package com.opalinskiy.ostap.pastebin.screens.myPastesScreen.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.opalinskiy.ostap.pastebin.Constants;
import com.opalinskiy.ostap.pastebin.R;
import com.opalinskiy.ostap.pastebin.interactor.models.Paste;
import com.opalinskiy.ostap.pastebin.screens.pasteCodeScreen.view.PasteCodeFragment;
import com.opalinskiy.ostap.pastebin.screens.mainScreen.view.NavigationDrawerActivity;
import com.opalinskiy.ostap.pastebin.screens.mainScreen.IMainScreen;
import com.opalinskiy.ostap.pastebin.screens.myPastesScreen.IMyPastesScreen;
import com.opalinskiy.ostap.pastebin.screens.myPastesScreen.presenter.MyPastesPresenter;


import java.util.List;

public class MyPastesFragment extends Fragment
        implements IMyPastesScreen.IView, IMyPastesScreen.IItemClickHandler {
    private IMyPastesScreen.IPresenter presenter;
    private List<Paste> pasteList;
    private RecyclerView recyclerView;
    private int myOrTrending;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_pastes_fragment, container, false);
        init(view);
        presenter.showMyPastes();
        return view;
    }

    private void init(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_MPF);
        myOrTrending = getArguments().getInt(Constants.MY_OR_TRANDING_KEY);
        final SharedPreferences prefs = getActivity().getSharedPreferences(Constants.PREFS_NAME, 0);
        presenter = new MyPastesPresenter(this, ((IMainScreen.IView) getActivity()), myOrTrending, prefs);
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
        pasteList = myPastes;
        setDataToRecyclerView(myPastes);
    }

    @Override
    public void showMessage() {
        Toast.makeText(getActivity(), "You need to login first.", Toast.LENGTH_SHORT).show();
    }

    private void setDataToRecyclerView(List<Paste> myPastes) {
        if (myPastes.size() > 0) {
            MyPastesAdapter adapter = new MyPastesAdapter(myPastes, this);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(linearLayoutManager);
        } else {
            Toast.makeText(getActivity(), "No pastes yet.", Toast.LENGTH_SHORT).show();
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
                , Constants.MY_PASTES_CODE_FRAGMENT_TAG, true);
    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
}
