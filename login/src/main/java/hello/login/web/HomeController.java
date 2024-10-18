package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;

  //  @GetMapping
    public String home() {
        return "home";
    }

    //로그인 안한 사용자도 들어와야 하기때문에 required = false로둔다
    @GetMapping
    public String homeLogin(@CookieValue(name = "memberId", required = false) Long memberId, Model model) {

        // 로그인 성공 못한 즉 쿠키가없으면 home으로
        if(memberId == null) {
            return "home";
        }

        // 쿠키가있으면 실행 (로그인 성공했던적이있는 사용자)
        Member loginMember = memberRepository.findById(memberId);
        //만약 쿠키가 사라져서 로그인 맴버를 못찾는다면
        if(loginMember == null) {
            return "home";
        }


        // 쿠키 o, 쿠키에 담긴 멤버id로 찾은 맴버 o 일경우 실행
        model.addAttribute("member", loginMember);
        return "loginHome";
    }

}
