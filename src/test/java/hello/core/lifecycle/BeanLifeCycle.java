package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycle {
    @Test
    public void lifeCycleTest(){
        // 스프링 컨테이너 생성 -> 스프링 빈 생성 -> 의존관계 주입 -> 초기화 콜백 사용 -> 소멸전 콜백 -> 스프링 종료

        // 스프링이 지원하는 생명 주기 콜백
        // 1. 인터페이스 (InitializingBean, DisposableBean) -> 초기화, 소멸 메서드의 이름을 변경불가 : 사용X
        // 2. 설정 정보에 초기화 메서드, 종료 메서드 지정 -> 메서드 이름을 자유롭게 줄 수 있다, 코드를 고칠수 없는 외부 라이브러리에 용이
        // 3. (**권장) @PostConstruct, @PreDestory 애노테이션 -> 자바표준. 스프링이 아닌 다른 컨테이너에서도 작동.


        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        System.out.println("test1");
        NetworkClient client = ac.getBean(NetworkClient.class);
        ac.close(); // 스프링 컨테이너를 종료, ConfigurableApplicationContext 필요
    }

    @Configuration
    static class LifeCycleConfig{
        // @Bean(initMethod = "init", destroyMethod = "close") : 위의 2번 방식
        @Bean
        public NetworkClient networkClient(){
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://hello-spring.dev");
            return networkClient;
        }
    }
}
