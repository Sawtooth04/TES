package org.sawtooth.models.customervalidator.abstractions;

public interface IValidator {
    public <T> boolean Validate(T model);
}
