package net.atopecode.pisthorioctest;

import net.atopecode.pisthorioctest.dependencies.Repository1;
import net.atopecode.pisthorioctest.dependencies.Repository2;
import net.atopecode.pisthorioctest.dependencies.Service1;
import net.atopecode.pisthorioctest.dependencies.Service2;
import net.atopecode.pisthorioctest.dependencies.interfaces.Controller;
import net.atopecode.pisthorioctest.dependencies.interfaces.IRepository;
import net.atopecode.pisthorioc.ioccontainer.IocContainer;
import net.atopecode.pisthorioc.ioccontainer.IocContainerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {
    public final static Logger LOGGER = LoggerFactory.getLogger(IocContainer.class);

    public final static String REPOSITORY1 = "repository1";
    public final static String REPOSITORY2 = "repository2";
    public final static String SERVICE1 = "service1";
    public final static String SERVICE2 = "service2";
    public final static String CONTROLLER = "controller";
    public final static String SERVICE_CIRCULAR1 = "serviceCircular1";
    public final static String SERVICE_CIRCULAR2 = "serviceCircular2";
    public final static String SERVICE_CIRCULAR3 ="serviceCircular3";


    public static void main(String[] args){
        IocContainer iocContainer = IocContainerFactory.singleton()
                .setLogger(LOGGER);

        iocContainer
                .register(
                        REPOSITORY1,
                        (dr) -> new Repository1())
                .register(
                        REPOSITORY2,
                        (dr) -> new Repository2())
                .register(
                        SERVICE1,
                        (dr) -> new Service1(
                                dr.resolve(REPOSITORY1, IRepository.class)))
                .register(
                        SERVICE2,
                        (dr) -> new Service2(
                                dr.resolve(REPOSITORY1, Repository1.class),
                                dr.resolve(REPOSITORY2, Repository2.class)))
                .register(
                        CONTROLLER,
                        (dr) -> new Controller(
                                dr.resolve(REPOSITORY1, Repository1.class),
                                dr.resolve(SERVICE1, Service1.class),
                                dr.resolve(SERVICE2, Service2.class)))
                .loadContent(true, true);

        Controller controller = iocContainer.resolve(CONTROLLER, Controller.class);


    }
}
