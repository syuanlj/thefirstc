package com.fuicuiedu.xc.mvp_20170627.login;

import android.os.AsyncTask;

import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;

/**
 * 作者：王小超
 * 邮箱：wxcican@qq.com
 */

public class LoginPresenter extends MvpNullObjectBasePresenter<LoginView>{

    public void login(){
        //显示加载动画
        getView().showPrb();
        //异步任务，登录操作
        new LoginAyncTask().execute();
    }

    class LoginAyncTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //提示用户
            getView().showMsg("登录成功");
            //隐藏prb
            getView().hidePrb();
        }
    }
}
