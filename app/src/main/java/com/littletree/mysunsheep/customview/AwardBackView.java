package com.littletree.mysunsheep.customview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

/**
 * @ProjectName: MySunSheep
 * @Package: com.littletree.mysunsheep.customview
 * @ClassName: AwardSheep
 * @Author: littletree
 * @CreateDate: 2022/11/4/004 11:37
 * 奖品羊 背景view
 */
public class AwardBackView extends View {
    private int mMeasureHeight;
    private int mMeasureWidth;

    private float startAngle;
    private ValueAnimator valueAnimator;

    private int colorStart = Color.parseColor("#f9f7a9");
    private int colorCenter = Color.parseColor("#c3ba3d");
    private int colorEnd9 = Color.parseColor("#e6c3ba3d");
    private int colorEnd8 = Color.parseColor("#dac3ba3d");
    private int colorEnd7 = Color.parseColor("#cdc3ba3d");
    private int colorEnd6 = Color.parseColor("#c0c3ba3d");
    private int colorEnd5 = Color.parseColor("#99c3ba3d");
    private int colorEnd4 = Color.parseColor("#60c3ba3d");
    private int colorEnd3 = Color.parseColor("#40c3ba3d");
    private int colorEnd2 = Color.parseColor("#25c3ba3d");
    private int colorEnd1 = Color.parseColor("#15c3ba3d");

    private Paint bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
    private Paint BlackPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
    private RectF pRectF;

    public AwardBackView(Context context) {
        this(context,null);
    }

    public AwardBackView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mMeasureWidth = getMeasuredWidth();
        mMeasureHeight = getMeasuredHeight();

        if (pRectF == null) {
            pRectF = new RectF(0, 0, mMeasureWidth, mMeasureHeight);}

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        BlackPaint.setColor(Color.BLACK);
        RadialGradient radialGradient = new RadialGradient(
                mMeasureWidth/2, mMeasureHeight/2,
                Math.min(mMeasureWidth/2, mMeasureHeight/2),
                new int[]{colorStart, colorCenter,colorEnd9,colorEnd8,colorEnd7,colorEnd6,colorEnd5,colorEnd4,colorEnd3,colorEnd2,colorEnd1}, null,
                Shader.TileMode.MIRROR
        );

        bgPaint.setShader(radialGradient);
        canvas.drawCircle(mMeasureWidth/2, mMeasureHeight/2, Math.min(mMeasureWidth/2, mMeasureHeight/2), bgPaint);

        RectF rf = new RectF(0, 0, mMeasureWidth, mMeasureHeight);
        for (int i = 0; i < 18; i++) {
            canvas.drawArc(rf, startAngle+i*20 , 10, true, BlackPaint);
        }
    }

    public void startAnim(){
        valueAnimator = ValueAnimator.ofFloat(0f, 360f);
        valueAnimator.setDuration(5000);
        valueAnimator.setInterpolator(new LinearInterpolator());  //线性变化
        valueAnimator.setRepeatCount(100);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedValue = (float) valueAnimator.getAnimatedValue();
                startAngle = animatedValue;
                postInvalidate();
            }
        });

        valueAnimator.start();
    }

    public void clearAnimator(){
        if (null!=valueAnimator){
            valueAnimator.cancel();
        }
    }
}
