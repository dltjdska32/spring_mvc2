package hello.login.web.login;

import hello.login.domain.login.LoginService;
import hello.login.domain.member.Member;
import hello.login.web.session.SessionConst;
import hello.login.web.session.SessionManager;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
    private final SessionManager sessionManager;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm loginForm) {
        return "login/loginForm";
    }

  //  @PostMapping("/login")
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


   // @PostMapping("/login")
    public String loginV2(@Valid @ModelAttribute("loginForm") LoginForm loginForm, BindingResult bindingResult, HttpServletResponse response) {
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

        //세션관리자릁 통해 세션 생성후, 회원 데이터 보관

        sessionManager.createSession(loginMember, response);
        return "redirect:/";

    }

    @PostMapping("/login")
    public String loginV3(@Valid @ModelAttribute("loginForm") LoginForm loginForm, BindingResult bindingResult, HttpServletRequest request) {
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
        //세션이 있으면 세션반환, 없으면 신규 세션 생성
        HttpSession session = request.getSession();
        //세션에 회원정보 보관(메모리에 저장)
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

  /*      //세션관리자릁 통해 세션 생성후, 회원 데이터 보관
        sessionManager.createSession(loginMember, response);*/


        return "redirect:/";

    }



    // @PostMapping("/logout")
    public String logout(HttpServletResponse response) {

        expire(response, "memberId");
        return "redirect:/";
    }







   // @PostMapping("/logout")
    public String logoutV2(HttpServletRequest request) {

        sessionManager.expire(request);
        return "redirect:/";
    }


    @PostMapping("/logout")
    public String logoutV3(HttpServletRequest request) {

        // 세션을가져온다.  getsession의 파라미터가 false면 세션이 있으면 가져오고 , 세션이 없으면 null 을반환
        HttpSession session = request.getSession(false);
        //세션이 있으면 세션과 세션에 저장한 데이터를 날린다.
        if(session != null) {
            session.invalidate();
        }


        return "redirect:/";
    }

    private static void expire(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
