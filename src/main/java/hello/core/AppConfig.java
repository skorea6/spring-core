package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// AppConfig 처럼 객체를 생성하고 관리하면서 의존관계를 연결해 주는 것을 'DI 컨테이너라' 한다.

// @Configuration : 이거를 안쓰고, @Bean만 사용해도 스프링 빈으로 등록되지만, 싱글톤을 보장하지 않는다.
// memberRepository() 처럼 의존관계 주입이 필요해서 메서드를 직접 호출할 때 싱글톤을 보장하지 않는다. (한개의 객체만 만들어져야 하는데 여러개의 객체가 만들어짐)
// 그러므로 무조건 쓰자.


@Configuration
public class AppConfig {
    // memberService (빈 이름) / MemberServiceImpl@345 (빈 객체)
    @Bean
    public MemberService memberService(){
        // 클라이언트인 memberServiceImpl 입장에서 보면
        // 의존관계를 마치 외부에서 주입해주는 것 같다고 해서
        // 'DI'(Dependency Injection) 우리말로 '의존관계 주입' 또는 '의존성 주입'이라 한다.

        // 의존관계 주입을 사용하면 클라이언트 코드를 변경하지 않고, 클라이언트가 호출하는 대상의 타입 인스턴스 를 변경할 수 있다.
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        // 여기서 memory 로 저장할건지, db에 저장할건지를 간편하게 바꿀수 있음. -> DI 장점.
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService(){
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy(){
        return new RateDiscountPolicy();
    }

}
