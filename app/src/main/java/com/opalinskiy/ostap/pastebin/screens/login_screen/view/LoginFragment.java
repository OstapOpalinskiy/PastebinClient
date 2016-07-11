package com.opalinskiy.ostap.pastebin.screens.login_screen.view;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.opalinskiy.ostap.pastebin.R;
import com.opalinskiy.ostap.pastebin.global.Constants;
import com.opalinskiy.ostap.pastebin.screens.base.BaseFragment;
import com.opalinskiy.ostap.pastebin.screens.login_screen.ILoginScreen;
import com.opalinskiy.ostap.pastebin.screens.login_screen.presenter.LoginPresenter;
import com.opalinskiy.ostap.pastebin.screens.main_screen.IMainScreen;

public class LoginFragment extends BaseFragment implements ILoginScreen.ILoginView {
    private EditText etLogin;
    private EditText etPassword;
    private TextView tvLogin;
    private ILoginScreen.IPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        initViews(view);

        final SharedPreferences prefs = getActivity().getSharedPreferences(Constants.PREFS_NAME, 0);
        presenter = new LoginPresenter((IMainScreen.IView) getActivity());
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = String.valueOf(etLogin.getText());
                String password = String.valueOf(etPassword.getText());
                presenter.onLogin(login, password, prefs);

            }
        });
        return view;
    }

    private void initViews(View view) {
        etLogin = (EditText) view.findViewById(R.id.et_login_FL);
        etPassword = (EditText) view.findViewById(R.id.et_password_FL);
        tvLogin = (TextView) view.findViewById(R.id.tv_login_FL);
    }

    @Override
    public void onResume() {
        setTitle(getResources().getString(R.string.login));
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        stopProgress();
    }
}
