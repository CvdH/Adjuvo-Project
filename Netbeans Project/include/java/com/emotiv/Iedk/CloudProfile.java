package com.emotiv.Iedk;

import com.sun.jna.ptr.IntByReference;

public class CloudProfile {
    private EmotivCloudClient eccInst;
    private String username;
    private String password;
    private String profile;
    private IntByReference engineUserID;
    private IntByReference userCloudID;
    
    /*
    constructor of CloudProfile object
    */
    public CloudProfile(String un, String pw, String pr) {
        eccInst = EmotivCloudClient.INSTANCE;
        username = un;
        password = pw;
        profile = pr;
        engineUserID = new IntByReference(0);
        userCloudID = new IntByReference(0);
    }
    
    /*
    
    */
    public int login() {
        if (eccInst.EC_Connect() != EmotivCloudErrorCode.EC_OK.ToInt()) {
            System.out.println("Failed to connect to Emotiv Cloud.");
            return 0;
        }

        if (eccInst.EC_Login(username, password) != EmotivCloudErrorCode.EC_OK.ToInt()) {
            System.out.println("Login attempt failed.");
            return 0;
        }
        eccInst.EC_GetUserDetail(userCloudID);
        int i = userCloudID.getValue();
        System.out.println("userCloudID: " + i); // should be 11941
        return i;
    }
}
