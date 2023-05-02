package com.joung45387.EveryAuction.Service;

import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class ImageService {
    public BufferedImage makeThumbnail(MultipartFile file, int width, int height) throws IOException {
        InputStream inputStream = file.getInputStream();
        BufferedImage readImage = ImageIO.read(inputStream);
        int w = readImage.getWidth();
        int h = readImage.getHeight();
        BufferedImage thumbImage;
        try{
            BufferedImage tmpImage = Scalr.crop(readImage, (w-width)/2, (h-height)/2, width, height);
            thumbImage = Scalr.resize(tmpImage, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, 178);
        }catch (Exception e){
            thumbImage = readImage;
        }
        inputStream.close();
        return thumbImage;
    }
}
