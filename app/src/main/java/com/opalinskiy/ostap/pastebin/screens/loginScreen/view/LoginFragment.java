package com.opalinskiy.ostap.pastebin.screens.loginScreen.view;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.opalinskiy.ostap.pastebin.Constants;
import com.opalinskiy.ostap.pastebin.R;
import com.opalinskiy.ostap.pastebin.screens.loginScreen.ILoginScreen;
import com.opalinskiy.ostap.pastebin.screens.loginScreen.presenter.LoginPresenter;
import com.opalinskiy.ostap.pastebin.screens.mainScreen.IMainScreen;

public class LoginFragment extends Fragment implements ILoginScreen.ILoginView {
    private EditText etLogin;
    private EditText etPassword;
    private TextView tvLogin;
    private ILoginScreen.IPresenter presenter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        etLogin = (EditText) view.findViewById(R.id.et_login_FL);
        etPassword = (EditText) view.findViewById(R.id.et_password_FL);
        tvLogin = (TextView) view.findViewById(R.id.tv_login_FL);
        final SharedPreferences prefs = getActivity().getSharedPreferences(Constants.PREFS_NAME, 0);
        presenter =new LoginPresenter((IMainScreen.IView)getActivity(), prefs);

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(Constants.TAG, "Login click!");
                String login = String.valueOf(etLogin.getText());
                String password = String.valueOf(etPassword.getText());
                presenter.onLogin(login, password, prefs);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        getActivity().setTitle(getResources().getString(R.string.login));
        super.onResume();
    }
}
