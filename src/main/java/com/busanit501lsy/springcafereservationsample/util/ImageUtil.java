package com.busanit501lsy.springcafereservationsample.util;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageUtil {

    // Static method to convert MultipartFile to a thumbnail
    public static byte[] createThumbnail(MultipartFile file, int width, int height) throws IOException {
        // Create a ByteArrayOutputStream to hold the thumbnail
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // Resize the image using Thumbnailator
        Thumbnails.of(file.getInputStream())
                .size(width, height)  // Set the desired thumbnail size
                .outputFormat("jpg")  // Output format (optional)
                .toOutputStream(outputStream);

        // Return the resulting thumbnail bytes
        return outputStream.toByteArray();
    }
}