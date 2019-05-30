package com.digicular.coinwatch.model;

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
public class PriceUnit {
    private Long timeStamp;
    private Double price;

    public PriceUnit(Long timeStamp, Double price){
        this.timeStamp = timeStamp;
        this.price = price;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public Double getPrice() {
        return price;
    }
}
