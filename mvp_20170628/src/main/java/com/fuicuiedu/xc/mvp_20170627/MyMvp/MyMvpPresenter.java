package com.fuicuiedu.xc.mvp_20170627.MyMvp;

/**
 * 作者：王小超
 * 邮箱：wxcican@qq.com
 */

//需要拿到视图接口

public class MyMvpPresenter<V extends MyMvpView> {

        private V v;


        //经常要用到View接口，所以写在父类方法，方便调用
        public V getView(){
            if (v == null){
                //抛异常
            }
            return v;
        }

}
