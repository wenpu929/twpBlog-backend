package com.twp.blog.vo;

//import com.twp.blog.dao.pojo.ArticleBody;
//import com.twp.blog.dao.pojo.Category;
import com.twp.blog.dao.pojo.SysUser;
import com.twp.blog.dao.pojo.Tag;
import lombok.Data;

import java.util.List;

@Data
public class ArticleVo {

    private Long id;

    private String title;

    private String summary;

    private int commentCounts;

    private int viewCounts;

    private int weight;
    /**
     * 创建时间
     */
    private String createDate;

    private String author;

//    private ArticleBodyVo body;

    private List<Tagvo> tags;

//    private List<CategoryVo> categorys;

}
