package xyz.dean.tutor_manager.utlis.token;

public interface TokenHelper {
    TokenModel create(String username);
    boolean check(TokenModel token);
    TokenModel get(String authStr);
    boolean delete(String username);
}
