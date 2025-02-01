package hello.core.lifecycle;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class NetworkClient {
//    implements InitializingBean, DisposableBean
    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출 , url = " + url);
//        connect();
//        call("초기화 연결 메세지");
    }

    public void setUrl(String url) {
        this.url = url;
    }

    //서비스 시작 시 호출
    public void connect() {
        System.out.println("connect = " + url);
    }

    public void call(String message) {
        System.out.println("call : " + url + " message = " + message);
    }

    //서비스 종료 시 호출
    public void disconnect() {
        System.out.println("disconnect = " + url);
    }

    //이 인터페이스는 스프링 전용 인터페이스로 해당코드가 스프링 전용 인터페이스에 의존한다.
    // 이름 변경 불가능 코드를 고칠 수 없는 외부 라이브러리에 적용할 수 없다. 거의 사용 안함.

//    @Override
//    public void afterPropertiesSet() throws Exception {
//        //의존관계 주입이 끝나면 호출
//        System.out.println("NetworkClient AfterPropertiesSet");
//        connect();
//        call("초기화 연결 메세지");
//    }
//
//    @Override
//    public void destroy() throws Exception {
//        //종료 시점
//        System.out.println("NetworkClient destory");
//        disconnect();
//    }
    //결론적으로 어노테이션을 써야함. java에서 공식 지원하는 것.
    // 유일한 단점은 외부 라이브러리에 적용을 못한다. 외부 라이브러리를 초기화 , 종료해야하면 @Bean 기능을 사용하자.
    // component scan 과 잘 어울림.
    @PostConstruct
    public void init() {
        //의존관계 주입이 끝나면 호출
        System.out.println("NetworkClient init");
        connect();
        call("초기화 연결 메세지");
    }

    @PreDestroy
    public void close() {
        //종료 시점
        System.out.println("NetworkClient destory");
        disconnect();
    }
}
