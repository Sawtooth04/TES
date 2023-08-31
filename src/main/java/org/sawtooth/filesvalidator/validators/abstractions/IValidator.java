package org.sawtooth.filesvalidator.validators.abstractions;

import org.springframework.web.multipart.MultipartFile;

public interface IValidator {
    public boolean Validate(MultipartFile file);
}
