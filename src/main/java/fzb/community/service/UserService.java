package fzb.community.service;

import fzb.community.mapper.UserMapper;
import fzb.community.model.User;
import fzb.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public void InsertOrUpdate(User user){
        UserExample userExample=new UserExample();
        userExample.createCriteria()
                .andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(userExample);
        if (users.size()!=0){
            User dbUser=users.get(0);
            User updateUser=new User();
            updateUser.setGmtModified(System.currentTimeMillis());
            updateUser.setAvatarUrl(user.getAvatarUrl());
            updateUser.setName(user.getName());
            updateUser.setToken(user.getToken());
            UserExample example=new UserExample();
            example.createCriteria()
                    .andIdEqualTo(dbUser.getId());
            userMapper.updateByExampleSelective(updateUser,example);
        }
        else {
            userMapper.insert(user);
        }
    }

    public List<User> findByToken(String token){
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andTokenEqualTo(token);
       return userMapper.selectByExample(userExample);
    }
}
