package com.twp.blog.service;

import com.twp.blog.vo.ArticleVo;
import com.twp.blog.vo.Result;
import com.twp.blog.vo.params.PageParams;

import java.util.List;

public interface ArticleService {
    List<ArticleVo> listArticlesPage(PageParams pageParams);
}
