package com.littletree.mysunsheep;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.bumptech.glide.Glide;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.littletree.mysunsheep.customview.AwardView;
import com.littletree.mysunsheep.customview.NoFastClickListener;
import com.littletree.mysunsheep.customview.SheepView;
import com.littletree.mysunsheep.databinding.ActivityGameBinding;
import com.littletree.mysunsheep.dialog.Dialog_pass;
import com.littletree.mysunsheep.dialog.Dialog_succeed;
import com.littletree.mysunsheep.utils.PUtil;
import com.tencent.mmkv.MMKV;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @ProjectName: MySunSheep
 * @Package: com.littletree.mysunsheep
 * @ClassName: GameActivity
 * @Author: littletree
 * @CreateDate: 2022/10/26/026 10:42
 */
public class GameActivity extends AppCompatActivity {
    ActivityGameBinding binding;
    TextView tvTitle;

    //过关dialog
    Dialog_pass dialog_pass;
    Dialog_pass.Builder dialog_passbuilder;
    TextView dialog_nolivetitle;
    TextView dialog_nolive_btn_one;
    ImageView iv;

    //通关dialog
    Dialog_succeed dialog_succeed;
    Dialog_succeed.Builder dialog_succeedbuilder;
    AwardView view_award;
    List<Integer> awardSheepRescoureList;

    private int barrierNum = 1;  //关卡
    SheepView sheepView;

    int clicknum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_game);
        initdialog();
        initview();
        inittitle();
    }

    private void initview(){
        sheepView = new SheepView(GameActivity.this, barrierNum);
        binding.rl.addView(sheepView);

        sheepView.setMySheepListener(new SheepView.MySheepListener() {
            @Override
            public void succeedListener() {
                if (1 == barrierNum){
                    dialog_nolivetitle.setText("过关");
                    dialog_nolive_btn_one.setText("进入下一关");
                    barrierNum = barrierNum + 1;

                    Glide.with(GameActivity.this)
                            .load(R.mipmap.ic_succeed)
                            .into(iv);

                    dialog_nolive_btn_one.setOnClickListener(new NoFastClickListener() {
                        @Override
                        protected void onSingleClick() {
                            dialog_pass.dismiss();
                            tvTitle.setText("第二关");
                            sheepView.setBarrierNum(barrierNum,0);
                        }
                    });

                    dialog_pass.show();
                }else {
                    //通关
                    view_award.startAnim();

                    int listnum = MMKV.defaultMMKV().decodeInt("specialSheepListNum");
                    if (listnum == 18){
                        Toast.makeText(GameActivity.this, "你已收集完所有类别", Toast.LENGTH_SHORT).show();
                        listnum = 17;
                    }
                    MMKV.defaultMMKV().encode("specialSheepListNum", listnum+1);

                    view_award.setRescoure(awardSheepRescoureList.get(listnum));
                    //随机gif
                    dialog_succeed.show();
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            mHandler.sendEmptyMessage(0X123);
                        }
                    },4000);

                }

            }

            @Override
            public void failListener() {
                dialog_nolivetitle.setText("你失败了 再接再厉");
                dialog_nolive_btn_one.setText("重新开始");
                Glide.with(GameActivity.this)
                        .load(R.mipmap.ic_fail)
                        .into(iv);

                dialog_nolive_btn_one.setOnClickListener(new NoFastClickListener() {
                    @Override
                    protected void onSingleClick() {
                        dialog_pass.dismiss();
                        sheepView.setBarrierNum(barrierNum,0);
                    }
                });
                dialog_pass.show();
            }

        });

        LiveEventBus.get("plieNum", Integer.class)
                .observe(this, new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer num) {
                        sheepView.setBarrierNum(2,num);
                        clicknum = 0;
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        LiveEventBus.get("GameOnresume").post(true);
    }

    private void inittitle(){
        tvTitle = new TextView(GameActivity.this);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(PUtil.dip2px(this,160), PUtil.dip2px(this,100));
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        layoutParams.topMargin = PUtil.dip2px(this,40);
        tvTitle.setLayoutParams(layoutParams);
        tvTitle.setTextSize(18);
        tvTitle.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        tvTitle.setTextColor(Color.BLACK);
        tvTitle.setGravity(Gravity.CENTER);
        if (barrierNum == 1){
            tvTitle.setText("第一关");
        }else {
            tvTitle.setText("第二关");
        }

        tvTitle.setOnClickListener(new NoFastClickListener() {
            @Override
            protected void onSingleClick() {
                clicknum++;
                if (clicknum>2&&barrierNum == 2){
                    startActivity(new Intent(GameActivity.this,SettingActivity.class));
                }
            }
        });
        binding.rl.addView(tvTitle);
    }

    private void initdialog() {
        dialog_passbuilder = new Dialog_pass.Builder(GameActivity.this);
        dialog_pass = dialog_passbuilder.create();
        dialog_nolivetitle = dialog_passbuilder.getDialog_title();
        dialog_nolive_btn_one = dialog_passbuilder.getBtn_one();
        iv = dialog_passbuilder.getIv_gif();
        dialog_pass.setCanceledOnTouchOutside(false);

        dialog_succeedbuilder = new Dialog_succeed.Builder(GameActivity.this);
        dialog_succeed = dialog_succeedbuilder.create();
        view_award = dialog_succeedbuilder.getView_award();
        dialog_succeed.setCanceledOnTouchOutside(false);

        awardSheepRescoureList = new ArrayList<>();
        initAwardSheepRescoure();
    }

    /**
     * 更新的Handler
     */
    private Handler mHandler = new Handler(Looper.getMainLooper()) {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0X123: {
                    LiveEventBus.get("succeed").post(true);
                    view_award.cancelAnim();
                    finish();
                    break;
                }
            }
        }
    };

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
}
