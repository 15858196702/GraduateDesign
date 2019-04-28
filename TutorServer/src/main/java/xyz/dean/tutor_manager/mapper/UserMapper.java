package xyz.dean.tutor_manager.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import xyz.dean.tutor_manager.pojo.User;

import java.util.List;

@Repository
@Mapper
public interface UserMapper {

    @Select("select * from user")
    List<User> getAllUsers();

    @Select("select * from user where username = #{username}")
    User findUserByName(String username);

    @Insert("insert into " +
                "user(username, password, age, sex, introduce, avatar_url, address, job) " +
                "values(#{username}, #{password}, #{age}, #{sex}, #{introduce}, #{avatarUrl}, #{address}, #{job})")
    int registerUser(User user);
}
