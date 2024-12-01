package hello.core;

import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.Order;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

public class AppConfig {
    //AppConfig는 생성한 객체 인스턴스의 참조를 생성자를 통해서 주입(Injection, 연결)한다.
    // 클라이언트 입장( memberServiceImpl 입장에서 보면 마치 외부에서 주입해주는 것 같다고 해서 DI(Dependency Injection) 의존관계주입 / 의존성 주입이라고한다.
    //역할의 구분
    //생성자 주입
    public MemberService memberService(){
        return new MemberServiceImpl(new MemoryMemberRepository());
    }

    public OrderService orderService(){
        return new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());
    }
}
