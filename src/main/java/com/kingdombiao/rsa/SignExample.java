package com.kingdombiao.rsa;


import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

/**
 * 签名
 */
public class SignExample {

    /**
     * RSA私钥签名
     *
     * @param data          签名数据 json格式
     * @param privateKeyStr 私钥字符串
     * @return
     */
    public static String rsaSign(String data, String privateKeyStr) {
        byte[] mdigestSHA = MdigestSHA(data);
        // 将私钥字符串转换成私钥对象
        PrivateKey privateKey = SecureUtil.generatePrivateKey("RSA", Base64.decode(privateKeyStr));
        // 设置签名对象以及加密算法
        Sign sign = SecureUtil.sign(SignAlgorithm.SHA256withRSA);
        // 设置私钥，然后执行签名
        sign.setPrivateKey(privateKey);
        byte[] bytes = sign.sign(mdigestSHA);
        // 将签名转换为 Base64 字符串，然后返回
        return Base64.encode(bytes);
    }

    /**
     * RSA 公钥验签
     *
     * @param data         签名数据 json字符串
     * @param publicKeyStr 公钥
     * @param signStr      签名
     * @return
     */
    public static boolean rsaSignVerify(String data, String publicKeyStr, String signStr) {
        byte[] mdigestSHA = MdigestSHA(data);
        //将公钥字符串转成成公钥对象
        PublicKey publicKey = SecureUtil.generatePublicKey("RSA", Base64.decode(publicKeyStr));
        // 设置签名对象以及加密算法
        Sign sign = SecureUtil.sign(SignAlgorithm.SHA256withRSA);
        // 设置公钥，然后执行验签
        sign.setPublicKey(publicKey);
        return sign.verify(mdigestSHA, Base64.decode(signStr));
    }


    /**
     * 建立新的密钥对，返回打包的Stirng[]形式私钥和公钥
     *
     * @return String[0]为私钥, String[1]为公钥
     */
    public static String[] getKeyPair() {
        KeyPair keyPair = SecureUtil.generateKeyPair("RSA", 2048);
        byte[] privateKey = keyPair.getPrivate().getEncoded();
        byte[] publicKey = keyPair.getPublic().getEncoded();
        String[] result = new String[2];
        result[0] = Base64.encode(privateKey);
        result[1] = Base64.encode(publicKey);
        System.out.println("private key length=" + result[0].length());
        System.out.println("private key =" + result[0]);
        System.out.println("public key length =" + result[1].length());
        System.out.println("public key  =" + result[1]);
        return result;
    }

    /**
     * 计算字符串的SHA数字摘要，以byte[]形式返回
     */
    public static byte[] MdigestSHA(String source) {
        try {
            String data = sortedParams(source);
            return DigestUtil.sha256(data);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 对参数按照ASCII排序（升序）
     *
     * @param param json格式的参数
     * @return
     */
    private static String sortedParams(String param) {
        Map<String, Object> paramMap = JSON.parseObject(param, new TypeReference<SortedMap<String, Object>>() {});
        Set<Map.Entry<String, Object>> set = paramMap.entrySet();
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, Object> entry : set) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (null != value && !"".equals(value) && !"sign".equals(key)) {
                sb.append(key + "=" + value + "&");
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String[] keyPair = getKeyPair();

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


        Map<String, Object> map = JSON.parseObject(req, new TypeReference<SortedMap<String, Object>>() {});

        JSONObject data =(JSONObject) map.get("data");

        //签名
        String sign = rsaSign(data.toJSONString(), keyPair[0]);
        //验签
        boolean result = rsaSignVerify(data.toJSONString(), keyPair[1], sign);
        if (result) {
            System.out.println("匹配成功,合法的签名");
        }else {
            System.out.println("匹配失败 不合法的签名!");
        }

    }


}
