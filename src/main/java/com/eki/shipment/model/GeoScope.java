package com.eki.shipment.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "geoscope")
public class GeoScope implements Serializable {

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

	@Column(name = "location_name")
	private String locationName;

	
	@Column(name = "is_port")
	private boolean port;

	public GeoScope() {
		// TODO Auto-generated constructor stub
	}

	public GeoScope(long id, @NotNull String countryCode, String locationCode, @NotNull String geoScopeType,
			String locationName, boolean port) {
		super();
		this.id = id;
		this.countryCode = countryCode;
		this.locationCode = locationCode;
		this.geoScopeType = geoScopeType;
		this.locationName = locationName;
		this.port = port;
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


	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	@Override
	public String toString() {
		return String.format("GoScope[id=%d, locationCode='%s', type='%s']", id, locationCode, geoScopeType);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((countryCode == null) ? 0 : countryCode.hashCode());
		result = prime * result + ((geoScopeType == null) ? 0 : geoScopeType.hashCode());
		result = prime * result + ((locationCode == null) ? 0 : locationCode.hashCode());
		result = prime * result + ((locationName == null) ? 0 : locationName.hashCode());
		result = prime * result + (port ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GeoScope other = (GeoScope) obj;
		if (countryCode == null) {
			if (other.countryCode != null)
				return false;
		} else if (!countryCode.equals(other.countryCode))
			return false;
		if (geoScopeType == null) {
			if (other.geoScopeType != null)
				return false;
		} else if (!geoScopeType.equals(other.geoScopeType))
			return false;
		if (locationCode == null) {
			if (other.locationCode != null)
				return false;
		} else if (!locationCode.equals(other.locationCode))
			return false;
		if (locationName == null) {
			if (other.locationName != null)
				return false;
		} else if (!locationName.equals(other.locationName))
			return false;
		if (port != other.port)
			return false;
		return true;
	}

}
