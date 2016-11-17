package MentalCommandTrainer;

import com.emotiv.Iedk.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HeadsetReader {
    private static HeadsetController hsController;
    private static CloudProfile profile;
    private static int userCloudID;
    
    private static BufferedReader readIn;
    private static String keyPress = "";
    
    public static void main(String[] args) {
        hsController = new HeadsetController();
        profile = new CloudProfile("hhs_ccj_project", "CCJproject001", "World");
        readIn = new BufferedReader(new InputStreamReader(System.in));
        
        int connect = hsController.connect();
        userCloudID = profile.login();
        if (userCloudID == 0)
            return;
        
        System.out.println("Starting headset monitoring...");
        while (true) {
            // Lees keyboard af, moet worden vervangen met headset input
            System.out.print("Input required: ");
            try {
                keyPress = readIn.readLine();
            } catch (IOException ex) {
                System.out.println("Bad input");
            }
            if (keyPress.equals("q")) {
                break;
            } else if (!keyPress.equals("n")) {
                handleKeyInput(keyPress);
            }
            
            if (hsController.validateSignal() == 1) { // controleer of het signal sterk genoeg is
                hsController.updateState(); // update de 'mcState' met het huidige herkende commando
                int state = hsController.getMCState();
                hsController.perform(state); // leest de huidige 'Mental Command' af en output deze naar de console
            } else {
                System.out.println("Signal is too noisy!");
            }
            
            // 
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Failure to call thread: " + e);
            }
        }
    }
    
    private static void handleKeyInput(String input) {
        System.out.println("Key pressed: " + input);
    }
}
