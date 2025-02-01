package hello.core.member;

import org.springframework.beans.factory.annotation.Qualifier;

public interface MemberRepository {
    void save(Member member);

    Member findById(Long memberId);
}
