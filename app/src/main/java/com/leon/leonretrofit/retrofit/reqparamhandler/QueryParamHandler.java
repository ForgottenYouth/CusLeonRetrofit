/*
 * Author: shiwenliang
 * Date: 2020/11/12 1:38
 * Description: Query注解参数
 */
package com.leon.leonretrofit.retrofit.reqparamhandler;

import com.leon.leonretrofit.retrofit.annotation.ApiMethodAnnotations;

public class QueryParamHandler extends ParamHandler {


    public QueryParamHandler(String key) {
        this.key = key;
    }

    @Override
    public void apply(ApiMethodAnnotations method, String value) {
        method.addQueryParameter(key,value);
    }
}