package com.eki.shipment.model.meta;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.eki.shipment.model.GeoScope;
import com.eki.shipment.model.KeyFigure;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(KeyFigure.class)
public abstract class KeyFigure_ {

	public static volatile SingularAttribute<KeyFigure, String> defaultEquipmentSize;
	public static volatile SingularAttribute<KeyFigure, String> equipmentSize;
	public static volatile SingularAttribute<KeyFigure, Boolean> isPreferred;
	public static volatile SingularAttribute<KeyFigure, GeoScope> via;
	public static volatile SingularAttribute<KeyFigure, Integer> hsWeightClass;
	public static volatile SingularAttribute<KeyFigure, Long> imRouteKey;
	public static volatile SingularAttribute<KeyFigure, BigDecimal> rate;
	public static volatile SingularAttribute<KeyFigure, String> transportMode;
	public static volatile SingularAttribute<KeyFigure, GeoScope> from;
	public static volatile SingularAttribute<KeyFigure, String> currency;
	public static volatile SingularAttribute<KeyFigure, Long> id;
	public static volatile SingularAttribute<KeyFigure, GeoScope> to;
	public static volatile SingularAttribute<KeyFigure, String> equipmentGroup;
	public static volatile SingularAttribute<KeyFigure, Date> startDate;

}

