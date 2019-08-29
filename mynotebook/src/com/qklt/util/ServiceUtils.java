package com.qklt.util;

import java.security.MessageDigest;
import java.util.Random;

public class ServiceUtils {

    // MD5加密
    // 关于: http://www.360doc.com/content/18/1123/02/99071_796636661.shtml
    public static String getMD5String(String str) {
        try {

            // getInstance返回MessageDigest单例
            MessageDigest md = MessageDigest.getInstance("MD5");
            // getBytes使用平台的默认字符集将字符串str编码为 byte序列(字符串->字节数组)
            byte[] strByte = str.getBytes();
            // 传入需要计算的字符串(该字符串已转成字节数组)
            md.update(strByte);
            // digest完成计算，计算结果通过字节数组返回(result就是md5加密后的密文)
            byte[] result = md.digest();
            // 计算结果是一个字节数组，再进行二次处理转换为字符串
            return byteArrayToHex(result);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 字节数组转成十六进制编码后的字符串
    private static String byteArrayToHex(byte[] byteArray) {

        // 存放16进制字符
        char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F' };

        char[] resultCharArray = new char[byteArray.length * 2];

        // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中
        int index = 0;

        for (byte b : byteArray) {
            resultCharArray[index ++] = hexDigits[b >>> 4 & 0xf];
            resultCharArray[index ++] = hexDigits[b & 0xf];
        }

        return new String(resultCharArray);
    }

    // 生成随机字符串
    public static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }


}
