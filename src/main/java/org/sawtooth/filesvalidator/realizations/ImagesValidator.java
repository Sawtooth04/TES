package org.sawtooth.filesvalidator.realizations;

import org.sawtooth.filesvalidator.abstractions.IFilesValidator;
import org.sawtooth.filesvalidator.validators.abstractions.IValidator;
import org.sawtooth.filesvalidator.validators.realizations.ImageValidator;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;

@Component
public class ImagesValidator implements IFilesValidator {
    private final ArrayList<IValidator> validators = new ArrayList<IValidator>(Arrays.asList(
        new ImageValidator()
    ));

    @Override
    public boolean Validate(MultipartFile file) {
        boolean result = true;

        for (IValidator validator : validators)
            result &= validator.Validate(file);
        return result;
    }
}
