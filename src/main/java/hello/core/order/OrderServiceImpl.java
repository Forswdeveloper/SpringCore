package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
//@RequiredArgsConstructor //final이 붙은 변수를 생성자로 만들어줌.
public class OrderServiceImpl implements OrderService{

    //private final MemberRepository memberRepository = new MemoryMemberRepository();
    //private DiscountPolicy discountPolicy = new FixDiscountPolicy();
    //private DiscountPolicy discountPolicy = new RateDiscountPolicy();   OCP 위반 구체적 정책을 impl이 직접 선택해서 할당하는 방식

    //final 선언시 무조건 생성자로 주입되어야함.
    //DIP 준수
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy; //인터페이스에 의존하도록 변경했지만 NullPointException
    // 해결법 : 누군가 구현 객체를 대신 생성하고 주입해주어야 한다.

//    private MemberRepository memberRepository;
//    private DiscountPolicy discountPolicy;
//    @Autowired private MemberRepository memberRepository;
//    @Autowired private DiscountPolicy discountPolicy;

    //Setter(수정자)로 의존성 주입
    //변경 가능성 있는 정보에 사용.
//    @Autowired
//    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
//        this.discountPolicy = discountPolicy;
//    }
//
//    @Autowired(required = false)
//    public void setMemberRepository(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    //@Autowired 매칭 1. 타입 매칭 2. 타입 매칭의 결과가 두개 이상일 때 필드명, 파라미터 명으로 빈 이름 매칭.
    @Autowired //생성자에서 여러 의존관계 한 번에 주입. 생성자가 하나면 생략가능.
//    public OrderServiceImpl(MemberRepository memberRepository, @Qualifier("mainDiscountPolicy") DiscountPolicy discountPolicy) {
    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
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
