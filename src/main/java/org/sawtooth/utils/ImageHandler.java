package org.sawtooth.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class ImageHandler {
    public int GetMedianColor(MultipartFile file) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
        ArrayList<Integer> colors = new ArrayList<>();

        for (int x = 0; x < bufferedImage.getWidth(); x += 5)
            for (int y = 0; y < bufferedImage.getHeight(); y += 5)
                colors.add(bufferedImage.getRGB(x, y));
        Collections.sort(colors);
        return colors.get(colors.size() / 2);
    }
}
