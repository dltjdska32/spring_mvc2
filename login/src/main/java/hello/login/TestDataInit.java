package hello.login;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestDataInit {
    private final MemberRepository memberRepository;


    /**
    *  테스트 데이터 추가
     *
    * */
    @PostConstruct
    public void init() {

        Member member = new Member();
        member.setLoginId("test");
        member.setPassword("test1");
        member.setName("테스터");

        memberRepository.save(member);
    }
}
