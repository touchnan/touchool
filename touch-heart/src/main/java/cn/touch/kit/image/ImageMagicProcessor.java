package cn.touch.kit.image;

import java.io.File;
import java.io.IOException;

import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import cn.touch.kit.MethodResult;
import cn.touch.kit.env.CommandExec;

/**
 * Sep 6, 2012
 * 
 * @author <a href="mailto:touchnan@gmail.com">chegnqiang.han</a>
 * 
 */
public class ImageMagicProcessor implements ImageProcessor {
    private static Log log = Logs.getLog(ImageMagicProcessor.class);

    private String imageMagic_install_path = "C:/Program Files/ImageMagick-6.7.9-Q16/";// imageMagic的安装目录

    public ImageMagicProcessor() {
        super();
    }

    public ImageMagicProcessor(String imageMagic_install_path) {
        super();
        this.imageMagic_install_path = imageMagic_install_path;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MethodResult creatMiniatureCommand(String sp, String dp, int pw, int ph, int quality) {
        return creatZoomCommand(sp, dp, 0, 0, pw, ph, quality);
    }

    private MethodResult creatZoomCommand(String sp, String dp, int maxW, int maxH, int pw, int ph, int quality) {
        if (pw < 0 || ph < 0 || Strings.isBlank(sp) || Strings.isBlank(dp)) {
            return new MethodResult("目录为空或宽高小于0!");
        }
        IntegerImage widths = new IntegerImage();
        IntegerImage hights = new IntegerImage();
        MethodResult r = ImageUtil.getImageWH(sp, widths, hights);
        if (!r.isSuccess()) {
            return r;
        }

        return creatZoomCommand(sp, widths, hights, dp, maxW, maxH, pw, ph, quality);

    }

    /**
     * @param sp
     *            源文件地址
     * @param spWidths
     *            源文件宽
     * @param spHights
     *            源文件高
     * @param dp
     *            目标文件地址
     * @param maxW
     *            目标文件最大宽
     * @param maxH
     *            目标文件最大高
     * @param pw
     *            目标文件宽
     * @param ph
     *            目标文件高
     * @param quality
     *            目标文件品质
     * @return
     */
    private MethodResult creatZoomCommand(String sp, IntegerImage spWidths, IntegerImage spHights, String dp, int maxW,
            int maxH, int pw, int ph, int quality) {
        if (pw + ph == 0) {
            pw = spWidths.i;
            ph = spHights.i;
        }

        if (maxW > 0 && pw > maxW) {
            pw = maxW;
        }

        if (maxH > 0 && ph > maxH) {
            ph = maxH;
        }

        if (log.isDebugEnabled()) {
            log.debug(spWidths.i + ":" + spHights.i);
        }

        String command = ""; // 命令
        float f =
                ((float) pw / (float) spWidths.i <= (float) ph / (float) spHights.i || ph<=0) ? ((float) pw * (float) 100 / (float) spWidths.i)
                        : ((float) ph * (float) 100 / (float) spHights.i);
        command =
                imageMagic_install_path + "convert \"" + sp + "\" -resize " + f + "%" + " -quality " + quality + " \""
                        + dp + "\"";

        MethodResult mr = CommandExec.runASystemCommand(false, command, "");
        if (mr.isSuccess()) {
            return mr;
        }
        File ef = new File(dp);
        return new MethodResult(ef.exists());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MethodResult creatMiniatureCommand(File srcFile, String dp, int pw, int ph, int quality) {
        return creatMiniatureCommand(srcFile, dp, 0, 0, pw, ph, quality);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MethodResult creatMiniatureCommand(File srcFile, String dp, int maxW, int maxH, int pw, int ph, int quality) {
        String fileName = null;
        try {
            fileName = srcFile.getCanonicalPath();
        } catch (IOException e) {
            log.error(e);
        }
        return creatZoomCommand(fileName, dp, maxW, maxH, pw, ph, quality);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MethodResult creatMiniatureCommand(String sp, ImageSpecification imageSpecification) {
        return creatZoomCommand(sp, imageSpecification.getImgFile(), imageSpecification.getMaxW(),
                imageSpecification.getMaxH(), imageSpecification.getPw(), imageSpecification.getPh(),
                imageSpecification.getQuality());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MethodResult creatMiniatureCommand(File srcFile, ImageSpecification imageSpecification) {
        return creatMiniatureCommand(srcFile, imageSpecification.getImgFile(), imageSpecification.getMaxW(),
                imageSpecification.getMaxH(), imageSpecification.getPw(), imageSpecification.getPh(),
                imageSpecification.getQuality());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MethodResult[] creatMiniatureCommand(File srcFile, ImageSpecification... imageSpecifications) {
        if (imageSpecifications != null) {
            String fileName = null;
            try {
                fileName = srcFile.getCanonicalPath();
            } catch (IOException e) {
                log.error(e);
            }
            return creatMiniatureCommand(fileName, imageSpecifications);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MethodResult[] creatMiniatureCommand(String sp, ImageSpecification... imageSpecifications) {
        if (imageSpecifications != null) {

            if (Strings.isBlank(sp)) {
                throw new RuntimeException("源文件目录为空或宽高小于0!");
            }

            for (int i = 0; i < imageSpecifications.length; i++) {
                ImageSpecification imgSpec = imageSpecifications[i];
                if (Strings.isBlank(imgSpec.getImgFile())) {
                    throw new RuntimeException("目标文件目录为空");
                }
                if (imgSpec.getPw() < 0 || imgSpec.getPh() < 0) {
                    throw new RuntimeException("目标文件[" + imgSpec.getImgFile() + "]目录为空或宽高小于0!");
                }
            }

            IntegerImage widths = new IntegerImage();
            IntegerImage hights = new IntegerImage();
            MethodResult r = ImageUtil.getImageWH(sp, widths, hights);
            if (!r.isSuccess()) {
                throw new RuntimeException("获取源文件出错:" + r.getMsg());
            }

            MethodResult[] mrs = new MethodResult[imageSpecifications.length];
            int idx = 0;
            for (ImageSpecification imgSpec : imageSpecifications) {
                mrs[idx] =
                        creatZoomCommand(sp, widths, hights, imgSpec.getImgFile(), imgSpec.getMaxW(),
                                imgSpec.getMaxH(), imgSpec.getPw(), imgSpec.getPh(), imgSpec.getQuality());
                idx++;
            }
            return mrs;
        }
        return null;
    }

}
