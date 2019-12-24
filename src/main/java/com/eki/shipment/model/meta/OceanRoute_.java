package com.eki.shipment.model.meta;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.eki.shipment.model.OceanRoute;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(OceanRoute.class)
public abstract class OceanRoute_ {
	
	public OceanRoute_() {
		
	}

	public static volatile SingularAttribute<OceanRoute, String> tsFac1;
	public static volatile SingularAttribute<OceanRoute, String> pod;
	public static volatile SingularAttribute<OceanRoute, String> prof3;
	public static volatile SingularAttribute<OceanRoute, String> prof2;
	public static volatile SingularAttribute<OceanRoute, Integer> transitTime;
	public static volatile SingularAttribute<OceanRoute, String> podFac;
	public static volatile SingularAttribute<OceanRoute, String> pol;
	public static volatile SingularAttribute<OceanRoute, String> prof;
	public static volatile SingularAttribute<OceanRoute, String> ts2;
	public static volatile SingularAttribute<OceanRoute, String> ts1;
	public static volatile SingularAttribute<OceanRoute, String> ts3;
	public static volatile SingularAttribute<OceanRoute, String> polFac;
	public static volatile SingularAttribute<OceanRoute, Long> id;
	public static volatile SingularAttribute<OceanRoute, String> ts1Fac3;
	public static volatile ListAttribute<OceanRoute, String> errors;
	public static volatile SingularAttribute<OceanRoute, String> ts1Fac2;
	 public static final String POL = "pol";
	    public static final String ID = "id";
	    public static final String POD = "pod";
	    public static final String TS1 = "ts1";
}

