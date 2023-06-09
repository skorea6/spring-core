package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// @Component 를 생성하면, 빈을 자동으로 생성한다.
// 빈 이름: memberServiceImpl , 빈 객체 : memberServiceImpl@x01

@Component
public class MemberServiceImpl implements MemberService{
    // private final MemberRepository memberRepository = new MemoryMemberRepository();
    // 위와 같이 정의하면, DI 위반.
    // 서비스계층의 클라이언트는 어떤 repository 를 받는지 모른 상태에서 작업한다.

    private final MemberRepository memberRepository;

    // @Autowired 는 '의존관계를 자동으로 주입'해준다.
    // 생성자에 @Autowired 를 지정하면, 스프링 컨테이너가 '자동으로 해당 스프링 빈을 찾아서' 주입한다.
    // MemberRepository 를 보고, memoryMemberRepository 빈을 자동으로 주입해준다. (부모가 MemberRepository 이므로)
    // this.memberRepository = new MemoryMemberRepository(); 와 동일.
    // 즉, 기본 조회 전략은 '타입이 같은 빈'을 찾아서 주입한다.

    // 1. 생성자 주입 (생성자가 딱 1개면, @Autowired 생략해도 자동주입 -> 스프링 빈만 해당) **권장!!
    //              생성자 주입은 객체를 생성할 때 딱 1번만 호출되므로 이후에 호출되는 일이 없다. 따라서 '불변'하게 설계할 수 있다.
    //              위에 필드에 final 키워드 사용 가능.
    // 2. 수정자 주입 (setter 주입) -> 선택, 변경 가능성이 있는 의존관계에 사용
    // 3. 필드 주입 -> 필드에 바로 주입. 외부에서 변경이 불가하여 테스트가 힘들기 때문에 사용X
    // 4. 일반 메서드 주입

    // [스프링빈 없이 작동하고 싶은 경우 - 옵션처리]
    // @Autowired(required=false) -> 아예 실행이 안됨
    // @Autowired 후, (@Nullable Member member) -> null
    // @Autowired(required=false) 후, (Optional<Member> member) -> Optional.empty

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
