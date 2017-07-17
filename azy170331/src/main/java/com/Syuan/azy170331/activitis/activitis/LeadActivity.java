package com.Syuan.azy170331.activitis.activitis;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.Syuan.azy170331.R;
import com.Syuan.azy170331.activitis.adaper.MyPagerAdaper;

public class LeadActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener, View.OnClickListener {
    private ViewPager vp;
    private RadioGroup rg;//单选属性
    private RadioButton rb_0, rb_1, rb_2, rb_3, rb_4;
    private TextView tv_jump;
    private MyPagerAdaper adapter;  //声明一个MyAdaper类对象
    private SharedPreferences sp;
    private  boolean isSetActivity=false;//用于判断是否是从设置界面跳转过来的

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead);
        init();

    }


    /**
     * 对象初始化
     */
    private void init() {
        isSetActivity=getIntent().getBooleanExtra("isSetActivity",false);
        sp = getSharedPreferences("ConfigInfo", MODE_PRIVATE);
        boolean isFirstRun = sp.getBoolean("isFirstRun", true);
        //如果是设置界面跳转而来，让isFirstRun为true，展示引导界面
        if (isSetActivity){
            isFirstRun=true;
        }
        if (isFirstRun) {
            SharedPreferences.Editor editor = sp.edit();
            //保存数据
            editor.putBoolean("isFirstRun", false);
//            editor.putString("name","zhangsan");
//            editor.putString("password","qwerdf");
            editor.commit();//关流
        } else {
            startActivity(new Intent(this, LogoActivity.class));
            finish();//结束当前运行的activity
        }

        vp = (ViewPager) findViewById(R.id.vp);
        rg = (RadioGroup) findViewById(R.id.rg);
        rb_0 = (RadioButton) findViewById(R.id.rb_0);
        rb_1 = (RadioButton) findViewById(R.id.rb_1);
        rb_2 = (RadioButton) findViewById(R.id.rb_2);
        rb_3 = (RadioButton) findViewById(R.id.rb_3);
        rb_4 = (RadioButton) findViewById(R.id.rb_4);
        tv_jump = (TextView) findViewById(R.id.tv_jump);
        adapter = new MyPagerAdaper();

        ImageView iv_0 = new ImageView(this);       //实例化ImageView对象
        iv_0.setImageResource(R.mipmap.pic_0);      //调用setImageResource()方法：设置图片
        adapter.addView(iv_0);//向适配器添加数据

        ImageView iv_1 = new ImageView(this);
        iv_1.setImageResource(R.mipmap.pic_1);
        adapter.addView(iv_1);

        ImageView iv_2 = new ImageView(this);
        iv_2.setImageResource(R.mipmap.pic_2);
        adapter.addView(iv_2);

        ImageView iv_3 = new ImageView(this);
        iv_3.setImageResource(R.mipmap.pic_3);
        adapter.addView(iv_3);

        ImageView iv_4 = new ImageView(this);
        iv_4.setImageResource(R.mipmap.pic_4);
        adapter.addView(iv_4);

        adapter.notifyDataSetChanged();//提示系统刷新适配器数据

        vp.setAdapter(adapter);//关联适配器
        rg.setOnCheckedChangeListener(this);//RadioGroup设置监听

        vp.setOnPageChangeListener(this);//设置监听ViewPAge
        tv_jump.setOnClickListener(this);

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.rb_0:
                vp.setCurrentItem(0);
                break;
            case R.id.rb_1:
                vp.setCurrentItem(1);
                break;
            case R.id.rb_2:
                vp.setCurrentItem(2);
                break;
            case R.id.rb_3:
                vp.setCurrentItem(3);
                break;
            case R.id.rb_4:
                vp.setCurrentItem(4);
                break;
        }
    }


    /**
     * 界面滑动
     *
     * @param position             位置
     * @param positionOffset
     * @param positionOffsetPixels
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    /**
     * 界面选中
     *
     * @param position 当前页面下标
     */

    @Override
    public void onPageSelected(int position) {
        switch (position) {//RadioButton 只能选择一个，其他的会默认为false
            case 0:
                rb_0.setChecked(true);//true表示选中，false表示没选中
                tv_jump.setVisibility(View.INVISIBLE);
                break;
            case 1:
                rb_1.setChecked(true);//true表示选中，false表示没选中
                tv_jump.setVisibility(View.INVISIBLE);
                break;
            case 2:
                rb_2.setChecked(true);//true表示选中，false表示没选中
                tv_jump.setVisibility(View.INVISIBLE);
                break;
            case 3:
                rb_3.setChecked(true);//true表示选中，false表示没选中
                tv_jump.setVisibility(View.INVISIBLE);
                break;
            case 4:
                rb_4.setChecked(true);//true表示选中，false表示没选中
                tv_jump.setVisibility(View.VISIBLE);
                break;
        }

    }


    /**
     * 界面滑动状态改变
     *
     * @param
     */
    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_jump:
                if (!isSetActivity)
                startActivity(new Intent(this, LogoActivity.class));
                finish();
                break;
        }
    }
}
