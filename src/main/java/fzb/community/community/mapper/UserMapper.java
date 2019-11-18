package fzb.community.community.mapper;

import fzb.community.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    @Insert("insert into user (account_id,name,token,gmt_create,gmt_modified) values(#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified})")
    void insert(User user);

    @Select("select * from user where token=#{token}")
    User findByToken(String token);

    @Update("update user set name=#{name},gmt_modified=#{modified}")
    void Update(String name,Long modified);
}

