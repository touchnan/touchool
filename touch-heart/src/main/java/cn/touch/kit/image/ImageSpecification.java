/*
 * cn.touch.kit.image.ImageSpecification.java
 * Sep 13, 2012 
 */
package cn.touch.kit.image;

/**
 * Sep 13, 2012
 * 
 * @author <a href="mailto:touchnan@gmail.com">chegnqiang.han</a>
 * 
 */
public class ImageSpecification {

    private String imgFile;//

    private int maxW;
    private int maxH;
    private int pw;
    private int ph;
    private int quality;

    public ImageSpecification() {
        super();
    }

    public ImageSpecification(int maxW, int maxH, int pw, int ph, int quality) {
        this(null, maxW, maxH, pw, ph, quality);
    }

    public ImageSpecification(String imgFile, int maxW, int maxH, int pw, int ph, int quality) {
        super();
        this.imgFile = imgFile;
        this.maxW = maxW;
        this.maxH = maxH;
        this.pw = pw;
        this.ph = ph;
        this.quality = quality;
    }

    /**
     * @return the maxW
     */
    public int getMaxW() {
        return maxW;
    }

    /**
     * @param maxW
     *            the maxW to set
     */
    public void setMaxW(int maxW) {
        this.maxW = maxW;
    }

    /**
     * @return the maxH
     */
    public int getMaxH() {
        return maxH;
    }

    /**
     * @param maxH
     *            the maxH to set
     */
    public void setMaxH(int maxH) {
        this.maxH = maxH;
    }

    /**
     * @return the pw
     */
    public int getPw() {
        return pw;
    }

    /**
     * @param pw
     *            the pw to set
     */
    public void setPw(int pw) {
        this.pw = pw;
    }

    /**
     * @return the ph
     */
    public int getPh() {
        return ph;
    }

    /**
     * @param ph
     *            the ph to set
     */
    public void setPh(int ph) {
        this.ph = ph;
    }

    /**
     * @return the quality
     */
    public int getQuality() {
        return quality;
    }

    /**
     * @param quality
     *            the quality to set
     */
    public void setQuality(int quality) {
        this.quality = quality;
    }

    /**
     * @return the imgFile
     */
    public String getImgFile() {
        return imgFile;
    }

    /**
     * @param imgFile
     *            the imgFile to set
     */
    public void setImgFile(String imgFile) {
        this.imgFile = imgFile;
    }

}
