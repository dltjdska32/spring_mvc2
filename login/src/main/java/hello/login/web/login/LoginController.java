package hello.login.web.login;

import hello.login.domain.login.LoginService;
import hello.login.domain.member.Member;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm loginForm) {
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginForm") LoginForm loginForm, BindingResult bindingResult, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }

        Member loginMember = loginService.login(loginForm.getLoginId(), loginForm.getPassword());

        //아이디 비밀번호 일치하지않을경우
        if (loginMember == null) {
            System.out.println("ㅇㅇㅇㅇ");
            //글로벌 오류 발생
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다");
            return "login/loginForm";
        }


        // 로그인 성공로직


        // 쿠키에 시간정보를 주지않으면 세션쿠키(브라우저 종료시 까지만 유지)
        // 로그인 성공시 쿠키 생성해서 응답에 담아보내줌.
        Cookie cookie = new Cookie("memberId", loginMember.getId().toString());
        response.addCookie(cookie);

        System.out.println("sssss");
        return "redirect:/";

    }

    @PostMapping("/logout")
    public String logout(HttpServletResponse response) {

        expire(response, "memberId");
        return "redirect:/";
    }

    private static void expire(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
