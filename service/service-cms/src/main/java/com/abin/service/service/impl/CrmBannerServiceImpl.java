package com.abin.service.service.impl;

import com.abin.service.entity.CrmBanner;
import com.abin.service.mapper.CrmBannerMapper;
import com.abin.service.service.CrmBannerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author 大冰
 * @since 2021-01-27
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Override
    // 存储redis中
    @Cacheable(value = "banner",key = "'selectIndexList'")
    public List<CrmBanner> getAllBanner() {
        // 根据id进行降序 ，排列显示前两条数据
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        // 拼接sql 拿出后两条数据
        wrapper.last("limit 2");

        List<CrmBanner> bannerList = baseMapper.selectList(wrapper);

        return bannerList;
    }
}
