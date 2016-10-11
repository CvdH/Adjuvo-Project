package com.emotiv.examples.EmoStateLogger;

import com.emotiv.Iedk.*;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.*;

/**
 * Simple example of JNA interface mapping and usage.
 */
public class EmoStateLogger {

    public static void main(String[] args) {
        Edk edkInst = Edk.INSTANCE;
        EmoState esInst = EmoState.INSTANCE;
        Pointer eEvent = edkInst.IEE_EmoEngineEventCreate();
        Pointer eState = edkInst.IEE_EmoStateCreate();
        IntByReference userID = new IntByReference(0);
        short composerPort = 1726;
        int option = 1;
        int state = 0;

        switch (option) {
            case 1: {
                if (edkInst.IEE_EngineConnect("Emotiv Systems-5") != EdkErrorCode.EDK_OK.ToInt()) {
                    System.out.println("Emotiv Engine start up failed.");
                    return;
                }
                System.out.println("Nothing happened.");
                break;
            }
            case 2: {
                System.out.println("Target IP of EmoComposer: [127.0.0.1] ");

                if (edkInst.IEE_EngineRemoteConnect("127.0.0.1", composerPort, "Emotiv Systems-5") != EdkErrorCode.EDK_OK.ToInt()) {
                    System.out.println("Cannot connect to EmoComposer on [127.0.0.1]");
                    return;
                }
                System.out.println("Connected to EmoComposer on [127.0.0.1]");
                break;
            }
            default:
                System.out.println("Invalid option...");
                return;
        }
        
        while (true) {
            state = edkInst.IEE_EngineGetNextEvent(eEvent);
            
            // New event needs to be handled
            if (state == EdkErrorCode.EDK_OK.ToInt()) {

                int eventType = edkInst.IEE_EmoEngineEventGetType(eEvent);
                edkInst.IEE_EmoEngineEventGetUserId(eEvent, userID);

                // Log the EmoState if it has been updated
                if (eventType == Edk.IEE_Event_t.IEE_EmoStateUpdated.ToInt()) {
                    
                    edkInst.IEE_EmoEngineEventGetEmoState(eEvent, eState);
                    float timestamp = esInst.IS_GetTimeFromStart(eState);
                    System.out.println(timestamp + " : New EmoState from user " + userID.getValue());

                    System.out.print("WirelessSignalStatus: ");
                    System.out.println(esInst.IS_GetWirelessSignalStatus(eState));

                    if (esInst.IS_FacialExpressionIsBlink(eState) == 1) {
                        System.out.println("Blink");
                    }
                    if (esInst.IS_FacialExpressionIsLeftWink(eState) == 1) {
                        System.out.println("LeftWink");
                    }
                    if (esInst.IS_FacialExpressionIsRightWink(eState) == 1) {
                        System.out.println("RightWink");
                    }

                    System.out.print("MentalCommandGetCurrentAction: ");
                    System.out.println(esInst.IS_MentalCommandGetCurrentAction(eState));
                    System.out.print("CurrentActionPower: ");
                    System.out.println(esInst.IS_MentalCommandGetCurrentActionPower(eState));
                }
            } else if (state != EdkErrorCode.EDK_NO_EVENT.ToInt()) {
                System.out.println("Internal error in Emotiv Engine!");
                break;
            }
        }

        edkInst.IEE_EngineDisconnect();
        System.out.println("Disconnected!");
    }
}
