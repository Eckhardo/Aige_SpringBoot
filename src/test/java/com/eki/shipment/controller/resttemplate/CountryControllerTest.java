package com.eki.shipment.controller.resttemplate;

import java.util.List;

import com.eki.common.util.ShipmentMappings;
import com.eki.shipment.dto.CountryDto;
import com.eki.shipment.model.Country;
import com.eki.shipment.util.EntityFactory;
import com.fasterxml.jackson.core.type.TypeReference;

public class CountryControllerTest extends AbstractWebControllerTest<Country, CountryDto> {

	public CountryControllerTest() {
		super(CountryDto.class);
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
	protected TypeReference<List<CountryDto>> getTypeRef() {
		return new TypeReference<List<CountryDto>>() {
		};
	}




	@Override
	protected CountryDto convertToDto(Country entity) {
		return modelMapper.map(entity, CountryDto.class);
	}




	@Override
	protected Country convertToEntity(CountryDto dto) {
		return modelMapper.map(dto, Country.class);
	}

}
