package com.demo.merchandisemot.screen.notification;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo.architect.data.model.offline.NotificationList;
import com.demo.merchandisemot.R;
import com.demo.merchandisemot.adapter.NotificationAdapter;
import com.demo.merchandisemot.app.base.BaseFragment;
import com.demo.merchandisemot.app.di.Precondition;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by MSI on 26/11/2017.
 */

public class NotificationFragment extends BaseFragment implements NotificationContract.View {
    private final String TAG = NotificationFragment.class.getName();
    private NotificationContract.Presenter mPresenter;

    @Bind(R.id.srl_noti)
    SwipeRefreshLayout srlNoti;

    @Bind(R.id.rv_noti)
    RecyclerView rvNoti;

    private NotificationAdapter adapter;

    public NotificationFragment() {
        // Required empty public constructor
    }


    public static NotificationFragment newInstance() {
        NotificationFragment fragment = new NotificationFragment();
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
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        srlNoti.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.downloadNotification();
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvNoti.setLayoutManager(layoutManager);

    }

    @Override
    public void setPresenter(NotificationContract.Presenter presenter) {
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
    public void showNotification(NotificationList list) {
       srlNoti.setRefreshing(false);
        adapter = new NotificationAdapter(list.getItemList());
        rvNoti.setAdapter(adapter);
    }

    @OnClick(R.id.img_refresh)
    public void refresh(){
        mPresenter.downloadNotification();
    }

    @OnClick(R.id.img_back)
    public void back(){
        getActivity().finish();
    }

}
