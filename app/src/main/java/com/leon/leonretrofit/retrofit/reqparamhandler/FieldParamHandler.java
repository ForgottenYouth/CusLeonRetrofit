/*
 * Author: shiwenliang
 * Date: 2020/11/12 1:37
 * Description: Field注解参数
 */
package com.leon.leonretrofit.retrofit.reqparamhandler;

public class FieldParamHandler implements ParamHandler {

    public String key;

    public FieldParamHandler(String key){
        this.key = key;
    }

    @Override
    public void apply(String key, String value) {

    }
}