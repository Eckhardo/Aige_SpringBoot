package com.eki.shipment.model.meta;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.eki.shipment.model.GeoScope;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(GeoScope.class)
public abstract class GeoScope_ {

	public static volatile SingularAttribute<GeoScope, Boolean> port;
	public static volatile SingularAttribute<GeoScope, String> countryCode;
	public static volatile SingularAttribute<GeoScope, String> geoScopeType;
	public static volatile SingularAttribute<GeoScope, String> name;
	public static volatile SingularAttribute<GeoScope, Long> id;
	public static volatile SingularAttribute<GeoScope, String> locationCode;

}

