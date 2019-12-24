package com.eki.shipment.model.meta;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.eki.shipment.model.Country;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Country.class)
public abstract class Country_ {

	public static volatile SingularAttribute<Country, String> code;
	public static volatile SingularAttribute<Country, String> name;
	public static volatile SingularAttribute<Country, Long> id;

}

