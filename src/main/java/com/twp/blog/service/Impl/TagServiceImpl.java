package com.twp.blog.service.Impl;

import com.twp.blog.dao.mapper.TagMapper;
import com.twp.blog.dao.pojo.Tag;
import com.twp.blog.service.TagService;
import com.twp.blog.vo.Tagvo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
}
