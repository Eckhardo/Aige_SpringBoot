package com.eki.shipment.controller.resttemplate;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import com.eki.common.util.ShipmentMappings;
import com.eki.shipment.dto.GeoScopeDto;
import com.eki.shipment.model.GeoScope;
import com.eki.shipment.util.EntityFactory;
import com.fasterxml.jackson.core.type.TypeReference;

public class GeoScopeControllerTest extends AbstractWebControllerTest<GeoScope, GeoScopeDto> {

	public GeoScopeControllerTest() {
		super(GeoScopeDto.class);
	}




	@Test
	public void whenSearchGeoScopesLikeDED_thenReturnLocations() {
		GeoScope[] body = this.restTemplate
				.getForObject("/geoscope/filter?location_code=DED&geo_scope_type=L&country_code=DE", GeoScope[].class);
		assertThat(body.length, is(2));

		for (int i = 0; i < body.length; i++) {
			assertThat(body[i].getLocationCode(), startsWith("DED"));
		}
	}

	@Test
	public void whenSearchGeoScopesLikeDUS_thenReturnCitys() {
		GeoScope[] body = this.restTemplate.getForObject("/geoscope/filter?location_code=DUS&geo_scope_type=T",
				GeoScope[].class);
		assertThat(body.length, is(1));

		for (int i = 0; i < body.length; i++) {
			assertThat(body[i].getLocationCode(), equalTo("DUSSELDORF"));
		}
	}

	@Test
	public void whenSearchPreferredPorts_thenReturnPorts() {
		GeoScope[] body = this.restTemplate.getForObject(
				"/geoscope/preferredPort?location_code=DUS&geo_scope_type=T&country_code=DE", GeoScope[].class);

		for (int i = 0; i < body.length; i++) {
			assertThat(body[i].isPort(), is(true));
		}
	}

	@Test
	public void whenSearchPorts_thenReturnPorts() {
		GeoScope[] body = this.restTemplate.getForObject("/geoscope/ports?location_code=ARB", GeoScope[].class);
		assertThat(body.length, is(equalTo(2)));
		for (int i = 0; i < body.length; i++) {
			assertThat(body[i].isPort(), is(true));
		}
	}

	@Override
	protected GeoScope createEntity() {
		return EntityFactory.createGeoScope();
	}

	@Override
	protected String getApiName() {
		return ShipmentMappings.GEOSCOPE;
	}

	@Override
	protected TypeReference<List<GeoScopeDto>> getTypeRef() {

		return new TypeReference<List<GeoScopeDto>>() {
		};
	}


	@Override
	protected GeoScopeDto convertToDto(GeoScope entity) {
		return modelMapper.map(entity, GeoScopeDto.class);
	}




	@Override
	protected GeoScope convertToEntity(GeoScopeDto dto) {
		return modelMapper.map(dto, GeoScope.class);
	}


}
