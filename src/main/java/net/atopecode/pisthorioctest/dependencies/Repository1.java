package net.atopecode.pisthorioctest.dependencies;


import net.atopecode.pisthorioctest.dependencies.interfaces.IRepository;

public class Repository1 implements IRepository {

    @Override
    public String getMessageRepository() {
        return "Respository1";
    }
}
