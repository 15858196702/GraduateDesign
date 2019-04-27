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
import xyz.dean.tutor_manager.utlis.token.NoneAuth;
import xyz.dean.tutor_manager.utlis.token.TokenHelper;
import xyz.dean.tutor_manager.utlis.token.TokenModel;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserMapper userMapper;
    @Autowired
    TokenHelper tokenHelper;

    @NoneAuth
    @GetMapping("/login")
    public String login(String username, String password) {
        User user = userMapper.findUserByName(username);

        if (user.getPassword().equals(password)) {
            TokenModel model = tokenHelper.create(username);
            return username + "_" + model.getToken();
        } else {
            return "Failure";
        }
    }

    @GetMapping("/user-list")
    public String getAll(
            @RequestParam(value = "start", defaultValue = "0")int start,
            @RequestParam(value = "size" , defaultValue = "5")int size) {
        PageHelper.startPage(start, size, "id desc");
        //这部分是把数据全部读取到内存中，数据量比较大的时候性能会很差吧
        //优化思路
        //第一次请求分页时从数据库获取一大块数据，
        //如果第二次请求的区间在第一次从数据库获取的范围内，则不再从数据库读取。
        List<User> users = userMapper.getAllUsers();
        PageInfo<User> page = new PageInfo<>(users);

        //分页
        List<User> uPage = page.getList();
        StringBuilder sb = new StringBuilder();
        for (User user:uPage) {
            sb.append(user.getUsername())
                    .append("|");
        }

        return sb.toString();
    }
}
