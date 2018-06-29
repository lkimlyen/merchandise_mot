package com.demo.merchandisemot.screen.timekeeping;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.merchandisemot.R;
import com.demo.merchandisemot.app.base.BaseFragment;
import com.demo.merchandisemot.app.di.Precondition;
import com.demo.merchandisemot.constants.Constants;
import com.demo.merchandisemot.util.ConvertUtils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.app.Activity.RESULT_OK;

/**
 * Created by MSI on 26/11/2017.
 */

public class TimekeepingFragment extends BaseFragment implements TimekeepingContract.View, OnMapReadyCallback {
    private static final int MY_LOCATION_REQUEST_CODE = 1345;
    private final String TAG = TimekeepingFragment.class.getName();
    private TimekeepingContract.Presenter mPresenter;
    private FusedLocationProviderClient mFusedLocationClient;
    private static final int REQUEST_CODE_TAKE_PHOTO = 234;
    private GoogleMap mMap;
    @Bind(R.id.txt_latitude)
    TextView txtLatitude;

    @Bind(R.id.txt_longitude)
    TextView txtLogitude;

    @Bind(R.id.txt_server_time)
    TextView txtServerTime;

    @Bind(R.id.txt_attendance)
    TextView txtAttendance;

    @Bind(R.id.img_camera)
    ImageView imgCamera;

    @Bind(R.id.rb_check_in)
    RadioButton rbCheckIn;

    @Bind(R.id.rb_check_out)
    RadioButton rbCheckOut;
    private String mCurrentPhotoPath;

    public TimekeepingFragment() {
        // Required empty public constructor
    }

    public static TimekeepingFragment newInstance() {
        TimekeepingFragment fragment = new TimekeepingFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_TAKE_PHOTO) {
                if (data.getData()!= null) {
                    mPresenter.uploadImage(Double.parseDouble(txtLatitude.getText().toString()),
                            Double.parseDouble(txtLogitude.getText().toString()), mCurrentPhotoPath,
                            txtServerTime.getText().toString());
                }
            }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_timekeeping, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void setPresenter(TimekeepingContract.Presenter presenter) {
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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        enableMyLocation();
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {

                        if (location != null) {
                            LatLng sydney = new LatLng(location.getLatitude(), location.getLongitude());

                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 18));
                            txtLatitude.setText(String.valueOf(location.getLatitude()));
                            txtLogitude.setText(String.valueOf(location.getLongitude()));
                        }
                    }
                });

        googleMap.setOnMyLocationClickListener(new GoogleMap.OnMyLocationClickListener() {
            @Override
            public void onMyLocationClick(@NonNull Location location) {
                txtLatitude.setText(String.valueOf(location.getLatitude()));
                txtLogitude.setText(String.valueOf(location.getLongitude()));
            }
        });

    }

    @OnClick(R.id.img_back)
    public void back() {
        getActivity().finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_LOCATION_REQUEST_CODE) {
            if (permissions.length == 1 &&
                    permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            } else {
                // Permission was denied. Display an error message.
            }
        }
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_LOCATION_REQUEST_CODE);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }

    private int countSecond;

    @Override
    public void showDateServer(long time) {
        txtServerTime.setText(ConvertUtils.convertLongToString(time));
        countSecond = 0;
        CountDownTimer mCountDownTimer = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {


            }

            @Override
            public void onFinish() {
                countSecond += 1000;
                txtServerTime.setText(ConvertUtils.convertLongToString(time + countSecond));
                start();
            }
        }.start();
    }

    @Override
    public void showError(String message) {
        startDialogNoti(message, SweetAlertDialog.ERROR_TYPE);
    }

    @Override
    public void showSuccess(String message) {
        txtAttendance.setVisibility(View.GONE);
        imgCamera.setVisibility(View.VISIBLE);
        Activity activity = getActivity();
        if (activity != null) {
            new SweetAlertDialog(activity, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText(getString(R.string.text_sweet_dialog_title))
                    .setContentText(message)
                    .setConfirmText(getString(R.string.text_continue))
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
    public void backToDashboard() {
        Intent returnIntent = new Intent();
        getActivity().setResult(RESULT_OK, returnIntent);
        getActivity().finish();
    }

    @OnClick(R.id.txt_attendance)
    public void attendance() {
        if (txtLatitude.getText().toString().equals("") || txtLogitude.getText().toString().equals("")) {
            Toast.makeText(getContext(), getString(R.string.text_location_null), Toast.LENGTH_SHORT).show();
            return;
        }
        String type;
        if (rbCheckOut.isChecked()) {
            type = Constants.CHECK_IN;
        } else {
            type = Constants.CHECK_OUT;
        }

        mPresenter.attendanceTracking(Double.parseDouble(txtLatitude.getText().toString()),
                Double.parseDouble(txtLogitude.getText().toString()), type, txtServerTime.getText().toString());
    }

    @OnClick(R.id.img_camera)
    public void capture() {
        Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePicture.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        "com.example.android.fileprovider",
                        photoFile);
                takePicture.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePicture, REQUEST_CODE_TAKE_PHOTO);
            }
        }
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".png",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
}
