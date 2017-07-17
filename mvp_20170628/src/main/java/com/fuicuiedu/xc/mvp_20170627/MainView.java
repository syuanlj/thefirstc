package com.fuicuiedu.xc.mvp_20170627;

import java.util.List;

/**
 * 作者：王小超
 * 邮箱：wxcican@qq.com
 */

public interface MainView {

    //progressbar 显示
    void showPrb();
    //progressbar 隐藏
    void hidePrb();
    //setData 设置内容（改变ListView，刷新）
    void setData(List<String> datas);
}
