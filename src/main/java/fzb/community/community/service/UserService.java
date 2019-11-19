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
        User byToken = userMapper.findByToken(user.getToken());
        if (byToken!=null){
            userMapper.Update(user.getName(),user.getGmtModified());
        }
        else {
            userMapper.insert(user);
        }
    }

    public User findByToken(String token){
       return userMapper.findByToken(token);
    }
}
