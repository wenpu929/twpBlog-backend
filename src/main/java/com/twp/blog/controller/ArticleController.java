package com.twp.blog.controller;

import com.twp.blog.service.ArticleService;
import com.twp.blog.vo.ArticleVo;
import com.twp.blog.vo.Result;
import com.twp.blog.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("articles")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
        /*首页文章列表
        *
        * */
    @PostMapping
    public Result listArticle(@RequestBody PageParams pageParams){
        List<ArticleVo> articles = articleService.listArticlesPage(pageParams);

        return Result.success(articles);

    }
}
