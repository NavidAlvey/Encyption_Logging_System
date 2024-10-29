import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String originalMessage = reader.readLine().trim();
            String[] parts = originalMessage.split(" ", 2); 
            String action = parts[0].toUpperCase();
            String message = parts.length > 1 ? action + " " + parts[1] : action;

            String directory = "./main";
            File dir = new File(directory);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File file = new File(directory, "log.txt");
            FileWriter writer = new FileWriter(file, true);

            while (!action.equals("QUIT")) {
                String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
                String fullMessage = message.substring(action.length()).trim();

                System.out.println(String.format("%s [%s] %s", timeStamp, action, fullMessage));
                writer.write(String.format("%s [%s] %s\n", timeStamp, action, fullMessage));
                writer.flush();

                originalMessage = reader.readLine().trim();
                parts = originalMessage.split(" ", 2);
                action = parts[0].toUpperCase(); 
                message = parts.length > 1 ? action + " " + parts[1] : action;
            }

            String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
            System.out.println(String.format("%s [QUIT]", timeStamp));
            writer.write(String.format("%s [QUIT]", timeStamp));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
