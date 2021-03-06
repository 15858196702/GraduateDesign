package xyz.dean.tutor_manager.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.dean.tutor_manager.common.ResponseCode;
import xyz.dean.tutor_manager.mapper.UserMapper;
import xyz.dean.tutor_manager.model.ResponseData;
import xyz.dean.tutor_manager.model.TokenModel;
import xyz.dean.tutor_manager.pojo.User;
import xyz.dean.tutor_manager.utlis.token.NoneAuth;
import xyz.dean.tutor_manager.utlis.token.TokenHelper;

import java.io.File;
import java.io.IOException;

@RestController
public class UserController {
    @Autowired
    UserMapper userMapper;
    @Autowired
    TokenHelper tokenHelper;
    @Autowired
    ResourceLoader resourceLoader;
    
    @NoneAuth
    @RequestMapping("/login")
    public ResponseData login(String username, String password) {
        ResponseData response;
        User user = userMapper.findUserByName(username);

        if (user == null) {
            response = ResponseData.errorResponse(ResponseCode.NOT_REGISTER);
        }else if (user.getPassword().equals(password)) {
            TokenModel model = tokenHelper.create(username);
            response = ResponseData.successResponse(model);
        } else {
            response = ResponseData.errorResponse(ResponseCode.WRONG_PWD);
        }
        return response;
    }

    @NoneAuth
    @RequestMapping("/register")
    public ResponseData register(@RequestBody User user) {
        ResponseData response;
        User findUser = userMapper.findUserByName(user.getUsername());

        if (findUser != null) {
            response = ResponseData.errorResponse(ResponseCode.NAME_IS_TAKEN);
        } else {
            int result = userMapper.registerUser(user);
            if (result < 1) {
                response = ResponseData.errorResponse(ResponseCode.DB_ERROR);
            } else {
                response = ResponseData.successResponse(null);
            }
        }
        return response;
    }

    @RequestMapping("/update")
    public ResponseData update(@RequestHeader String auth, @RequestBody User user) {
        ResponseData response;
        TokenModel token = tokenHelper.get(auth);
        if (token.getUsername() != null && token.getUsername().equals(user.getUsername())) {
            int result = userMapper.update(user);
            if (result < 1) {
                response = ResponseData.errorResponse(ResponseCode.DB_ERROR);
            } else {
                response = ResponseData.successResponse(null);
            }
        } else {
            response = ResponseData.unknownError(null);
        }
        return response;
    }

    @RequestMapping("/logout")
    public ResponseData logout(@RequestHeader String auth) {
        ResponseData response;
        TokenModel token = tokenHelper.get(auth);
        boolean result = tokenHelper.delete(token);
        if (result) {
            response = ResponseData.successResponse(null);
        } else {
            response = ResponseData.errorResponse(ResponseCode.INVALID_TOKEN);
        }
        return response;
    }

    @RequestMapping("/user")
    public ResponseData get(String username) {
        User user = userMapper.findUserByName(username);
        System.out.println(user.getAvatarUrl());
        return ResponseData.successResponse(user);
    }

    @RequestMapping("/uploadAvatar")
    public ResponseData uploadAvatar(@RequestHeader String auth,
                                     @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseData.unknownError(null);
        }
        ResponseData response;
        TokenModel token = tokenHelper.get(auth);
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  //获取后缀名
        String destPath = "E://avatar/" + token.getUsername() + suffixName;

        File dest = new File(destPath);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
             file.transferTo(dest);
             String avatarUrl = "/avatar/" + token.getUsername() + suffixName;
             userMapper.setAvatar(avatarUrl, token.getUsername());
             response = ResponseData.successResponse(avatarUrl);
        } catch (IOException e) {
            response = ResponseData.unknownError(e);
            e.printStackTrace();
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
