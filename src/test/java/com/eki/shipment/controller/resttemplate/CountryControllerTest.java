package com.eki.shipment.controller.resttemplate;

import java.util.List;

import com.eki.common.util.ShipmentMappings;
import com.eki.shipment.model.Country;
import com.eki.shipment.util.EntityFactory;
import com.fasterxml.jackson.core.type.TypeReference;

public class CountryControllerTest extends AbstractWebControllerTest<Country> {

	public CountryControllerTest() {
		super(Country.class);
	}




	@Override
	protected Country createEntity() {
		return EntityFactory.createNewCountry();
	}

	@Override
	protected String getApiName() {
		return ShipmentMappings.COUNTRY;
	}



	@Override
	protected TypeReference<List<Country>> getTypeRef() {
		return new TypeReference<List<Country>>() {
		};
	}

}
