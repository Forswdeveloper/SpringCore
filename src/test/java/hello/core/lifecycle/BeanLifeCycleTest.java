package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {
    @Test
    public void lifeCycleTest() {
        //close를 위한 설정. AnnotationConfigApplicationContext의 상위
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient client = ac.getBean(NetworkClient.class);
        ac.close();

        //객체 생성 -> 의존관계 주입. 필드 인젝션같은 경우 객체 생성 이후 의존관계 주입해야 함.
        //초기화라는건 일을 처음 시작을 위한 설정.
        //개발자가 의존관계 주입이 모두 완료된 시점을 아는 법 : 주입 완료되면 스프링빈에게 콜백메서드를 통해서 초기화 시점을 알려주는 다양한 기능을 제공.
        // 또한 스프링은 스프링 컨테이너가 종료되기 직전에 소멸 콜백을 준다. -=> 안전하게 종료 작업 진행.

        //스프링 빈의 라이프 사이글
        // 스프링 컨테이너 생성 -> 스프링 빈 생성(생성자) -> 의존관계 주입 (필드 인젝션) -> 초기화 콜백 -> 사용 -> 소멸전 콜백 -> 스프링 종료
        // 객체의 생성과 초기화를 분리해야함.생성자안에서 무거운 초기화 작업을 함께하는 것보다 객체를 생성하는 부분 과 초기화 하는 부분을 명화하게 나누어야함.
        // 외부 커넥션은 최초의 행위가 올 때까지 미룰 수 있음.분리하면 이런 장점도 있음.
        //스프링은 크게 3가지 방법으로 생명주기 콜백을 지원함
        //인터페이스(InitializingBean, DisposableBean) 설정정보에 초기화 메서드, 종료 메서드 지정, @PostConstruct, @PreDestory 어노테이션 지원
    }

    @Configuration
    static class LifeCycleConfig {
        // 스프링 코드에 의존하지 않음
        // 코드가 아니라 설정 정보를 사용하기 때문에 코드를 고칠 수 없는 외부 라이브러리에도 초기화, 종료 메서드를 적용할 수 있다.
        //@Bean destroyMethod는 default가 inferred(추론)인데 추론기능은 자동으로 close나 shutdown 메서드가 있으면 자동으로 호출해줌.
        // 종료 메서드를 적어주지 않아도 작동함. 사용하기 싫으면 destroyMethod = "" 사용.  AutoCloseable 알아보면 좋은?
//        @Bean(initMethod = "init", destroyMethod = "close")
        @Bean
        public NetworkClient networkClient() {
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://hello-spring.dev");
            return networkClient;
        }
    }
}
