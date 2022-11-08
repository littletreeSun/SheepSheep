package com.littletree.mysunsheep;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.jeremyliao.liveeventbus.LiveEventBus;
import com.littletree.mysunsheep.customview.NoFastClickListener;
import com.littletree.mysunsheep.databinding.ActivitySettingBinding;

/**
 * @ProjectName: MySunSheep
 * @Package: com.littletree.mysunsheep
 * @ClassName: SettingActivity
 * @Author: littletree
 * @CreateDate: 2022/11/3/003 17:59
 */
public class SettingActivity extends AppCompatActivity {
    ActivitySettingBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting);

        binding.TvEnsure.setOnClickListener(new NoFastClickListener() {
            @Override
            protected void onSingleClick() {
                if (null!=binding.etNum.getText()&& !TextUtils.isEmpty(binding.etNum.getText().toString())){
                    LiveEventBus.get("plieNum").post(Integer.parseInt(binding.etNum.getText().toString()));
                    finish();
                }
            }
        });

    }
}
