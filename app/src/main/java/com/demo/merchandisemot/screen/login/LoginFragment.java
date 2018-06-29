package com.demo.merchandisemot.screen.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.demo.merchandisemot.R;
import com.demo.merchandisemot.app.base.BaseFragment;
import com.demo.merchandisemot.app.di.Precondition;
import com.demo.merchandisemot.screen.dashboard.DashboardActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by MSI on 26/11/2017.
 */

public class LoginFragment extends BaseFragment implements LoginContract.View {
    private final String TAG = LoginFragment.class.getName();
    private LoginContract.Presenter mPresenter;

    @Bind(R.id.edt_password)
    EditText edtPassword;

    @Bind(R.id.edt_username)
    EditText edtUsername;

    public LoginFragment() {
        // Required empty public constructor
    }


    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        this.mPresenter = Precondition.checkNotNull(presenter);
    }

    @Override
    public void showProgressBar() {
        showProgressDialog();
    }

    @Override
    public void hideProgressBar() {
        hideProgressDialog();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.stop();
    }

    @OnClick(R.id.btn_login)
    public void login() {
        if (edtUsername.getText().toString().equals("")) {
            startDialogNoti(getActivity().getString(R.string.text_username_null), SweetAlertDialog.WARNING_TYPE);
            return;
        }
        if (edtPassword.getText().toString().equals("")) {
            startDialogNoti(getActivity().getString(R.string.text_password_null), SweetAlertDialog.WARNING_TYPE);
            return;
        }

        mPresenter.login(edtUsername.getText().toString(), edtPassword.getText().toString());
    }

    private void startDialogNoti(String content, int type) {
        Activity activity = getActivity();
        if (activity != null) {
            new SweetAlertDialog(activity, type)
                    .setTitleText(getString(R.string.text_sweet_dialog_title))
                    .setContentText(content)
                    .setConfirmText(getString(R.string.text_ok))
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                        }
                    })
                    .show();
        }
    }


    @Override
    public void showError(String error) {
        startDialogNoti(error, SweetAlertDialog.ERROR_TYPE);
    }

    @Override
    public void loginSuccess() {
        DashboardActivity.start(getContext());
        getActivity().finish();
    }


}
