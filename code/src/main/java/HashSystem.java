import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashSystem {

    /**
     * encrypt a given password
     * @param passwordToHash - the password that need to hash
     * @return - hash password
     * @throws NoSuchAlgorithmException - if the SecureRandom didn't find
     */
    private String encrypt(String passwordToHash) throws NoSuchAlgorithmException {
        String generatedPassword = null;
        byte[] salt = getSalt();
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(salt);
        byte[] bytes = md.digest(passwordToHash.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
        }
        generatedPassword = sb.toString();
        return generatedPassword;
    }

    /**
     * salting bytes
     * @return - salted bytes
     * @throws NoSuchAlgorithmException - if the SecureRandom didn't find
     */
    private byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA512PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

}
