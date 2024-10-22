package hello.login.web.interceptor;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Slf4j

public class LogInterceptor implements HandlerInterceptor {

    public static final String LOG_ID = "uuid";
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        String uuid = UUID.randomUUID().toString();


        // afterCompletion으로 넘기기위해 -> 로그찍기위해서
        request.setAttribute(LOG_ID, uuid);


        //@Requestmapping : handlerMethod
        // 정적리소스 : resourceHttprequestHandelr
        if(handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;

        }

        log.info("REQUEST [{}][{}][{}]", uuid, requestURI, handler);
        return true; //true를 리턴하면 다음 컨트롤러가 호출된다.
                    //false를 호출하면 여기서 끝남.
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle [{}]", modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String requestURI = request.getRequestURI();
        Object uuid = request.getAttribute(LOG_ID);

        log.info("afterCompletion [{}][{}][{}]", uuid, requestURI, handler);
        log.error("after completion error!!!",  ex);
    }
}
