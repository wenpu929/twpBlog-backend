package com.twp.blog.service.Impl;

import com.twp.blog.dao.mapper.TagMapper;
import com.twp.blog.dao.pojo.Tag;
import com.twp.blog.service.TagService;
import com.twp.blog.vo.Result;
import com.twp.blog.vo.Tagvo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagMapper tagMapper;
    public Tagvo copy(Tag tag){
        Tagvo tagVo = new Tagvo();
        BeanUtils.copyProperties(tag,tagVo);
        return tagVo;
    }
    public List<Tagvo> copyList(List<Tag> tagList){
        List<Tagvo> tagVoList = new ArrayList<>();
        for (Tag tag : tagList) {
            tagVoList.add(copy(tag));
        }
        return tagVoList;
    }

    @Override
    public List<Tagvo> findTagsByArticleId(Long articleId) {
        List<Tag>tags=tagMapper.findTagsByArticleId(articleId);
        return copyList(tags);
    }

    @Override
    public Result hot(int limit) {
        /*
        * 标签所拥有的文章数量最多，为最热标签
        *
        * 查询 根据tagid 分组 计数 从大到小排列 取前limit个
        *
        * */
        List<Long> tagIds=tagMapper.findHotsTagIds(limit);
        if(CollectionUtils.isEmpty(tagIds))
        {
            return Result.success(Collections.emptyList());
        }
        //需求标签id和标签名

        List<Tag> tagList=tagMapper.findTagsByTagsIds(tagIds);

        return Result.success(tagList);
    }
}
