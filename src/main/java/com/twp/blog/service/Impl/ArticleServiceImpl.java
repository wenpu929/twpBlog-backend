package com.twp.blog.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDto;
import com.twp.blog.dao.mapper.ArticleMapper;
import com.twp.blog.dao.pojo.Article;
import com.twp.blog.service.ArticleService;
import com.twp.blog.service.SysUserService;
import com.twp.blog.service.TagService;
import com.twp.blog.vo.ArticleVo;
import com.twp.blog.vo.Result;
import com.twp.blog.vo.params.PageParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private TagService tagService;
    @Autowired
    SysUserService sysUserService;
    @Override
    public List<ArticleVo> listArticlesPage(PageParams pageParams) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        Page<Article> page = new Page<>(pageParams.getPage(),pageParams.getPageSize());
        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
        List<Article> records=articlePage.getRecords();
        List<ArticleVo> articleVoList = copyList(records,true,true);
        return articleVoList;
    }

    @Override
    public Result hotArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper =new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.last("limit "+limit );
        //select id,title form article order by view_counts desc limit 5
        List<Article> articles = articleMapper.selectList(queryWrapper);

        return Result.success(copyList(articles,false,false));
    }


    private List<ArticleVo> copyList(List<Article> records,boolean isTags,boolean isAuthor) {
        List<ArticleVo> articleVoList =new ArrayList<>();
        for (Article record : records) {
            ArticleVo articleVo = copy(record,isTags,isAuthor);
            articleVoList.add(articleVo);
        }

        return articleVoList;
    }

    private ArticleVo copy(Article article,boolean isTag,boolean isAuthor)
    {
        ArticleVo articleVo=new ArticleVo();
        BeanUtils.copyProperties(article,articleVo);
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-mm-dd HH:MM"));
        if(isTag)
        {
            Long articleId = article.getId();
            articleVo.setTags(tagService.findTagsByArticleId(articleId));
        }
        if(isAuthor){
            Long authorId = article.getAuthorId();
            articleVo.setAuthor(sysUserService.findUserById(authorId).getNickname());
        }

        return articleVo;
    }

}
