/*
 * Author: shiwenliang
 * Date: 2020/11/12 1:38
 * Description: Query注解参数
 */
package com.leon.leonretrofit.retrofit.reqparamhandler;

public class QueryParamHandler implements ParamHandler {

    public String key;

    public QueryParamHandler(String key) {
        this.key = key;
    }

    @Override
    public void apply(String key, String value) {

    }
}