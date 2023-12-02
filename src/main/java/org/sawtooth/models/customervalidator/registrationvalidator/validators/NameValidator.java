package org.sawtooth.models.customervalidator.registrationvalidator.validators;

import org.sawtooth.models.customer.CustomerRegistrationModel;
import org.sawtooth.models.customervalidator.abstractions.IValidator;


public class NameValidator implements IValidator {
    @Override
    public <T> boolean Validate(T model) {
        try {
            if (model instanceof CustomerRegistrationModel customerRegistrationModel)
                return customerRegistrationModel.name().length() >= 8 && customerRegistrationModel.name().length() <= 30;
        }
        catch (Exception exception) {
            return false;
        }
        return false;
    }
}
