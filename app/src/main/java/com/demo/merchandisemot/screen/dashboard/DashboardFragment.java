package com.demo.merchandisemot.screen.dashboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.demo.architect.data.model.OutletEntiy;
import com.demo.merchandisemot.R;
import com.demo.merchandisemot.app.base.BaseFragment;
import com.demo.merchandisemot.app.di.Precondition;
import com.demo.merchandisemot.screen.notification.NotificationActivity;
import com.demo.merchandisemot.screen.timekeeping.TimekeepingActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by MSI on 26/11/2017.
 */

public class DashboardFragment extends BaseFragment implements DashboardContract.View {
    private final String TAG = DashboardFragment.class.getName();
    private DashboardContract.Presenter mPresenter;

    @Bind(R.id.txt_address)
    TextView txtAddress;

    @Bind(R.id.txt_full_name)
    TextView txtFullName;

    @Bind(R.id.txt_outlet_name)
    TextView txtOutletName;

    @Bind(R.id.txt_city)
    TextView txtCity;
    public DashboardFragment() {
        // Required empty public constructor
    }


    public static DashboardFragment newInstance() {
        DashboardFragment fragment = new DashboardFragment();
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
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void setPresenter(DashboardContract.Presenter presenter) {
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

    @Override
    public void showInfoOutlet(OutletEntiy outlet) {
        txtAddress.setText(outlet.getAddress());
        txtOutletName.setText(outlet.getOutletName());
        txtCity.setText(outlet.getCity());
    }

    @Override
    public void showUserInfo(String fullName) {
        txtFullName.setText(fullName);
    }

    @OnClick(R.id.btn_notification)
    public void openNotification(){
        NotificationActivity.start(getContext());
    }

    @OnClick(R.id.btn_timekeeping)
    public void openTimekeeping(){
        TimekeepingActivity.start(getContext());
    }
}
