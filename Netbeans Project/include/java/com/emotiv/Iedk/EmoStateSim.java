package com.emotiv.Iedk;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class EmoStateSim {
    public static Map actions = new HashMap();

    
    public EmoStateSim() {
        actions = new HashMap();
    }
    
    public int setFacialExpressionEvent(String strState) {
        EmoState.IEE_FacialExpressionAlgo_t action = null;
        
        String[] m = strState.trim().split(" ");
        
        if (m.length < 2) {
            return -1;
        }
        
        String actionStr = m[0];
        String expressionStr = m[1];
        int strengthStr = 0;
               
        if (m.length > 2) {
            //return -1;
            strengthStr = Integer.parseInt(m[2]);
        }
        
        if (strengthStr<1)      strengthStr = 1;
        if (strengthStr>100)    strengthStr = 100;
        
        switch (expressionStr) {
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
        
            if (actionStr.equals("add")) {
                if (actions.containsKey(num)) {
                    return 2; // already in list
                } else {
                    actions.put(num, strengthStr);
                    return 1; // succesfully added
                }
            } else if (actionStr.equals("rmv")) {
                if (!actions.containsKey(num)) {

                    return 2; // already not in list
                }
                else {
                    actions.remove(num);
                    return 1; // succesfully removed
                }
            } else {
                return -1; // unrecognized command
            }
        }
        return 0; // nothing
    }
}
