package com.twp.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.twp.blog.dao.dos.Achives;
import com.twp.blog.dao.pojo.Article;

import java.util.List;

public interface ArticleMapper extends BaseMapper<Article> {

    List<Achives> listArchives();
}
