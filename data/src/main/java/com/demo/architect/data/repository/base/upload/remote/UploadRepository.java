package com.demo.architect.data.repository.base.upload.remote;


import com.demo.architect.data.model.UploadEntity;

import java.io.File;

import okhttp3.RequestBody;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by uyminhduc on 10/16/16.
 */

public interface UploadRepository {
    Observable<UploadEntity> uploadImage(File file, String appCode, String sessionCode,
                                         int userTeamID, String dateTimeDevice,
                                         String date, String dateServer,
                                         double latitude, double longitude,
                                         String fileName);
}
