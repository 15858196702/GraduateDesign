package xyz.dean.tutor_manager.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello() throws Exception{
        throw new Exception("wtf?");
    }
}
