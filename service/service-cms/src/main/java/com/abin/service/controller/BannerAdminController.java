package com.abin.service.controller;


import com.abin.service.entity.CrmBanner;
import com.abin.service.service.CrmBannerService;
import com.abin.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 大冰
 * @since 2021-01-27
 */
// 管理员
@CrossOrigin
@RestController
@RequestMapping("/service/bannerAdmin")
public class BannerAdminController {


    @Autowired
    private CrmBannerService crmbannerservice;

    //展示所有的轮播图  方便改
    @GetMapping("page/{page}/{limit}")
    public  R getpageBanner(@PathVariable long page , @PathVariable long limit){

        QueryWrapper<CrmBanner> objectQueryWrapper = new QueryWrapper<>();
        Page<CrmBanner> page1 = new Page<>(page, limit);
        crmbannerservice.page(page1,objectQueryWrapper);
        long total = page1.getTotal();
        List<CrmBanner> records = page1.getRecords();
        Map map=new HashMap();
        map.put("total",total);
        map.put("rows",records);
        return R.ok().data(map);
    }


    //ADD轮播图
    @PostMapping("addBanner")
    public  R addBander(@RequestBody CrmBanner crmBanner){
        crmbannerservice.save(crmBanner);
        return R.ok();
    }

    @ApiOperation(value = "获取Banner")
    @GetMapping("get/{id}")
    public R get(@PathVariable String id) {
        CrmBanner banner = crmbannerservice.getById(id);
        return R.ok().data("item", banner);
    }
    @ApiOperation(value = "修改Banner")
    @PutMapping("update")
    public R updateById(@RequestBody CrmBanner banner) {
        crmbannerservice.updateById(banner);
        return R.ok();
    }

    @ApiOperation(value = "删除Banner")
    @DeleteMapping("remove/{id}")
    public R remove(@PathVariable String id) {
        crmbannerservice.removeById(id);
        return R.ok();
    }



}

