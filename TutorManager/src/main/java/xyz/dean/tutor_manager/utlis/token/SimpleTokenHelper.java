package xyz.dean.tutor_manager.utlis.token;

public class SimpleTokenHelper implements TokenHelper{

    @Override
    public TokenModel create(Integer id) {
        return null;
    }

    @Override
    public boolean check(TokenModel token) {
        return false;
    }

    @Override
    public TokenModel get(String authStr) {
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }
}
