/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eki.model;

/**
 *
 * @author ekirschning
 */
public class Trade {
    
           
private final String tradeName;
private final String tradeCode;

    public Trade(String tradeName, String tradeCode) {
        this.tradeName = tradeName;
        this.tradeCode = tradeCode;
    }

    public String getTradeName() {
        return tradeName;
    }

    public String getTradeCode() {
        return tradeCode;
    }

}
