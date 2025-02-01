package hello.core.discount;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.springframework.stereotype.Component;

@Component
//@Qualifier("mainDiscountPolicy")
@MainDiscountPolicy
//@Primary
public class RateDiscountPolicy implements DiscountPolicy{
    //Primary 는 기본값 처럼 동작하는 것이고 @Qualifier는 매우 상세하게 동작한다.
    // 스프링은 자동보다는 수동이, 넓은 범위의 선택권보단느 좁은 범위의 선택권이 우선순위가 높다.
    // 결국 @Qualifier가 우선권이 높다.
    private int discountPercent = 10;

    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == Grade.VIP) {
            return price * discountPercent / 100;
        } else {
            return 0;
        }
    }
}
