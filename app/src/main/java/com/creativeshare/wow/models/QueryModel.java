package com.creativeshare.wow.models;

import java.io.Serializable;

public class QueryModel implements Serializable {

    private String keyword;

    public QueryModel(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }
}
