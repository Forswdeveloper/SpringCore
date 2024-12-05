package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.Order;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration //설정 정보임을 명시
public class AppConfig {
    //AppConfig는 생성한 객체 인스턴스의 참조를 생성자를 통해서 주입(Injection, 연결)한다.
    // 클라이언트 입장( memberServiceImpl 입장에서 보면 마치 외부에서 주입해주는 것 같다고 해서 DI(Dependency Injection) 의존관계주입 / 의존성 주입이라고한다.
    //역할의 구분
    //생성자 주입
    @Bean //스프링 컨테이너에 명시.
    private static MemoryMemberRepository memberRepository() {
        return new MemoryMemberRepository();  // -> 변경 필요 시 수정 부분
    }

    @Bean
    public MemberService memberService(){
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public OrderService orderService(){
        return new OrderServiceImpl(memberRepository(), discountPolicy());  //역할이 명시  -> 역할과 구현 클래스의 구분. 중복 제거
    }

    @Bean
    public DiscountPolicy discountPolicy(){
        //return new FixDiscountPolicy(); // -> 변경 필요 시 수정 부분
        return new RateDiscountPolicy();
    }
}
