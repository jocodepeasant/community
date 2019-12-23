package fzb.community;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fzb.community.utils.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;

@SpringBootTest
class CommunityApplicationTests {

    @Resource
    private RedisUtils redisUtils;

    @Test
    void contextLoads() {
            redisUtils.hset("key112","name",98);
    }
}
