/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eki.shipment.model;

/**
 *
 * @author ekirschning
 */
public class VesselSystemModel {
     
private final int  vsId;
private final String vsCode;
private final String vsName;

    public VesselSystemModel(int vsId, String vsCode, String vsName) {
        this.vsId = vsId;
        this.vsCode = vsCode;
        this.vsName = vsName;
    }

    public int getVsId() {
        return vsId;
    }

    public String getVsCode() {
        return vsCode;
    }

    public String getVsName() {
        return vsName;
    }
 
}
