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
* 순수 자바를 통해 AppConfig Bean에 접근
  ```
  AppConfig appConfig = new AppConfig();
  MemberService memberService = appConfig.memberService();
  ```
* 자바 스프링을 통해 Appconfig Bean에 접근
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


## AutoAppConfig / Component Scan
- 스프링은 설정 정보가 없어도 '자동으로 스프링 빈을 등록'하는 '컴포넌트 스캔'이라는 기능을 제공한다.
- 또 의존관계도 자동으로 주입하는 @Autowired 라는 기능도 제공한다.
- 컴포넌트 스캔은 이름 그대로 @Component 애노테이션이 붙은 클래스를 스캔해서 '스프링 빈'으로 등록한다. 스프링 빈을 등록하고 싶은 class 에 @Component 를 붙여주자.
* [컴포넌트 스캔 기본 대상]
  - @Component : 컴포넌트 스캔에서 사용
  - @Controlller : 스프링 MVC 컨트롤러에서 사용
  - @Service : 스프링 비즈니스 로직에서 사용  (특별한처리 X, 비즈니스 계층을 인식하는데 도움)
  - @Repository : 스프링 데이터 접근 계층에서 사용, 데이터 계층의 예외를 스프링 예외로 변환
  - @Configuration : 스프링 설정 정보에서 사용 (스프링 빈이 싱글톤을 유지하도록 추가 처리)

## Component, Autowired
- @Component 를 생성하면, 빈을 자동으로 생성한다. (예: 빈 이름 memberServiceImpl , 빈 객체 memberServiceImpl@x01)
- @Autowired 는 '의존관계를 자동으로 주입'해준다. 생성자에 @Autowired 를 지정하면, 스프링 컨테이너가 '자동으로 해당 스프링 빈을 찾아서' 주입한다!
- 기본 조회 전략은 '타입이 같은 빈'을 찾아서 주입한다.
* [DI 위반 예시]
  ```
  private final MemberRepository memberRepository = new MemoryMemberRepository();
  ```
  - 서비스계층의 클라이언트는 어떤 repository 를 받는지 모른 상태에서 작업한다.
* [빈 자동 주입 여러가지 방법]
  1. 생성자 주입 (권장)
     - 생성자 주입은 객체를 생성할 때 딱 1번만 호출되므로 이후에 호출되는 일이 없다. 따라서 '불변'하게 설계할 수 있다.
     - 생성자가 딱 1개면, @Autowired 생략해도 자동주입 -> 스프링 빈만 해당
     - 위에 필드에 final 키워드 사용 가능.
  2. 수정자 주입 (setter 주입)
     - 선택, 변경 가능성이 있는 의존관계에 사용
  3. 필드 주입
     - 필드에 바로 주입. 외부에서 변경이 불가하여 테스트가 힘들기 때문에 사용X
  4. 일반 메서드 주입
 
* [스프링빈 없이 작동하고 싶은 경우 - Autowired 옵션]
  - @Autowired(required=false) -> 아예 실행이 안됨
  - @Autowired 후, (@Nullable Member member) -> null
  - @Autowired(required=false) 후, (Optional<Member> member) -> Optional.empty


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
   
## Lombok
* @RequiredArgsConstructor
  - final 이 붙은 필드를 모아서 '생성자를 자동으로 생성'
  - @Autowired 쓰여진 생성자 전체를 없애고 대체할 수 있음.

 ## Singleton
 1. 스프링 없는 순수한 DI 컨테이너
    - 스프링 없는 순수한 DI 컨테이너인 AppConfig 는 요청을 할 때 마다 객체를 새로 생성한다.
    - 고객 트래픽이 초당 100이 나오면 초당 100개 객체가 생성되고 소멸된다! -> 메모리 낭비가 심하다.
    - 해결방안은 해당 객체가 '딱 1개'만 생성되고, 공유하도록 설계하면 된다. -> '싱글톤 패턴'
 2. 싱글톤 패턴을 적용한 객체사용
    - 클래스의 인스턴스가 딱 1개만 생성되는 것을 보장하는 디자인 패턴
    - private 생성자를 사용해서 외부에서 임의로 new 키워드를 사용하지 못하도록 막아야 한다 (객체 인스턴스 2개 이상 생성 방지)
     1. static 영역에 객체를 딱 1개만 생성해둔다.
      ```
      private static final SingletonService instance = new SingletonService();
      ```
     2. public 으로 열어서 객체 인스터스가 필요하면 이 static 메서드를 통해서만 조회하도록 허용한다. (항상 같은 Instance 반환!)
      ```
      public static SingletonService getInstance(){
          return instance;
      }
      ```
     3. 생성자를 private 으로 선언해서 외부에서 new 키워드를 사용한 객체 생성을 못하게 막는다
      ```
      private SingletonService(){ }
      ```
    * [싱글톤 패턴 문제점]
      - 싱글톤 패턴을 구현하는 코드 자체가 많이 들어간다.
      - 의존관계상 클라이언트가 구체 클래스에 의존한다. DIP를 위반한다. 클라이언트가 구체 클래스에 의존해서 OCP 원칙을 위반할 가능성이 높다. 테스트하기 어렵다.
      - 내부 속성을 변경하거나 초기화 하기 어렵다.
      - private 생성자로 자식 클래스를 만들기 어렵다.
      - 결론적으로 유연성이 떨어진다.
      - 안티패턴으로 불리기도 한다.
  3. 스프링 컨테이너와 싱글톤 (싱글톤 컨테이너)
     - 스프링 빈이 싱글톤으로 관리되는 '빈'
     - 스프링 컨테이너는 싱글턴 패턴을 적용하지 않아도, 객체 인스턴스를 '싱글톤'으로 관리
     - 스프링 컨테이너는 '싱글톤 컨테이너' 역할. 이렇게 싱글톤 객체를 생성하고 관리하는 기능을 '싱글톤 레지스트리'라 한다.
     - 지저분한 코드 필요X
     - DIP, OCP, 테스트, private 생성자로 부터 자유롭게 싱글톤을 사용할 수 있다.
  4. Stateful
     - 여러 클라이언트가 '하나의 같은 객체 인스턴스'를 공유하기 때문에 싱글톤 객체는 상태를 유지 (stateful)하게 설계하면 안된다.
     * '무상태(stateless)'로 설계해야 한다!
       - 의존적인 필드나 클라이언트가 변경할 수 있는 코드가 있으면 안됨! (공유값 설정X)
       - 가급적 읽기만.
  ```
  private int price;  // 상태를 유지하는 필드 (공유 되는 필드)
  ```
  ```
  public void order(String name, int price) {
      System.out.println("name = " + name + "price = " + price);
      this.price = price; // 여기가 문제! => 상태를 변경하면 안됨!!!
  }
  ```
    


인프런 강의: 스프링 핵심 원리 - 기본편
https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81-%ED%95%B5%EC%8B%AC-%EC%9B%90%EB%A6%AC-%EA%B8%B0%EB%B3%B8%ED%8E%B8/dashboard
