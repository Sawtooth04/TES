package org.sawtooth.models.customervalidator.registrationvalidator;

import org.sawtooth.models.customervalidator.abstractions.IValidator;
import org.sawtooth.models.customervalidator.registrationvalidator.validators.*;
import org.sawtooth.storage.abstractions.IStorage;

public class RegistrationValidator implements IValidator {
    private RegistrationValidationResults results;
    private final IValidator nameFreeValidator, nameValidator, passwordValidator, emailValidator, emailFreeValidator;

    public RegistrationValidator(IStorage storage, RegistrationValidationResults results) {
        this.results = results;
        nameFreeValidator = new NameFreeValidator(storage);
        nameValidator = new NameValidator();
        passwordValidator = new PasswordValidator();
        emailValidator = new EmailValidator();
        emailFreeValidator = new EmailFreeValidator(storage);
    }

    @Override
    public <T> boolean Validate(T model) {
        results.isNameFree = nameFreeValidator.Validate(model);
        results.isNameValid = nameValidator.Validate(model);
        results.isPasswordValid = passwordValidator.Validate(model);
        results.isEmailValid = emailValidator.Validate(model);
        results.isEmailFree = emailFreeValidator.Validate(model);
        results.setTotal();
        return results.total;
    }
}
