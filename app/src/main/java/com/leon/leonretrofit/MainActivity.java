/*
 * Author: shiwenliang
 * Date: 2020/11/12 0:36
 * Description:
 */
package com.leon.leonretrofit;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.leon.leonretrofit.api.WeatherApi;
import com.leon.leonretrofit.databinding.ActivityMainLayoutBinding;
import com.leon.leonretrofit.retrofit.LeonRetrofit;

public class MainActivity extends AppCompatActivity {

    ActivityMainLayoutBinding mDataBinding;

    WeatherApi weatherApi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main_layout);

        //自定义的构造者模式来实例化
        LeonRetrofit leonRetrofit = new LeonRetrofit.Builder("https://restapi.amap.com").build();
        weatherApi = leonRetrofit.create(WeatherApi.class);

        mDataBinding.get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mDataBinding.post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}