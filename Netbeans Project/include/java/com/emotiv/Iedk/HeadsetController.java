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
    
    */
    public void perform() {
        int i = emsInst.IS_MentalCommandGetCurrentAction(eState);
        String s = "Unspecified/NaN";

        if (i == EmoState.IEE_MentalCommandAction_t.MC_NEUTRAL.ToInt()) {
            s = "Neutral";
        } else if (i == EmoState.IEE_MentalCommandAction_t.MC_PUSH.ToInt()) {
            s = "Push";
        } else if (i == EmoState.IEE_MentalCommandAction_t.MC_PULL.ToInt()) {
            s = "Pull";
        } else if (i == EmoState.IEE_MentalCommandAction_t.MC_LIFT.ToInt()) {
            s = "Lift";
        } else if (i == EmoState.IEE_MentalCommandAction_t.MC_DROP.ToInt()) {
            s = "Drop";
        } else if (i == EmoState.IEE_MentalCommandAction_t.MC_LEFT.ToInt()) {
            s = "Left";
        } else if (i == EmoState.IEE_MentalCommandAction_t.MC_RIGHT.ToInt()) {
            s = "Right";
        } else if (i == EmoState.IEE_MentalCommandAction_t.MC_ROTATE_LEFT.ToInt()) {
            s = "Rotate Left";
        } else if (i == EmoState.IEE_MentalCommandAction_t.MC_ROTATE_RIGHT.ToInt()) {
            s = "Rotate Right";
        } else if (i == EmoState.IEE_MentalCommandAction_t.MC_ROTATE_CLOCKWISE.ToInt()) {
            s = "Rotate Clockwise";
        } else if (i == EmoState.IEE_MentalCommandAction_t.MC_ROTATE_COUNTER_CLOCKWISE.ToInt()) {
            s = "Rotate Counter-clockwise";
        } else if (i == EmoState.IEE_MentalCommandAction_t.MC_ROTATE_FORWARDS.ToInt()) {
            s = "Rotate Forwards";
        } else if (i == EmoState.IEE_MentalCommandAction_t.MC_ROTATE_REVERSE.ToInt()) {
            s = "Rotate Backwards/Reverse";
        } else if (i == EmoState.IEE_MentalCommandAction_t.MC_DISAPPEAR.ToInt()) {
            s = "Disappear";
        }

        float f = emsInst.IS_MentalCommandGetCurrentActionPower(eState); // between 0.0 and 1.0
        System.out.println("mental command: " + s + " / power: " + f);
    }

    public int getMCState() {
        return mcState;
    }

    public void setMCState(int mcState) {
        this.mcState = mcState;
    }
}
