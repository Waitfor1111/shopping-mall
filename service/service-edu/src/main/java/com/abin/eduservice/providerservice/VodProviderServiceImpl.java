package com.abin.eduservice.providerservice;

import com.abin.utils.R;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 大冰
 * @version 1.0
 * @date 2021/1/27 10:05
 */
@Component
public class VodProviderServiceImpl implements VodProviderService {
    @Override
    public R removeVideo(String id) {
        return R.error().message("系统出错啦 请稍后再试 ~_~");
    }

    @Override
    public R deleteBatch(List<String> videoList) {
        return R.error().message("系统出错啦 请稍后再试 ~_~");
    }
}
