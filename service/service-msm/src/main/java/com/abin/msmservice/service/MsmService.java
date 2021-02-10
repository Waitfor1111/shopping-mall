package com.abin.msmservice.service;

import java.util.Map;

/**
 * @author 大冰
 * @version 1.0
 * @date 2021/1/28 11:02
 */
public interface MsmService {
    Boolean sendCode(Map<String, String> param, String phoneNumber);

    boolean send(Map<String, String> param, String phone);
}
