package fzb.community.community.service;

import fzb.community.community.mapper.UserMapper;
import fzb.community.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public void InsertOrUpdate(User user){
        User byId = userMapper.findByAccountId(user.getAccountId());
        if (byId!=null){
            userMapper.Update(user);
        }
        else {
            userMapper.insert(user);
        }
    }

    public User findByToken(String token){
       return userMapper.findByToken(token);
    }
}
