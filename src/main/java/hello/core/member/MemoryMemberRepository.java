package hello.core.member;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

// @Component 를 생성하면, 빈을 자동으로 생성한다.
// 빈 이름: memoryMemberRepository , 빈 객체 : memoryMemberRepository@x03

@Component
public class MemoryMemberRepository implements MemberRepository{
    private static Map<Long, Member> store = new HashMap<>();

    @Override
    public void save(Member member){
        store.put(member.getId(), member);
    }

    @Override
    public Member findById(Long memberId){
        return store.get(memberId);
    }

}
