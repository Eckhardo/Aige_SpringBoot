package com.eki.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "geoscope")
public class GeoScope  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6449537980828936420L;

	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	
	@NotNull
	 @Column(name = "country_code")
	private String countryCode;
	
    @Column(name = "location_code")
	private String locationCode;
	
	@NotNull
	 @Column(name = "geo_scope_type")
  	private String geoScopeType;
	
	 @Column(name = "is_port")
 	private boolean port;
	

	

	public GeoScope() {
		// TODO Auto-generated constructor stub
	}




	public long getId() {
		return id;
	}




	public void setId(long id) {
		this.id = id;
	}




	public String getCountryCode() {
		return countryCode;
	}




	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}




	public String getLocationCode() {
		return locationCode;
	}




	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}




	public String getGeoScopeType() {
		return geoScopeType;
	}




	public void setGeoScopeType(String geoScopeType) {
		this.geoScopeType = geoScopeType;
	}




	public boolean isPort() {
		return port;
	}




	public void setPort(boolean isPort) {
		this.port = isPort;
	}




	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	 @Override
	    public String toString() {
	        return String.format(
	                "GoScope[id=%d, locationCode='%s', type='%s']",
	                id, locationCode, geoScopeType);
	    }

}
