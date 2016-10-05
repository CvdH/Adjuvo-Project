package com.emotiv.examples.EmoStateLogger;

import com.emotiv.Iedk.*;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Simple example of JNA interface mapping and usage.
 */
public class EmoStateLoggerSim {

    public static void main(String[] args) {
        Pointer eEvent = Edk.INSTANCE.IEE_EmoEngineEventCreate();
        Pointer eState = Edk.INSTANCE.IEE_EmoStateCreate();
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        IntByReference userID = new IntByReference(0);
        EmoStateSim sim = new EmoStateSim();
        int state = 0;
        int curAction = 0;

        System.out.println("EmoStateLogger simulation started.");

        while (true) {
            int eventType = 0;
            try {
                System.out.print("Command: [b = blink; etc]");
                int newAction = sim.setFacialExpressionEvent(in.readLine());
                if (newAction == 0) {
                    state = 0; // Exit the simulation //EdkErrorCode.EDK_NO_EVENT.ToInt();
                } else {
                    state = EdkErrorCode.EDK_OK.ToInt(); // 
                    if (curAction != newAction) {
                        curAction = newAction;
                        eventType = Edk.IEE_Event_t.IEE_EmoStateUpdated.ToInt(); // Update the Emostate
                    }
                }
            } catch (IOException ex) {
                System.out.println("Readline failed:" + ex);
            }
            
            // New event needs to be handled
            if (state == EdkErrorCode.EDK_OK.ToInt()) {
                Edk.INSTANCE.IEE_EmoEngineEventGetUserId(eEvent, userID);

                // Log the EmoState if it has been updated
                if (eventType == Edk.IEE_Event_t.IEE_EmoStateUpdated.ToInt()) {
                    
                    Edk.INSTANCE.IEE_EmoEngineEventGetEmoState(eEvent, eState);
                    float timestamp = EmoState.INSTANCE.IS_GetTimeFromStart(eState);
                    System.out.println(timestamp + " : New EmoState from user " + userID.getValue());

                    System.out.print("WirelessSignalStatus: ");
                    System.out.println(EmoState.INSTANCE.IS_GetWirelessSignalStatus(eState));

                    // Check which action was performed
                    if (EmoState.INSTANCE.IS_FacialExpressionIsBlink(eState) == 1) {
                        System.out.println("Blink");
                    }
                    if (EmoState.INSTANCE.IS_FacialExpressionIsLeftWink(eState) == 1) {
                        System.out.println("LeftWink");
                    }
                    if (EmoState.INSTANCE.IS_FacialExpressionIsRightWink(eState) == 1) {
                        System.out.println("RightWink");
                    }

                    System.out.print("MentalCommandGetCurrentAction: ");
                    System.out.println(EmoState.INSTANCE.IS_MentalCommandGetCurrentAction(eState));
                    System.out.print("CurrentActionPower: ");
                    System.out.println(EmoState.INSTANCE.IS_MentalCommandGetCurrentActionPower(eState));
                }
            } else if (state != EdkErrorCode.EDK_NO_EVENT.ToInt()) {
                System.out.println("Internal error in Emotiv Engine!");
                break;
            }
        }

        Edk.INSTANCE.IEE_EngineDisconnect();
        System.out.println("Disconnected!");
    }
}
