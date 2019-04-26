package xyz.dean.tutor_manager.utlis.token;

public interface TokenHelper {
    TokenModel create(Integer id);
    boolean check(TokenModel token);
    TokenModel get(String authStr);
    boolean delete(Integer id);
}
