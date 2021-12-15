import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.security.*;

public class Key {
    public static SecretKey generateKey() {
        KeyGenerator keyGenerator = null;
        SecureRandom secureRandom = new SecureRandom();
        int keyBitSize = 256;

        try {
            keyGenerator = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        keyGenerator.init(keyBitSize, secureRandom);

        return keyGenerator.generateKey();
    }

    public static String hexString(byte[] bytes) {
        BigInteger bigInteger = new BigInteger(1, bytes);
        return String.format("%0" + (bytes.length << 1) + "X", bigInteger);
    }

    public static String generateHMAC(String algorithm, String data, String key) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), algorithm);
        Mac mac = Mac.getInstance(algorithm);
        mac.init(secretKeySpec);
        return hexString(mac.doFinal(data.getBytes()));
    }
}
