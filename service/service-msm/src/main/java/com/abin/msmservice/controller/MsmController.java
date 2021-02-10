package com.abin.msmservice.controller;

import com.abin.msmservice.service.MsmService;
import com.abin.msmservice.utils.RandomUtil;
import com.abin.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author 大冰
 * @version 1.0
 * @date 2021/1/28 11:01
 */
@CrossOrigin
@RestController
@RequestMapping("/edumsm/msm")
public class MsmController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

//    @GetMapping("/send/{phoneNumber}")
//    public R send(@PathVariable String phoneNumber) {
//        // 从redis中获取验证码 如果存在就直接返回
//        String code = redisTemplate.opsForValue().get(phoneNumber);
//        if (!StringUtils.isEmpty(code)) {
//            return R.ok();
//        }
//        // redis中不存在 获取四位随机数
//        code = RandomUtil.getSixBitRandom(); // 获取到的验证码
//
//        System.out.println("验证吗" + code);
//        System.out.println("数据类型是否为String" + code instanceof String);
//
//        Map<String, String> param = new HashMap<>();
//        param.put("code", code);
//
//        Boolean isSend = msmService.sendCode(param, phoneNumber);
//        if (isSend) {
//            // 把阿里云验证码发送redis中 并且设置失效时间 5分钟
//            redisTemplate.opsForValue().set(phoneNumber, code, 5, TimeUnit.MINUTES);
//            return R.ok();
//        } else {
//            return R.error().message("验证码发送失败");
//        }
//    }

    //发送短信的方法
    @GetMapping("/send/{phone}")
    public R sendMsm(@PathVariable("phone") String phone) {
        //1 从redis获取验证码，如果获取到直接返回
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)) {
            return R.ok();
        }
        //2 如果redis获取 不到，进行阿里云发送
        //生成随机值，传递阿里云进行发送
        code = RandomUtil.getFourBitRandom();
        Map<String, String> param = new HashMap<>();
        param.put("code", code);
        //调用service发送短信的方法
        boolean isSend = msmService.send(param, phone);
        if (isSend) {
            //发送成功，把发送成功验证码放到redis里面
            //设置有效时间
            ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
            redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
            return R.ok();
        } else {
            return R.error().message("短信发送失败");
        }
    }


}
