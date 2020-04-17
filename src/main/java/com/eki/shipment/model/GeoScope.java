package com.eki.shipment.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.eki.common.interfaces.INameableDto;
import com.eki.common.interfaces.INameableEntity;

@Entity
@Table(name = "geoscope")
public class GeoScope implements INameableEntity, INameableDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "country_code")
	private String countryCode;

	@Column(name = "location_code")
	private String locationCode;

	@NotNull
	@Column(name = "geo_scope_type")
	private String geoScopeType;

	@Column(name = "location_name")
	@NotNull()
	private String name;

	@Column(name = "is_port")
	private boolean port;

	public GeoScope() {
		// TODO Auto-generated constructor stub
	}

	public GeoScope(@NotNull String countryCode, String locationCode, @NotNull String geoScopeType, String name,
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
