package hello.core.scope;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.assertj.core.api.Assertions.assertThat;

public class PrototypeTest {

    // 싱글톤: 기본 스코프, 스프링 컨테이너의 시작과 종료까지 유지되는 '가장 넓은 범위'의 스코프이다.
    //      - 항상 '같은 인스턴스'의 스프링 빈을 반환

    // 프로토타입: 스프링 컨테이너는 프로토타입 빈의 생성과 의존관계 주입까지만 관여하고 더는 관리하지 않는 '매우 짧은 범위'의 스코프이다.
    //      - 항상 '새로운 인스턴스'를 생성해서 반환
    //      - 스프링 컨테이너는 프로토타입 빈을 생성하고, 의존관계 주입, 초기화까지만 처리
    //      - 프로토타입 빈은 프로토타입 빈을 조회한 '클라이언트가 관리'해야 한다. 종료 메서드에 대한 호출도 클라이언트가 직접 해야한다.

    @Test
    void prototypeBeanFind(){
        // 두 빈은 다른 인스턴스
        // 싱글톤 빈은 스프링 컨테이너 생성 시점에 초기화 메서드가 실행 되지만, 프로토타입 스코프의 빈은 스프링 컨테이너에서 빈을 조회할 때 생성되고, 초기화 메서드도 실행된다.

        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        System.out.println("find prototypeBean1");
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        // 여기서 초기화 메소드 실행 (1번)
        System.out.println("find prototypeBean2");
        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        // 여기서 초기화 메소드 실행 (2번)
        System.out.println("prototypeBean1 = " + prototypeBean1);
        System.out.println("prototypeBean2 = " + prototypeBean2);
        assertThat(prototypeBean1).isNotSameAs(prototypeBean2);

        // @PreDestroy 호출 안됨!! 초기화까지만 관여하고 더이상 관리X
        ac.close();
    }

    @Scope("prototype") // prototype 이라고 명시해주어야 한다!
    static class PrototypeBean{
        @PostConstruct
        public void init(){
            System.out.println("PrototypeBean.init");
        }

        @PreDestroy
        public void destroy(){
            System.out.println("PrototypeBean.destroy");
        }
    }
}
