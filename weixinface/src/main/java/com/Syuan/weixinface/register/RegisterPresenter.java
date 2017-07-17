package com.Syuan.weixinface.register;

import android.os.AsyncTask;

/**
 * @auther sy on 2017/7/5 21:33.
 * @Email 893110793@qq.com
 */

public class RegisterPresenter {
    //点击注册后，延迟三秒模拟网络请求，吐司注册成功

    RegisterModel registerModel;

    public RegisterPresenter(RegisterModel registerModel) {
        this.registerModel=registerModel;
    }

    public void register(){
        registerModel.showProb();
        new MyAsyncTask().execute();
    }

    class MyAsyncTask extends AsyncTask<Void,Void,Void>{



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
            registerModel.hideProb();
            registerModel.toast("注册成功");
        }
    }
}
