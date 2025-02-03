package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.inject.Provider;

public class SingletonWithPrototypeTest1 {

    @Test
    void prototypeFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ProtoTypeBean.class);

        ProtoTypeBean prototypeBean1 = ac.getBean(ProtoTypeBean.class);
        prototypeBean1.addCount();
        Assertions.assertThat(prototypeBean1.getCount()).isEqualTo(1);

        ProtoTypeBean prototypeBean2 = ac.getBean(ProtoTypeBean.class);
        prototypeBean2.addCount();
        Assertions.assertThat(prototypeBean2.getCount()).isEqualTo(1);
    }

    @Test
    void singletonClientUsePrototype() {
        AnnotationConfigApplicationContext ac =
                new AnnotationConfigApplicationContext(ClientBean.class, ProtoTypeBean.class);

        ClientBean clientBeanA = ac.getBean(ClientBean.class);
        int count1 = clientBeanA.logic();
        Assertions.assertThat(count1).isEqualTo(1);

        ClientBean clientBeanB = ac.getBean(ClientBean.class);
        int count2 = clientBeanB.logic();
        Assertions.assertThat(count2).isEqualTo(1);

        // 프로토타입빈이 싱글톤 빈과 함게 계속 유지되는 것이 문제.
        // 사용할 때마다 새로 생성하는것을 원함.
        // 여러 빈에서 같은 프로토타입 빈을 주입 받으면 주입 받는 시점에 새로운 프로토타입 빈이 생성된다. 하지만 사용할 때마다 생성되지는 않는다.
    }

    @Scope("singleton")
    static class ClientBean {
//        private final ProtoTypeBean protoTypeBean;

        @Autowired
        private Provider<ProtoTypeBean> protoTypeBeanProvider;
//        private ObjectProvider<ProtoTypeBean> prototypeBeanProvider; //DL -> 기존 ObjectFactory에서 편의기능을 더 넣은 것.

//        @Autowired
//        ApplicationContext applicationContext;
//        @Autowired
//        public ClientBean(ProtoTypeBean protoTypeBean) {
//            this.protoTypeBean = protoTypeBean;
//            System.out.println("ClientBean = " + protoTypeBean);
//        }

        public int logic() {
//            ProtoTypeBean protoTypeBean = applicationContext.getBean(ProtoTypeBean.class);
            // 의존관계를 외부에서 주입 받는게 아니라, 이렇게 직접 필요한 의존관계를 찾는 것을 DL(Dependency Lookup)이라 한다.
            // 딱 DL 기능을 하는게 필요함.
            // 1. ObjectProvider<T>
            // 2. Provider get을 호출하면 해당 빈을 찾아서 반환함, 기능이 단순하여 단위테스트를 만듥거나 mock 코드를 만들기는 훨씬 쉽다. 대신
            // 딱 필요한 기능만 제공함.

            // 다른 컨테이너에서도 사용할 수 있어야 할 경우 Provider 사용
            ProtoTypeBean protoTypeBean = protoTypeBeanProvider.get();
            protoTypeBean.addCount();
            int count = protoTypeBean.getCount();
            return count;
        }
    }


    @Scope("prototype")
    static class ProtoTypeBean {
        private int count = 0;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.inti " + this);
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }
    }
}
