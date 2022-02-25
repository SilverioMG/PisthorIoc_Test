package net.atopecode.pisthorioctest.dependencies;

import net.atopecode.pisthorioctest.dependencies.interfaces.IRepository;

public class RepositoryMock implements IRepository {
    @Override
    public String getMessageRepository() {
        return "This is a Repository Mock";
    }
}
