package hello.thymeleaf.basic;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/basic")
public class BasicController {
    @GetMapping("/text-basic")
    public String textBasic(Model model) {
        model.addAttribute("data", "Hello World!");
        return "basic/text-basic";
    }

    @GetMapping("/text-unescaped")
    public String textUnescaped(Model model) {
        model.addAttribute("data", "Hello <b> World! </b>");
        return "basic/text-unescaped";
    }

    @GetMapping("/variable")
    public String variable(Model model) {
        User user = new User("김구라", 20);
        User user2 = new User("고구마", 30);
        User user3 = new User("감자", 40);

        List<User> users = new ArrayList<User>();
        users.add(user);
        users.add(user2);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userA", user);
        map.put("userB", user2);
        map.put("userC", user3);

        model.addAttribute("user", user);
        model.addAttribute("users", users);
        model.addAttribute("userMap", map);

        return "basic/variable";
    }


    @GetMapping("/basic-objects")
    String basicObjects(Model model, HttpServletRequest request,
                        HttpServletResponse response, HttpSession session) {

        session.setAttribute("sessionData", "hi session");
        model.addAttribute("request", request);
        model.addAttribute("response", response);
        model.addAttribute("servletContext", request.getServletContext());
        return "basic/basic-objects";
    }

    @GetMapping("/date")
    public String date(Model model) {
        model.addAttribute("localDateTime", LocalDateTime.now());
        return "basic/date";
    }

    @GetMapping("/link")
    public String link(Model model) {
        model.addAttribute("param1", "data1");
        model.addAttribute("param2", "data2");
        return "basic/link";
    }


    @GetMapping("/literal")
    public String literal(Model model) {
        model.addAttribute("value", "spring!");
        return "basic/literal";
    }

    @GetMapping("operation")
    public String operation(Model model) {
        model.addAttribute("nullData", null);
        model.addAttribute("data", "spring!");
        return "basic/operation";
    }

    //빈등록 직접 html에서 직접 참조
    @Component("hello")
    static class HelloBean {
        public String hello(String name) {
            return "Hello " + name;
        }
    }



    @Data
    static class User {

        private String username;
        private int age;

        public User(String username, int age) {
            this.username = username;
            this.age = age;
        }
    }
}
