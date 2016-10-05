package com.emotiv.Iedk;

public class EmoStateSim {
    public int setFacialExpressionEvent(String strState) {
        EmoState.IEE_FacialExpressionAlgo_t state = null;
        switch (strState) {
            case "b":
                state = EmoState.IEE_FacialExpressionAlgo_t.FE_BLINK;
                break;
            case "q":
                return 0;
        }
        if (state != null)
            return state.ToInt();
        return 0;
    }
}
