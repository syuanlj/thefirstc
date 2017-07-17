package com.Syuan.azy170331.activitis.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by sy on 2017/4/20.
 */

public class DarwCircle extends View {
    private int width;
    private int height;
    private RectF oval;
    private float startAngle = -90;
    private float sweepAngle = 360;
    private Paint paint;
    public DarwCircle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.rgb(255,140,0));
    }

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
        canvas.drawArc(oval,startAngle,sweepAngle,false,paint);
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
