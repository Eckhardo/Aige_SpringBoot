package com.eki.shipment.dto;

import javax.validation.constraints.NotNull;

import com.eki.common.interfaces.INameableDto;

public class GeoScopeDto implements INameableDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private String countryCode;

	private String locationCode;

	private String geoScopeType;

		private String name;

	private boolean port;

	public GeoScopeDto() {
		// TODO Auto-generated constructor stub
	}

	public GeoScopeDto(@NotNull String countryCode, String locationCode, @NotNull String geoScopeType, String name,
			boolean port) {
		super();
		this.countryCode = countryCode;
		this.locationCode = locationCode;
		this.geoScopeType = geoScopeType;
		this.name = name;
		this.port = port;
	}

	@Override
	public Long getId() {

		return id;
	}

	@Override
	public void setId(Long id) {

		this.id = id;
	}

	@Override
	public String getName() {

		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name = name;

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
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		GeoScopeDto other = (GeoScopeDto) obj;
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
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (port != other.port)
			return false;
		return true;
	}
}