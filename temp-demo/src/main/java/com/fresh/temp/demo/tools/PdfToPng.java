package com.fresh.temp.demo.tools;


import com.aspose.pdf.Document;
import com.aspose.pdf.devices.JpegDevice;
import com.aspose.pdf.devices.Resolution;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class PdfToPng {
    static String path = "F:\\temp";

    public static void main(String[] argv) {
        InputStream pdfIs = PdfToPng.class.getClassLoader().getResourceAsStream("memo-493.pdf");

        File imgFile = new File(path + "\\1.png");
        pdfToImage(pdfIs, imgFile);
    }

    static void pdfToImage(InputStream inputStream, File imgFile) {
        List<File> fileList = new ArrayList<>();

        try {
            long old = System.currentTimeMillis();
            System.out.println("begin..............");
            Document pdfDocument = new Document(inputStream);

            Resolution resolution = new Resolution(80);
            JpegDevice jpegDevice = new JpegDevice(resolution);
            List<BufferedImage> imageList = new ArrayList<BufferedImage>();

            for (int index = 1; index <= pdfDocument.getPages().size(); index++) {
                //System.out.println(System.getProperty("java.io.tmpdir"));
                //C:\Users\DELL\AppData\Local\Temp\
                //File file = File.createTempFile("tempFile", ".png");
                File file = Files.createTempFile(FileSystems.getDefault().getPath(path), "tempFile", ".png").toFile();
                FileOutputStream fileOS = new FileOutputStream(file);

                jpegDevice.process(pdfDocument.getPages().get_Item(index), fileOS);
                fileOS.close();
                imageList.add(ImageIO.read(file));
                fileList.add(file);
            }

            BufferedImage mergeImage = mergeImage(false, imageList);
            ImageIO.write(mergeImage, "png", imgFile);
            long now = System.currentTimeMillis();
            System.out.println("end..............\n共耗时：" + ((now - old) / 1000.0) + "秒");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //删除临时文件
            for (File f : fileList) {
                f.delete();
            }
        }
    }

    static BufferedImage mergeImage(boolean isHorizontal, List<BufferedImage> imgs) {
        // 生成新图片
        BufferedImage destImage = null;
        // 计算新图片的长和高
        int allw = 0, allh = 0, allwMax = 0, allhMax = 0;
        // 获取总长、总宽、最长、最宽
        for (int i = 0; i < imgs.size(); i++) {
            BufferedImage img = imgs.get(i);
            allw += img.getWidth();

            if (imgs.size() != i + 1) {
                allh += img.getHeight() + 5;
            } else {
                allh += img.getHeight();
            }


            if (img.getWidth() > allwMax) {
                allwMax = img.getWidth();
            }
            if (img.getHeight() > allhMax) {
                allhMax = img.getHeight();
            }
        }

        // 创建新图片
        if (isHorizontal) {
            destImage = new BufferedImage(allw, allhMax, BufferedImage.TYPE_INT_RGB);
        } else {
            destImage = new BufferedImage(allwMax, allh, BufferedImage.TYPE_INT_RGB);
        }
        Graphics2D g2 = (Graphics2D) destImage.getGraphics();
        g2.setBackground(Color.LIGHT_GRAY);
        g2.clearRect(0, 0, allw, allh);
        g2.setPaint(Color.RED);

        // 合并所有子图片到新图片
        int wx = 0, wy = 0;
        for (int i = 0; i < imgs.size(); i++) {
            BufferedImage img = imgs.get(i);
            int w1 = img.getWidth();
            int h1 = img.getHeight();
            // 从图片中读取RGB
            int[] ImageArrayOne = new int[w1 * h1];
            // 逐行扫描图像中各个像素的RGB到数组中
            ImageArrayOne = img.getRGB(0, 0, w1, h1, ImageArrayOne, 0, w1);
            if (isHorizontal) {
                // 水平方向合并
                // 设置上半部分或左半部分的RGB
                destImage.setRGB(wx, 0, w1, h1, ImageArrayOne, 0, w1);
            } else {
                // 垂直方向合并
                // 设置上半部分或左半部分的RGB
                destImage.setRGB(0, wy, w1, h1, ImageArrayOne, 0, w1);
            }
            wx += w1;
            wy += h1 + 5;
        }

        return destImage;
    }
}
