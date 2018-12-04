import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;


public class SessionKey {

    private SecretKey secretKey;

    public SecretKey getSecretKey() {
        return secretKey;
    }

    public String encodeKey() {
        return Base64
                .getEncoder()
                .encodeToString(secretKey.getEncoded());
    }

    public SessionKey(Integer keylength) throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(keylength);
        secretKey = keyGen.generateKey();
    }

    public SessionKey(String encodedkey) {
        byte[] decodedKey = Base64.getDecoder().decode(encodedkey);
        secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }

    public static void main(String args[]) throws NoSuchAlgorithmException{
        SessionKey keyOne = new SessionKey(128);
        String keyOneString = keyOne.encodeKey();
        System.out.println(keyOneString);
        SessionKey keyTwo = new SessionKey(keyOneString);
        String keyTwoString = keyTwo.encodeKey();
        System.out.println(keyTwoString);

        if (keyOne.getSecretKey().equals(keyTwo.getSecretKey())) {
            System.out.println("Pass");
        }
        else {
            System.out.println("Fail");
        }        
    }
}