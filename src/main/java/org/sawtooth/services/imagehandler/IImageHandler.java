package org.sawtooth.services.imagehandler;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IImageHandler {
    public int GetMedianColor(MultipartFile file) throws IOException;
}
