package com.exchange.buy.sell.market.utils;

import org.apache.tomcat.util.codec.binary.Base64;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;

/**
 * Created by oleht on 11.05.2018
 */
public class ImageLoadUtil {

    public static Byte[] getTestImg(String pathFromResource) throws IOException {
        File fi = new File("src/test/resources/" + pathFromResource);
        byte[] array = Files.readAllBytes(fi.toPath());
        Byte[] byteObjects = new Byte[array.length];

        int i = 0;
        for (byte b : array) {
            byteObjects[i++] = b;
        }

        return byteObjects;
    }

    public static String base64EncodedImage(Byte[] image) {
        byte[] bytes = new byte[image.length];
        int i = 0;
        for (byte b : image) {
            bytes[i++] = b;
        }

        byte[] encodeBase64 = Base64.encodeBase64(bytes);
        String base64Encoded = null;

        try {
            base64Encoded = new String(encodeBase64, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return base64Encoded;
    }
}
