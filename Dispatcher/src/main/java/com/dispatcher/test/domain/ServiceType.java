package com.dispatcher.test.domain;

/**
 * Created by oleg on 01.12.2017.
 */
public enum ServiceType {
    WEATHER(1), GOOGLE(2), MONEY(3);

    public int getNumValue() {
        return numValue;
    }

    private int numValue;

    ServiceType(int num){
        this.numValue = num;
    }
}
