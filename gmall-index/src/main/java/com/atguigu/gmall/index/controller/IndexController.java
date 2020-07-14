package com.atguigu.gmall.index.controller;

import com.atguigu.core.bean.Resp;
import com.atguigu.gmall.index.service.IndexService;
import com.atguigu.gmall.pms.entity.CategoryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/index")
@RestController
public class IndexController {
    @Autowired
    private IndexService indexService;
    @GetMapping("cates")
    public Resp<List<CategoryEntity>> queryCategories(){
        List<CategoryEntity> categoryEntitys= indexService.queryCategories();
        return   Resp.ok(categoryEntitys);
    }
    @GetMapping("cates/{pid}")
    public Resp<List<CategoryEntity>> queryCategoryWithSub(@PathVariable("pid")Long pid){
        List<CategoryEntity> categoryEntitys= indexService.queryCategoryWithSub();
        return   Resp.ok(categoryEntitys);
    }
}
