package com.eki.shipment.controller.resttemplate;

import java.util.List;

import org.modelmapper.ModelMapper;

import com.eki.common.util.ShipmentMappings;
import com.eki.shipment.dto.CountryDto;
import com.eki.shipment.model.Country;
import com.eki.shipment.util.EntityFactory;
import com.fasterxml.jackson.core.type.TypeReference;

public class CountryControllerTest extends AbstractWebControllerTest<Country, CountryDto> {

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




	@Override
	protected CountryDto convertToDto(Country entity) {
		return modelMapper.map(entity, CountryDto.class);
	}




	@Override
	protected Country convertToEntity(CountryDto dto) {
		return modelMapper.map(dto, Country.class);
	}

}
