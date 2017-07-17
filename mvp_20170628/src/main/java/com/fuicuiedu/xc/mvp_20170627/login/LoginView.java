package com.fuicuiedu.xc.mvp_20170627.login;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * 作者：王小超
 * 邮箱：wxcican@qq.com
 */

public interface LoginView extends MvpView{

    void showPrb();
    void hidePrb();
    void showMsg(String msg);
}
