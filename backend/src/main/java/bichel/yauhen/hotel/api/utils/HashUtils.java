package bichel.yauhen.hotel.api.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Random;

public final class HashUtils {

    private HashUtils() {

    }

    /**
     * Calculates the hash of a password and salt using SHA-256.
     *
     * @param password - password to hash
     * @param salt - salt associated with user
     * @return hashed password
     */
    public static String getSHA256Hash(String password, String salt) {
        String salted = salt + password;
        String hashed = salted;

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salted.getBytes());
            hashed = encodeHex(md.digest(), 64);
        }
        catch (Exception ex) {
            System.out.println(ex);
        }

        return hashed;
    }

    /**
     * Returns the hex encoding of a byte array.
     *
     * @param bytes - byte array to encode
     * @param length - desired length of encoding
     * @return hex encoded byte array
     */
    public static String encodeHex(byte[] bytes, int length) {
        BigInteger bigint = new BigInteger(1, bytes);
        String hex = String.format("%0" + length + "X", bigint);

        assert hex.length() == length;
        return hex;
    }

    public static byte[] generateSalt() {
        // Generate salt
        byte[] saltBytes = new byte[16];
        Random random = new Random();
        random.nextBytes(saltBytes);

        return saltBytes;
    }
}
