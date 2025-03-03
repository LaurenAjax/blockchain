package edu.grinnell.csc207.blockchain;

import java.util.Arrays;

/**
 * A wrapper class over a hash value (a byte array).
 */
public class Hash {

    private byte[] hash;
    /* the array of bytes that make up the hash */

    /**
     * Generates the hash that contains the data
     *
     * @param data the data that denotes the blockchain legitimate
     */
    public Hash(byte[] data) {
        hash = data;
    }

    /**
     * Returns the data belonging to the hash
     *
     * @return the byte array that makes up the hash
     */
    public byte[] getData() {
        return hash;
    }

    /**
     * Verifies whether or not the first three values of the hash are zero
     *
     * @return whether or not the hash is valid
     */
    public boolean isValid() {
        return hash[0] == 0 && hash[1] == 0 && hash[2] == 0;
    }

    /**
     * Returns the hash as a string
     *
     * @return the string that represents the hash
     */
    public String toString() {
        String str = "";
        for (int i = 0; i < hash.length; i++) {
            str = str + String.format("%x", Byte.toUnsignedInt(hash[i]));
        }
        return str;
    }

    /**
     * Checks whether two Hashes are equal to each other
     *
     * @param other the hash to be compared
     * @return whether the two hashes are equal to each other
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Hash) {
            Hash o = (Hash) other;
            return Arrays.equals(hash, o.getData());
        }
        return false;
    }
}
