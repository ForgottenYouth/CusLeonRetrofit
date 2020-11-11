/*
 * Author: shiwenliang
 * Date: 2020/11/12 1:34
 * Description: 请求参数处理类接口
 */

package com.leon.leonretrofit.retrofit.reqparamhandler;

public interface ParamHandler {

    public void apply(String key ,String value);
}
