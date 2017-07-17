package com.fuicuiedu.xc.mvp_20170627.register;

import android.os.AsyncTask;

/**
 * 作者：王小超
 * 邮箱：wxcican@qq.com
 */

public class RegisterPresenter {

    private RegisterView registerView;

    public RegisterPresenter(RegisterView registerView){
        this.registerView = registerView;
    }

    public void register(){
        //显示加载动画
        registerView.showPrb();
        new MyAsyncTash().execute();
    }


    class MyAsyncTash extends AsyncTask<Void,Void,Void>{
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
            registerView.showMsg("注册成功");
            registerView.hidePrb();
        }
    }
}
