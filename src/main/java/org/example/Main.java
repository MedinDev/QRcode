package org.example;

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        // Define your website link
        String websiteLink = "https://www.instagram.com/a.e.s.g.chine/#";

        // Generate QR code
        ByteArrayOutputStream out = QRCode.from(websiteLink).to(ImageType.PNG).stream();

        try {
            // Convert the QR code to a BufferedImage
            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(out.toByteArray()));

            // Make the white background of the PNG image transparent
            BufferedImage transparentBufferedImage = new BufferedImage(
                    bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = transparentBufferedImage.createGraphics();
            g.drawImage(bufferedImage, 0, 0, null);
            g.dispose();

            for (int y = 0; y < transparentBufferedImage.getHeight(); y++) {
                for (int x = 0; x < transparentBufferedImage.getWidth(); x++) {
                    int argb = transparentBufferedImage.getRGB(x, y);
                    if (argb == Color.WHITE.getRGB()) {
                        transparentBufferedImage.setRGB(x, y, 0);
                    }
                }
            }

            // Save the QR code image as a PNG file with a transparent background
            ImageIO.write(transparentBufferedImage, "PNG", new File("website_qrcode_transparent.png"));

            // Save the QR code image as a JPEG file with a white background
            ImageIO.write(bufferedImage, "JPEG", new File("website_qrcode.jpeg"));

            System.out.println("QR codes generated successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}