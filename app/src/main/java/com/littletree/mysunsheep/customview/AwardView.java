package com.littletree.mysunsheep.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.littletree.mysunsheep.R;

/**
 * @ProjectName: MySunSheep
 * @Package: com.littletree.mysunsheep.customview
 * @ClassName: AwardView
 * @Author: littletree
 * @CreateDate: 2022/11/4/004 15:10
 * 奖励羊
 */
public class AwardView extends FrameLayout {
    AwardBackView awardBackView;
    ImageView iv_award;
    Context mcontext;

    public AwardView(@NonNull Context context) {
        this(context,null);
    }

    public AwardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mcontext = context;
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        View view_awardSheep = inflate(context, R.layout.layout_awardsheep, this);
        awardBackView = view_awardSheep.findViewById(R.id.backview_award);

        iv_award = view_awardSheep.findViewById(R.id.iv);
        iv_award.setVisibility(GONE);

        view_awardSheep.post(new Runnable() {
            @Override
            public void run() {
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(view_awardSheep.getWidth() / 2, view_awardSheep.getHeight() / 2);
                layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                iv_award.setLayoutParams(layoutParams);
                iv_award.setVisibility(VISIBLE);
            }
        });
    }

    public void setRescoure(int rescoure){
        Glide.with(mcontext)
                .load(rescoure)
                .into(iv_award);
    }

    public void startAnim(){
        awardBackView.startAnim();
    }

    public void cancelAnim(){
        awardBackView.clearAnimator();
    }
}
