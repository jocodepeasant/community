package fzb.community;

import fzb.community.utils.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class CommunityApplicationTests {

    @Autowired
    private RedisUtils redisUtils;

    @Test
    void contextLoads() {
        redisUtils.set("lkay","aaa");
    }

}
