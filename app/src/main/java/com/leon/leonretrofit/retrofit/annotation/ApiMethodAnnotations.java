/*
 * Author: shiwenliang
 * Date: 2020/11/12 1:15
 * Description: 请求接口的方法上的注解管理类
 */
package com.leon.leonretrofit.retrofit.annotation;

import com.leon.leonretrofit.retrofit.reqparamhandler.FieldParamHandler;
import com.leon.leonretrofit.retrofit.reqparamhandler.ParamHandler;
import com.leon.leonretrofit.retrofit.reqparamhandler.QueryParamHandler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class ApiMethodAnnotations {


    public static class Builder {


        private Annotation[][] parameterAnnotations;
        private Annotation[] methodAnnotations;
        private String requestMethod;
        private String urlPath;
        private boolean hasBody;

        private ParamHandler[] paramHandlers;

        public Builder(Method method) {
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
            return new ApiMethodAnnotations();
        }
    }
}