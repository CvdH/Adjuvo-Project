package com.emotiv.Iedk;

import com.sun.jna.Pointer;

public class HeadsetController {
    private final Edk edkInst;
    private final EmoState emsInst;
    private final Pointer eEvent;
    private final Pointer eState;
    private int mcState;

    public HeadsetController() {
        this.emsInst = EmoState.INSTANCE;
        this.edkInst = Edk.INSTANCE;
        this.eEvent = edkInst.IEE_EmoEngineEventCreate();
        this.eState = edkInst.IEE_EmoStateCreate();
        this.mcState = 0;
    }
    
    public void updateState() { // update the state of the current Mental Command
        int s = Edk.INSTANCE.IEE_EngineGetNextEvent(eEvent);
        setMcState(s);
    }

    public int getMcState() {
        return mcState;
    }

    public void setMcState(int mcState) {
        this.mcState = mcState;
    }
}
