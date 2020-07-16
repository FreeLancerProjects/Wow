package com.creative_share_apps.wow.tags;

import android.os.Environment;

public class Tags {

    public static final String base_url = "http://twseelwow.com";
    public static final String googleDirectionBase_url = "https://maps.googleapis.com/maps/api/";
    public static final String IMAGE_URL = base_url+"/uploads/images/";
    public static final String session_login = "login";
    public static final String session_logout = "logout";
    public static final String local_folder_path = Environment.getExternalStorageDirectory().getAbsoluteFile()+"/Aamr_Audio";

    public static final int MALE = 1;
    public static final int FEMALE = 2;

    public static final String TYPE_CLIENT = "1";
    public static final String TYPE_DELEGATE = "2";
    public static final String TYPE_FAMILY = "3";

    public static final int APPABOUT = 1;
    public static final int APPTERMS = 3;
    public static final int APPPRIVACY = 4;
    public static final int APPTERMSDeELEGATE = 5;


    public static final String ORDER_NEW = "0";
    public static final String ORDER_CURRENT = "3";
    public static final String ORDER_OLD = "7";



    public static final int STATE_ORDER_NEW = 0;
    public static final int STATE_DELEGATE_SEND_OFFER = 1;
    public static final int STATE_DELEGATE_REFUSE_ORDER = 2;
    public static final int STATE_CLIENT_ACCEPT_OFFER = 3;
    public static final int STATE_CLIENT_REFUSE_OFFER = 4;
    public static final int STATE_DELEGATE_COLLECTING_ORDER = 5;
    public static final int STATE_DELEGATE_COLLECTED_ORDER = 6;
    public static final int STATE_DELEGATE_DELIVERING_ORDER = 7;
    public static final int STATE_DELEGATE_DELIVERED_ORDER = 8;


    public static final int START_TYPING = 1;
    public static final int END_TYPING= 2;


    public static final String FIREBASE_NOT_TYPING = "typing";
    public static final String FIREBASE_NOT_ORDER_STATUS = "order_status";
    public static final String FIREBASE_NOT_SEND_MESSAGE = "send_message";
    public static final String FIREBASE_NOT_RATE = "rate";
    public static final String FIREBASE_NOT_BE_DELEGATE= "be_driver";
    public static final String FIREBASE_NOT_BE_FAMILY = "be_family";
    public static final String FIREBASE_NOT_GENERAL_NOT = "general_notifications";
    public static final String FIREBASE_NOT_BALANCE = "balance_notifications";
    public static final String FIREBASE_Order_Deleted = "order_deleted";
    public static final String FIREBASE_NOT_BEDRIVER = "be_driver";
    public static final String FIREBASE_NOT_DRIVER_UPDATE_LOCATION = "driver_update_location";

    public static final String MESSAGE_TEXT = "1";
    public static final String MESSAGE_IMAGE_TEXT = "2";




}
