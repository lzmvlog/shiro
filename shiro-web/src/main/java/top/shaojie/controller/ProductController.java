package top.shaojie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author ShaoJie
 * @Date 2019/10/17
 */
@Controller
@RequestMapping("product")
public class ProductController {

    @GetMapping("add")
    public String add(){
        return "product/add";
    }

    @GetMapping("update")
    public String update(){
        return "product/update";
    }

    @GetMapping("list")
    public String list(){
        return "product/list";
    }

    @GetMapping("delete")
    public String delete(){
        return "product/delete";
    }

}
