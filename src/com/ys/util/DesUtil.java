package com.ys.util;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.ys.util.basequery.BaseQuery;

import sun.misc.BASE64Decoder;
public class DesUtil {
    private final static String DES = "DES";
    private final static String KEYFILE = "/setting/desKey";
    private final static String KEYNAME = "key";
    //private static String key = "123";
    private static String key = "";
    
     /**
     * Description ��ݼ�ֵ���н���
     * @param data
     * @param key  ���ܼ�byte����
     * @return
     * @throws IOException
     * @throws Exception
     */
    public static String DesDeEncryptData(String data) throws IOException,
            Exception {
        if (data == null)
            return null;
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] buf = decoder.decodeBuffer(data);
        byte[] bt = decrypt(buf);
        return new String(bt);
    }
    /**
     * ���ַ���� 
     * @param str
     * @return
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     */
    public static String DesEncryptData(String str) throws InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException,
            InvalidKeySpecException, NoSuchAlgorithmException,
            NoSuchPaddingException {
        //��ȡkey
        getKey();
        //��ȡ��Կ
        SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
        DESKeySpec keyspec = new DESKeySpec(key.getBytes());
        SecretKey deskey = factory.generateSecret(keyspec);
        // Cipher������ɼ��ܻ���ܹ���
        Cipher c = Cipher.getInstance("DES");
        // �����Կ����Cipher������г�ʼ����DECRYPT_MODE��ʾ����ģʽ  
        c.init(Cipher.ENCRYPT_MODE, deskey);
        byte[] src = str.getBytes();
        // ���ֽ����鸺�𱣴���ܵĽ��
        byte[] cipherByte = c.doFinal(src);
        String enstr = new String(Base64.encodeBase64(cipherByte));
        return enstr;
    }
     /**
     * Description ��ݼ�ֵ���н���
     * @param data
     * @param key  ���ܼ�byte����
     * @return
     * @throws Exception
     */
    private static byte[] decrypt(byte[] data) throws Exception {
    	
    	byte[] keyBytes = key.getBytes();
        // ���һ�������ε������Դ
        SecureRandom sr = new SecureRandom();
        // ��ԭʼ��Կ��ݴ���DESKeySpec����
        DESKeySpec dks = new DESKeySpec(keyBytes);
        // ����һ����Կ������Ȼ�������DESKeySpecת����SecretKey����
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
        // Cipher����ʵ����ɽ��ܲ���
        Cipher cipher = Cipher.getInstance(DES);
        // ����Կ��ʼ��Cipher����
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
        return cipher.doFinal(data);
    }
    
    private static void getKey() {
    	if (key.equals("")) {
    		key = BaseQuery.getContent(KEYFILE, KEYNAME);
    	}    	
    }
}