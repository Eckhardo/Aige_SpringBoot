package com.ek.shipment.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import com.eki.shipment.service.CountryServiceUnitTest;
import com.eki.shipment.service.GeoScopeServiceUnitTest;
import com.eki.shipment.service.KeyFigureServiceUnitTest;
import com.eki.shipment.service.OceanRouteServiceUnitTest;
import com.eki.shipment.service.main.CountryServiceIntegrationTest;
import com.eki.shipment.service.main.GeoScopeServiceIntegrationTest;
import com.eki.shipment.service.main.KeyFigureServiceIntegrationTest;
import com.eki.shipment.service.main.OceanRouteServiceIntegrationTest;

@RunWith(Suite.class)
@SuiteClasses({ // @formatter:off
	CountryServiceIntegrationTest.class,
	OceanRouteServiceIntegrationTest.class,
	GeoScopeServiceIntegrationTest.class,
	KeyFigureServiceIntegrationTest.class
})
// @formatter:on
public final class ServiceIntegrationTestSuite {
    //
}
