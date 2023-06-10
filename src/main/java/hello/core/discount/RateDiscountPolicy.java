package hello.core.discount;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

// @Component 를 생성하면, 빈을 자동으로 생성한다.
// 빈 이름: rateDiscountPolicy , 빈 객체 : rateDiscountPolicy@x01

// rate도 @component 를 쓰고, fix도 @component를 써서 빈을 생성하면, @Autowired 를 쓸때 스프링 컨테이너가 자동으로 해당 빈을 찾을 수 있는가? 중복이거든.
// -> 에러 발생. 중복을 허용하지 않는다.

// [조회 대상 빈이 2개 이상일때 해결 방법]
// 1. Autowired 필드명 매칭
//      - 타입 매칭
//      - 타입 매칭의 결과가 2개 이상일 때 필드 명, 파라미터 명으로 빈 이름 매칭 / @Autowired private DiscountPolicy rateDiscountPolicy
// 2. @Qualifier 사용
//      - @Qualifier("mainDiscountPolicy") , 커포넌트쪽에도 써주고, 생성자쪽에도 써주기. (@Qualifier("mainDiscountPolicy") DiscountPolicy)
//      - @Qualifier 끼리 매칭 -> 빈 이름 매칭
// 3. @Primary 사용
//      - 우선순위를 정함. @Autowired 시에 여러 빈이 매칭되면 @Primary 가 우선권을 가진다.
//      - 커포넌트쪽에만 붙여주면 됨.

// * 애노테이션을 직접 만들어서 사용하는 방법도 존재.
// @MainDiscountPolicy
// 커포넌트쪽에도 써주고, 생성자쪽에도 써주기. (@MainDiscountPolicy DiscountPolicy)

@Component
@MainDiscountPolicy
public class RateDiscountPolicy implements DiscountPolicy{
    private int discountPercent = 10;

    @Override
    public int discount(Member member, int price) {
        if(member.getGrade() == Grade.VIP){
            return price*discountPercent/100;
        }else{
            return 0;
        }
    }
}
