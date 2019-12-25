package com.ek.shipment.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import com.eki.shipment.service.CountryServiceUnitTest;
import com.eki.shipment.service.GeoScopeServiceUnitTest;
import com.eki.shipment.service.KeyFigureServiceUnitTest;
import com.eki.shipment.service.OceanRouteServiceUnitTest;

@RunWith(Suite.class)
@SuiteClasses({ // @formatter:off
	CountryServiceUnitTest.class,
	OceanRouteServiceUnitTest.class,
	GeoScopeServiceUnitTest.class,
	KeyFigureServiceUnitTest.class
})
// @formatter:on
public final class ServiceUnitTestSuite {
    //
}
