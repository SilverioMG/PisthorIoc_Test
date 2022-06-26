package net.atopecode.pisthorioctest.dependencies;

import net.atopecode.pisthorioc.ioccontainer.interfaces.IDependency;
import net.atopecode.pisthorioctest.dependencies.interfaces.IRepository;

public class IDependencyImplService implements IDependency {

    private boolean postConstructDone = false;
    private boolean preDestroyDone = false;

    private IRepository repository;

    public IDependencyImplService(IRepository repository) {
        this.repository = repository;
    }

    @Override
    public void postContruct() {
        postConstructDone = true;
    }

    @Override
    public void preDestory() {
        preDestroyDone = true;
    }

    public boolean isPostConstructDone() {
        return postConstructDone;
    }

    public boolean isPreDestroyDone() {
        return preDestroyDone;
    }

    public IRepository getRepository() {
        return repository;
    }
}
