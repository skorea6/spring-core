package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {
    public static void main(String[] args){
        // 이렇게 테스트하는 것은 좋은 방법이 아니다.
        // Junit 테스트를 이용하자.

        // 1. AppConfig 에서 가져오는 방법 (순수 JAVA 방법)
        //AppConfig appConfig = new AppConfig();
        //MemberService memberService = appConfig.memberService();


        // 2. Bean 을 통해 직접적으로 접근하는 방법 (자바 스프링 방법)

        // ApplicationContext -> '스프링 컨테이너' (인터페이스)라 함. BeanFactory 기능을 상속받음.
//        * 메시지소스를 활용한 국제화 기능
//        ex: 한국에서 들어오면 한국어로, 영어권에서 들어오면 영어로 출력
//        * 환경변수
//        ex: 로컬, 개발, 운영등을 구분해서 처리
//        * 애플리케이션 이벤트
//        ex: 이벤트를 발행하고 구독하는 모델을 편리하게 지원
//        * 편리한 리소스 조회
//        ex: 파일, 클래스패스, 외부 등에서 리소스를 편리하게 조회


        // 스프링 컨테이너는 XML 기반 혹은 애노테이션 기반의 자바 설정 클래스(AppConfig)로 만들 수 있음.
        // AppConfig.class(인터페이스) -> 구성정보.
        // 스프링 컨테이너는 BeanDefinition(빈 설정 메타정보)를 기반으로 스프링 빈을 생성한다. BeanDefinition 안에는 AppConfig.class, appConfig.xml ..
        // AnnotationConfigApplicationContext -> AnnotatedBeanDefinitionReader -> AppConfig.class -> BeanDefinition(빈 메타정보 생성)

        // 스프링 컨테이너 안에 -> 스프링 빈 저장소 (빈 이름 / 빈 객체)가 있음. 빈이 저장됨.
        // ex: memberService (빈 이름) / MemberServiceImpl@345 (빈 객체)
        // * 빈 이름 중복 불가.

        // Bean Factory ( 스프링 컨테이너의 최상위 인터페이스 ) -> 스프링 빈을 관리하고 조회
        // BeanFactory 를 직접 사용할 일은 거의 없다.
        // 부가기능이 포함된 ApplicationContext 를 사용한다. BeanFactory 나 ApplicationContext 를 스프링 컨테이너라 한다.


        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);

        //MemberService memberService = new MemberServiceImpl();
        Member member = new Member(1L, "memberA", Grade.VIP);

        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        System.out.println("newMember = " + member.getName());
        System.out.println("findMember = "+ findMember.getName());
    }
}
