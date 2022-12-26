package com.twp.blog.service;

import com.twp.blog.vo.Tagvo;

import java.util.List;

public interface TagService {
    List<Tagvo> findTagsByArticleId(Long articleId);
}
