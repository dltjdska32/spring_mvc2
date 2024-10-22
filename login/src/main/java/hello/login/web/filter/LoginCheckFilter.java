package hello.login.web.filter;

import hello.login.web.session.SessionConst;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

@Slf4j
public class LoginCheckFilter implements Filter {

    // 경로중 로그인과 상관없이 들어와야하는 경로
    private static final String[] whiteList = {"/", "/members/add", "/login", "/logout", "/css/*"};


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String requestURI = request.getRequestURI();

        HttpServletResponse response = (HttpServletResponse) servletResponse;

        try{
            log.info("인증체크 필터 시작{}",requestURI);

            //만약 화이트리스트가 아니면 -> 로그인이 필요한 페이지일경우
            if(isLoginCheckPath(requestURI)) {
                log.info("인증 체크 로직 실행 {} ", requestURI);
                HttpSession session = request.getSession(false);

                //세션이 없거나  세션의 로그인 멤버가 없다면실행
                if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
                    log.info("미인증 사용자 요청{} " , requestURI);

                    // 로그인으로 redirect -> ?redirectURL=~~~ - 들어가려했던 페이지가 로그인이 필요한 페이지여서
                    // 로그인페이지로 팅길때 다시 redirect할 페이지를 응답으로 보내준다.
                    response.sendRedirect("/login?redirectURL=" + requestURI);
                    return;
                }
            }
            // 다음 필터 호출
            filterChain.doFilter(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            throw e; //톰캣까지 예외를 보내줌
        } finally{
            log.info("인증 체크 필터 종료{}", requestURI);

        }


    }


    /**
     *  화이트 리스트 인증 체크 x
     * */
    private boolean isLoginCheckPath(String requestURI){
        //화이트리스트와 requestUri가 매치되는가 확인 -> 화이트리스트와 매치되지 않는것 false
        return !PatternMatchUtils.simpleMatch(whiteList, requestURI);
    }
}
