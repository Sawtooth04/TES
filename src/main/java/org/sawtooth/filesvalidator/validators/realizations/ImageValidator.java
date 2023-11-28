package org.sawtooth.filesvalidator.validators.realizations;

import org.sawtooth.filesvalidator.validators.abstractions.IValidator;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;

public class ImageValidator implements IValidator {
    private final ArrayList<String> whiteList = new ArrayList<>(Arrays.asList("image/jpeg", "image/png"));

    @Override
    public boolean Validate(MultipartFile file) {
        return whiteList.contains(file.getContentType());
    }
}
