package com.eki.shipment.controller.resttemplate;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;

import com.eki.common.util.ShipmentMappings;
import com.eki.shipment.dto.GeoScopeDto;
import com.eki.shipment.dto.IntermodalRouteDto;
import com.eki.shipment.model.GeoScope;
import com.eki.shipment.model.KeyFigure;
import com.fasterxml.jackson.core.type.TypeReference;

import data.IntermodalRouteFigureData;

public class IntermodalRouteControllerTest extends AbstractWebControllerTest<KeyFigure, IntermodalRouteDto> {

	public IntermodalRouteControllerTest() {
		super(IntermodalRouteDto.class);
	}


	@Override
	protected String getApiName() {
		return ShipmentMappings.INTERMODAL_ROUTE;
	}

	@Override
	protected KeyFigure createEntity() {
		return IntermodalRouteFigureData.getSingle();
	}


	@Override
	protected TypeReference<List<IntermodalRouteDto>> getTypeRef() {
		return new TypeReference<List<IntermodalRouteDto>>() {
		};
	}
	@Override
	protected IntermodalRouteDto convertToDto(KeyFigure entity) {
		return modelMapper.map(entity, IntermodalRouteDto.class);
	}


	@Override
	protected KeyFigure convertToEntity(IntermodalRouteDto dto) {
		return modelMapper.map(dto, KeyFigure.class);
	}
}
