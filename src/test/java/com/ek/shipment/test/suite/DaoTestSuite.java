package com.ek.shipment.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.eki.shipment.dao.CountryRepositoryTest;
import com.eki.shipment.dao.GeoScopeRepositoryTest;
import com.eki.shipment.dao.IntermodalRouteDynamicQueryDaoTest;
import com.eki.shipment.dao.OceanRouteDynamicQueryDaoTest;
import com.eki.shipment.dao.OceanRouteRepositoryTest;


@RunWith(Suite.class)
@SuiteClasses({ // @formatter:off
	OceanRouteDynamicQueryDaoTest.class,
	OceanRouteRepositoryTest.class,
	CountryRepositoryTest.class,
	GeoScopeRepositoryTest.class,
	IntermodalRouteDynamicQueryDaoTest.class
})
// @formatter:on
public final class DaoTestSuite {
    //
}
