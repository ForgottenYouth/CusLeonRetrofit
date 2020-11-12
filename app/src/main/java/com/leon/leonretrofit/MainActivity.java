/*
 * Author: shiwenliang
 * Date: 2020/11/12 0:36
 * Description:
 */
package com.leon.leonretrofit;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.leon.leonretrofit.api.WeatherApi;
import com.leon.leonretrofit.databinding.ActivityMainLayoutBinding;
import com.leon.leonretrofit.retrofit.LeonRetrofit;

import java.io.IOException;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {

    ActivityMainLayoutBinding mDataBinding;

    WeatherApi weatherApi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main_layout);

        //自定义的构造者模式来实例化
        final LeonRetrofit leonRetrofit = new LeonRetrofit.Builder("https://restapi.amap.com").build();
        weatherApi = leonRetrofit.create(WeatherApi.class);

        mDataBinding.get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                okhttp3.Call call = weatherApi.getWeather("110101", "ae6c53e2186f33bbf240a12d80672d1b");
                call.enqueue(new okhttp3.Callback() {

                    @Override
                    public void onFailure(okhttp3.Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(okhttp3.Call call, Response response) throws IOException {
                        Log.i("shiwenliang", "onResponse enjoy get: " + response.body().toString());
                        response.close();
                    }

                });
            }
        });

        mDataBinding.post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}