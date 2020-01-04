package com.ek.shipment.test.restclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.eki.shipment.model.Country;
import com.eki.shipment.util.EntityFactory;

@Component
public final class CountryRestClient extends AbstractRestClient<Country> {
	

    @Autowired
    private IUriMapper uriMapper;


	public CountryRestClient() {
		super(Country.class);
	}

	@Override
	protected String getApiName() {
		return	uriMapper.getUriBase(Country.class);
	}

	@Override
	protected Country createEntity() {
		return EntityFactory.createNewCountry();
	}

}
