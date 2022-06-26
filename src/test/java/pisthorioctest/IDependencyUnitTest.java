package pisthorioctest;

import net.atopecode.pisthorioc.dependencyfactory.DependencyFactory;
import net.atopecode.pisthorioc.exceptions.IocDependencyFactoryNotFoundException;
import net.atopecode.pisthorioc.ioccontainer.IocContainer;
import net.atopecode.pisthorioc.ioccontainer.IocContainerFactory;
import net.atopecode.pisthorioctest.dependencies.IDependencyImplService;
import net.atopecode.pisthorioctest.dependencies.Repository1;
import net.atopecode.pisthorioctest.dependencies.Service1;
import net.atopecode.pisthorioctest.dependencies.interfaces.Controller;
import net.atopecode.pisthorioctest.dependencies.interfaces.IRepository;
import net.atopecode.pisthorioctest.dependencies.interfaces.IService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IDependencyUnitTest {


    @Test
    @DisplayName("Se registra un objeto que implementa la interfaz 'IDependency' y al resolverla se comprueba que se" +
        " ejecutó su método 'postConstruct()'.")
    public void resolve_IDependencyObject_And_PostConstruct_Method_Is_Executed() {
        IocContainer container = IocContainerFactory.newInstance();

        container
                .register(
                        "idependencyImpl",
                        (r) -> new IDependencyImplService(r.resolve("repository", IRepository.class))
                )
                .register(
                        "repository",
                        (r) -> new Repository1())
                .loadContent();

        IDependencyImplService service = new IDependencyImplService(null);
        assertFalse(service.isPostConstructDone());

        service = container.resolve("idependencyImpl", IDependencyImplService.class);
        assertTrue(service.isPostConstructDone());
    }

    @Test
    @DisplayName("Se registra un objeto que implementa la interfaz 'IDependency' y al resolverla se comprueba que se" +
            " ejecutó su método 'preDestroy'.")
    public void resolve_IDependencyObject_And_PreDestroy_Method_Is_Executed() {
        IocContainer container = IocContainerFactory.newInstance();

        container
                .register(
                        "idependencyImpl",
                        (r) -> new IDependencyImplService(r.resolve("repository", IRepository.class))
                )
                .register(
                        "repository",
                        (r) -> new Repository1())
                .loadContent();

        IDependencyImplService service = new IDependencyImplService(null);
        assertFalse(service.isPreDestroyDone());

        service = container.resolve("idependencyImpl", IDependencyImplService.class);
        container.clear();
        assertTrue(service.isPreDestroyDone());
    }

    @Test
    @DisplayName("Se registrar varios objetos en el contenedor de dependencias y se resuelven correctamente. Posteriormente " +
            "se limpia el contenedor (factories y objects) y se comprueba que no se puede resolver ninguna dependencia. " +
            "Se comprueba también que después de limpiar el contenedor se puede volver a registrar y resolver dependencias correctamente."
    )
    public void clear_container_ok(){
        IocContainer container = IocContainerUnitTest
                .getNewContainerWithAllDepenciesRegistered(DependencyFactory.DependencyType.SINGLETON, null);

        container.loadContent();
        IRepository repository = container.resolve(IocContainerUnitTest.REPOSITORY1, IRepository.class);
        IService service = container.resolve(IocContainerUnitTest.SERVICE1, IService.class);
        Controller controller = container.resolve(IocContainerUnitTest.CONTROLLER, Controller.class);

        assertNotNull(repository);
        assertNotNull(service);
        assertNotNull(controller);

        container.clear();

        assertThrows(IocDependencyFactoryNotFoundException.class,
                () -> {
                    container.resolve(IocContainerUnitTest.REPOSITORY1, IRepository.class);
                });

        assertThrows(IocDependencyFactoryNotFoundException.class,
                () -> {
                    container.resolve(IocContainerUnitTest.SERVICE1, IService.class);
                });

        assertThrows(IocDependencyFactoryNotFoundException.class,
                () -> {
                    container.resolve(IocContainerUnitTest.CONTROLLER, Controller.class);
                });

        container
                .register(
                    "service1",
                    (r) -> new Service1(r.resolve("repository1", IRepository.class))
                )
                .register(
                        "repository1",
                        (r) -> new Repository1()
                )
                .loadContent();

        service = container.resolve("service1", IService.class);
        assertNotNull(service);
        assertNotNull(service.getRepositories().get(0));
    }
}
