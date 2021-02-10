package com.abin.eduservice.controller;

import com.abin.utils.R;
import org.springframework.web.bind.annotation.*;

/**
 * @author 大冰
 * @version 1.0
 * @date 2021/1/21 16:03
 */
@RestController
@RequestMapping("/eduservice/user")
@CrossOrigin  //后端配合解决跨域问题
public class EduLoginController {


    @PostMapping("/login")
    public R login() {

        return R.ok().data("token","admin");
    }


    @GetMapping("/info")
    public R info() {

        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }


}
