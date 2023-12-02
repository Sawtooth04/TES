package org.sawtooth.models.customervalidator.registrationvalidator;

public class RegistrationValidationResults {
    public boolean total, isNameFree, isNameValid, isPasswordValid, isEmailValid, isEmailFree;

    public void setTotal() {
        this.total = isNameFree && isNameValid && isPasswordValid && isEmailValid && isEmailFree;
    }
}
