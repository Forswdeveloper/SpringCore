package hello.core.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class MemberServiceTest {
    public MemberService memberService = new MemberServiceImpl();
    @Test
    void join(){
        //give
        Member member = new Member(1L,"memberA",Grade.VIP);


        //when
        memberService.joinMember(member);
        Member findMember = memberService.findMember(1L);


        //then
        Assertions.assertThat(member).isEqualTo(findMember);
    }
}
