package MentalCommandTrainer;

import com.emotiv.Iedk.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HeadsetReader {
    private static HeadsetController hc;
    private static CloudProfile pr;
    private static int userCloudID;
    
    private static BufferedReader in;
    private static String keyPress = "";
    
    public static void main(String[] args) {
        hc = new HeadsetController();
        pr = new CloudProfile("hhs_ccj_project", "CCJproject001", "World");
        in = new BufferedReader(new InputStreamReader(System.in));
        
        int connect = hc.connect();
        userCloudID = pr.login();
        if (userCloudID == 0)
            return; // just end it
        
        System.out.println("Starting headset monitoring...");
        while (true) {
            // The part where you put in keyboard commands
            System.out.print("Input required: ");
            try {
                keyPress = in.readLine();
            } catch (IOException ex) {
                System.out.println("Bad input");
            }
            if (keyPress.equals("q")) {
                break;
            } else if (!keyPress.equals("n")) {
                handleKeyInput(keyPress);
            }
            
            // The part where the state of the headset is read
            if (hc.validateSignal() == 1) {
                hc.updateState();
                int state = hc.getMCState();
                hc.perform();
            } else {
                System.out.println("Signal is too noisy!");
            }
            
            // 
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                System.out.println("Can't wake up.");
            }
        }
    }
    
    private static void handleKeyInput(String input) {
        System.out.println("Key pressed: " + input);
    }
}
