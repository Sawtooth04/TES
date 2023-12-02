package org.sawtooth.models.customervalidator.registrationvalidator.validators;

import org.sawtooth.models.customer.CustomerRegistrationModel;
import org.sawtooth.models.customervalidator.abstractions.IValidator;

import java.util.regex.Pattern;

public class EmailValidator implements IValidator {
    @Override
    public <T> boolean Validate(T model) {
        try {
            if (model instanceof CustomerRegistrationModel customerRegistrationModel)
                return Pattern.matches("[\\w.]+@[a-zA-Z]+\\.[a-zA-Z]+", customerRegistrationModel.email());
        }
        catch (Exception exception) {
            return false;
        }
        return false;
    }
}
