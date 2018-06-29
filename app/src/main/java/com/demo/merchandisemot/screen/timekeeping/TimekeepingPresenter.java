package com.demo.merchandisemot.screen.timekeeping;

import android.support.annotation.NonNull;
import android.util.Log;

import com.demo.architect.data.model.offline.AttendanceImageModel;
import com.demo.architect.data.model.offline.AttendanceModel;
import com.demo.architect.data.model.offline.ImageModel;
import com.demo.architect.data.repository.base.local.LocalRepository;
import com.demo.architect.domain.AddImageAttendanceUsecase;
import com.demo.architect.domain.AttendanceUsecase;
import com.demo.architect.domain.BaseUseCase;
import com.demo.architect.domain.GetDateUsecase;
import com.demo.architect.domain.UploadImageUsecase;
import com.demo.merchandisemot.R;
import com.demo.merchandisemot.app.CoreApplication;
import com.demo.merchandisemot.constants.Constants;
import com.demo.merchandisemot.manager.UserManager;
import com.demo.merchandisemot.util.ConvertUtils;

import java.io.File;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Created by MSI on 26/11/2017.
 */

public class TimekeepingPresenter implements TimekeepingContract.Presenter {

    private final String TAG = TimekeepingPresenter.class.getName();
    private final TimekeepingContract.View view;
    private final GetDateUsecase getDateUsecase;
    private final AttendanceUsecase attendanceUsecase;
    private final UploadImageUsecase uploadImageUsecase;
    private final AddImageAttendanceUsecase addImageAttendanceUsecase;
    @Inject
    LocalRepository localRepository;

    @Inject
    TimekeepingPresenter(@NonNull TimekeepingContract.View view, GetDateUsecase getDateUsecase, AttendanceUsecase attendanceUsecase, UploadImageUsecase uploadImageUsecase, AddImageAttendanceUsecase addImageAttendanceUsecase) {
        this.view = view;
        this.getDateUsecase = getDateUsecase;
        this.attendanceUsecase = attendanceUsecase;
        this.uploadImageUsecase = uploadImageUsecase;
        this.addImageAttendanceUsecase = addImageAttendanceUsecase;
    }

    @Inject
    public void setupPresenter() {
        view.setPresenter(this);
    }


    @Override
    public void start() {
        Log.d(TAG, TAG + ".start() called");
        getDateServer();
    }

    @Override
    public void stop() {
        Log.d(TAG, TAG + ".stop() called");
    }


    @Override
    public void getDateServer() {
        getDateUsecase.executeIO(new GetDateUsecase.RequestValue(),
                new BaseUseCase.UseCaseCallback<GetDateUsecase.ResponseValue,
                        GetDateUsecase.ErrorValue>() {
                    @Override
                    public void onSuccess(GetDateUsecase.ResponseValue successResponse) {
                        final long timeCurrent = ConvertUtils.convertStingToLong(successResponse.getDate());
                        view.showDateServer(timeCurrent);

                    }

                    @Override
                    public void onError(GetDateUsecase.ErrorValue errorResponse) {

                    }
                });


    }

    private int attendanceId = 0;

    @Override
    public void attendanceTracking(double latitude, double longitude, String type, String dateServer) {
        view.showProgressBar();
        int userTeamId = UserManager.getInstance().getUser().getUserTeamId();
        attendanceId = 0;
        attendanceUsecase.executeIO(new AttendanceUsecase.RequestValue(ConvertUtils.getCodeGenerationByTime(),
                        userTeamId, ConvertUtils.getDateTimeCurrent(), type, latitude, longitude, 1,
                        ConvertUtils.formatDate(dateServer)),
                new BaseUseCase.UseCaseCallback<AttendanceUsecase.ResponseValue,
                        AttendanceUsecase.ErrorValue>() {
                    @Override
                    public void onSuccess(AttendanceUsecase.ResponseValue successResponse) {
                        localRepository.addAttendanceModel(new AttendanceModel(successResponse.getId(), latitude + "," + longitude,
                                dateServer, ConvertUtils.getDateTimeCurrent(), userTeamId)).subscribe();
                        view.hideProgressBar();
                        view.showSuccess(CoreApplication.getInstance().getString(R.string.text_attendance_success_and_capture));
                        attendanceId = successResponse.getId();
                    }

                    @Override
                    public void onError(AttendanceUsecase.ErrorValue errorResponse) {
                        view.hideProgressBar();
                        view.showError(errorResponse.getDescription());
                    }
                });
    }

    @Override
    public void uploadImage(double latitude, double longitude, String path, String dateServer) {
        view.showProgressBar();
        int userTeamId = UserManager.getInstance().getUser().getUserTeamId();
        String nameFile = Constants.ATTENDANCE + userTeamId + UserManager.getInstance().getUser().getDisplayName()
                + ConvertUtils.getCodeGenerationByTime();
        uploadImageUsecase.executeIO(new UploadImageUsecase.RequestValue(new File(path), ConvertUtils.getCodeGenerationByTime(),
                        userTeamId, ConvertUtils.getDateTimeCurrent(), ConvertUtils.getDateTimeCurrent(),
                        ConvertUtils.formatDate(dateServer), latitude, longitude, nameFile),
                new BaseUseCase.UseCaseCallback<UploadImageUsecase.ResponseValue,
                        UploadImageUsecase.ErrorValue>() {
                    @Override
                    public void onSuccess(UploadImageUsecase.ResponseValue successResponse) {
                        final int serverId = successResponse.getId();
                        addImageAttendanceUsecase.executeIO(new AddImageAttendanceUsecase.RequestValue(ConvertUtils.getCodeGenerationByTime(),
                                        attendanceId, successResponse.getId()),
                                new BaseUseCase.UseCaseCallback<AddImageAttendanceUsecase.ResponseValue,
                                        AddImageAttendanceUsecase.ErrorValue>() {
                                    @Override
                                    public void onSuccess(AddImageAttendanceUsecase.ResponseValue successResponse) {
                                        view.hideProgressBar();
                                        view.backToDashboard();
                                        localRepository.addImageModel(new ImageModel(latitude + "," + longitude,
                                                userTeamId, dateServer, ConvertUtils.getDateTimeCurrent(),
                                                path, serverId, Constants.WAITING_UPLOAD)).subscribe(new Action1<Integer>() {
                                            @Override
                                            public void call(Integer s) {
                                                localRepository.addAttendanceImageModel(new AttendanceImageModel
                                                        (successResponse.getId(), s, attendanceId,
                                                                ConvertUtils.getDateTimeCurrent(), userTeamId)).subscribe();
                                            }
                                        });
                                    }

                                    @Override
                                    public void onError(AddImageAttendanceUsecase.ErrorValue errorResponse) {
                                        view.hideProgressBar();
                                        view.showError(errorResponse.getDescription());
                                    }
                                });
                    }

                    @Override
                    public void onError(UploadImageUsecase.ErrorValue errorResponse) {
                        view.hideProgressBar();
                        view.showError(errorResponse.getDescription());
                    }
                }
        );
    }

}
