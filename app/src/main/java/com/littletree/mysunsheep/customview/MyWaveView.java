package com.littletree.mysunsheep.customview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

import com.littletree.mysunsheep.R;
import com.littletree.mysunsheep.utils.PUtil;

/**
 * @ProjectName: MySunSheep
 * @Package: com.littletree.mysunsheep.customview
 * @ClassName: MyWaveView
 * @Author: littletree
 * @CreateDate: 2022/10/26/026 11:19
 * 开始游戏 按钮
 */
public class MyWaveView extends View {
    private Context mContext;
    private String getText;
    private float getTextSize;
    private int getColor;

    private Paint textPaint;
    public Paint wavePaint;

    //组件的宽和高
    private float mOriginalViewWidth;
    private float mOriginalViewHeight;

    private float mViewWidth;
    private float mViewHeight;

    //波浪的轨迹和画笔
    private Path wavePath1;
    private Path wavePath2;
    private Path wavePath3;
    private Path wavePath4;

    private float mLevelHeight;//水位线高度,改变这个值即可以模拟出波浪上下动的效果(这个是外界控制的)
    private float mLevelWidth;

    public MyWaveView(Context context) {
        this(context,null);
    }

    public MyWaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MyWaveView,0,0);
        getText = typedArray.getString(R.styleable.MyWaveView_text);
        getTextSize = typedArray.getDimension(R.styleable.MyWaveView_textSize,14f);
        getColor = typedArray.getColor(R.styleable.MyWaveView_color, Color.parseColor("#333333"));
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mOriginalViewWidth = getMeasuredWidth();
        mOriginalViewHeight = getMeasuredHeight();
        mLevelHeight = 16f;

        //单个波浪宽调整（为了整除）
        mLevelWidth = 60f;
        int num1 = (int) ((mOriginalViewWidth- mLevelHeight *2)/mLevelWidth);
        mLevelWidth = (mOriginalViewWidth- mLevelHeight *2)/num1;
        mViewWidth = mOriginalViewWidth;

        int num2 = (int) ((mOriginalViewHeight - mLevelHeight *2)/mLevelWidth);
        mViewHeight = num2 * mLevelWidth + mLevelHeight *2;
        init();
    }

    private void init(){
        textPaint = new Paint();
        wavePaint = new Paint();

        textPaint.setColor(getColor);
        textPaint.setStrokeWidth(2f);
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setTextSize(getTextSize);

        wavePaint.setAntiAlias(true);
        wavePaint.setColor(getColor);
        wavePaint.setStrokeWidth(12f);
        wavePaint.setStyle(Paint.Style.STROKE);

        wavePath1 = new Path(); //上
        wavePath1.moveTo( mLevelHeight ,mLevelHeight );
        int num_horizontal = (int) ((mViewWidth- mLevelHeight *2)/mLevelWidth);
        for (int i = 0; i < num_horizontal; i++) {
            wavePath1.quadTo( mLevelHeight+mLevelWidth/2+mLevelWidth*i ,0,mLevelHeight+mLevelWidth*(i+1),mLevelHeight);
        }

        wavePath2 = new Path();  //右
        wavePath2.moveTo( mViewWidth-mLevelHeight ,mLevelHeight );
        int num_vertical = (int) ((mViewHeight - mLevelHeight *2)/mLevelWidth);
        for (int i = 0; i < num_vertical; i++) {
            wavePath2.quadTo( mViewWidth ,mLevelHeight+mLevelWidth/2+mLevelWidth*i,mViewWidth-mLevelHeight,mLevelHeight+mLevelWidth*(i+1));
        }

        wavePath3 = new Path(); //下
        wavePath3.moveTo( mLevelHeight ,mViewHeight - mLevelHeight );
        for (int i = 0; i < num_horizontal; i++) {
            wavePath3.quadTo( mLevelHeight+mLevelWidth/2+mLevelWidth*i ,mViewHeight,mLevelHeight+mLevelWidth*(i+1),mViewHeight - mLevelHeight);
        }

        wavePath4 = new Path();  //左
        wavePath4.moveTo( mLevelHeight ,mLevelHeight );
        for (int i = 0; i < num_vertical; i++) {
            wavePath4.quadTo( 0 ,mLevelHeight+mLevelWidth/2+mLevelWidth*i,mLevelHeight,mLevelHeight+mLevelWidth*(i+1));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //文字处于控件中心
        float strWidth = textPaint.measureText(getText);
        canvas.drawText(getText,(mViewWidth-strWidth)/2, (mOriginalViewHeight)/2,textPaint);

        canvas.drawPath(wavePath1,wavePaint);
        canvas.drawPath(wavePath2,wavePaint);
        canvas.drawPath(wavePath3,wavePaint);
        canvas.drawPath(wavePath4,wavePaint);
    }
}
