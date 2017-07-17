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

public class ClearView extends View {
    private Paint paint;
    private RectF oval;
    private float startAngle = -90;
    private float sweepAngle = 360;
    private int width;
    private int height;
    private Context context;//上下文
    public static final int STATE_BACKWARD = 0;//后退状态
    public static final int STATE_FORWARD = 1;//前进状态
    private int currentState = 0;//当前状态
    private boolean isCrazy = false;
    private Timer timer;
    private float speedBackwardIndex = 1, speedForwardIndex = 20;
    private ScoreInterface scoreInterface;


    public ClearView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {

        paint = new Paint();
        paint.setColor(Color.RED);
    }

    /**
     * 接受来自Activity传递的接口对象
     * @param scoreInterface
     */
    public void setScoreInterface(ScoreInterface scoreInterface){
        this.scoreInterface=scoreInterface;
    }
    public void setAngle(final float angle) {
        if (isCrazy) {
            Toast.makeText(context, "Are you crazy?", Toast.LENGTH_SHORT).show();
            return;
        }
        timer = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                switch (currentState) {
                    //后退状态
                    case STATE_BACKWARD:
                        isCrazy = true;
                        sweepAngle -= speedBackwardIndex;
                        if (speedBackwardIndex <= 20) {
                            speedBackwardIndex += 0.5;
                        } else {
                            speedBackwardIndex = 20;
                        }
                        if (sweepAngle <= 0) {
                            sweepAngle = 0;
                            currentState = STATE_FORWARD;
                            postInvalidate();
                        }
                        break;

                    //前进状态
                    case STATE_FORWARD:
                        sweepAngle += speedForwardIndex;
                        if (speedForwardIndex >= 1) {
                            speedForwardIndex -= 0.5;
                        } else {
                            speedForwardIndex = 5;
                        }
                        if (sweepAngle >= angle) {
                            isCrazy = false;
                            sweepAngle = angle;
                            currentState = STATE_BACKWARD;
                            speedBackwardIndex = 1;
                            speedForwardIndex = 20;
                            cancel();//取消TimerTask
                            postInvalidate();
                        }
                        break;
                }
                scoreInterface.getScore( sweepAngle*100/360);
                postInvalidate();//非UI线程:实现View更新
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
        setMeasuredDimension(width, height);
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
        canvas.drawArc(oval, startAngle, sweepAngle, true, paint);
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
