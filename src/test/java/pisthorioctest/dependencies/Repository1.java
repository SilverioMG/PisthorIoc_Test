package pisthorioctest.dependencies;


import pisthorioctest.dependencies.interfaces.IRepository;

public class Repository1 implements IRepository {

    @Override
    public String getMessageRepository() {
        return "Respository1";
    }
}
