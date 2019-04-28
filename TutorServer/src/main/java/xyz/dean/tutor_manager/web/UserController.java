package xyz.dean.tutor_manager.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.dean.tutor_manager.mapper.UserMapper;
import xyz.dean.tutor_manager.pojo.User;
import xyz.dean.tutor_manager.trans.ResponseData;
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
    @RequestMapping("/login")
    public ResponseData login(String username, String password) {
        ResponseData response;
        User user = userMapper.findUserByName(username);

        if (user.getPassword().equals(password)) {
            TokenModel model = tokenHelper.create(username);
            response = new ResponseData<>(0, "", model);
        } else {
            response = new ResponseData<>(-100, "登录失败", null);
        }
        return response;
    }

    @NoneAuth
    @RequestMapping("/register")
    public ResponseData register(@RequestBody User user) {
        ResponseData response;
        User findUser = userMapper.findUserByName(user.getUsername());

        if (findUser != null) {
            response = new ResponseData<>(-101, "用户名已被注册。", null);
        } else {
            int result = userMapper.registerUser(user);
            if (result < 1) {
                response = new ResponseData<>(-401, "数据库发生错误。", null);
            } else {
                response = new ResponseData<>(0, "", user);
            }
        }
        return response;
    }

//    @GetMapping("/user-list")
//    public String getAll(
//            @RequestParam(value = "start", defaultValue = "0")int start,
//            @RequestParam(value = "size" , defaultValue = "5")int size) {
//        PageHelper.startPage(start, size, "id desc");
//        //这部分是把数据全部读取到内存中，数据量比较大的时候性能会很差吧
//        //优化思路
//        //第一次请求分页时从数据库获取一大块数据，
//        //如果第二次请求的区间在第一次从数据库获取的范围内，则不再从数据库读取。
//        List<User> users = userMapper.getAllUsers();
//        PageInfo<User> page = new PageInfo<>(users);
//
//        //分页
//        List<User> uPage = page.getList();
//        StringBuilder sb = new StringBuilder();
//        for (User user:uPage) {
//            sb.append(user.getUsername())
//                    .append("|");
//        }
//
//        return sb.toString();
//    }
}
