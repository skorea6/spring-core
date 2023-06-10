package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// @Component 를 생성하면, 빈을 자동으로 생성한다.
// 빈 이름: orderServiceImpl , 빈 객체 : orderServiceImpl@x03

@Component
// @RequiredArgsConstructor -> '롬복' (lombok) final 이 붙은 필드를 모아서 '생성자를 자동으로 생성'! 즉, 밑에 @Autowired 쓰여진 생성자 전체를 없애고 대체할 수 있음.
public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository ; // final 은 필수!! 값을 불변으로 만들어준다!! 컴파일 오류나면 찾기 편리!
    private final DiscountPolicy discountPolicy;

    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName,  int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
