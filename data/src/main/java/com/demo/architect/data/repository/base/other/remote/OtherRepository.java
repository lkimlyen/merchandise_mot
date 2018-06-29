package com.demo.architect.data.repository.base.other.remote;


import com.demo.architect.data.model.OutletResponse;
import com.demo.architect.data.model.UserLoginResponse;

import rx.Observable;

/**
 * Created by uyminhduc on 10/16/16.
 */

public interface OtherRepository {
    Observable<String> getDate();
}
