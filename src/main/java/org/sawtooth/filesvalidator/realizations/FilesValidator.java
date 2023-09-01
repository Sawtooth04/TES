package org.sawtooth.filesvalidator.realizations;

import org.sawtooth.filesvalidator.abstractions.IFilesValidator;
import org.sawtooth.filesvalidator.validators.abstractions.IValidator;
import org.sawtooth.filesvalidator.validators.realizations.IMimeValidator;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

@Component
public class FilesValidator implements IFilesValidator {
    private final ArrayList<IValidator> validators = new ArrayList<IValidator>(Arrays.asList(
        new IMimeValidator()
    ));

    @Override
    public boolean ValidateTask(MultipartFile file) {
        boolean result = true;

        for (IValidator validator : validators)
            result &= validator.Validate(file);
        return result;
    }
}
