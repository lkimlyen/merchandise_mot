package com.demo.merchandisemot.app.base;

/**
 * Created by uyminhduc on 10/16/16.
 */

public interface iBaseView {
    void showToast(String desc);

    void unKnownError();

    void showProgressDialog();

    void hideProgressDialog();
}
