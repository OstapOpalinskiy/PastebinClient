package com.opalinskiy.ostap.pastebin.screens.newPasteScreen.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.opalinskiy.ostap.pastebin.Constants;
import com.opalinskiy.ostap.pastebin.R;
import com.opalinskiy.ostap.pastebin.screens.mainScreen.IMainScreen;
import com.opalinskiy.ostap.pastebin.screens.mainScreen.presenter.MainFragmentPresenter;
import com.opalinskiy.ostap.pastebin.screens.newPasteScreen.INewPaste;
import com.opalinskiy.ostap.pastebin.screens.newPasteScreen.presenter.NewPastePresenter;

public class NewPasteFragment extends Fragment implements INewPaste.IView {
    private INewPaste.IPresenter presenter;
    private EditText etCode;
    private BottomSheetBehavior bottomSheetBehavior;
    private ImageView bottomSheetArrow;
    private Spinner spinnerSyntax;
    private Spinner spinnerExpiration;
    private Spinner spinnerExposure;
    private EditText etPasteName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_screen_fragment, container, false);
        init(view);
        setSpinnerAdapters();
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    bottomSheetArrow.animate().rotation(0);
                }
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetArrow.animate().rotation(180);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });

        setHasOptionsMenu(true);
        return view;
    }

    private void init(View view) {
        Log.d(Constants.TAG, "MyPastes onCreateView");
        presenter = new NewPastePresenter(this);
        etCode = (EditText) view.findViewById(R.id.et_code_MSF);

        View bottomSheet = view.findViewById(R.id.bottom_sheet_view);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheetArrow = (ImageView) bottomSheet.findViewById(R.id.iv_BS);

        spinnerSyntax = (Spinner) bottomSheet.findViewById(R.id.spinner_syntax_BS);
        spinnerExpiration = (Spinner) bottomSheet.findViewById(R.id.spinner_expiration_BS);
        spinnerExposure = (Spinner) bottomSheet.findViewById(R.id.spinner_exposure_BS);
        etPasteName = (EditText) bottomSheet.findViewById(R.id.et_paste_name_BS);
    }

    @Override
    public void onResume() {
        getActivity().setTitle(getResources().getString(R.string.new_paste));
        super.onResume();
    }

    private void setSpinnerAdapters() {
        ArrayAdapter<?> adapterSyntax =
                ArrayAdapter.createFromResource(getActivity(), R.array.syntax, android.R.layout.simple_spinner_item);
        adapterSyntax.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSyntax.setAdapter(adapterSyntax);

        ArrayAdapter<?> adapterExpiration =
                ArrayAdapter.createFromResource(getActivity(), R.array.expiration, android.R.layout.simple_spinner_item);
        adapterExpiration.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerExpiration.setAdapter(adapterExpiration);

        ArrayAdapter<?> adapterExposure =
                ArrayAdapter.createFromResource(getActivity(), R.array.exposure, android.R.layout.simple_spinner_item);
        adapterExposure.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerExposure.setAdapter(adapterExposure);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_screen, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String code = String.valueOf(etCode.getText());
        String pasteName = String.valueOf(etPasteName.getText());
        String syntax = String.valueOf(spinnerSyntax.getSelectedItem());
        String expiration = String.valueOf(spinnerExpiration.getSelectedItem());
        String exposure = String.valueOf(spinnerExposure.getSelectedItem());

        if (!TextUtils.isEmpty(code)) {
            presenter.onPostPaste(code, pasteName, syntax, expiration, exposure);
        } else {
            Toast.makeText(getActivity(), "Please, input some code.", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    public void setText(String pasteUrl) {
        etCode.setText(pasteUrl);
    }

    @Override
    public void showMessage() {
        Toast.makeText(getActivity(), "Please, input some code.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
    //    presenter.onDestroy();
        super.onDestroy();
    }
}
