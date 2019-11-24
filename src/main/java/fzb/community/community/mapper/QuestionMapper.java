package fzb.community.community.mapper;

import fzb.community.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author fzb
 */
@Mapper
public interface QuestionMapper {

    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void Insert(Question question);

    @Select("select * from question")
    List<Question> findAll();

    /**
     * 总问题数
     *
     * @return
     */
    @Select("select count(1) from question")
    public int total();

    /**
     * 按更新时间查询
     * @param index
     * @param size
     * @return
     */
    @Select("select * from question order by gmt_modified desc limit  #{index},#{size}")
    List<Question> findPageAllOrderByModified(Integer index, Integer size);

    /**
     * 按用户查询
     * @param creator
     * @return
     */
    @Select("select * from question where creator=#{creator}")
    List<Question> findByCreator(Long creator);

    @Select("select * from question where id=#{id}")
    Question selectById(Long id);

    @Update("update question set view_count=view_count+1 where id=#{id}")
    void UpdateViewCutById(Long id);
}
