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

import org.hibernate.annotations.Immutable;

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
}
