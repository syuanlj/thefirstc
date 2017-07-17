package com.fuicuiedu.xc.mvp_20170627.register;

/**
 * 作者：王小超
 * 邮箱：wxcican@qq.com
 */

public interface RegisterView {

    //progressbar显示和隐藏
    void showPrb();

    void hidePrb();

    //toast 显示注册提示信息
    void showMsg(String msg);
}
