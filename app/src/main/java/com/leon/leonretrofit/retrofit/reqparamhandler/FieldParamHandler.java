/*
 * Author: shiwenliang
 * Date: 2020/11/12 1:37
 * Description: Field注解参数
 */
package com.leon.leonretrofit.retrofit.reqparamhandler;

import com.leon.leonretrofit.retrofit.annotation.ApiMethodAnnotations;

public class FieldParamHandler extends ParamHandler {


    public FieldParamHandler(String key){
        this.key = key;
    }

    @Override
    public void apply(ApiMethodAnnotations method, String value) {
        method.addFieldParameter(key,value);
    }
}