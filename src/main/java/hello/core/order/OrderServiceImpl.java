package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository = new MemoryMemberRepository();

    //private DiscountPolicy discountPolicy = new FixDiscountPolicy();
    //private DiscountPolicy discountPolicy = new RateDiscountPolicy();   OCP 위반


    private DiscountPolicy discountPolicy; //인터페이스에 의존하도록 변경했지만 NullPointException
    // 해결법 : 누군가 구현 객체를 대신 생성하고 주입해주어야 한다.

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {

        Member findMember = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(findMember, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
