/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.poefinal;

/**
 *
 * @author mokwena
 */
import javax.swing.*;
import java.util.*;
import java.util.regex.*;
import java.io.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
public class POEFinal {

   


    static String regUsername = "";
    static String regPassword = "";
    static String regCell = "";
    static boolean loggedIn = false;
    static int messageCount = 0;

    static ArrayList<String> sentMessages = new ArrayList<>();
    static ArrayList<String> disregardedMessages = new ArrayList<>();
    static ArrayList<String> storedMessages = new ArrayList<>();
    static ArrayList<String> messageHashes = new ArrayList<>();
    static ArrayList<String> messageIDs = new ArrayList<>();

    public static void main(String[] args) {
        registerUser();
        loginUser();

        if (loggedIn) {
            JOptionPane.showMessageDialog(null, "Welcome to QuickChat.");
            int totalMessages = Integer.parseInt(JOptionPane.showInputDialog("How many messages would you like to send?"));

            for (int i = 0; i < totalMessages; i++) {
                int option;
                do {
                    option = Integer.parseInt(JOptionPane.showInputDialog(
                        "Choose an option:\n1) Send Message\n2) Show Recently Sent (Coming Soon)\n3) Quit"
                    ));

                    switch (option) {
                        case 1:
                            handleMessageFlow();
                            break;
                        case 2:
                            JOptionPane.showMessageDialog(null, "Coming Soon.");
                            break;
                        case 3:
                            JOptionPane.showMessageDialog(null, "Exiting...");
                            break;
                        default:
                            JOptionPane.showMessageDialog(null, "Invalid option.");
                    }
                } while (option != 3 && messageCount < totalMessages);
            }

            JOptionPane.showMessageDialog(null, "Total messages sent: " + messageCount);
        }
    }

    // ---------------- Registration ----------------

    public static void registerUser() {
        regUsername = JOptionPane.showInputDialog("Enter username (max 5 chars, must include _):");
        if (!checkUsername(regUsername)) {
            JOptionPane.showMessageDialog(null,
                "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.");
            System.exit(0);
        } else {
            JOptionPane.showMessageDialog(null, "Username successfully captured.");
        }

        regPassword = JOptionPane.showInputDialog("Enter password:");
        if (!checkPasswordComplexity(regPassword)) {
            JOptionPane.showMessageDialog(null,
                "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
            System.exit(0);
        } else {
            JOptionPane.showMessageDialog(null, "Password successfully captured.");
        }

        regCell = JOptionPane.showInputDialog("Enter SA cell number (must start with +27):");
        if (!checkCellPhoneNumber(regCell)) {
            JOptionPane.showMessageDialog(null,
                "Cell phone number incorrectly formatted or does not contain international code.");
            System.exit(0);
        } else {
            JOptionPane.showMessageDialog(null, "Cell phone number successfully added.");
        }
    }

    public static boolean checkUsername(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    public static boolean checkPasswordComplexity(String password) {
        return password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$");
    }

    public static boolean checkCellPhoneNumber(String cell) {
        return cell.matches("^\\+27\\d{9}$");
    }

    // ---------------- Login ----------------

    public static void loginUser() {
        String u = JOptionPane.showInputDialog("Enter your username:");
        String p = JOptionPane.showInputDialog("Enter your password:");

        if (u.equals(regUsername) && p.equals(regPassword)) {
            String fname = JOptionPane.showInputDialog("Enter your first name:");
            String lname = JOptionPane.showInputDialog("Enter your last name:");
            JOptionPane.showMessageDialog(null, "Welcome " + fname + ", " + lname + " it is great to see you again.");
            loggedIn = true;
        } else {
            JOptionPane.showMessageDialog(null, "Username or password incorrect, please try again.");
            System.exit(0);
        }
    }

    // ---------------- Messaging ----------------

    public static void handleMessageFlow() {
        String recipient = JOptionPane.showInputDialog("Enter recipient number (must start with +27):");
        if (!checkCellPhoneNumber(recipient)) {
            JOptionPane.showMessageDialog(null,
                "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.");
            return;
        }

        String message = JOptionPane.showInputDialog("Enter your message (max 250 characters):");

        if (message.length() > 250) {
            JOptionPane.showMessageDialog(null,
                "Message exceeds 250 characters by " + (message.length() - 250) + ", please reduce size.");
            return;
        }

        String messageID = String.valueOf((long) (Math.random() * 1_000_000_0000L));
        String hash = createMessageHash(messageID, messageCount, message);

        String[] options = {"Send", "Disregard", "Store"};
        int choice = JOptionPane.showOptionDialog(null, "Choose action", "Message Action", 0,
            JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        switch (choice) {
            case 0:
                sentMessages.add(message);
                messageHashes.add(hash);
                messageIDs.add(messageID);
                messageCount++;
                JOptionPane.showMessageDialog(null,
                    "Message sent!\nID: " + messageID + "\nHASH: " + hash + "\nTO: " + recipient + "\nMESSAGE:\n" + message);
                break;

            case 1:
                disregardedMessages.add(message);
                JOptionPane.showMessageDialog(null, "Message disregarded.");
                break;

            case 2:
                storedMessages.add(message);
                storeMessageJSON(recipient, messageID, hash, message);
                JOptionPane.showMessageDialog(null, "Message stored.");
                break;
        }
    }

    public static String createMessageHash(String messageID, int count, String message) {
        String[] words = message.split(" ");
        String first = words.length > 0 ? words[0] : "";
        String last = words.length > 1 ? words[words.length - 1] : "";
        return (messageID.substring(0, 2) + ":" + count + ":" + first + last).toUpperCase();
    }

    public static void storeMessageJSON(String recipient, String messageID, String hash, String message) {
        JSONObject msg = new JSONObject();
        msg.put("recipient", recipient);
        msg.put("messageID", messageID);
        msg.put("hash", hash);
        msg.put("message", message);

        JSONArray messagesArray = new JSONArray();

        try {
            File file = new File("messages.json");
            if (file.exists()) {
                JSONParser parser = new org.json.simple.parser.JSONParser();
                Object obj = parser.parse(new FileReader("messages.json"));
                messagesArray = (JSONArray) obj;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        messagesArray.add(msg);

        try (FileWriter file = new FileWriter("messages.json")) {
            file.write(messagesArray.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

    

