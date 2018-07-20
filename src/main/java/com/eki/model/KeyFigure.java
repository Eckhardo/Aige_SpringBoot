package com.eki.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "keyfigure")

public class KeyFigure implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -690482985342171659L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "from_id")
	private  GeoScope from;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "via_id")
	private  GeoScope via;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "to_id")
	private  GeoScope to;

	@Column(name = "im_route")
	private  long imRouteKey;

	@Column(name = "tpmode")
	private  String transportMode;

	@Column(name = "preferred")

	private  boolean isPreferred;

	@Column(name = "eq_size")
	private  String equipmentSize;

	@Column(name = "eq_group")
	private  String equipmentGroup;

	@Column(name="rate", columnDefinition="Decimal(10,2) default '0.00'")
	private  BigDecimal rate;

	@Column(name = "currency")
	private  String currency; // nullable

	@Column(name = "weight_class")
	private  int hsWeightClass;

	@Column(name = "def_eq_size")
	private  String defaultEquipmentSize;

	@Basic
	@Temporal(TemporalType.DATE)
	private  Date startDate;
//---------------------- contrcutor ------
	
	public KeyFigure() {
	
}
	
	public KeyFigure(long id, GeoScope from, GeoScope via, GeoScope to, long imRouteKey, String transportMode,
			boolean isPreferred, String equipmentSize, String equipmentGroup, BigDecimal rate, String currency,
			int hsWeightClass, String defaultEquipmentSize, Date startDate) {
		this.id = id;
			this.from = from;
		this.via = via;
		this.to = to;
		this.imRouteKey = imRouteKey;
		this.transportMode = transportMode;
		this.isPreferred = isPreferred;
		this.equipmentSize = equipmentSize;
		this.equipmentGroup = equipmentGroup;
		this.rate = rate;
		this.currency = currency;
		this.hsWeightClass = hsWeightClass;
		this.defaultEquipmentSize = defaultEquipmentSize;
		this.startDate = startDate;
	}



	public long getId() {
		return id;
	}

	public GeoScope getFrom() {
		return from;
	}

	public GeoScope getVia() {
		return via;
	}

	public GeoScope getTo() {
		return to;
	}

	public long getImRouteKey() {
		return imRouteKey;
	}

	public String getTransportMode() {
		return transportMode;
	}

	public boolean isPreferred() {
		return isPreferred;
	}

	public String getEquipmentSize() {
		return equipmentSize;
	}

	public String getEquipmentGroup() {
		return equipmentGroup;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public String getCurrency() {
		return currency;
	}

	public int getHsWeightClass() {
		return hsWeightClass;
	}

	public String getDefaultEquipmentSize() {
		return defaultEquipmentSize;
	}

	public Date getStartDate() {
		return startDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("KeyFigure [from=").append(from.getLocationCode()).append(", to=").append(to.getLocationCode())
				.append(", transportMode=").append(transportMode).append(", equipmentSize=").append(equipmentSize)
				.append(", rate=").append(rate).append(", currency=").append(currency).append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + ((defaultEquipmentSize == null) ? 0 : defaultEquipmentSize.hashCode());
		result = prime * result + ((equipmentGroup == null) ? 0 : equipmentGroup.hashCode());
		result = prime * result + ((equipmentSize == null) ? 0 : equipmentSize.hashCode());
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + hsWeightClass;
		result = prime * result + (int) (imRouteKey ^ (imRouteKey >>> 32));
		result = prime * result + (isPreferred ? 1231 : 1237);
		result = prime * result + ((rate == null) ? 0 : rate.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + ((to == null) ? 0 : to.hashCode());
		result = prime * result + ((transportMode == null) ? 0 : transportMode.hashCode());
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
		KeyFigure other = (KeyFigure) obj;
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency))
			return false;
		if (defaultEquipmentSize == null) {
			if (other.defaultEquipmentSize != null)
				return false;
		} else if (!defaultEquipmentSize.equals(other.defaultEquipmentSize))
			return false;
		if (equipmentGroup == null) {
			if (other.equipmentGroup != null)
				return false;
		} else if (!equipmentGroup.equals(other.equipmentGroup))
			return false;
		if (equipmentSize == null) {
			if (other.equipmentSize != null)
				return false;
		} else if (!equipmentSize.equals(other.equipmentSize))
			return false;
		if (from == null) {
			if (other.from != null)
				return false;
		} else if (!from.equals(other.from))
			return false;
		if (hsWeightClass != other.hsWeightClass)
			return false;
		if (imRouteKey != other.imRouteKey)
			return false;
		if (isPreferred != other.isPreferred)
			return false;
		if (rate == null) {
			if (other.rate != null)
				return false;
		} else if (!rate.equals(other.rate))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (to == null) {
			if (other.to != null)
				return false;
		} else if (!to.equals(other.to))
			return false;
		if (transportMode == null) {
			if (other.transportMode != null)
				return false;
		} else if (!transportMode.equals(other.transportMode))
			return false;
		return true;
	}


	
	
}
