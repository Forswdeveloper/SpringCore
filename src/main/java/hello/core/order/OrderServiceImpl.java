package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService{

    //private final MemberRepository memberRepository = new MemoryMemberRepository();
    //private DiscountPolicy discountPolicy = new FixDiscountPolicy();
    //private DiscountPolicy discountPolicy = new RateDiscountPolicy();   OCP 위반 구체적 정책을 impl이 직접 선택해서 할당하는 방식

    //final 선언시 무조건 생성자로 주입되어야함.
    //DIP 준수
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy; //인터페이스에 의존하도록 변경했지만 NullPointException
    // 해결법 : 누군가 구현 객체를 대신 생성하고 주입해주어야 한다.

    @Autowired //생성자에서 여러 의존관계 한 번에 주입.
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {

        Member findMember = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(findMember, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    //테스트용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
