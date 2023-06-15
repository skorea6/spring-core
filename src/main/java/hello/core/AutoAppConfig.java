package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration

// 스프링은 설정 정보가 없어도 '자동으로 스프링 빈을 등록'하는 '컴포넌트 스캔'이라는 기능을 제공한다.
// 또 의존관계도 자동으로 주입하는 @Autowired 라는 기능도 제공한다.

// 컴포넌트 스캔을 사용하면 @Configuration 이 붙은 설정 정보도 자동으로 등록되기 때문에,
// AppConfig, TestConfig 등 앞서 만들어두었던 설정 정보도 함께 등록되고, 실행되어 버린다.
// 그래서 excludeFilters 를 이용해서 설정정보는 컴포넌트 스캔 대상에서 제외했다. (보통의 경우엔 안적음)

// 컴포넌트 스캔은 이름 그대로 @Component 애노테이션이 붙은 클래스를 스캔해서 '스프링 빈'으로 등록한다.
// 스프링 빈을 등록하고 싶은 class 에 @Component 를 붙여주자.


// com.hello => 프로젝트 시작 루트, 여기에 AppConfig 같은 메인 설정 정보를 두고, @ComponentScan 애노테이션을 붙이고, basePackages 지정은 생략한다.
// 이렇게 하면 com.hello 를 포함한 하위는 모두 자동으로 컴포넌트 스캔의 대상이 된다.
// @SpringBootApplication 를 이 프로젝트 시작 루트 위치에 두는 것이 관례

// [컴포넌트 스캔 기본 대상]
// @Component : 컴포넌트 스캔에서 사용
// @Controlller : 스프링 MVC 컨트롤러에서 사용
// @Service : 스프링 비즈니스 로직에서 사용  (특별한처리 X, 비즈니스 계층을 인식하는데 도움)
// @Repository : 스프링 데이터 접근 계층에서 사용, 데이터 계층의 예외를 스프링 예외로 변환
// @Configuration : 스프링 설정 정보에서 사용 (스프링 빈이 싱글톤을 유지하도록 추가 처리)

@ComponentScan(
        basePackages =  "hello.core",
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {

}
