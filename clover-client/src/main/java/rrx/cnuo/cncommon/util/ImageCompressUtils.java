package rrx.cnuo.cncommon.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifIFD0Directory;

/**
 * 图片压缩工具类
 * @author xuhongyu
 * @date 2019年6月18日
 */
public class ImageCompressUtils {
    private static final double MAX_PIXELS = 2000000; //图片压缩后的最大分辨率限制，值为 长 x 宽 的积
//    private static final float COMPRESS_QUALITY = 0.4f;
    private static final float NARROW_QUALITY = 0.6f;  //最后压缩成JPG图片时，图片的质量分数，范围 0-1，值越大，越清晰。

    public static final int SIZE_SMALL = 120;           //头像缩略尺寸
    public static final int SIZE_MIDDLE = 240;         //图片缩略尺寸

    /** 生成图片的自适应大小压缩图，压缩图的大小可通过修改MAX_PIXELS进行设定
     * @param image: 图片流输入
     * */
    public static byte[] imageCompressToJpg(byte [] image){
        return imageCompressInJpgType(image,MAX_PIXELS);
    }

    /** 生成图片的缩略图
     * @param image: 图片流输入
     * @param size: 压缩尺寸，将图片压缩为size * size 的尺寸。提供两种尺寸选择，ImageCompressUtils.SIZE_SMALL  ImageCompressUtils.SIZE_MIDDLE
     * */
    public static byte[] imageNarrowToJpg(byte [] image,int size){
        return imageCompressInJpgType(image,size*size);
    }

    private static BufferedImage rotateImage(Image inputImage, int angle){
        int old_width = inputImage.getWidth(null);
        int old_height = inputImage.getHeight(null);
        int width,height,trans_move = 0;
        if(angle == 90){
            width = old_height;
            height = old_width;
            trans_move = (width - old_width)/2;
        }else if(angle == 180){
            width = old_width;
            height = old_height;
        }
        else if(angle == 270){
            width = old_height;
            height = old_width;
            trans_move = (old_width - width)/2;
        }else
            return null;
        BufferedImage outImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = outImage.createGraphics();
        g2d.translate(trans_move,trans_move);
        g2d.rotate(Math.toRadians(angle), width / 2, height / 2);
        g2d.drawImage(inputImage,0, 0,null);
        g2d.dispose();
        return outImage;
    }

