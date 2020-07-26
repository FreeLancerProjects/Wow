package com.arab_developer.wow.models;

import java.io.Serializable;

public class OrderIdDataModel implements Serializable {

    private OrderIdModel data;

    public OrderIdModel getData() {
        return data;
    }

    public class OrderIdModel implements Serializable
    {
        private String order_id;

        public String getOrder_id() {
            return order_id;
        }
    }
}
