package edu.grinnell.csc207.blockchain;

import java.security.NoSuchAlgorithmException;

/**
 * A linked list of hash-consistent blocks representing a ledger of monetary
 * transactions.
 */
public class BlockChain {

    private static class Node {

        public Block block;
        /* the block assigned to the node */
        public Node next;
        /* the next node in the chain */

        /**
         * Generates a new Node from the given parameters
         *
         * @param blk the block contained within the node
         * @param newNode the next node in the list
         * @throws NoSuchAlgorithmException throws in case encryption method in
         * unavailable
         */
        public Node(Block blk, Node newNode) throws NoSuchAlgorithmException {
            block = blk;
            next = newNode;
        }
    }

    private Node first;
    /* the first node in the chain */
    private Node last;
    /* the last node in the chain */

    /**
     * Initialized the blockchain by setting both first and last equal to the
     * new node constructed
     *
     * @param initial the initial amount of money used by the blocks
     * @throws NoSuchAlgorithmException throws in case encryption method in
     * unavailable
     */
    public BlockChain(int initial) throws NoSuchAlgorithmException {
        first = last = new Node(new Block(0, initial, null), null);
    }

    /**
     * Mines up a new block that can be placed next in the chain
     *
     * @param amount the amount of money involved in the transaction
     * @return the new block made out of the amount of money
     * @throws NoSuchAlgorithmException throws in case encryption method in
     * unavailable
     */
    public Block mine(int amount) throws NoSuchAlgorithmException {
        Block blk = new Block(last.block.getNum() + 1, amount, last.block.getHash());
        return blk;
    }

    /**
     * Returns the number of blocks in the chain
     *
     * @return the size of the blockchain
     */
    public int getSize() {
        return last.block.getNum() + 1;
    }

    /**
     * Adds a new block to the blockchain
     *
     * @param blk a block constructed from a given nonce
     * @throws NoSuchAlgorithmException throws in case encryption method in
     * unavailable
     */
    public void append(Block blk) throws NoSuchAlgorithmException {
        if (blk.getPrevHash() != last.block.getHash()) {
            throw new IllegalArgumentException();
        } else {
            last.next = new Node(blk, null);
            last = last.next;
        }
    }

    /**
     * Removes the last block from the chain if there is more than one
     *
     * @return whether or not a block was successfully removed
     */
    public boolean removeLast() {
        if (first == last) {
            return false;
        } else {
            Node cur;
            for (cur = first; cur.next != last; cur = cur.next) {
            }
            cur.next = null;
            last = cur;
            return true;
        }
    }

    /**
     * Returns the hash value of the current block
     *
     * @return the hash value belonging to the last block in the chain
     */
    public Hash getHash() {
        return last.block.getHash();
    }

    /**
     * Determines whether Alice or Bob have a valid amount of money in their
     * accounts
     *
     * @return whether either Alice or Bob have a negative amount of money in
     * their accounts
     */
    public boolean isValidBlockChain() {
        int amountAlice = 0;
        int amountBob = first.block.getAmount();
        for (Node cur = first; cur != null; cur = cur.next) {
            amountAlice += cur.block.getAmount();
            amountBob -= cur.block.getAmount();
            if (amountAlice < 0 || amountBob < 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Prints the amount of money Alice and Bob have in their accounts
     *
     */
    public void printBalances() {
        int initial = first.block.getAmount();
        int amountAlice = initial;
        for (Node cur = first.next; cur != null; cur = cur.next) {
            amountAlice += cur.block.getAmount();
        }
        int amountBob = initial - amountAlice;
        System.out.println("Alice: " + amountAlice + ", Bob: " + amountBob);
    }

    /**
     * Returns all the blocks in the chain as a list-like string
     *
     * @return the blocks of the chain with a new line in between each block
     */
    @Override
    public String toString() {
        String str = "";
        for (Node cur = first; cur != null; cur = cur.next) {
            str = str + cur.block.toString() + '\n';
        }
        return str;
    }
}
