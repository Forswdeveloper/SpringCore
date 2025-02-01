package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("fixDiscountPolicy")
public class FixDiscountPolicy implements DiscountPolicy {
    //Qualifier 매칭
    //1. @Qualifier 끼리 매칭, 2. 빈 이름 매칭 3. NoSuchBeanDefinitionException
    // 단점. 타입 체크가 안됨 -> 어노테이션 생성.
    private int discountFixMount = 1000;

    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == Grade.VIP) {
            return discountFixMount;
        } else {
            return 0;
        }
    }
}
