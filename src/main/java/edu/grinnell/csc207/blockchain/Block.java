package edu.grinnell.csc207.blockchain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.ByteBuffer;

/**
 * A single block of a blockchain.
 */
public class Block {
    
    private int count;
    /* is assigned the number of the block */
    private int transaction;
    /* is assigned the amount of money transferred in the transaction */
    private Hash previous;
    /* is assigned the hash value of the previous hash in the blockchain */
    private long number;
    /* is assigned the long value of the nonce that makes the hash */
    private Hash current;
    /* is assigned the hash of the block*/
    
    /**
     * Calculates the block's hash from its data
     * 
     * @return the byte array that makes up the blocks hash
     * @throws NoSuchAlgorithmException throws in case encryption method in
     * unavailable
     */
    public byte[] calculateHash() throws NoSuchAlgorithmException {
        byte[] arrNum = ByteBuffer.allocate(4).putInt(count).array();
        byte[] arrAmount = ByteBuffer.allocate(4).putInt(transaction).array();
        byte[] arrNonce = ByteBuffer.allocate(8).putLong(number).array();
        MessageDigest md = MessageDigest.getInstance("sha-256");
        md.update(arrNum);
        md.update(arrAmount);
        if (previous != null) {
            md.update(previous.getData());
        }
        md.update(arrNonce);
        return md.digest();
    }
    
    /**
     * Creates a valid hash for the block
     * 
     * @throws NoSuchAlgorithmException throws in case encryption method in
     * unavailable
     */
    public void createValidHash() throws NoSuchAlgorithmException {
        for (long i = 0; i < Long.MAX_VALUE; i += 1) {
            number = i;
            current = new Hash(calculateHash());
            if (current.isValid()) {
                break;
            }
        }
    }

    /**
     * Generates a new block with the given parameters
     *
     * @param num the number of the block in the blockchain
     * @param amount the amount of money being transferred in the transaction
     * @param prevHash the hash value of the previous block
     * @throws NoSuchAlgorithmException throws in case encryption method in
     * unavailable
     */
    public Block(int num, int amount, Hash prevHash) throws NoSuchAlgorithmException {
        count = num;
        transaction = amount;
        previous = prevHash;
        createValidHash();
    }

    /**
     * Generates a new block with the given parameters this time giving nonce
     *
     * @param num the number of the block in the blockchain
     * @param amount the amount of money being transferred in the transaction
     * @param prevHash the hash value of the previous block
     * @param nonce the value used to compute hash
     * @throws NoSuchAlgorithmException throws in case encryption method in
     * unavailable
     */
    public Block(int num, int amount, Hash prevHash, long nonce) throws NoSuchAlgorithmException {
        count = num;
        transaction = amount;
        previous = prevHash;
        number = nonce;
        current = new Hash(calculateHash());
        if (!current.isValid()) {
            createValidHash();
        }
    }

    /**
     * Returns the count of the block
     *
     * @return the number of the block in the blockchain
     */
    public int getNum() {
        return count;
    }

    /**
     * Returns the amount of the transaction
     *
     * @return the amount of money transferred
     */
    public int getAmount() {
        return transaction;
    }

    /**
     * Returns the number used to generate the hash
     *
     * @return the long that defines the hash of the block
     */
    public long getNonce() {
        return number;
    }

    /**
     * Returns the value of the previous hash
     *
     * @return the previous hash in the blockchain
     */
    public Hash getPrevHash() {
        return previous;
    }

    /**
     * Returns the value of the current hash
     *
     * @return the current hash in the blockchain
     */
    public Hash getHash() {
        return current;
    }

    /**
     * Returns a string representation of all the parts in the block
     *
     * @return a string that conveys the various parts of the block
     */
    @Override
    public String toString() {
        return "Block " + count + " (Amount: " + transaction + ", Nonce: " + number
                + ", prevHash: " + previous + ", hash: " + current + ")";
    }
}
