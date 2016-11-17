package com.emotiv.Iedk;

import com.sun.jna.Pointer;

public class HeadsetController {

    private final Edk edkInst;
    private final EmoState emsInst;
    private final Pointer eEvent;
    private final Pointer eState;
    private int mcState;

    /*
    
    */
    public HeadsetController() {
        this.emsInst = EmoState.INSTANCE;
        this.edkInst = Edk.INSTANCE;
        this.eEvent = edkInst.IEE_EmoEngineEventCreate();
        this.eState = edkInst.IEE_EmoStateCreate();
        this.mcState = 0;
    }

    /*
    
    */
    public int connect() {
        if (edkInst.IEE_EngineConnect("Emotiv Systems-5") != EdkErrorCode.EDK_OK.ToInt()) {
            System.out.println("Emotiv Engine start up failed.");
            return 0;
        }
        System.out.println("Emotiv Engine connection succeeded.");
        return 1;
    }
    
    public int validateSignal() {
        return emsInst.IS_MentalCommandIsActive(eState);
    }

    /*
    
    */
    public void updateState() { // update the state with the current Mental Command
        int i = edkInst.IEE_EngineGetNextEvent(eEvent);
        System.out.println("state: " + i);
        setMCState(i);
    }

    /*
        Vergelijk de staat van de headset met de mogelijke commando's
    */
    public void perform(int state) {
        String output = "Unspecified/NaN";

        if (state == EmoState.IEE_MentalCommandAction_t.MC_NEUTRAL.ToInt()) {
            output = "Neutral";
        } else if (state == EmoState.IEE_MentalCommandAction_t.MC_PUSH.ToInt()) {
            output = "Push";
        } else if (state == EmoState.IEE_MentalCommandAction_t.MC_PULL.ToInt()) {
            output = "Pull";
        } else if (state == EmoState.IEE_MentalCommandAction_t.MC_LIFT.ToInt()) {
            output = "Lift";
        } else if (state == EmoState.IEE_MentalCommandAction_t.MC_DROP.ToInt()) {
            output = "Drop";
        } else if (state == EmoState.IEE_MentalCommandAction_t.MC_LEFT.ToInt()) {
            output = "Left";
        } else if (state == EmoState.IEE_MentalCommandAction_t.MC_RIGHT.ToInt()) {
            output = "Right";
        } else if (state == EmoState.IEE_MentalCommandAction_t.MC_ROTATE_LEFT.ToInt()) {
            output = "Rotate Left";
        } else if (state == EmoState.IEE_MentalCommandAction_t.MC_ROTATE_RIGHT.ToInt()) {
            output = "Rotate Right";
        } else if (state == EmoState.IEE_MentalCommandAction_t.MC_ROTATE_CLOCKWISE.ToInt()) {
            output = "Rotate Clockwise";
        } else if (state == EmoState.IEE_MentalCommandAction_t.MC_ROTATE_COUNTER_CLOCKWISE.ToInt()) {
            output = "Rotate Counter-clockwise";
        } else if (state == EmoState.IEE_MentalCommandAction_t.MC_ROTATE_FORWARDS.ToInt()) {
            output = "Rotate Forwards";
        } else if (state == EmoState.IEE_MentalCommandAction_t.MC_ROTATE_REVERSE.ToInt()) {
            output = "Rotate Backwards/Reverse";
        } else if (state == EmoState.IEE_MentalCommandAction_t.MC_DISAPPEAR.ToInt()) {
            output = "Disappear";
        }

        System.out.println("mental command: " + output);
    }

    public int getMCState() {
        return mcState;
    }

    public void setMCState(int mcState) {
        this.mcState = mcState;
    }
}
