package cn.touch.common;

import java.io.UnsupportedEncodingException;

/**
 * Created by <a href="mailto:88052350@qq.com">touchnan</a> on 2016/3/25.
 */
public class XorEncrypt {

    /**
     * @param args
     */
    public static void main(String[] args) {
        String val = "user.account=afsd&user.id=192.168.3.232中国人";
        byte[] vB = null;
        try {
            vB = val.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] evB = XorEncrypt.codeEncrypt(vB);
        String hexEV = getHexStr(evB);
        System.out.println(hexEV);
        byte[] dvB = getByteArray(hexEV);
        System.out.println(new String(XorEncrypt.codeDecrypt(dvB)));
    }

    public static byte[] codeEncrypt(byte[] orgbyte, byte key) {
        byte[] result = new byte[orgbyte.length];
        byte key1 = key;
        for (int i = 0; i < orgbyte.length; i++) {
            byte center = orgbyte[i];
            result[i] = (byte) (center ^ key1);
        }
        return result;
    }

    public static byte getRomdomByte() {
        int i = Math.abs(new java.util.Random().nextInt() % 255);
        return ((byte) i);
    }

    public static byte[] codeEncrypt(byte[] orgbyte) {
        byte[] code = new byte[orgbyte.length];
        byte key = getRomdomByte();
        code = codeEncrypt(orgbyte, key);
        byte[] result = new byte[orgbyte.length + 1];
        result[0] = key;
        for (int i = 1; i < orgbyte.length + 1; i++) {
            result[i] = code[i - 1];
        }
        return result;
    }

    public static byte[] codeDecrypt(byte[] orgbyte) {
        byte key = orgbyte[0];
        byte[] code = new byte[orgbyte.length - 1];
        for (int i = 0; i < code.length; i++) {
            code[i] = orgbyte[i + 1];
        }
        byte[] result = codeEncrypt(code, key);
        return result;
    }

    public static String getHexStr(byte[] b) {
        StringBuffer bf = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            byte c = b[i];
            bf.append("0123456789ABCDEF".charAt((c & 0xf0) >> 4));
            bf.append("0123456789ABCDEF".charAt(c & 0x0f));
        }
        return bf.toString();
    }

    public static byte[] getByteArray(String hex) {
        byte[] rtByte = new byte[hex.length() / 2];
        for (int i = 0; i < hex.length(); i += 2) {
            byte tempByte;
            char c = hex.charAt(i);
            c -= 48;
            if (c > 16) {
                c -= 7;
            }
            tempByte = (byte) (c * 0x10);
            c = hex.charAt(i + 1);
            c -= 48;
            if (c > 16) {
                c -= 7;
            }
            tempByte += c;
            rtByte[i / 2] = tempByte;
        }
        return rtByte;
    }
}
