/**
 * 
 */
package com.eki.oceanroute;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.eki.model.OceanRoute;

import static com.eki.TestData.*;

/**
 * @author eckhard kirschning
 *
 */

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestPropertySource(locations = "classpath:application-test.properties")
public class OceanRouteRepositoryTest {

	@Autowired
	private OceanRouteRepository oceanRouteRepository;


	@Test
	public void whenFindByPol_thenReturnOceanRouteList() {

		// when
		List<OceanRoute> found = oceanRouteRepository.findByPol(LOCATION_BRSSZ);

		// then
		assertThat(found.isEmpty(), is(false));
		Predicate<OceanRoute> isBrssz = or -> or.getPol().equals(LOCATION_BRSSZ);
		assertTrue(found.stream().allMatch(isBrssz));
	}
	

	@Test
	public void whenFindByPol_thenPrintOceanRoutePols() {

		// when
		List<OceanRoute> found = oceanRouteRepository.findByPol(LOCATION_BRSSZ);
		assertThat(found.isEmpty(), is(false));


			Consumer<String> action = System.out::println;
		found.stream().map(oc -> oc.getPol()).forEach(action);
	}
	
	@Test
	public void whenFindByExample_thenReturnOceanRoutes() {
        
		OceanRoute or=new OceanRoute(LOCATION_BRSSZ,LOCATION_HKHKG, null, null);
		ExampleMatcher matcher = ExampleMatcher.matchingAny()
				.withIgnorePaths("transitTime", "polFac")
				.withMatcher("pol", m -> m.startsWith())
		.withMatcher("pod", m -> m.startsWith());
		Example<OceanRoute> example = Example.of(or,matcher);

		// when
		List<OceanRoute> found = oceanRouteRepository.findAll(example);
		assertThat(found.isEmpty(), is(false));


			Consumer<String> action = System.out::println;
		found.stream().map(oc -> oc.getPod()).forEach(action);
	}
}
