import java.util.Scanner;

public class EncryptionProgram {
    private static String passkey = "";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String action = "";

        while (!action.equals("QUIT")) {
            String message = scanner.nextLine().toUpperCase();
            action = message.split(" ")[0];

            switch (action) {
                case "PASSKEY":
                    passkey = message.split(" ")[1];
                    System.out.println("RESULT Success");
                    break;
                case "ENCRYPT":
                    if (passkey.isEmpty()) {
                        System.out.println("ERROR Password not set");
                        continue;
                    }
                    String encrypted = encrypt(message.split(" ")[1], passkey);
                    System.out.println("RESULT " + encrypted);
                    break;
                case "DECRYPT":
                    if (passkey.isEmpty()) {
                        System.out.println("ERROR Passkey not set");
                        continue;
                    }
                    String decrypted = decrypt(message.split(" ")[1], passkey);
                    System.out.println("RESULT " + decrypted);
                    break;
            }
        }

        scanner.close();
    }

    private static String encrypt(String word, String cypher) {
        StringBuilder encryptedWord = new StringBuilder();
        int index = 0;
        for (char letter : word.toCharArray()) {
            int charValue = letter;
            int cypherValue = cypher.charAt(index);
            int shiftedValue = (charValue + cypherValue) % 26;
            char newLetter = (char) (shiftedValue + 65);
            encryptedWord.append(newLetter);
            index = (index + 1) % cypher.length();
        }
        return encryptedWord.toString();
    }

    private static String decrypt(String word, String cypher) {
        StringBuilder decryptedWord = new StringBuilder();
        int index = 0;
        for (char letter : word.toCharArray()) {
            int charValue = letter;
            int cypherValue = cypher.charAt(index);
            int shiftedValue = (charValue - cypherValue) % 26;
            char newLetter = (char) (shiftedValue + 65);
            decryptedWord.append(newLetter);
            index = (index + 1) % cypher.length();
        }
        return decryptedWord.toString();
    }
}
