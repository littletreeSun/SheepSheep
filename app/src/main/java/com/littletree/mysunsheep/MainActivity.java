package com.littletree.mysunsheep;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.littletree.mysunsheep.customview.GrassView;
import com.littletree.mysunsheep.customview.NoFastClickListener;
import com.littletree.mysunsheep.customview.PlayVoice;
import com.littletree.mysunsheep.databinding.ActivityMainBinding;
import com.littletree.mysunsheep.utils.PUtil;
import com.tencent.mmkv.MMKV;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    List<ObjectAnimator> animList;

    private List<GrassView> GrassViewList;  //草list
    private List<Integer[]> mGrasslocationList;  //草坐标列表
    private int grassNum = 28;  //草的数量

    private List<Integer> awardSheepRescoureList; //通关羊gif
    private Intent intent;
    private int listSucceednum;  //通关羊数量

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        MMKV.initialize(this);
        GrassViewList = new ArrayList<>();
        mGrasslocationList = new ArrayList<>();
        animList = new ArrayList<>();
        awardSheepRescoureList = new ArrayList<>();
        initAwardSheepRescoure();
        initGrassView();
        inittitle();
        initBeepSound();
        binding.TvStart.setOnClickListener(new NoFastClickListener() {
            @Override
            protected void onSingleClick() {
                startActivity(new Intent(MainActivity.this,GameActivity.class));
            }
        });

        //通关在首页增加一只
        LiveEventBus.get("succeed", Boolean.class)
                .observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean bo) {
                        listSucceednum = listSucceednum+1;
                        ImageView iv = new ImageView(MainActivity.this);
                        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(PUtil.dip2px(MainActivity.this, 60), PUtil.dip2px(MainActivity.this, 60));
                        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                        layoutParams.leftMargin = (int) (PUtil.getScreenW(MainActivity.this)/2 - PUtil.dip2px(MainActivity.this, 50) - Math.random()*PUtil.dip2px(MainActivity.this, 160) + PUtil.dip2px(MainActivity.this, 80));
                        layoutParams.bottomMargin = (int) (PUtil.getScreenH(MainActivity.this)/5 - Math.random()*PUtil.dip2px(MainActivity.this, 60) + PUtil.dip2px(MainActivity.this, 30));
                        iv.setLayoutParams(layoutParams);
                        binding.rl.addView(iv);

                        Glide.with(MainActivity.this)
                                .load(awardSheepRescoureList.get(listSucceednum-1))
                                .into(iv);
                    }
                });

        //游戏页面onresume
        LiveEventBus.get("GameOnresume", Boolean.class)
                .observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean boo) {
                        intent.putExtra("action","resume");
                        startService(intent);
                    }
                });
    }

    private void initGrassView(){
        for (int i = 0; i < grassNum; i++) {
            addGrassLocation(mGrasslocationList, 0);
        }
        for (Integer[] integers : mGrasslocationList) {
            GrassView grassView = new GrassView(this);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(PUtil.dip2px(this, 30), PUtil.dip2px(this, 30));
            layoutParams.leftMargin = integers[0];
            layoutParams.topMargin = integers[1];
            grassView.setLayoutParams(layoutParams);
            GrassViewList.add(grassView);
            binding.rl.addView(grassView);
        }

        //抖动
        for (GrassView grassView : GrassViewList) {
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_grass);
            animation.setAnimationListener(new ReStartAnimationListener());
            grassView.startAnimation(animation);
        }
    }

    private void inittitle(){
        ImageView ivTitle = new ImageView(MainActivity.this);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(PUtil.dip2px(this,400), PUtil.dip2px(this,240));
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        layoutParams.topMargin = PUtil.dip2px(this,40);
        ivTitle.setLayoutParams(layoutParams);
        binding.rl.addView(ivTitle);

        Glide.with(this)
                .load(R.mipmap.ic_title)
                .into(ivTitle);
    }

    private void initBeepSound() {
        // 启动服务播放背景音乐
        intent = new Intent(this,MyIntentService.class);
        intent.putExtra("action","play");
        startService(intent);
    }

    private void startAnim(){
        for (int i = 0; i < 7; i++) {
            int size = 0;
            int recoures = 0;
            int runTime = 0;
            switch (i){
                case 0:
                    size = 220;
                    recoures = R.mipmap.ic_gif1;
                    runTime = 8000;
                    break;
                case 1:
                    size = 80;
                    recoures = R.mipmap.ic_gif2;
                    runTime = 7000;
                    break;
                case 2:
                    size = 80;
                    recoures = R.mipmap.ic_gif3;
                    runTime = 7000;
                    break;
                case 3:
                    size = 100;
                    recoures = R.mipmap.ic_gif4;
                    runTime = 7000;
                    break;
                case 4:
                    size = 130;
                    recoures = R.mipmap.ic_gif5;
                    runTime = 6000;
                    break;
                case 5:
                    size = 110;
                    recoures = R.mipmap.ic_gif6;
                    runTime = 6000;
                    break;
                case 6:
                    size = 110;
                    recoures = R.mipmap.ic_gif7;
                    runTime = 6000;
                    break;
            }

            ImageView iv = new ImageView(MainActivity.this);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(PUtil.dip2px(this, size), PUtil.dip2px(this, size));
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            layoutParams.leftMargin = -PUtil.dip2px(this, size);
            layoutParams.bottomMargin = PUtil.getScreenH(this)/2-PUtil.dip2px(this,220/2);
            iv.setLayoutParams(layoutParams);
            binding.rl.addView(iv);

            Glide.with(this)
                    .load(recoures)
                    .into(iv);

            ObjectAnimator animator = ObjectAnimator.ofFloat(iv, "translationX", -PUtil.dip2px(this, size), PUtil.getScreenW(this)+PUtil.dip2px(this, size)*2);
            animator.setDuration(runTime);
            animator.setInterpolator(new LinearInterpolator());
            animator.setRepeatCount(ValueAnimator.INFINITE);
            animator.setRepeatMode(ValueAnimator.RESTART);
            animator.setStartDelay(i *300);

            animator.start();

            animList.add(animator);
        }

        //以获取的战利品羊gif
        listSucceednum = MMKV.defaultMMKV().decodeInt("specialSheepListNum");
        if (listSucceednum>0){
            List<Integer> mList = new ArrayList<>();
            for (int i = 0; i < listSucceednum; i++) {
                mList.add(awardSheepRescoureList.get(i));
            }
            for (Integer recoures : mList) {
                ImageView iv = new ImageView(MainActivity.this);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(PUtil.dip2px(this, 60), PUtil.dip2px(this, 60));
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                layoutParams.leftMargin = (int) (PUtil.getScreenW(this)/2 - PUtil.dip2px(this, 50) - Math.random()*PUtil.dip2px(this, 160) + PUtil.dip2px(this, 80));
                layoutParams.bottomMargin = (int) (PUtil.getScreenH(this)/5 - Math.random()*PUtil.dip2px(this, 60) + PUtil.dip2px(this, 30));
                iv.setLayoutParams(layoutParams);
                binding.rl.addView(iv);

                Glide.with(this)
                        .load(recoures)
                        .into(iv);
            }
        }
    }

    private void addGrassLocation(List<Integer[]> list,int repeatNum){  //判断是否添加成功，如果大于100次循环就强制结束
        Integer[] mGrasslocation = {(int) (Math.round(Math.random() * PUtil.getScreenW(this))),(int) (Math.round(Math.random() * (PUtil.getScreenH(this) - PUtil.dip2px(this,90))))};
        //判断是否有重复
        boolean isRepeat = false;
        for (Integer[] integers : list) {
            if (Math.abs(mGrasslocation[0]-integers[0])<PUtil.dip2px(this, 30)
                    &&Math.abs(mGrasslocation[1]-integers[1])<PUtil.dip2px(this, 30)){
                isRepeat = true;
                break;
            }
        }

        if (!isRepeat){
            list.add(mGrasslocation);
        }else {
            repeatNum = repeatNum + 1;
            if (repeatNum>100){
            }else {
                addGrassLocation(list,repeatNum);
            }
        }
    }

    private class ReStartAnimationListener implements Animation.AnimationListener {
        public void onAnimationEnd(Animation animation) {
            animation.reset();
            animation.setAnimationListener(new ReStartAnimationListener());
            animation.start();
        }

        public void onAnimationRepeat(Animation animation) {

        }

        public void onAnimationStart(Animation animation) {

        }

    }

    private void initAwardSheepRescoure(){
        awardSheepRescoureList.add(R.mipmap.ic_award_sheep2);
        awardSheepRescoureList.add(R.mipmap.ic_award_sheep3);
        awardSheepRescoureList.add(R.mipmap.ic_award_sheep4);
        awardSheepRescoureList.add(R.mipmap.ic_award_sheep5);
        awardSheepRescoureList.add(R.mipmap.ic_award_sheep6);
        awardSheepRescoureList.add(R.mipmap.ic_award_sheep7);
        awardSheepRescoureList.add(R.mipmap.ic_award_sheep8);
        awardSheepRescoureList.add(R.mipmap.ic_award_sheep9);
        awardSheepRescoureList.add(R.mipmap.ic_award_sheep10);
        awardSheepRescoureList.add(R.mipmap.ic_award_sheep11);
        awardSheepRescoureList.add(R.mipmap.ic_award_sheep12);
        awardSheepRescoureList.add(R.mipmap.ic_award_sheep13);
        awardSheepRescoureList.add(R.mipmap.ic_award_sheep14);
        awardSheepRescoureList.add(R.mipmap.ic_award_sheep15);
        awardSheepRescoureList.add(R.mipmap.ic_award_sheep16);
        awardSheepRescoureList.add(R.mipmap.ic_award_sheep17);
        awardSheepRescoureList.add(R.mipmap.ic_award_sheep18);
        awardSheepRescoureList.add(R.mipmap.ic_award_sheep19);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("孙", "onStop: ");
        for (ObjectAnimator objectAnimator : animList) {
            objectAnimator.pause();
        }
        intent.putExtra("action","pause");
        startService(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("孙", "onResume: ");
        if (animList.size()>0){
            for (ObjectAnimator objectAnimator : animList) {
                objectAnimator.resume();
            }
        }else {
            startAnim();
        }
        intent.putExtra("action","resume");
        startService(intent);
    }

    @Override
    protected void onDestroy() {
        for (ObjectAnimator objectAnimator : animList) {
            objectAnimator.cancel();
        }
        for (GrassView grassView : GrassViewList) {
            grassView.getAnimation().cancel();
        }
        //停止服务
        stopService(intent);

        super.onDestroy();
    }
}