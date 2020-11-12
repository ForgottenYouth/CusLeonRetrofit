/*
 * Author: shiwenliang
 * Date: 2020/11/12 0:46
 * Description:
 */

package com.leon.leonretrofit.api;

import com.leon.leonretrofit.retrofit.annotation.LEONField;
import com.leon.leonretrofit.retrofit.annotation.LEONGET;
import com.leon.leonretrofit.retrofit.annotation.LEONPOST;
import com.leon.leonretrofit.retrofit.annotation.LEONQuery;

import retrofit2.http.FormUrlEncoded;

public interface WeatherApi {

    @LEONPOST("/v3/weather/weatherInfo")
    @FormUrlEncoded
    okhttp3.Call postWeather(@LEONField("city") String city, @LEONField("key") String key);

    @LEONGET("/v3/weather/weatherInfo")
    okhttp3.Call getWeather(@LEONQuery("city") String city, @LEONQuery("key") String key);
}
