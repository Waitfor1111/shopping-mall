package com.abin.msmservice.service.impl;

import com.abin.msmservice.service.MsmService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author 大冰
 * @version 1.0
 * @date 2021/1/28 11:02
 */
@Service
public class MsmServiceImpl implements MsmService {


    @Override
    public Boolean sendCode(Map<String, String> param, String phoneNumber) {
        // 判断手机号是否为空 是空返回false
        if (StringUtils.isEmpty(phoneNumber)) return false;

        // TODO 密钥需要修改成自己的
        // 自己的密钥LTAI4G531F8so6gVSZ9GyynT   haym9CgnuhBaNbj4XlBM9OLMZ3SwN4
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "AccessKeyID", "AccessKeyPassword");

        IAcsClient client = new DefaultAcsClient(profile);

        // 设置相关固定参数
        CommonRequest request = new CommonRequest();
//        // 设置提交方式
//        request.setMethod(MethodType.POST);
//        request.setDomain("dysmsapi.aliyuncs.com");
//        // 版本号
//        request.setVersion("2017-05-25");
//        request.setAction("SendSms");
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
//        request.putQueryParameter("RegionId", "cn-hangzhou");

        // 设置相关发送参数
        request.putQueryParameter("PhoneNumbers", phoneNumber); // 手机号
//        request.putQueryParameter("SignName", "我的新学堂在线教育网站"); // 申请的签名名称
//        request.putQueryParameter("TemplateCode", "SMS_210765345"); // 阿里云模板CODE
        // TODO 需要改成自己的
        request.putQueryParameter("SignName", "我的谷粒在线教育网站"); //申请阿里云 签名名称
        request.putQueryParameter("TemplateCode", "SMS_180051135"); //申请阿里云 模板code
        // 把map集合转成json数据
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));

        try {
            // 发送
            CommonResponse response = client.getCommonResponse(request);
            System.out.println("验证码：" + response.getData());
            // 返回是否发送成功
            boolean success = response.getHttpResponse().isSuccess();

            return success;
        } catch (ClientException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean send(Map<String, String> param, String phone) {
        if (StringUtils.isEmpty(phone)) return false;

        DefaultProfile profile =
                DefaultProfile.getProfile("default", "AccessKeyID", "AccessKeyPassword");
        IAcsClient client = new DefaultAcsClient(profile);

        //设置相关固定的参数
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

        //设置发送相关的参数
        request.putQueryParameter("PhoneNumbers", phone); //手机号
        request.putQueryParameter("SignName", "我的谷粒在线教育网站"); //申请阿里云 签名名称
        request.putQueryParameter("TemplateCode", "SMS_180051135"); //申请阿里云 模板code
        request.putQueryParameter("TemplateParam",  JSONObject.toJSONString(param)); //验证码数据，转换json数据传递


        System.out.println("json字符串：" + JSON.toJSONString(param));
        try {
            //最终发送
            CommonResponse response = client.getCommonResponse(request);
            boolean success = response.getHttpResponse().isSuccess();
            System.out.println("验证码：" + response.getData());
            return success;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}
