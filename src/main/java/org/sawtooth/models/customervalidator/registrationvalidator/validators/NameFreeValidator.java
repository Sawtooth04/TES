package org.sawtooth.models.customervalidator.registrationvalidator.validators;

import org.sawtooth.models.customer.CustomerRegistrationModel;
import org.sawtooth.models.customervalidator.abstractions.IValidator;
import org.sawtooth.storage.abstractions.IStorage;
import org.sawtooth.storage.repositories.customer.abstractions.ICustomerRepository;

public class NameFreeValidator implements IValidator {
    private final IStorage storage;

    public NameFreeValidator(IStorage storage) {
        this.storage = storage;
    }

    @Override
    public <T> boolean Validate(T model) {
        try {
            if (model instanceof CustomerRegistrationModel customerRegistrationModel)
                return storage.GetRepository(ICustomerRepository.class).IsCustomerNameFree(customerRegistrationModel.name());
        }
        catch (Exception exception) {
            return false;
        }
        return false;
    }
}
