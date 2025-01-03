package hello.login.web.session;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 세션관리
* */
@Component
public class SessionManager {
    public static final String SESSION_COOKIE_NAME = "mySessionId";
    private Map<String, Object> sessionStore = new ConcurrentHashMap<>();

    /**
    * 세션 생성
     * sessionId 생성(임의의 추정 불가능한 랜덤값);
     * 세션 저장소에 sessionId와 보관할 값 저장
     * sessionId로 응답 쿠키를 생성해서 클라이언트에 전달
    *
    * */
    public void createSession(Object value, HttpServletResponse response) {
        //sessionId 생성(임의의 추정 불가능한 랜덤값); -> 세션아이디 생성하고 값을 세션에 저장
        String sessionId = UUID.randomUUID().toString();
        sessionStore.put(sessionId, value);

        // 쿠키생성
        Cookie cookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
        response.addCookie(cookie);
    }

    /**
    * 세션 조회
    * */
    public Object getSession(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        Cookie sessionCookie = findCookie(cookies, SESSION_COOKIE_NAME);
        if (sessionCookie != null) {
            return sessionStore.get(sessionCookie.getValue());
        }

        return null;
    }

    /**
    * 세션 만료
    * */
    public void expire(HttpServletRequest request) {
        Cookie sessionCookie = findCookie(request.getCookies(), SESSION_COOKIE_NAME);
        if (sessionCookie != null) {
            sessionStore.remove(sessionCookie.getValue());
        }
    }

    private Cookie findCookie(Cookie[] cookies, String cookieName) {
           if(cookies != null) {
               return Arrays.stream(cookies).filter(cookie -> cookie.getName().equals(cookieName))
                       .findAny()
                       .orElse(null);
           }
           return null;
    }
}
