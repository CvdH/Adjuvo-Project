package com.emotiv.Iedk;

import java.util.ArrayList;

public class EmoStateSim {
    public static ArrayList<Integer> actions;
    
    public EmoStateSim() {
        actions = new ArrayList();
    }
    
    public int setFacialExpressionEvent(String strState) {
        EmoState.IEE_FacialExpressionAlgo_t action = null;
        
        String[] m = strState.trim().split(" ");
        if (m.length < 2) {
            return -1;
        }
        String doStr = m[0];
        String withStr = m[1];
        
        switch (withStr) {
            case "b":
                action = EmoState.IEE_FacialExpressionAlgo_t.FE_BLINK;
                break;
            case "s":
                action = EmoState.IEE_FacialExpressionAlgo_t.FE_SURPRISE;
                break;
            case "l":
                action = EmoState.IEE_FacialExpressionAlgo_t.FE_LAUGH;
                break;
            default:
                return -1; // unrecognized command
        }
        
        if (action != null) {
            int num = action.ToInt();
        
            if (doStr.equals("add")) {
                if (actions.contains(num)) {
                    return 2; // already in list
                } else {
                    actions.add(num);
                    return 1; // succesfully added
                }
            } else if (doStr.equals("rmv")) {
                if (!actions.contains(num)) {
                    return 2; // already not in list
                }
                else {
                    actions.remove(Integer.valueOf(num)); // want we willen niet de verwijder-van-index-"num" versie hebben
                    return 1; // succesfully removed
                }
            } else {
                return -1; // unrecognized command
            }
        }
        return 0; // nothing
    }
}
