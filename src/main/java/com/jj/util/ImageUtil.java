package com.jj.util;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Base64;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.binary.Hex;

public class ImageUtil {

    private static Logger logger = Logger.getLogger(ImageUtil.class.getName());

    private ImageUtil() {
    }

    public static String getBase64(String imageUrl) {
        return getBase64(imageUrl, 0, 0);
    }

    /**
     * Metodo responsavel por alterar a dimensao da imagem de acordo com os tamanhos passados por parametros.
     * 
     * @param BufferedImage img
     * @param int targetWidth
     * @param int targetHeight
     * @param Boolean higherQuality
     */
    public static BufferedImage resize(BufferedImage img, int targetWidth, int targetHeight, Boolean higherQuality) {

        if (targetWidth == 0 && targetHeight == 0) {
            return img;
        }

        int type = (img.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;

        int width = higherQuality ? img.getWidth() : targetWidth;
        int height = higherQuality ? img.getHeight() : targetHeight;

        return resizeImage(img, height, width, targetWidth, targetHeight, higherQuality, type);
    }

    /**
     * Metodo responsavel por alterar a dimensao da imagem dinamicamente de acordo com os tamanhos passados por parametros. Para isso a imagem sofre alteracoes no seu tamanho ate chegar na dimensao especificada.
     * 
     * @param BufferedImage img
     * @param int height
     * @param int width
     * @param int targetWidth
     * @param int targetHeight
     * @param Boolean quality
     * @param int type
     */
    private static BufferedImage resizeImage(BufferedImage img, int height, int width, int targetWidth, int targetHeight, Boolean quality, int type) {

        BufferedImage ret = img;
        int w = width;
        int h = height;

        do {

            if (quality && w > targetWidth) {
                w = w /= 2;
                w = w < targetWidth ? targetWidth : w;
            }

            if (quality && h > targetHeight) {
                h /= 2;
                h = h < targetHeight ? targetHeight : h;
            }

            if (quality && w < targetWidth) {
                w = w *= 2;
                w = w > targetWidth ? targetWidth : w;
            }

            if (quality && h < targetHeight) {
                h *= 2;
                h = h > targetHeight ? targetHeight : h;
            }

            BufferedImage tmp = new BufferedImage(w, h, type);
            Graphics2D g2 = tmp.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(ret, 0, 0, w, h, null);
            g2.dispose();

            ret = tmp;

        } while (w != targetWidth || h != targetHeight);

        return ret;
    }

    public static String getBase64(BufferedImage bufferedImageOriginal, int width, int height, String type) {

        StringBuilder base64 = new StringBuilder("data:");

        try {

            String mimeType = StringUtil.isNullOrEmpty(type) ? "image/png" : type.substring(type.indexOf(":") + 1, type.indexOf(";"));

            BufferedImage bufferedImage = resize(bufferedImageOriginal, width, height, Boolean.TRUE);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            ImageIO.write(bufferedImage, mimeType.split("/")[1], baos);

            base64.append(mimeType).append(";base64,");

            base64.append(DatatypeConverter.printBase64Binary(baos.toByteArray()));

            baos.flush();
            baos.close();

        } catch (Exception e) {

            logger.info("Não foi possível converter a imagem para Base64.");

            base64.setLength(0);
        }

        return base64.toString();

    }

    public static String getBase64(String imageUrl, int width, int height) {

        String base64 = "";

        try {

            URL url = new URL(imageUrl);

            BufferedImage bufferedImageOriginal = ImageIO.read(url.openStream());

            base64 = getBase64(bufferedImageOriginal, width, height, null);

        } catch (Exception e) {

            logger.info("Não foi possível converter a imagem para Base64.");

        }

        return base64.toString();

    }

    public static String getBase64(File imagem, int width, int height) {

        String base64 = "";

        try {

            BufferedImage bufferedImageOriginal = ImageIO.read(imagem);

            base64 = getBase64(bufferedImageOriginal, width, height, null);

        } catch (Exception e) {

            logger.info("Não foi possível converter a imagem para Base64.");

        }

        return base64.toString();
    }

    public static String redimensionarBase64(String imagem, int width, int height) {

        String base64 = "";

        if (imagem.contains("data:image")) {

            try {

                String[] partes = imagem.split(",");

                BufferedImage image = null;
                byte[] imageByte;

                imageByte = Base64.getDecoder().decode(partes[1].trim());
                ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
                image = ImageIO.read(bis);
                bis.close();

                base64 = getBase64(image, width, height, partes[0]);

            } catch (Exception e) {

                logger.info("Não foi possível converter a imagem para Base64.");

            }
        } else {
            logger.info("A string não possui formato de imagem em base64.");
        }

        return base64;
    }

    public static String getHexString(String imageUrl) {

        String imageHex = "";
        try {

            URL url = new URL(imageUrl);
            InputStream in = new BufferedInputStream(url.openStream());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int n = 0;

            while (-1 != (n = in.read(buf))) {
                out.write(buf, 0, n);
            }

            imageHex = Hex.encodeHexString(out.toByteArray());

        } catch (Exception e) {

            logger.info("Não foi possível converter a imagem para Hexdecimal.");

        }

        return imageHex;
    }

    /**
     * Salva imagem redimensionada em diretorio parametrizado.
     * 
     * @param img
     * @param height
     * @param width
     * @param diretorio
     * @param nameFile
     * @throws IOException
     */
    public static Boolean saveImage(String img, int height, int width, String diretorio, String nameFile) throws IOException {
        return saveImage(ImageUtil.redimensionarBase64(img, width, height), diretorio, nameFile);
    }

    /**
     * Salva imagem em diretorio parametrizado.
     * 
     * @param imageByte
     * @param diretorio
     * @param nameFile
     * @throws IOException
     */
    public static Boolean saveImage(String img, String diretorio, String nameFile) throws IOException {

        String[] partes = img.split(",");
        String type = partes[0];
        byte[] imageByte = Base64.getDecoder().decode(partes[1]);

        File outputDir = new File(diretorio);

        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
        BufferedImage image = ImageIO.read(bis);
        bis.close();

        return ImageIO.write(image, type.substring(type.indexOf("/") + 1, type.indexOf(";")), new File(diretorio.concat(nameFile)));
    }
}