    private static Image checkImageRorate(byte [] image){
        InputStream in1 = new ByteArrayInputStream(image);
        InputStream in2 = new ByteArrayInputStream(image);
        Image fromImage = null;
        try {
            fromImage = ImageIO.read(in1);
            Metadata metadata = ImageMetadataReader.readMetadata(in2);
            Directory directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
            if(null != directory && directory.containsTag(ExifIFD0Directory.TAG_ORIENTATION)){
                // Exif信息中方向　　
                int orientation = directory.getInt(ExifIFD0Directory.TAG_ORIENTATION);
                System.out.println(orientation);
                // 原图片的方向信息
                if(6 == orientation ){
                    //6旋转90
                    fromImage = rotateImage(fromImage,90);
                }else if( 3 == orientation){
                    //3旋转180
                    fromImage = rotateImage(fromImage,180);
                }else if( 8 == orientation){
                    //8旋转90
                    fromImage = rotateImage(fromImage,270);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MetadataException e) {
            e.printStackTrace();
        } catch (ImageProcessingException e) {
            e.printStackTrace();
        }
        return fromImage;
    }

    private static byte[] imageCompressInJpgType(byte [] image,double allPixels){
        int width,height;
        Image fromImage = null;
        try {
            fromImage = checkImageRorate(image);

            int old_w = fromImage.getWidth(null);
            int old_h = fromImage.getHeight(null);
            double whSum = old_h * old_w;
            if(allPixels < whSum){
                double zoomNum = Math.sqrt(whSum / allPixels);
                width = (int)(old_w / zoomNum);
                height = (int)(old_h / zoomNum);
            }
            else{
                width = old_w;
                height = old_h;
            }
            BufferedImage firstCompress = new BufferedImage(old_w, old_h, BufferedImage.TYPE_INT_RGB);      //第一次压缩图片，将原图绘入新图
            Graphics2D g2d = firstCompress.createGraphics();
            g2d.setColor(Color.white);
            g2d.fillRect(0,0,old_w,old_h);
            g2d.drawImage(fromImage, 0, 0, old_w, old_h, null);
            g2d.dispose();

            BufferedImage secondCompress = null;
            if(allPixels < whSum) {
                secondCompress = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);        //第二次压缩图片，将原图绘入新图
                secondCompress.getGraphics().drawImage(firstCompress.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
            }

            Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("jpg");        // 得到迭代器
            ImageWriter thirdCompressWriter = (ImageWriter) iter.next();                    // 得到writer

            IIOMetadata meta = thirdCompressWriter.getDefaultImageMetadata(new ImageTypeSpecifier((RenderedImage) fromImage), null);

            ImageWriteParam iwp = thirdCompressWriter.getDefaultWriteParam();               // 得到指定writer的输出参数设置(ImageWriteParam )
            iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);                          // 设置可否压缩
            iwp.setCompressionQuality(0.4f);                                             // 设置压缩质量参数
            iwp.setProgressiveMode(ImageWriteParam.MODE_DISABLED );

            ColorModel colorModel = ColorModel.getRGBdefault();                             // 指定压缩时使用的色彩模式
            iwp.setDestinationType(new javax.imageio.ImageTypeSpecifier(colorModel, colorModel.createCompatibleSampleModel(16, 16)) );

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            thirdCompressWriter.setOutput(ImageIO.createImageOutputStream(byteArrayOutputStream));
            IIOImage tempImage;
            if(allPixels < whSum) {
                tempImage = new IIOImage(secondCompress, null, null);
            }
            else
                tempImage = new IIOImage(firstCompress, null, null);
            thirdCompressWriter.write(meta, tempImage, iwp);                //第三次压缩
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] imageCompressWithHW(byte [] image, int picWidth, int picHeight){
        int width = picWidth,height = picHeight;
        Image fromImage = null;
        try {
            fromImage = checkImageRorate(image);

            int old_w = fromImage.getWidth(null);
            int old_h = fromImage.getHeight(null);
            if(width < old_w || height < old_h){
                BufferedImage firstCompress = new BufferedImage(old_w, old_h, BufferedImage.TYPE_INT_RGB);      //第一次压缩图片，将原图绘入新图
                Graphics2D g2d = firstCompress.createGraphics();
                g2d.setColor(Color.white);
                g2d.fillRect(0,0,old_w,old_h);
                g2d.drawImage(fromImage, 0, 0, old_w, old_h, null);
                g2d.dispose();

                BufferedImage secondCompress = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);        //第二次压缩图片，将原图压缩入新图
                secondCompress.getGraphics().drawImage(firstCompress.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
                Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("jpg");        // 得到迭代器
                ImageWriter thirdCompressWriter = (ImageWriter) iter.next();                    // 得到writer

                ImageWriteParam iwp = thirdCompressWriter.getDefaultWriteParam();               // 得到指定writer的输出参数设置(ImageWriteParam )
                iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);                          // 设置可否压缩
                iwp.setCompressionQuality(NARROW_QUALITY);                                             // 设置压缩质量参数
                iwp.setProgressiveMode(ImageWriteParam.MODE_DISABLED );

                ColorModel colorModel = ColorModel.getRGBdefault();                             // 指定压缩时使用的色彩模式
                iwp.setDestinationType(new javax.imageio.ImageTypeSpecifier(colorModel, colorModel.createCompatibleSampleModel(16, 16)) );

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                thirdCompressWriter.setOutput(ImageIO.createImageOutputStream(byteArrayOutputStream));
                IIOImage tempImage = new IIOImage(secondCompress, null, null);
                thirdCompressWriter.write(null, tempImage, iwp);                //第三次压缩
                return byteArrayOutputStream.toByteArray();
            }else{
                BufferedImage firstCompress = new BufferedImage(old_w, old_h, BufferedImage.TYPE_INT_RGB);      //第一次压缩图片，将原图绘入新图

                Graphics2D g2d = firstCompress.createGraphics();
                g2d.setColor(Color.white);
                g2d.fillRect(0,0,old_w,old_h);
                g2d.drawImage(fromImage, 0, 0, old_w, old_h, null);
                g2d.dispose();

                Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("jpg");    // 得到迭代器
                ImageWriter thirdCompressWriter = (ImageWriter) iter.next();                // 得到writer

                ImageWriteParam iwp = thirdCompressWriter.getDefaultWriteParam();           // 得到指定writer的输出参数设置(ImageWriteParam )
                iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);                      // 设置可否压缩
                iwp.setCompressionQuality(NARROW_QUALITY);                                         // 设置压缩质量参数
                iwp.setProgressiveMode(ImageWriteParam.MODE_DISABLED );

                ColorModel colorModel = ColorModel.getRGBdefault();                         // 指定压缩时使用的色彩模式
                iwp.setDestinationType(new javax.imageio.ImageTypeSpecifier(colorModel, colorModel.createCompatibleSampleModel(16, 16)) );

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                thirdCompressWriter.setOutput(ImageIO.createImageOutputStream(byteArrayOutputStream));
                IIOImage tempImage;
                tempImage = new IIOImage(firstCompress, null, null);
                thirdCompressWriter.write(null, tempImage, iwp);            //第二次压缩
                return byteArrayOutputStream.toByteArray();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
