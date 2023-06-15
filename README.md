# spring-core
### 스프링 핵심 원리

* Spring Conatiner
* AppConfig, AutoAppConfig
* Bean (Bean Factory, Bean Repository, BeanDefinition ..)
* ComponentScan (@Component, @Controller, @Service, @Repository, @Configuration)
* DI (@Autowired)
* Lombok (@RequiredArgsConstructor)
* Scope
* Singleton (Singleton Pattern, Spring Singleton, Stateless..)


### AppConfig / DI
- AppConfig 처럼 객체를 생성하고 관리하면서 의존관계를 연결해 주는 것을 'DI 컨테이너라' 한다.
- @Configuration : 안쓰고, @Bean만 사용해도 스프링 빈으로 등록되지만, 싱글톤을 보장하지 않는다. memberRepository() 처럼 의존관계 주입이 필요해서 메서드를 직접 호출할 때 싱글톤을 보장하지 않는다. (한개의 객체만 만들어져야 하는데 여러개의 객체가 만들어짐)
- 그러므로 @Configuration 을 붙여준다.


- 클라이언트인 memberServiceImpl 입장에서 보면 의존관계를 마치 외부에서 주입해주는 것 같다고 해서 'DI'(Dependency Injection) 우리말로 '의존관계 주입' 또는 '의존성 주입'이라 한다.
- 의존관계 주입을 사용하면 클라이언트 코드를 변경하지 않고, 클라이언트가 호출하는 대상의 타입 인스턴스 를 변경할 수 있다.


### Appconfig Bean 접근 (자바 and 스프링)
* 순수 자바를 통해 AppConfig 접근
  ```
  AppConfig appConfig = new AppConfig();
  MemberService memberService = appConfig.memberService();
  ```
* 자바 스프링을 통해 Bean에 접근
  ```
  ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
  MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
  ```
  
  - ApplicationContext -> '스프링 컨테이너' (인터페이스)라 함. BeanFactory 기능을 상속받음.
    - 메시지소스를 활용한 국제화 기능 (ex: 한국에서 들어오면 한국어로, 영어권에서 들어오면 영어로 출력)
    - 환경변수 (ex: 로컬, 개발, 운영등을 구분해서 처리)
    - 애플리케이션 이벤트 (ex: 이벤트를 발행하고 구독하는 모델을 편리하게 지원)
    - 편리한 리소스 조회 (ex: 파일, 클래스패스, 외부 등에서 리소스를 편리하게 조회)
  - 스프링 컨테이너는 XML 기반 혹은 애노테이션 기반의 자바 설정 클래스(AppConfig)로 만들 수 있음.
    - AppConfig.class(인터페이스) -> 구성정보
    - 스프링 컨테이너는 BeanDefinition(빈 설정 메타정보)를 기반으로 스프링 빈을 생성한다. BeanDefinition 안에는 AppConfig.class, appConfig.xml ..
    - AnnotationConfigApplicationContext -> AnnotatedBeanDefinitionReader -> AppConfig.class -> BeanDefinition(빈 메타정보 생성)
  - 스프링 컨테이너 안에 -> 스프링 빈 저장소 (빈 이름 / 빈 객체)가 있음. 빈이 저장!
    - ex: memberService (빈 이름) / MemberServiceImpl@345 (빈 객체)
    - 단, 빈 이름 중복 불가!
  - Bean Factory (스프링 컨테이너의 최상위 인터페이스) -> 스프링 빈을 관리하고 조회
    - BeanFactory 를 직접 사용할 일은 거의 없다.
    - 부가기능이 포함된 ApplicationContext 를 사용한다. BeanFactory 나 ApplicationContext 를 스프링 컨테이너라 한다.


## AutoAppConfig / 컴포넌트 스캔
- 스프링은 설정 정보가 없어도 '자동으로 스프링 빈을 등록'하는 '컴포넌트 스캔'이라는 기능을 제공한다.


## 조회 대상 빈이 2개 이상일때 해결 방법
 1. Autowired 필드명 매칭
      - 타입 매칭
      - 타입 매칭의 결과가 2개 이상일 때 필드 명, 파라미터 명으로 빈 이름 매칭 / @Autowired private DiscountPolicy rateDiscountPolicy
 2. @Qualifier 사용
      - @Qualifier("mainDiscountPolicy") , 커포넌트쪽에도 써주고, 생성자쪽에도 써주기. (@Qualifier("mainDiscountPolicy") DiscountPolicy)
      - @Qualifier 끼리 매칭 -> 빈 이름 매칭
 3. @Primary 사용
      - 우선순위를 정함. @Autowired 시에 여러 빈이 매칭되면 @Primary 가 우선권을 가진다.
      - 커포넌트쪽에만 붙여주면 됨.


인프런 강의: 스프링 핵심 원리 - 기본편
https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81-%ED%95%B5%EC%8B%AC-%EC%9B%90%EB%A6%AC-%EA%B8%B0%EB%B3%B8%ED%8E%B8/dashboard
