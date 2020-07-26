package com.arab_developer.wow.models;

import java.io.Serializable;

public class OrderModel implements Serializable
{
    private String order_id;
    private String order_type;
    private String order_status;
    private String client_id;
    private String client_address;
    private String client_lat;
    private String client_long;
    private String driver_id;
    private String driver_offer;
    private String order_details;
    private String order_movement;
    private String place_lat;
    private String place_long;
    private String order_time_arrival;
    private String driver_user_phone;
    private String driver_user_phone_code;
    private String driver_user_full_name;
    private String driver_user_image;
    private String client_user_phone;
    private String client_user_phone_code;
    private String client_user_full_name;
    private String client_user_image;
    private String order_date;
    private double rate;
    private String room_id_fk;
    private String place_address;
    private String order_image;
    private String driver_lat;
    private String driver_long;
    private String bill_image;
    private String bill_cost;
    private String order_time;
    private String place_name;
    private String bill_step;
    private String bill_amount;

    public String getBill_amount() {
        return bill_amount;
    }

    public void setBill_step(String bill_step) {
        this.bill_step = bill_step;
    }

    public void setBill_amount(String bill_amount) {
        this.bill_amount = bill_amount;
    }

    public String getBill_step() {
        return bill_step;
    }

    public String getPlace_name() {
        return place_name;
    }

    public String getOrder_id() {
        return order_id;
    }

    public String getOrder_type() {
        return order_type;
    }

    public String getOrder_status() {
        return order_status;
    }

    public String getClient_id() {
        return client_id;
    }

    public String getClient_address() {
        return client_address;
    }

    public String getClient_lat() {
        return client_lat;
    }

    public String getClient_long() {
        return client_long;
    }

    public String getDriver_id() {
        return driver_id;
    }

    public String getDriver_offer() {
        return driver_offer;
    }

    public String getOrder_details() {
        return order_details;
    }

    public String getOrder_movement() {
        return order_movement;
    }

    public String getPlace_lat() {
        return place_lat;
    }

    public String getPlace_long() {
        return place_long;
    }

    public String getOrder_time_arrival() {
        return order_time_arrival;
    }

    public String getDriver_user_phone() {
        return driver_user_phone;
    }

    public String getDriver_user_phone_code() {
        return driver_user_phone_code;
    }

    public String getDriver_user_full_name() {
        return driver_user_full_name;
    }

    public String getDriver_user_image() {
        return driver_user_image;
    }

    public String getClient_user_phone() {
        return client_user_phone;
    }

    public String getClient_user_phone_code() {
        return client_user_phone_code;
    }

    public String getClient_user_full_name() {
        return client_user_full_name;
    }

    public String getClient_user_image() {
        return client_user_image;
    }

    public String getOrder_date() {
        return order_date;
    }

    public double getRate() {
        return rate;
    }

    public String getRoom_id_fk() {
        return room_id_fk;
    }

    public String getPlace_address() {
        return place_address;
    }

    public String getOrder_image() {
        return order_image;
    }

    public String getDriver_lat() {
        return driver_lat;
    }

    public String getDriver_long() {
        return driver_long;
    }

    public String getBill_image() {
        return bill_image;
    }

    public String getBill_cost() {
        return bill_cost;
    }

    public String getOrder_time() {
        return order_time;
    }
}
