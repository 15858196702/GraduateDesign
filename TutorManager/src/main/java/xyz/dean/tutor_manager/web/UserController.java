package xyz.dean.tutor_manager.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.dean.tutor_manager.dao.UserDao;
import xyz.dean.tutor_manager.pojo.User;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserDao userDao;

    @RequestMapping("/users")
    public String listUsers() {
        List<User> users = userDao.findAll();
        StringBuffer sb = new StringBuffer();
        for (User user : users) {
            sb.append(user.getUsername());
        }
        return sb.toString();
    }
}
