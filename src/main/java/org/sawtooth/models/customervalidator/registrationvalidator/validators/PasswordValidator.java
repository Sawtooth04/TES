package org.sawtooth.models.customervalidator.registrationvalidator.validators;

import org.sawtooth.models.customer.CustomerRegistrationModel;
import org.sawtooth.models.customervalidator.abstractions.IValidator;

public class PasswordValidator implements IValidator {
    @Override
    public <T> boolean Validate(T model) {
        try {
            if (model instanceof CustomerRegistrationModel customerRegistrationModel) {
                return customerRegistrationModel.password().length() >= 8 &&
                    customerRegistrationModel.password().length() <= 20;
            }
        }
        catch (Exception exception) {
            return false;
        }
        return false;
    }
}
