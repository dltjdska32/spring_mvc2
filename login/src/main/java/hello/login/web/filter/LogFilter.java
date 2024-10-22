package hello.login.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.UUID;

@Slf4j
public class LogFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("로그 필터 초기화");
    }

    // 고객의 http요청시 dofilter호출
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("로그 필터 실행");

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String requestURI = request.getRequestURI();

        String uuid = UUID.randomUUID().toString();

        try{
            log.info("Request [{}][{}]", uuid, requestURI);


            // 체인을 통해 다음 필터를 호출해 준다.
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
            throw e;
        } finally {
            log.info("Response [{}][{}]", uuid, requestURI);
        }
    }

    @Override
    public void destroy() {
        log.info("로그 필터 제거");
    }
}
