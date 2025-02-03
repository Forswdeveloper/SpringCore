package hello.core.common;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
//@Scope(value = "request")
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
//CGLIB라는 라이브러리로 내 클래스를 상속 받은 가짜 프록스 클래스를 만들어서 주입
//가짜 프록시 객체는 요청이 오면 그 때 내부에서 진짜 빈을 요청하는 위임 로직이 들어있다.
//가짜 프록시 객체는 원본 클래스를 상속받아서 만들어졌기 때문에 원본인지 아닌지도 모르게 동일하게 사용 가능.(다형성)
//다형성과 Di컨테이너가 가진 큰 강점.
public class MyLogger {
    //Http 요청 당 하나씩 생성됨. 요청이 끝나면 소멸.
    private String uuid;
    private String requestURL;

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public void log(String message) {
        System.out.println("[" + uuid + "]" + "[" + requestURL + "] " + message);
    }

    @PostConstruct
    public void init() {
        uuid = UUID.randomUUID().toString();
        System.out.println("[" + uuid + "] request scope bean create: " + this);
    }

    @PreDestroy
    public void close() {
        System.out.println("[" + uuid + "] request scope bean close: " + this);
    }
}
