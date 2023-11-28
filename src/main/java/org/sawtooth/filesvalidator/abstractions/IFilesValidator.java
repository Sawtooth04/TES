package org.sawtooth.filesvalidator.abstractions;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface IFilesValidator {
    public boolean Validate(MultipartFile file);
}
