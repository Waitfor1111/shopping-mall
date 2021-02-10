package com.abin.service.controller;


import com.abin.service.entity.CrmBanner;
import com.abin.service.service.CrmBannerService;
import com.abin.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 大冰
 * @since 2021-01-27
 */
@CrossOrigin
@RestController
@RequestMapping("/service/bannerFront")
public class BannerFrontController {

    @Autowired
    private CrmBannerService crmbannerservice;

    @GetMapping("/getAllbanner")
    public R getBander(){
        List<CrmBanner> list=crmbannerservice.getAllBanner();
        return R.ok().data("list",list);
    }


}

