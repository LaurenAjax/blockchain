package edu.grinnell.csc207.blockchain;

import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/**
 * The main driver for the block chain program.
 */
public class BlockChainDriver {

    /**
     * Generates a new block and shows its transaction amount and nonce
     * 
     * @param bc the blockchain that contains mine
     * @throws NoSuchAlgorithmException throws in case encryption method in
     * unavailable
     */
    public static void mineBlock(BlockChain bc) throws NoSuchAlgorithmException {
        System.out.print("Amount transferred? ");
        Scanner scan = new Scanner(System.in);
        int answer = scan.nextInt();
        Block blk = bc.mine(answer);
        System.out.println("amount = " + answer + ", nonce = " + blk.getNonce());
    }

    /**
     * Appends a block of the given transaction amount and nonce to the chain
     * 
     * @param bc the blockchain the block is appended to
     * @throws NoSuchAlgorithmException throws in case encryption method in
     * unavailable
     */
    public static void appendBlock(BlockChain bc) throws NoSuchAlgorithmException {
        System.out.print("Amount transferred? ");
        Scanner scan = new Scanner(System.in);
        int answer = scan.nextInt();
        System.out.print("Nonce? ");
        Scanner nonce = new Scanner(System.in);
        long reply = scan.nextLong();
        Block blk = new Block(bc.getSize(), answer, bc.getHash(), reply);
        bc.append(blk);
    }

    /**
     * Prints whether or not the blockchain is valid
     * 
     * @param bc the blockchain being checked
     */
    public static void checkBlock(BlockChain bc) {
        if (bc.isValidBlockChain()) {
            System.out.println("Chain is valid!");
        } else {
            System.out.println("Chain is invalid!");
        }
    }

    /**
     * Prints out a list of the various commands the user can input
     * 
     */
    public static void listOfCommands() {
        System.out.println("Valid commands: ");
        System.out.println("\tmine: discovers the nonce for a given transaction ");
        System.out.println("\tappend: appends a new block onto the end of the chain ");
        System.out.println("\tremove: removes the last block from the end of the chain ");
        System.out.println("\tcheck: checks that the block chain is valid ");
        System.out.println("\treport: reports the balances of Alice and Bob ");
        System.out.println("\thelp: prints this list of commands ");
        System.out.println("\tquit: quits the program ");
    }

    /**
     * Determines which of the commands to execute based on the user's input
     * 
     * @param bc the blockchain the commands use
     * @param answer the input of the user
     * @throws NoSuchAlgorithmException throws in case encryption method in
     * unavailable
     */
    public static void commands(BlockChain bc, String answer) throws NoSuchAlgorithmException {
        switch (answer) {
            case "mine":
                mineBlock(bc);
                break;
            case "append":
                appendBlock(bc);
                break;
            case "remove":
                bc.removeLast();
                break;
            case "check":
                checkBlock(bc);
                break;
            case "report":
                bc.printBalances();
                break;
            case "help":
                listOfCommands();
                break;
            case "quit":
                System.exit(0);
                break;
            default:
                System.out.println("Invalid input. Please use help command to find valid inputs.");
        }
    }

    /**
     * The main entry point for the program.
     *
     * @param args the command-line arguments
     * @throws java.security.NoSuchAlgorithmException
     */
    public static void main(String[] args) throws NoSuchAlgorithmException {
        if (args.length != 1 || Integer.parseInt(args[0]) < 0) {
            System.out.println("Usage: java BlockChainDriver <amount>");
            System.exit(1);
        }
        BlockChain bc = new BlockChain(Integer.parseInt(args[0]));
        while (true) {
            System.out.print(bc.toString());
            System.out.print("Command? ");
            Scanner scan = new Scanner(System.in);
            String answer = scan.next();
            commands(bc, answer);
            System.out.println("");
        }
    }
}
