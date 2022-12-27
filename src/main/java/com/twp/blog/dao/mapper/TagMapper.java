package com.twp.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.twp.blog.dao.pojo.Tag;

import java.util.List;

public interface TagMapper extends BaseMapper<Tag> {
    /*
    * 根据文章id去查询标签列表*/
    List<Tag> findTagsByArticleId(Long articleId);

    List<Long> findHotsTagIds(int limit);

    List<Tag> findTagsByTagsIds(List<Long> tagIds);
}
