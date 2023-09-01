package org.sawtooth.filesvalidator.validators.realizations;

import org.sawtooth.filesvalidator.validators.abstractions.IValidator;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

public class IMimeValidator implements IValidator {
    private ArrayList<String> whiteList = new ArrayList<>(Arrays.asList("application/json"));

    @Override
    public boolean Validate(MultipartFile file) {
        return whiteList.contains(file.getContentType());
    }
}
