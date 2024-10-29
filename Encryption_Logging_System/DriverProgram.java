import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class DriverProgram {
    public static void main(String[] args) throws IOException {
        ProcessBuilder pb1 = new ProcessBuilder("java", "/CS 4348.003 Navid Alvey/Project 1/main/EncryptionProgram.java");
        Process mem1 = pb1.start();
        BufferedReader reader1 = new BufferedReader(new InputStreamReader(mem1.getInputStream()));
        OutputStreamWriter writer1 = new OutputStreamWriter(mem1.getOutputStream());

        ProcessBuilder pb2 = new ProcessBuilder("java", "/CS 4348.003 Navid Alvey/Project 1/main/Logger.java");
        Process mem2 = pb2.start();
        BufferedReader reader2 = new BufferedReader(new InputStreamReader(mem2.getInputStream()));
        OutputStreamWriter writer2 = new OutputStreamWriter(mem2.getOutputStream());

        writer2.write("START Logging Started.\n");
        writer2.flush();

        String password = "";
        List<String> passwords = new ArrayList<>();
        String message = "";
        BufferedReader sysIn = new BufferedReader(new InputStreamReader(System.in));

        while (!message.equals("QUIT")) {
            message = printMenu(sysIn);
            writer2.write("COMMAND " + message + "\n");
            writer2.flush();
            switch (message) {
                case "PASSWORD":
                    while (true) {
                        System.out.print("Would you like to use the history? (Y/N) ");
                        String action = sysIn.readLine().trim().toUpperCase();
                        writer2.write("USE_HISTORY " + action + "\n");
                        writer2.flush();
                        if (action.equals("Y")) {
                            printHistory(passwords, 0);
                            System.out.print("Select Password: ");
                            action = sysIn.readLine().trim().toUpperCase();
                            if (Integer.parseInt(action) == passwords.size() + 1) {
                                continue;
                            }
                            password = passwords.get(Integer.parseInt(action) - 1);
                            writer1.write("PASSKEY " + password + "\n");
                            writer1.flush();
                            String res = reader1.readLine().trim();
                            System.out.println(res);
                            writer2.write("PASSKEY " + password + "\n");
                            writer2.flush();
                            writer2.write(res + "\n");
                            writer2.flush();
                            break;
                        } else {
                            System.out.print("Enter PASSKEY: ");
                            password = sysIn.readLine().trim().toUpperCase();
                            passwords.add(password);
                            writer1.write("PASSKEY " + password + "\n");
                            writer1.flush();
                            String res = reader1.readLine().trim();
                            System.out.println(res);
                            writer2.write("PASSKEY " + password + "\n");
                            writer2.flush();
                            writer2.write(res + "\n");
                            writer2.flush();
                            break;
                        }
                    }
                    break;
                case "HISTORY":
                    printHistory(passwords, 1);
                    System.out.print("Press enter to continue...");
                    sysIn.readLine().trim().toUpperCase();
                    break;
                case "ENCRYPT":
                    System.out.print("Enter String to ENCRYPT: ");
                    String action = sysIn.readLine().trim().toUpperCase();
                    writer1.write("ENCRYPT " + action + "\n");
                    writer1.flush();
                    String res = reader1.readLine().trim();
                    writer2.write("ENCRYPT " + action + "\n");
                    writer2.flush();
                    writer2.write(res + "\n");
                    writer2.flush();
                    System.out.println("\n" + res + "\n");
                    System.out.print("Press enter to continue...");
                    sysIn.readLine().trim().toUpperCase();
                    break;
                case "DECRYPT":
                    System.out.print("Enter String to DECRYPT: ");
                    action = sysIn.readLine().trim().toUpperCase();
                    writer1.write("DECRYPT " + action + "\n");
                    writer1.flush();
                    res = reader1.readLine().trim();
                    writer2.write("DECRYPT " + action + "\n");
                    writer2.flush();
                    writer2.write(res + "\n");
                    writer2.flush();
                    System.out.println("\n" + res + "\n");
                    System.out.print("Press enter to continue...");
                    sysIn.readLine().trim().toUpperCase();
                    break;
            }
        }
        writer1.write("QUIT\n");
        writer1.flush();
        writer2.write("QUIT\n");
        writer2.flush();

        try {
            mem1.waitFor();
            mem2.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static String printMenu(BufferedReader sysIn) throws IOException {
        System.out.println();
        System.out.println("--------------------------------------------------------");
        System.out.println("                         MENU                           ");
        System.out.println("--------------------------------------------------------");
        System.out.println();
        System.out.println(" password - set the password for encryption/decryption");
        System.out.println(" encrypt  - encrypt a string");
        System.out.println(" decrypt  - decrypt a string");
        System.out.println(" history  - show history");
        System.out.println(" quit     - quit program");
        System.out.println();
        System.out.println("--------------------------------------------------------");
        System.out.print("Enter Command: ");
        return sysIn.readLine().trim().toUpperCase();
    }

    private static void printHistory(List<String> passwords, int mode) {
        System.out.println();
        System.out.println("--------------------------------------------------------");
        System.out.println("                        HISTORY                         ");
        System.out.println("--------------------------------------------------------");
        System.out.println();
        for (int i = 0; i < passwords.size(); i++) {
            System.out.println(" " + (i + 1) + ". " + passwords.get(i));
        }
        if (mode == 0) {
            System.out.println(" " + (passwords.size() + 1) + ". (Go Back)");
        }
        System.out.println();
        System.out.println("--------------------------------------------------------");
    }
}


