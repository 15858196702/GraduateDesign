package xyz.dean.tutor_manager.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.dean.tutor_manager.mapper.UserMapper;
import xyz.dean.tutor_manager.pojo.User;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserMapper userMapper;

    @GetMapping("/login")
    public String login(String username, String password) {
        User user = userMapper.findUserByName(username);

        if (user.getPassword().equals(password)) {
            return "Success";
        } else {
            return "Failure";
        }
    }

    @GetMapping("/user-list")
    public String getAll(
            @RequestParam(value = "start", defaultValue = "0")int start,
            @RequestParam(value = "size" , defaultValue = "5")int size) {
        PageHelper.startPage(start, size, "id desc");
        List<User> users = userMapper.getAllUsers();
        PageInfo<User> page = new PageInfo<>(users);

        //分页
        List<User> uPage = page.getList();
        StringBuffer sb = new StringBuffer();
        for (User user:uPage) {
            sb.append(user.getUsername())
                    .append("|");
        }

        return sb.toString();
    }
}
