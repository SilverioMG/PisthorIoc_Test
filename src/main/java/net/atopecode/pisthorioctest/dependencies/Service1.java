package net.atopecode.pisthorioctest.dependencies;


import net.atopecode.pisthorioctest.dependencies.interfaces.IRepository;
import net.atopecode.pisthorioctest.dependencies.interfaces.IService;

import java.util.List;

public class Service1 implements IService {

    private IRepository repository1;

    public Service1(IRepository repository1){
        this.repository1 = repository1;
    }

    @Override
    public String getMessageService() {
        return "Service1";
    }

    @Override
    public List<IRepository> getRepositories() {
        return List.of(repository1);
    }
}
