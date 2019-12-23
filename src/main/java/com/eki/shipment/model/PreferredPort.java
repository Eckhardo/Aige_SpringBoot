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
public class PreferredPort {
     
private final int  geoScopeId;
private final String geoScopeType;
private final String locationCode;

    public PreferredPort(int geo_scope_id,  String geo_scope_type, String location_code) {
        this.geoScopeId = geo_scope_id;
        this.geoScopeType = geo_scope_type;
        this.locationCode = location_code;
    }

    public int getGeoScopeId() {
        return geoScopeId;
    }

    public String getGeoScopeType() {
        return geoScopeType;
    }

    public String getLocationCode() {
        return locationCode;
    }
      
}
