package xyz.dean.tutor_manager.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import xyz.dean.tutor_manager.utlis.token.TokenModel;

import java.util.List;

@Repository
@Mapper
public interface TokenMapper {
    @Insert("insert into token(username, token, create_time, duration) values(#{username}, #{token}, #{createTime}, #{duration})")
    void addToken(TokenModel tokenModel);

    @Select("select * from token where username = #{username}")
    List<TokenModel> getTokenByUsername(String username);

    @Delete("delete from token where username = #{username} and token = #{token}")
    void expireToken(TokenModel tokenModel);
}
