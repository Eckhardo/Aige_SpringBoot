package com.eki.shipment.controller.resttemplate;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;

import com.eki.common.util.ShipmentMappings;
import com.eki.shipment.model.KeyFigure;
import com.fasterxml.jackson.core.type.TypeReference;

import data.IntermodalRouteFigureData;

public class IntermodalRouteControllerTest extends AbstractWebControllerTest<KeyFigure> {

	public IntermodalRouteControllerTest() {
		super(KeyFigure.class);
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
	protected ParameterizedTypeReference<List<KeyFigure>> getResponseTypeAsList() {

		return new ParameterizedTypeReference<List<KeyFigure>>() {
		};
	}

	@Override
	protected TypeReference<List<KeyFigure>> getTypeRef() {
		return new TypeReference<List<KeyFigure>>() {
		};
	}

}
