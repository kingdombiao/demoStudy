package com.kingdombiao.rsa;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.codec.binary.Base64;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;
import java.util.SortedMap;

public class RSAUtils {

    /**
     * 使用私钥加签
     *
     * @param privateKeyByte 打包成byte[]的私钥
     * @param source    要签名的数据，一般应是数字摘要
     * @return 签名 byte[]
     */
    public static byte[] sign(byte[] privateKeyByte, byte[] source) {
        try {
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyByte);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
            Signature signature= Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKey);
            signature.update(source);
            return signature.sign();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 公钥验证数字签名
     *
     * @param publicKeyByte 打包成byte[]形式的公钥
     * @param source    原文的数字摘要
     * @param sign      签名（对原文的数字摘要的签名）
     * @return 是否证实 boolean
     */
    public static boolean verify(byte[] publicKeyByte, byte[] source, byte[] sign) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            Signature signature = Signature.getInstance("SHA256withRSA");
            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(publicKeyByte);
            PublicKey pubKey = keyFactory.generatePublic(pubKeySpec);
            signature.initVerify(pubKey);
            signature.update(source);
            return signature.verify(sign);
        } catch (Exception e) {
            return false;
        }
    }

    public static KeyPair getKeyPair() {
        KeyPair kp = null;
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(2048);
            kp = kpg.generateKeyPair();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kp;
    }

    /**
     * 建立新的密钥对，返回打包的byte[]形式私钥和公钥
     *
     * @return 包含打包成byte[]形式的私钥和公钥的object[], 其中，object[0]为私钥byte[],object[1]为公钥byte[]
     */
    public static Object[] giveRSAKeyPairInByte() {
        KeyPair keyPair = getKeyPair();
        if (keyPair == null) {
            return null;
        }
        Object[] result = new Object[2];
        if (keyPair != null) {
            PrivateKey privateKey = keyPair.getPrivate();
            byte[] privteByte = privateKey.getEncoded();
            PublicKey pub = keyPair.getPublic();
            byte[] publicByte = pub.getEncoded();
            result[0] = privteByte;
            System.out.println("private key length="+Base64.encodeBase64String(privteByte).length());
            System.out.println("private key ="+Base64.encodeBase64String(privteByte));
            System.out.println("public key length ="+Base64.encodeBase64String(publicByte).length());
            System.out.println("public key  ="+Base64.encodeBase64String(publicByte));
            result[1] = publicByte;
            return result;
        }
        return null;
    }


    /**
     * 计算字符串的SHA数字摘要，以byte[]形式返回
     */
    public static byte[] MdigestSHA(String source) {
        try {
            MessageDigest thisMD = MessageDigest.getInstance("SHA-256");
            byte[] digest = thisMD.digest(source.getBytes("UTF-8"));
            return digest;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将byte[] 转换成16进制字符串
     */
    public static String byte2Hex(byte[] srcBytes) {
        StringBuilder hexRetSB = new StringBuilder();
        for (byte b : srcBytes)
            hexRetSB.append(byte2Hex(b));
        return hexRetSB.toString();
    }

    public static String byte2Hex(byte srcByte) {
        return String.format("%02X", srcByte);
    }

    public static byte hex2Byte(String source) {
        return (byte) Integer.parseInt(source, 16);
    }

    /**
     * 将16进制字符串转为byte[]
     */
    public static byte[] hex2Bytes(String source) {
        byte[] sourceBytes = new byte[source.length() / 2];
        for (int i = 0; i < sourceBytes.length; i++)
            sourceBytes[i] = hex2Byte(source.substring(i * 2, i * 2 + 2));
        return sourceBytes;
    }

    public static void main(String[] args) {
        //生成私钥-公钥对
        Object[] keyPairInByte = giveRSAKeyPairInByte();

        //获得摘要
        byte[] source = MdigestSHA("test");

        //使用私钥对摘要进行加密 获得密文 即数字签名
        byte[] sign = sign((byte[]) keyPairInByte[0], source);

        //使用公钥对密文进行解密,解密后与摘要进行匹配
        boolean yes = verify((byte[]) keyPairInByte[1], source, sign);
        if (yes) {
            System.out.println("匹配成功 合法的签名!");
        }

        String req ="{\n" +
                "    \"code\": 0,\n" +
                "    \"msg\": \"\",\n" +
                "    \"data\": {\n" +
                "      \"sub_code\": 0,\n" +
                "      \"sub_msg\": \"success\",\n" +
                "      \"data\": {\n" +
                "          \"auth_status\": 1\n" +
                "          }\n" +
                "    }\n" +
                " }";

        Map<String, Object> map = JSON.parseObject(req, new TypeReference<SortedMap<String, Object>>() {
        });

        System.out.println(map);


    }


}
