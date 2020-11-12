/*
 * Author: shiwenliang
 * Date: 2020/11/12 1:34
 * Description: 请求参数处理类接口
 */

package com.leon.leonretrofit.retrofit.reqparamhandler;

import com.leon.leonretrofit.retrofit.annotation.ApiMethodAnnotations;

public abstract class ParamHandler {

    protected String key;
    public  abstract void apply(ApiMethodAnnotations method , String value);
}
