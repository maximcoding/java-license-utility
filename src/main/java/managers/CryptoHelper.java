package managers;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CryptoHelper {

    static String aesPaddingAlgorith = "AES/CBC/PKCS5PADDING";
    static String charsetName = "UTF-8";
    public static String decrypt(String encrypted, String key, String initVector) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes(charsetName));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(charsetName), "AES");
            Cipher cipher = Cipher.getInstance(aesPaddingAlgorith);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));
            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String encrypt(String key, String initVector, String value) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes(charsetName));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(charsetName), "AES");
            Cipher cipher = Cipher.getInstance(aesPaddingAlgorith);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(value.getBytes());
            System.out.println("encrypted string: " + Base64.encodeBase64String(encrypted));
            return Base64.encodeBase64String(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}

