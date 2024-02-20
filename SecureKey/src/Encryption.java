import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import javax.crypto.*;
import java.util.Base64;
import javax.crypto.spec.*;


public class Encryption {
        
        
        private static Key generateKey(String key) {
            byte[] keyValue = null;
            try {
                keyValue = key.getBytes("utf-8");
            } catch (UnsupportedEncodingException ex) {
                System.out.println("KEY GENERATION ERROR");
            }
            return new SecretKeySpec(keyValue, "AES");
        }
        
        public static String encrypt(String data , String key)  {
            try {
                Key KEY = generateKey(key);
                Cipher cipher = Cipher.getInstance("AES");
                
                cipher.init(Cipher.ENCRYPT_MODE, KEY);
                byte[] encryptedByteValue = cipher.doFinal(data.getBytes("utf-8"));
                return Base64.getEncoder().encodeToString(encryptedByteValue);
                
            } catch (Exception ex) {
                System.out.println("ENCRYPTION ERROR: " + ex.getMessage());
            }
            return null;
        }
        
        public static String decrypt(String data , String key){
            
            try {
                Key KEY = generateKey(key);
                Cipher cipher = Cipher.getInstance("AES");
                cipher.init(Cipher.DECRYPT_MODE, KEY);
                byte[] decryptedValue64 = Base64.getDecoder().decode(data);
                byte[] decryptedByteValue = cipher.doFinal(decryptedValue64);
                return new String(decryptedByteValue, "utf-8");
            } catch (Exception ex) {
                System.out.println("DECRYPTION ERROR" + ex.getMessage());
            }
            return null;
        }
        
        
        public static String hashing(String input)
        {
            String hashString = null;
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
                hashString = bytesToHex(hashBytes);
                
            } catch (NoSuchAlgorithmException ex) {
                System.out.println("HASHING ERROR" + ex.getMessage());
            }
            return hashString;
        }
        
        private static String bytesToHex(byte[] bytes) {
            StringBuilder hexString = new StringBuilder();
            for (byte b : bytes) {
                String hex = String.format("%02x", b);
                hexString.append(hex);
            }
            return hexString.toString();
        }
       
        
}
