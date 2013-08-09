/*
 * cn.touch.kit.image.ImageProcessor.java
 * Sep 6, 2012 
 */
package cn.touch.kit.image;

import java.io.File;

import cn.touch.kit.MethodResult;

/**
 * Sep 6, 2012
 * 
 * @author <a href="mailto:touchnan@gmail.com">chegnqiang.han</a>
 * 
 */
public interface ImageProcessor {

    /**
     * ************************************************************************* 对一副图像进行缩放,如果后两个参数为0则相当于拷贝这个图像文件
     * 
     * @param sp
     *            源图文件名
     * @param dp
     *            目标图文件名
     * @param pw
     *            目标图宽
     * @param ph
     *            目标图高
     * @param quality
     *            缩放质量0-100的数字
     * @return 返回true缩放成功
     ************************************************************************* 
     */
    MethodResult creatMiniatureCommand(String sp, String dp, int pw, int ph, int quality);

    MethodResult creatMiniatureCommand(File srcFile, String dp, int pw, int ph, int quality);

    /**
     * ************************************************************************* 对一副图像进行缩放,如果后两个参数为0则相当于拷贝这个图像文件
     * 
     * @param srcFile
     *            源图文件
     * @param dp
     *            目标图文件名
     * @param maxW
     *            目标图最大宽 (大于零生效)
     * @param maxH
     *            目标图最大高(大于零生效)
     * @param pw
     *            目标图宽
     * @param ph
     *            目标图高
     * @param quality
     *            缩放质量0-100的数字
     * @return 返回true缩放成功
     ************************************************************************* 
     */
    MethodResult creatMiniatureCommand(File srcFile, String dp, int maxW, int maxH, int pw, int ph, int quality);

    /**
     * ************************************************************************* 对一副图像进行缩放,如果后两个参数为0则相当于拷贝这个图像文件
     * 
     * @param srcFile
     *            源图文件
     * @param imageSpecification
     *            目标图规格
     * @return 返回true缩放成功
     ************************************************************************* 
     */
    MethodResult creatMiniatureCommand(File srcFile, ImageSpecification imageSpecification);

    MethodResult creatMiniatureCommand(String sp, ImageSpecification imageSpecification);

    /**
     * ************************************************************************* 对一副图像进行缩放,如果后两个参数为0则相当于拷贝这个图像文件
     * 
     * @param srcFile
     *            源图文件
     * @param imageSpecifications
     *            目标图规格列表
     * @return 返回true缩放成功
     ************************************************************************* 
     */
    MethodResult[] creatMiniatureCommand(File srcFile, ImageSpecification... imageSpecifications);

    MethodResult[] creatMiniatureCommand(String sp, ImageSpecification... imageSpecifications);
}
