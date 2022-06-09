package com.wanjuanshu.o2o.util;

import com.wanjuanshu.o2o.dto.ImageHolder;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author yangshucheng
 * @create 2021-04-06 16:43
 * C:\Users\Administrator\Pictures\Saved Pictures\蕾姆.jpg
 */
public class ImageUtil {
    private static String basePath = PathUtil.getImgBasePath();

    private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final Random r = new Random();
    private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);


    /**
     * 将CommonsMultipartFile转化成File
     * @param cFile
     * @return
     */
    public static File transferCommonsMultipartFileToFile(CommonsMultipartFile cFile){
        File newFile = new File(cFile.getOriginalFilename());
        try {
            cFile.transferTo(newFile);
        } catch (IOException e) {
            logger.error(e.toString());
            e.printStackTrace();
        }
        return newFile;
    }
    //CommonsMultipartFile   spring自带的文件处理对象

    /**
     * 处理缩略图，并返回新生成图片的相对值路径
     * @param thumbnail
     * @param targetAddr
     * @return
     */
    public static String generateThumbnail(ImageHolder thumbnail, String targetAddr){
        String realFileName = getRandomFileName();
        String extension = getFileExtension(thumbnail.getImageName());
        makeDirPath(targetAddr);
        String relativeAddr = targetAddr + realFileName + extension;
        logger.debug("current relativeAddr is: " + relativeAddr);
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        logger.debug("current complete addr is: " + PathUtil.getImgBasePath() + relativeAddr);
        try {
            Thumbnails.of(thumbnail.getImage()).size(200,200).
                    watermark(Positions.BOTTOM_RIGHT,
                            ImageIO.read(new File(basePath + "/watermark.jpg")),0.25f)
                    .outputQuality(0.8f).toFile(dest);
        } catch (IOException e) {
            logger.error(e.toString());
            e.printStackTrace();
        }
        return relativeAddr;
    }
    public static String generateNormalImg(ImageHolder thumbnail, String targetAddr){
        //获取不重复的随机名
        String realFileName = getRandomFileName();
        //获取文件的扩展名如png，jpg
        String extension = getFileExtension(thumbnail.getImageName());
        //如果目标路径不存在，则自动创建
        makeDirPath(targetAddr);
        //获取文件存储的相对路劲（带文件名）
        String relativeAddr = targetAddr + realFileName + extension;
        logger.debug("current basePath is: " + basePath);
        logger.debug("current relativeAddr is: " + relativeAddr);
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        logger.debug("current complete addr is: " + PathUtil.getImgBasePath() + relativeAddr);
        logger.debug("current watermark is: " + basePath + "/watermark.jpg");
        //调用Thumbnails生成带有水印的图片
        try {
            Thumbnails.of(thumbnail.getImage()).size(337,640).
                    watermark(Positions.BOTTOM_RIGHT,
                            ImageIO.read(new File(basePath + "/watermark.jpg")),0.25f)
                    .outputQuality(0.9f).toFile(dest);
        } catch (IOException e) {
            logger.error(e.toString());
            throw new RuntimeException("缩略图：" + e.toString());
        }
        //返回图片相对路径的地址
        return relativeAddr;
    }
    /**
     * 创建目标路径所涉及到的目录，即/home/work/yangshucheng/xxx.jpg
     * 那么home work yangshucheng这三个文件夹都得自动创建
     * @param targetAddr
     */
    private static void makeDirPath(String targetAddr) {
        String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
        File dirPath = new File(realFileParentPath);
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }
    }

    /**
     * 生成随机文件名，当前年月日小时分钟秒钟+五位随机数
     */
    public static String getRandomFileName(){
        //获取随机的五位数
        int rannum = r.nextInt(89999) + 10000;
        String nowTimeStr = sDateFormat.format(new Date());
        return nowTimeStr + rannum;
    }

    /**
     * 获取输入文件流的扩展名
     */
    public static String getFileExtension(String fileName){
        //originalFileName.lastIndexOf(".") 获取最后一个点的索引
        //substring 返回从起始索引开始到结尾的字符串
        return fileName.substring(fileName.lastIndexOf("."));
    }


    public static void main(String[] args) throws IOException {
        //获取绝对值路径
        Thumbnails.of(new File("C:\\Users\\Administrator\\Pictures\\Saved Pictures\\蕾姆.jpg"))
        .size(200,200).
                watermark(Positions.BOTTOM_RIGHT,
                        ImageIO.read(new File(basePath + "\\watermark.jpg")),0.25f)
        .outputQuality(0.8f).toFile("C:\\Users\\Administrator\\Pictures\\Saved Pictures\\蕾姆new.jpg");
    }

    /**
     * storePath是文件的路径还是目录的路径
     * 如果storePath是文件路径则删除该文件
     * 如果storePath是目录路径则删除该目录下的所有文件
     * @param storePath
     */
    public static void deleteFileOrPath(String storePath){
        File fileOrPath = new File(PathUtil.getImgBasePath() + storePath);
        if (fileOrPath.exists()){
            if (fileOrPath.isDirectory()){
                File files[] = fileOrPath.listFiles();
                for (int i = 0; i < files.length; i++) {
                    files[i].delete();
                }
            }
            fileOrPath.delete();
        }
    }
}
