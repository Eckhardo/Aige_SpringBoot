package com.eki.shipment.service;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;

import com.eki.shipment.dao.CountryRepository;
import com.eki.shipment.model.Country;
import com.eki.shipment.util.EntityFactory;
import com.google.common.collect.Lists;
public class CountryServiceUnitTest extends AbstractServiceUnitTest<Country> {

	private CountryService serviceUnderTest;

	private CountryRepository daoMock;

	@Override
	@Before
	public final void before() {
		serviceUnderTest = new CountryService();

		daoMock = mock(CountryRepository.class);
		when(daoMock.save(any(Country.class))).thenReturn(EntityFactory.createNewCountry());
		when(daoMock.findAll()).thenReturn(Lists.<Country>newArrayList());
		serviceUnderTest.dao = daoMock;
		super.before();
	}

	@Override
	protected Country createNewEntity() {
		return EntityFactory.createNewCountry();
	}

	@Override
	protected void changeEntity(Country entity) {
		entity.setName(randomAlphabetic(7));

	}

	@Override
	protected Country stubDaoGetOne(long id) {
		final Country entity = createNewEntity();
		entity.setId(id);
		when(daoMock.findById(id)).thenReturn(Optional.of(entity));
		return entity;
	}

	@Override
	protected CountryService getApi() {
		return serviceUnderTest;
	}

	@Override
	protected CountryRepository getDAO() {
		return daoMock;
	}

}
