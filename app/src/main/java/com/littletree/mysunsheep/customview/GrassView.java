package com.littletree.mysunsheep.customview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

import com.littletree.mysunsheep.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: MySunSheep
 * @Package: com.littletree.mysunsheep.customview
 * @ClassName: GrassView
 * @Author: littletree
 * @CreateDate: 2022/11/1/001 14:43
 * 会动的小草
 */
public class GrassView extends View {
    private Paint mPaint;

    List<PointF> points = new ArrayList<>();
    private ValueAnimator anim;

    public GrassView(Context context) {
        this(context,null);
    }

    public GrassView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPoints(points);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#639332"));
        mPaint.setAntiAlias(true);//抗锯齿效果
        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.STROKE);

        int num = (int) (Math.round(Math.random() * 3));
        Bitmap mBitmap = null;
        switch (num){
            case 0:
                mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_grass1);
                break;
            case 1:
                mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_grass2);
                break;
            case 2:
                mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_grass3);
                break;
            case 3:
                mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_grass5);
                break;
        }
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(mBitmap, 60, 60, true);
        canvas.drawBitmap(scaledBitmap, 0f, 0f, mPaint);

//        PointF pStart, pEnd;
//
//        Path path = new Path();
//        for (int i = 0; i < points.size() - 1; i++) {
//            pStart = points.get(i);
//            pEnd = points.get(i + 1);
//            PointF point3 = new PointF();
//            PointF point4 = new PointF();
//            float wd = (pStart.x + pEnd.x) / 2;
//            point3.x = wd;
//            point3.y = pStart.y;
//            point4.x = wd;
//            point4.y = pEnd.y;
//            path.moveTo(pStart.x, pStart.y);
//            path.cubicTo(point3.x, point3.y, point4.x, point4.y, pEnd.x, pEnd.y);
//            canvas.drawPath(path, mPaint);
//        }
    }

    private void initPoints(List<PointF> pointList){
        int num = (int) (Math.round(Math.random() * 4));
        if (num == 0){
            pointList.add(new PointF(0,50));
            pointList.add(new PointF(32,25));
            pointList.add(new PointF(48,40));
            pointList.add(new PointF(64,30));
            pointList.add(new PointF(96,50));
        }else if (num == 1){
            pointList.add(new PointF(0,80));
            pointList.add(new PointF(16,25));
            pointList.add(new PointF(32,70));
            pointList.add(new PointF(40,50));
            pointList.add(new PointF(48,80));
        }else if (num == 2){
            pointList.add(new PointF(0,80));
            pointList.add(new PointF(8,65));
            pointList.add(new PointF(16,70));
            pointList.add(new PointF(24,65));
            pointList.add(new PointF(32,70));
            pointList.add(new PointF(40,25));
            pointList.add(new PointF(48,70));
            pointList.add(new PointF(56,65));
            pointList.add(new PointF(64,70));
            pointList.add(new PointF(72,65));
            pointList.add(new PointF(80,70));
        }else if (num == 3){
            pointList.add(new PointF(0,80));
            pointList.add(new PointF(16,50));
            pointList.add(new PointF(32,70));
            pointList.add(new PointF(40,25));
            pointList.add(new PointF(48,80));
        }else if (num == 4){
            pointList.add(new PointF(16,80));
            pointList.add(new PointF(0,30));
            pointList.add(new PointF(8,25));
            pointList.add(new PointF(32,80));
            pointList.add(new PointF(45,55));
            pointList.add(new PointF(48,60));
            pointList.add(new PointF(40,80));
        }
    }
}
