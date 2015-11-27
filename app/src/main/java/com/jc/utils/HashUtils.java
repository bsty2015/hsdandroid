package com.jc.utils;



import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * @author jared
 * @Description: hash相关工具类
 * @date Nov 5, 2014 2:41:32 PM
 */
public final class HashUtils {

    private static final String SHA_1 = "SHA-1";

    /**
     * md5字符串加密处理
     *
     * @param value
     * @return
     */
    public static String md5(String value) {
        if (!StringUtils.isEmpty(value)) {
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.reset();
                md.update(value.getBytes("UTF-8"));
                return ByteUtils.toHex(md.digest());
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        return value;
    }

    public static String fileMd5(InputStream in) {
        int bufferSize = 256 * 1024;
        DigestInputStream digestInputStream = null;
        try {

            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            digestInputStream = new DigestInputStream(in, messageDigest);
            // read的过程中进行MD5处理，直到读完文件
            byte[] buffer = new byte[bufferSize];
            while (digestInputStream.read(buffer) > 0)
                ;
            // 获取最终的MessageDigest
            messageDigest = digestInputStream.getMessageDigest();
            // 拿到结果，也是字节数组，包含16个元素
            byte[] resultByteArray = messageDigest.digest();
            return ByteUtils.toHex(resultByteArray);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * sha1数据加密
     *
     * @param data 比特流数据
     * @return
     */
    public static String sha1(byte[] data) {
        if (data != null && data.length > 0) {
            try {
                return new String(ByteUtils.toHex(MessageDigest.getInstance(SHA_1).digest(data)));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * int类型数据做hash
     *
     * @param k
     * @return
     */
    public static int hash(int k) {
        k ^= k >>> 16;
        k *= 0x85ebca6b;
        k ^= k >>> 13;
        k *= 0xc2b2ae35;
        k ^= k >>> 16;
        return k;
    }

    /**
     * long类型数据做hash
     *
     * @param value
     * @return
     */
    public static int hash(long value) {
        return (int) (value ^ (value >>> 32));
    }




    /**
     * 生成简单的code用于邀请人
     * @return
     */
    public static String InviteCode(String key) {
        String time = String.valueOf(System.currentTimeMillis());
        return md5(time + key);
    }

    public static void main(String[] args) {
		System.out.println(InviteCode("13867445646"));
	}
    
}
