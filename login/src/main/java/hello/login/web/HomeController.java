package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.web.session.SessionConst;
import hello.login.web.session.SessionManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;
    private final SessionManager sessionManager;

    //  @GetMapping
    public String home() {
        return "home";
    }

    //로그인 안한 사용자도 들어와야 하기때문에 required = false로둔다
    //  @GetMapping
    public String homeLogin(@CookieValue(name = "memberId", required = false) Long memberId, Model model) {

        // 로그인 성공 못한 즉 쿠키가없으면 home으로
        if (memberId == null) {
            return "home";
        }

        // 쿠키가있으면 실행 (로그인 성공했던적이있는 사용자)
        Member loginMember = memberRepository.findById(memberId);
        //만약 쿠키가 사라져서 로그인 맴버를 못찾는다면
        if (loginMember == null) {
            return "home";
        }


        // 쿠키 o, 쿠키에 담긴 멤버id로 찾은 맴버 o 일경우 실행
        model.addAttribute("member", loginMember);
        return "loginHome";
    }

   // @GetMapping
    public String homeLoginV2(HttpServletRequest request, Model model) {

        //세션관리자에 저장된 회원정보 조회
        Member loginMember = (Member) sessionManager.getSession(request);

        // 로그인
        if (loginMember == null) {
            return "home";
        }


        // 쿠키 o, 쿠키에 담긴 멤버id로 찾은 맴버 o 일경우 실행
        model.addAttribute("member", loginMember);
        return "loginHome";
    }


   // @GetMapping
    public String homeLoginV3(HttpServletRequest request, Model model) {

        // 세션이 있으면 반환, 없으면 null 반환
        HttpSession session = request.getSession(false);

        // 세션이없으면 home화면으로
        if(session == null) {
            return "home";
        }

        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        //세션이 있으면 loginhome으로 이동
        model.addAttribute("member", loginMember);
        return "loginHome";
    }

    @GetMapping
    public String homeLoginV3Spring(
            @SessionAttribute(name=SessionConst.LOGIN_MEMBER
                    , required = false) Member loginMember
            , Model model) {


        if(loginMember == null) {
            return "home";
        }

        //세션이 있으면 loginhome으로 이동
        model.addAttribute("member", loginMember);
        return "loginHome";
    }



}
