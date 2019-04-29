package xyz.dean.tutor_manager.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import xyz.dean.tutor_manager.common.ResponseCode;
import xyz.dean.tutor_manager.utlis.token.NoneAuth;
import xyz.dean.tutor_manager.utlis.token.TokenHelper;
import xyz.dean.tutor_manager.model.TokenModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Repository
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    TokenHelper tokenHelper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("===========Login Interceptor=============");
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        if (method.getAnnotation(NoneAuth.class) != null) {
            return true;
        }

        String authStr = request.getHeader("auth");
        TokenModel model = tokenHelper.get(authStr);

        if (tokenHelper.check(model)) {
            return true;
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        String re = "{"
                    + "\"code\":" + ResponseCode.INVALID_TOKEN + ","
                    + "\"msg\" :" + "\"无效token\"" + ","
                    + "\"data\":" + "null"
                    + "}";
        response.getWriter().write(re);
        return false;
    }
}
