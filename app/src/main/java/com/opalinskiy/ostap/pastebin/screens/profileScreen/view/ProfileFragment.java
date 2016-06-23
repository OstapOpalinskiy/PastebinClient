package com.opalinskiy.ostap.pastebin.screens.profileScreen.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.opalinskiy.ostap.pastebin.Constants;
import com.opalinskiy.ostap.pastebin.R;
import com.opalinskiy.ostap.pastebin.interactor.models.User;
import com.opalinskiy.ostap.pastebin.screens.mainScreen.view.NavigationDrawerActivity;
import com.opalinskiy.ostap.pastebin.screens.profileScreen.IProfileScreen;
import com.opalinskiy.ostap.pastebin.screens.profileScreen.presenter.ProfilePresenter;
import com.opalinskiy.ostap.pastebin.screens.mainScreen.IMainScreen;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment implements IProfileScreen.IProfileView {
    private TextView tvName;
    private TextView tvEmail;
    private TextView tvLocation;
    private TextView tvLogOut;
    private CircleImageView avatar;
    private IProfileScreen.IPresenter presenter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        Log.d(Constants.TAG, "onCreateViewFragment");
        initViews(view);

        final SharedPreferences prefs = getActivity().getSharedPreferences(Constants.PREFS_NAME, 0);
        presenter = new ProfilePresenter(this, (IMainScreen.IView) getActivity(), prefs);
        tvLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onLogout(prefs);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        presenter.loadData();
        getActivity().setTitle(getResources().getString(R.string.profile));
        super.onResume();
    }


    @Override
    public void showUser(User user) {
        Picasso.
                with(getActivity()).
                load(user.getUserAvatarUrl()).
                into(avatar);
        tvName.setText(user.getUserName());
        tvEmail.setText(user.getUserEmail());
        tvLocation.setText(user.getUserLocation());

        tvEmail.setVisibility(View.VISIBLE);
        tvLocation.setVisibility(View.VISIBLE);

        tvLogOut.setText(R.string.logout);
    }

    @Override
    public void showGuest() {
        avatar.setImageDrawable(getResources().getDrawable(R.drawable.guest_green));
        tvName.setText(R.string.guest);
        tvEmail.setText(R.string.no_data);
        tvLocation.setText(R.string.no_data);

        tvEmail.setVisibility(View.INVISIBLE);
        tvLocation.setVisibility(View.INVISIBLE);

        tvLogOut.setText(R.string.login);
    }

    private void initViews(View view) {
        avatar = (CircleImageView) view.findViewById(R.id.iv_avatar_PF);
        tvName = (TextView) view.findViewById(R.id.tv_name_PF);
        tvEmail = (TextView) view.findViewById(R.id.tv_email_PF);
        tvLocation = (TextView) view.findViewById(R.id.tv_location_PF);
        tvLogOut = (TextView) view.findViewById(R.id.tv_logout_PF);
    }
}
