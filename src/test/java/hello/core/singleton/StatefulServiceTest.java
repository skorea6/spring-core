package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {
    @Test
    void statefulServiceSingleTon(){
        //  여러 클라이언트가 '하나의 같은 객체 인스턴스'를 공유하기 때문에 싱글톤 객체는 상태를 유지 (stateful)하게 설계하면 안된다.

        // '무상태(stateless)'로 설계해야 한다!
        // -> 의존적인 필드나 클라이언트가 변경할 수 있는 코드가 있으면 안됨! (공유값 설정X)
        // -> 가급적 읽기만.

        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        // ThreadA: A 사용자 만원 주문
        statefulService1.order("userA", 10000);
        // ThreadB: B 사용자 2만원 주문
        statefulService2.order("userB", 20000);


        // ThreadA: A 사용자 주문 금액 조회
        int price = statefulService1.getPrice();
        System.out.println("price = " + price);

        Assertions.assertThat(statefulService1.getPrice()).isEqualTo(20000);
    }

    static class TestConfig{
        @Bean
        public StatefulService statefulService(){
            return new StatefulService();
        }
    }

}