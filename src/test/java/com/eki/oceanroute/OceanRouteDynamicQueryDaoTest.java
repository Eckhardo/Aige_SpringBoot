package com.eki.oceanroute;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.eki.model.OceanRoute;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestPropertySource(locations = "classpath:application-test.properties")
public class OceanRouteDynamicQueryDaoTest {

	@Autowired
	private OceanRouteDynamicQueryDao oceanRouteDynamicQueryDao;

	static String POL = "BRSSZ";
	static String POD = "HKHKG";
	static String TS1 = "COCTG";
	static String TS2 = "ARBUE";

	@Test
	public void givenPolAndPod_whenSearchingForOceanRoutes() {

		List<OceanRoute> oceanRoutes = oceanRouteDynamicQueryDao.findRoutes(POL, POD, null, null, null, false);
		assertThat(oceanRoutes.size(), is(10));

		Predicate<OceanRoute> p1 = g -> g.getPol().equals(POL);
		assertTrue(oceanRoutes.stream().allMatch(p1));
		assertTrue(oceanRoutes.stream().noneMatch(e -> e.getPol().equals(TS1)));
	}

	
	@Test
	public void givenPolAndPodAndTS1_whenSearchingForOceanRoutes() {

		List<OceanRoute> oceanRoutes = oceanRouteDynamicQueryDao.findRoutes(POL, POD, TS1, null, null, false);
		assertThat(oceanRoutes.size(), is(1));

		Predicate<OceanRoute> p1 = g -> g.getPol().equals(POL);
		assertTrue(oceanRoutes.stream().allMatch(p1));
		assertTrue(oceanRoutes.stream().allMatch(e -> e.getTs1().equals(TS1)));
	}
	@Test
	public void givenPolAndPodAndTS1AndTs2_whenSearchingForOceanRoutes() {
		

		List<OceanRoute> oceanRoutes = oceanRouteDynamicQueryDao.findRoutes(POL, POD, TS1, TS1, null, false);
		assertThat(oceanRoutes.size(), is(1));

		Predicate<OceanRoute> p1 = g -> g.getPol().equals(POL);
		assertTrue(oceanRoutes.stream().allMatch(p1));
		assertTrue(oceanRoutes.stream().allMatch(e -> e.getTs2().equals(TS1)));
	}
	@Test
	public void givenIsShunting_whenSearchingForOceanRoutes() {

		List<OceanRoute> oceanRoutes = oceanRouteDynamicQueryDao.findRoutes(POL, POD, null, null, null, true);
		assertThat(oceanRoutes.size(), is(2));
		assertTrue(oceanRoutes.stream().allMatch(e -> e.isShunting()));
	}
	
	@Test
	public void givenIncludeInvalidRoutes_whenSearchingForOceanRoutes() {

		List<OceanRoute> oceanRoutes = oceanRouteDynamicQueryDao.findRoutes(POL, POD, null, null, null, false);
		
	oceanRoutes=	oceanRoutes.stream().filter(or -> ! or.getErrors().isEmpty()).collect(Collectors.toList());
		assertThat(oceanRoutes.size(), is(4));
		assertTrue(oceanRoutes.stream().noneMatch(e -> e.getErrors().isEmpty()));
	}
}
