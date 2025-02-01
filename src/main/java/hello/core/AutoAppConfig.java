package hello.core;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
//        basePackages = "hello.core.member",
//        basePackageClasses = AutoAppConfig.class,
        excludeFilters = @ComponentScan.Filter(type= FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {
    //컴포턴트 스캔은 @Component 어노테이션이 붙은 클래스를 스캔에서 스프링 빈으로 등록한다.
    //기존엔 의존관계 주입을 직접 설정해줬지만 @Autowired를 명시함으로 자동으로 주입하게된다.
    //타입이 비슷한게 등록되어있는지 체크하고 등록함.
    //설정 정보 클래스의 위치를 프로젝트 최상단에 두어 모든 패키지를 탐색하게 하는게 권장 방식.
    //프로젝트의 메인 설정 정보는 스프링 부트 대표 시작 정보인 @SpringBootApplication를 프로젝트 시작 루트 위치에 두는 것이 관례.
    //스캔 기본 대상 : @Component, @Controller, @Service, @Repository, @Configuration
    //어노테이션은 상속관계가 없어서 서로 연동되지는 않는다. 자바 언어가 지원하는 기능이 아닌 스프링이 지원하는 기능이다.
//    @Bean(name = "memoryMemberRepository")
//    public MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
//    }
}
