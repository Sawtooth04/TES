package org.sawtooth.storage.abstractions;

import org.sawtooth.storage.repositories.IRepository;

public interface IStorage {
    public <T extends IRepository> T GetRepository(Class<T> interfaceObject) throws ClassNotFoundException,
            InstantiationException, IllegalAccessException;
}
