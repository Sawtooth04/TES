package org.sawtooth.models.customervalidator.registrationvalidator.validators;

import org.sawtooth.models.customer.CustomerRegistrationModel;
import org.sawtooth.models.customervalidator.abstractions.IValidator;
import org.sawtooth.storage.abstractions.IStorage;
import org.sawtooth.storage.repositories.customer.abstractions.ICustomerRepository;

public class EmailFreeValidator implements IValidator {
    private final IStorage storage;

    public EmailFreeValidator(IStorage storage) {
        this.storage = storage;
    }

    @Override
    public <T> boolean Validate(T model) {
        try {
            if (model instanceof CustomerRegistrationModel customerRegistrationModel)
                return storage.GetRepository(ICustomerRepository.class).IsCustomerEmailFree(customerRegistrationModel.email());
        }
        catch (Exception exception) {
            return false;
        }
        return false;
    }
}
