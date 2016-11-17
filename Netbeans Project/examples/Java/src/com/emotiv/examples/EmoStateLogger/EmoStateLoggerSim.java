package com.emotiv.examples.EmoStateLogger;

import com.emotiv.Iedk.*;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.Iterator;
import java.util.Map.Entry;

/**
 * Simple example of JNA interface mapping and usage.
 */
public class EmoStateLoggerSim {

    public static void main(String[] args) {
        System.out.println("num: " + EmoState.IEE_MentalCommandAction_t.MC_NEUTRAL.ToInt());
        System.out.println("num: " + EmoState.IEE_MentalCommandAction_t.MC_PUSH.ToInt());
        System.out.println("num: " + EmoState.IEE_MentalCommandAction_t.MC_PULL.ToInt());
        System.out.println("num: " + EmoState.IEE_MentalCommandAction_t.MC_LIFT.ToInt());
        System.out.println("num: " + EmoState.IEE_MentalCommandAction_t.MC_DROP.ToInt());
        System.out.println("num: " + EmoState.IEE_MentalCommandAction_t.MC_LEFT.ToInt());
        System.out.println("num: " + EmoState.IEE_MentalCommandAction_t.MC_RIGHT.ToInt());
        System.out.println("num: " + EmoState.IEE_MentalCommandAction_t.MC_ROTATE_LEFT.ToInt());
        System.out.println("num: " + EmoState.IEE_MentalCommandAction_t.MC_ROTATE_RIGHT.ToInt());
        
        Pointer eEvent = Edk.INSTANCE.IEE_EmoEngineEventCreate();
        Pointer eState = Edk.INSTANCE.IEE_EmoStateCreate();
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        IntByReference userID = new IntByReference(0);
        EmoStateSim sim = new EmoStateSim();
        int state = 0;
        int curAction = 0;

        System.out.println("EmoStateLogger simulation started.");
        System.out.println("Syntax: \"<<add|rmv> <b(blink)|l(laugh)|s(surprise)> <0-100(strength)>>\t|<n(nothing)|q(quit)>\"");
        
        OUTER:
        while (true) {
            int eventType = 0;
            try {
                System.out.print("Input: ");
                String inStr = in.readLine();
                if (inStr.equals("q")) { // quit
                    break;
                } else if (!inStr.equals("n")) {
                    int ret = sim.setFacialExpressionEvent(inStr);
                    switch (ret) {
                        case -1:
                            System.out.println("Unrecognized command.");
                            break;
                        case 1:
                            System.out.println("Operation succesful.");
                            state = EdkErrorCode.EDK_OK.ToInt();
                            eventType = Edk.IEE_Event_t.IEE_EmoStateUpdated.ToInt(); // Update the Emostate
                            break;
                        case 2:
                            System.out.println("Operation not applicable.");
                            state = EdkErrorCode.EDK_OK.ToInt();
                            break;
                        default:
                            break;
                    }
                }
            } catch (IOException ex) {
                System.out.println("Readline failed:" + ex);
            }
            if (state == EdkErrorCode.EDK_OK.ToInt()) {
                //Edk.INSTANCE.IEE_EmoEngineEventGetUserId(eEvent, userID);

                // Log the EmoState if it has been updated
                //if (eventType == Edk.IEE_Event_t.IEE_EmoStateUpdated.ToInt()) {

                    System.out.println("Current commandlist: " + EmoStateSim.actions.toString());
                    
                    System.out.print("Actions: ");
                    // Check which actions are being performed
                    //for (int[] action : EmoStateSim.actions) {
                                                          
                    Iterator entries = EmoStateSim.actions.entrySet().iterator();
                    while (entries.hasNext()) {
                        Entry thisEntry = (Entry)entries.next();
                        int key = (int)thisEntry.getKey();
                        int value = (int)thisEntry.getValue();
                        
                        if (key == EmoState.IEE_FacialExpressionAlgo_t.FE_BLINK.ToInt()) {
                            System.out.print(" doBlink(" + value + ") ");
                        } else if (key == EmoState.IEE_FacialExpressionAlgo_t.FE_SURPRISE.ToInt()) {
                            System.out.print(" doSuprise(" + value + ") ");
                        } else if (key == EmoState.IEE_FacialExpressionAlgo_t.FE_LAUGH.ToInt()) {
                            System.out.print(" doLaugh(" + value + ") ");
                        }
                    }
                    System.out.println();
                    
                    //Edk.INSTANCE.IEE_EmoEngineEventGetEmoState(eEvent, eState);
                    //float timestamp = EmoState.INSTANCE.IS_GetTimeFromStart(eState);
                    //System.out.println(timestamp + " : New EmoState from user " + userID.getValue());

                    //System.out.print("WirelessSignalStatus: ");
                    //System.out.println(EmoState.INSTANCE.IS_GetWirelessSignalStatus(eState));

                    //System.out.print("MentalCommandGetCurrentAction: " + EmoState.INSTANCE.IS_MentalCommandGetCurrentAction(eState));
                    //System.out.print("CurrentActionPower: " + EmoState.INSTANCE.IS_MentalCommandGetCurrentActionPower(eState));
                //}
            } else if (state != EdkErrorCode.EDK_NO_EVENT.ToInt()) {
                System.out.println("Internal error in Emotiv Engine!");
                break;
            }
        }

        Edk.INSTANCE.IEE_EngineDisconnect();
        System.out.println("Disconnected!");
    }
}
