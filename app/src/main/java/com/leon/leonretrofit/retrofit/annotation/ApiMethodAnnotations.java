/*
 * Author: shiwenliang
 * Date: 2020/11/12 1:15
 * Description: 请求接口的方法上的注解管理类
 */
package com.leon.leonretrofit.retrofit.annotation;

import com.leon.leonretrofit.retrofit.LeonRetrofit;
import com.leon.leonretrofit.retrofit.reqparamhandler.FieldParamHandler;
import com.leon.leonretrofit.retrofit.reqparamhandler.ParamHandler;
import com.leon.leonretrofit.retrofit.reqparamhandler.QueryParamHandler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;

public class ApiMethodAnnotations {

    private Annotation[][] parameterAnnotations;
    private Annotation[] methodAnnotations;
    private String requestMethod;
    private String urlPath;
    private boolean hasBody;

    private ParamHandler[] paramHandlers;

    HttpUrl baseUrl;
    HttpUrl.Builder urlBuilder;

    FormBody.Builder formBuild;

    Call.Factory callFactory;


    public ApiMethodAnnotations(Builder builder) {
        baseUrl = builder.leonRetrofit.baseUrl;
        callFactory = builder.leonRetrofit.callFactory;

        this.parameterAnnotations = builder.parameterAnnotations;
        this.methodAnnotations = builder.methodAnnotations;
        this.requestMethod = builder.requestMethod;
        this.urlPath = builder.urlPath;
        this.hasBody = builder.hasBody;
        this.paramHandlers = builder.paramHandlers;

        if (hasBody) {
            formBuild = new FormBody.Builder();
        }
    }

    public Object invoke(Method method, Object[] args) {
        /*
         * TODO 拼接地址
         */

        for (int i = 0; i < paramHandlers.length; i++) {
            ParamHandler paramHandler = paramHandlers[i];
            paramHandler.apply(this, args[i].toString());
        }

        HttpUrl url;
        if (urlBuilder == null) {
            urlBuilder = baseUrl.newBuilder(urlPath);
        }
        url = urlBuilder.build();

        FormBody formBody = null;
        if (formBuild != null) {
            formBody = formBuild.build();
        }

        Request request = new Request.Builder().url(url).method(requestMethod, formBody).build();

        return this.callFactory.newCall(request);
    }

    public void addFieldParameter(String key, String value) {
        formBuild.add(key, value);
    }

    public void addQueryParameter(String key, String value) {
        if (urlBuilder != null) {
            urlBuilder.addQueryParameter(key, value);
        }
    }


    public static class Builder {

        private Annotation[][] parameterAnnotations;
        private Annotation[] methodAnnotations;
        private String requestMethod;
        private String urlPath;
        private boolean hasBody;
        private ParamHandler[] paramHandlers;
        LeonRetrofit leonRetrofit;

        public Builder(Method method, LeonRetrofit leonRetrofit) {
            this.leonRetrofit = leonRetrofit;
            //得到方法上的注解
            methodAnnotations = method.getDeclaredAnnotations();
            //得到方法参数上的注解
            parameterAnnotations = method.getParameterAnnotations();
        }

        public ApiMethodAnnotations build() {
            //开始解析方法上的注解
            for (Annotation methodAnnotation : methodAnnotations) {
                if (methodAnnotation instanceof LEONPOST) {
                    //post 请求
                    this.requestMethod = "LEONPOST";
                    urlPath = ((LEONPOST) methodAnnotation).value();
                    this.hasBody = true;
                } else if (methodAnnotation instanceof LEONGET) {
                    //get 请求
                    this.requestMethod = "LEONGET";
                    urlPath = ((LEONGET) methodAnnotation).value();
                    this.hasBody = false;
                }
            }

            int parameterLength = parameterAnnotations.length;
            paramHandlers = new ParamHandler[parameterLength];
            //开始解析方法参数上的注解
            for (int i = 0; i < parameterLength; i++) {
                //轮询得到某一个参数上的注解
                Annotation[] parameterAnnotation = parameterAnnotations[i];
                for (Annotation annotation : parameterAnnotation) {
                    if (annotation instanceof LEONField) {
                        String fieldkey = ((LEONField) annotation).value();
                        paramHandlers[i] = new FieldParamHandler(fieldkey);
                    } else if (annotation instanceof LEONQuery) {
                        String value = ((LEONQuery) annotation).value();
                        paramHandlers[i] = new QueryParamHandler(value);
                    }
                }
            }
            return new ApiMethodAnnotations(this);
        }
    }
}