/*
 * Author: shiwenliang
 * Date: 2020/11/12 0:52
 * Description:
 */
package com.leon.leonretrofit.retrofit;

import com.leon.leonretrofit.retrofit.annotation.ApiMethodAnnotations;
import com.leon.leonretrofit.retrofit.annotation.LEONGET;
import com.leon.leonretrofit.retrofit.annotation.LEONPOST;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

/*
 * TODO 建造者模式创建LeonRetrofit 的实例
 */
public class LeonRetrofit {

    public HttpUrl baseUrl;
    public okhttp3.Call.Factory callFactory;

    LeonRetrofit(HttpUrl baseUrl, okhttp3.Call.Factory callFactory) {
        this.baseUrl = baseUrl;
        this.callFactory = callFactory;
    }

    /*
     * TODO 动态代理模式实现api调用
     */
    public <T> T create(final Class<T> services) {
        return (T) Proxy.newProxyInstance(services.getClassLoader(), new Class[]{services}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                /*
                 * TODO 此时应该是解析方法上的所有注解
                 */
                ApiMethodAnnotations apiMethodAnnotations = loadAnnotationFromMethod(method);
                return apiMethodAnnotations.invoke(method, args);
            }
        });
    }


    /*
     * TODO 解析方法上的注解
     */
    private ApiMethodAnnotations loadAnnotationFromMethod(Method method) {
        ApiMethodAnnotations build = new ApiMethodAnnotations.Builder(method, this).build();
        return build;
    }


    public static final class Builder {

        HttpUrl baseUrl;

        public Builder(String baseUrl) {
            this.baseUrl = HttpUrl.get(baseUrl);
        }

        public LeonRetrofit build() {
            if (baseUrl == null) {
                throw new IllegalStateException("Base Url required");
            }
            OkHttpClient okHttpClient = new OkHttpClient();
            return new LeonRetrofit(baseUrl, okHttpClient);
        }
    }

}