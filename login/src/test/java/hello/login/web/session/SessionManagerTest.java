package hello.login.web.session;

import hello.login.domain.member.Member;
import jakarta.servlet.http.HttpServletResponse;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class SessionManagerTest {
    SessionManager sessionManager = new SessionManager();

    @Test
    void sessionTest() {
        MockHttpServletResponse response = new MockHttpServletResponse();

        // 세션 생성 - 리스폰스에 세션id 담아서 응답으로 나감
        Member member = new Member();
        sessionManager.createSession(member, response);

        // 요청에 응답쿠키저장 -> 요청에 쿠키(sessionid)담아서 서버로 전송
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.setCookies(response.getCookies());

        //세션조회 -> 세션id를 통해서 sessionstore에서 member객체가져옴
        Object result = sessionManager.getSession(mockHttpServletRequest);
        assertThat(result).isEqualTo(member);


        //세션만료
        sessionManager.expire(mockHttpServletRequest);
        Object result2 = sessionManager.getSession(mockHttpServletRequest);
        assertThat(result2).isNull();
    }

}