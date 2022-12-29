package com.twp.blog;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlogApplicationTests {
    private  String salt = "mszlu!@#";
    @Test
    void contextLoads() {
        String s= DigestUtils.md5Hex("abc123"+salt);
        System.out.println(s);
    }


}
