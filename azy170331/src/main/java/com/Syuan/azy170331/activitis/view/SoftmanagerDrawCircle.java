package com.Syuan.azy170331.activitis.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import com.Syuan.azy170331.activitis.myInterface.ScoreInterface;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by sy on 2017/4/7.
 */

public class SoftmanagerDrawCircle extends View {
    private Paint paint;
    private RectF oval;
    private float startAngle = -90;
    private float sweepAngle = 360;//背景
    private float inSweepAngle = 0;//外置存储
    private float outSweepAngle = 0;//内置存储
    private int width;
    private int height;
    private Context context;//上下文
    private Timer timer;


    public SoftmanagerDrawCircle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);//抗锯齿（磨边）

    }

    public void setAngle(final float inAngle, final float outAngle) {
        timer = new Timer();
        final TimerTask task = new TimerTask() {
            @Override
            public void run() {
                //背景

                //内存储画圆

                if (inSweepAngle >= inAngle) {
                    inSweepAngle = inAngle;
                }else {
                    inSweepAngle += 3;
                }
                //外存储画圆

                if (outSweepAngle>= outAngle) {
                    outSweepAngle = outAngle;
                }else {
                    outSweepAngle += 3;
                }

                //取消线程
                if (inSweepAngle == inAngle&&outSweepAngle ==outAngle){
                    cancel();
                }
                postInvalidate();//非UI线程
//                invalidate();//UI线程
            }
        };
        timer.schedule(task, 0, 30);
    }

    /**
     * 测量屏幕宽高
     *
     * @param heightMeasureSpec
     * @param widthMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);

        //设置控件宽高
        setMeasuredDimension(width, height);//保存宽高
        oval = new RectF(0, 0, width, height);
    }

    /**
     * 绘制视图
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.rgb(117,119,117));//#757775
        canvas.drawArc(oval, startAngle, sweepAngle, true, paint);//背景圆
        paint.setColor(Color.BLUE);
        canvas.drawArc(oval, startAngle, inSweepAngle, true, paint);//内存储圆
        paint.setColor(Color.GREEN);
        canvas.drawArc(oval, startAngle+inSweepAngle, outSweepAngle, true, paint);//外存储圆
    }

    /**
     * 将准备好的控件放到屏幕中指定的位置
     *
     * @param changed
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

}
