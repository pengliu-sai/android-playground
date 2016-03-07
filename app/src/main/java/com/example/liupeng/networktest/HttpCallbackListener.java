package com.example.liupeng.networktest;

/**
 * Created by liupeng on 16/3/6.
 */
public interface HttpCallbackListener {

    void onFinish(String response);

    void onError(Exception e);
}
