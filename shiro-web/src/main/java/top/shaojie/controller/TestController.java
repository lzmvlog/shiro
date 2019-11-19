package top.shaojie.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import top.shaojie.model.User;


/**
 * @author ShaoJie
 * @Date 2019/10/24
 */
@Controller
public class TestController {

    @PostMapping(value = "/subLogin")
    public String subLogin(User user) {
        try {
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
            subject.login(token);
        } catch (AuthenticationException e) {
            return e.getMessage();
        }
        return "index";
    }

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

}
