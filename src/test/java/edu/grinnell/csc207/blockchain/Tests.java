package edu.grinnell.csc207.blockchain;

import java.security.NoSuchAlgorithmException;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Tests {

    /**
     * Verifies the functions of hash work correctly
     *
     * @throws NoSuchAlgorithmException throws in case encryption method in
     * unavailable
     */
    @Test
    public void hashTests() throws NoSuchAlgorithmException {
        BlockChain bc = new BlockChain(300);
        Block blk = new Block(0, 300, null, 9324351);
        Hash hash = bc.getHash();
        assertEquals(true, hash.isValid());
        assertEquals("000201f6c32c24b52b8a5b7d664af23e7db95af8867dbe80eb5c4c30a7", hash.toString());
        assertEquals(true, hash.equals(blk.getHash()));
    }

    /**
     * Verifies the functions of block work correctly
     * 
     * @throws NoSuchAlgorithmException throws in case encryption method in
     * unavailable
     */
    @Test
    public void blockTests() throws NoSuchAlgorithmException {
        BlockChain bc = new BlockChain(300);
        Block blk = new Block(0, 300, null);
        assertEquals(blk.getHash(), bc.mine(-150).getPrevHash());
        assertEquals(0, blk.getNum());
        assertEquals(300, blk.getAmount());
        assertEquals(9324351, blk.getNonce());
        blk = new Block(4, 350, null);
        assertEquals(4, blk.getNum());
        assertEquals(350, blk.getAmount());
        assertEquals(2097632, blk.getNonce());
    }

    /**
     * Verifies the functions of block work correctly
     * 
     * @throws NoSuchAlgorithmException throws in case encryption method in
     * unavailable
     */
    @Test
    public void blockChainTests() throws NoSuchAlgorithmException {
        BlockChain bc = new BlockChain(300);
        assertEquals(false, bc.removeLast());
        assertEquals(1, bc.getSize());
        bc.append(bc.mine(-150));
        assertEquals(2, bc.getSize());
        assertEquals(true, bc.removeLast());
        assertEquals(1, bc.getSize());
        assertEquals("000201f6c32c24b52b8a5b7d664af23e7db95af8867dbe80eb5c4c30a7", bc.getHash().toString());
    }

    /**
     * Verifies that isValidBlockChain works correctly
     * 
     * @throws NoSuchAlgorithmException throws in case encryption method in
     * unavailable
     */
    @Test
    public void isValidBlockChainTests() throws NoSuchAlgorithmException {
        BlockChain bc = new BlockChain(300);
        assertEquals(true, bc.isValidBlockChain());
        bc.append(bc.mine(-150));
        bc.append(bc.mine(-100));
        bc.append(bc.mine(50));
        assertEquals(true, bc.isValidBlockChain());
        bc.append(bc.mine(-100));
        assertEquals(true, bc.isValidBlockChain());
        bc.append(bc.mine(-200));
        assertEquals(false, bc.isValidBlockChain());
        bc = new BlockChain(-300);
        assertEquals(false, bc.isValidBlockChain());
    }

    /**
     * Verifies that toString returns accurate strings
     * 
     * @throws NoSuchAlgorithmException throws in case encryption method in
     * unavailable
     */
    @Test
    public void stringTests() throws NoSuchAlgorithmException {
        BlockChain bc = new BlockChain(300);
        Block blk = bc.mine(-150);
        assertEquals("Block 1 (Amount: -150, Nonce: 2016357, prevHash: 000201f"
                + "6c32c24b52b8a5b7d664af23e7db95af8867dbe80eb5c4c30a7, hash: "
                + "000d744da56bbf9a87737a7491b557d49f502de375678ca16143986c26)",
                blk.toString());
        bc.append(blk);
        assertEquals("Block 0 (Amount: 300, Nonce: 9324351, prevHash: null, "
                + "hash: 000201f6c32c24b52b8a5b7d664af23e7db95af8867dbe80eb5c4"
                + "c30a7)" + '\n' + "Block 1 (Amount: -150, Nonce: 2016357, "
                + "prevHash: 000201f6c32c24b52b8a5b7d664af23e7db95af8867dbe80e"
                + "b5c4c30a7, hash: 000d744da56bbf9a87737a7491b557d49f502de375"
                + "678ca16143986c26)" + '\n', bc.toString());
    }
}
