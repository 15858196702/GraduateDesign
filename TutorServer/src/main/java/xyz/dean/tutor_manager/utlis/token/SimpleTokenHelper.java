package xyz.dean.tutor_manager.utlis.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import xyz.dean.tutor_manager.mapper.TokenMapper;
import xyz.dean.tutor_manager.model.TokenModel;

import java.util.List;
import java.util.UUID;

@Repository("TokenHelper")
public class SimpleTokenHelper implements TokenHelper{
    @Autowired
    TokenMapper tokenMapper;

    @Override
    public TokenModel create(String username) {
        String token = UUID.randomUUID().toString().replace("-", "");
        TokenModel mode = new TokenModel(username, token, System.currentTimeMillis(), 30*24*60*60*1000L);
        tokenMapper.addToken(mode);
        return mode;
    }

    @Override
    public boolean check(TokenModel mode) {
        boolean result = false;
        if(mode != null) {
            String username = mode.getUsername();
            String token = mode.getToken();
            List<TokenModel> modes = tokenMapper.getTokenByUsername(username);
            for (TokenModel tokenmodel :modes) {
                if (tokenmodel.getToken().equals(token)
                        && tokenmodel.getCreateTime() - System.currentTimeMillis() < tokenmodel.getDuration()) {
                    return true;
                }
            }

            return true;
        }
        return result;
    }

    @Override
    public TokenModel get(String authStr) {
        TokenModel model = null;
        if(!StringUtils.isEmpty(authStr)) {
            String[] modelArr = authStr.split("_");
            if(modelArr.length == 2) {
                model = new TokenModel(modelArr[0], modelArr[1], 0L, 0L);
            }
        }
        return model;
    }

    @Override
    public boolean delete(String username) {
        return false;
    }
}
