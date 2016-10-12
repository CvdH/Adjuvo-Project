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
        float f = emsInst.IS_MentalCommandGetCurrentActionPower(eState);
        System.out.println("mental command: " + i + " / power: " + f);
    }

    public int getMCState() {
        return mcState;
    }

    public void setMCState(int mcState) {
        this.mcState = mcState;
    }
}
