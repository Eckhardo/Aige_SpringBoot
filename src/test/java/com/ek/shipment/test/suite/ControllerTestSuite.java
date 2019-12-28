package com.ek.shipment.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.eki.shipment.controller.resttemplate.CountryControllerTest;
import com.eki.shipment.controller.resttemplate.GeoScopeControllerTest;
import com.eki.shipment.controller.resttemplate.KeyFigureControllerTest;
import com.eki.shipment.controller.resttemplate.OceanRouteControllerTest;
/**
 * 
 * 
 * @author eckha
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ // @formatter:off
	CountryControllerTest.class,
	GeoScopeControllerTest.class,
	OceanRouteControllerTest.class,
	KeyFigureControllerTest.class
})
public final class ControllerTestSuite {

}
