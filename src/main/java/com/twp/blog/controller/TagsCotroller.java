package com.twp.blog.controller;

import com.twp.blog.service.TagService;
import com.twp.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("tags")
public class TagsCotroller {
    @Autowired
    private TagService tagService;
    //tags/hot
    @GetMapping("hot")
    public Result hot()
    {
        int limit=6;
        Result hot = tagService.hot(limit);
        return hot;

    }

}
