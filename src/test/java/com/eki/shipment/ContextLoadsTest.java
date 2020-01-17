package com.eki.shipment;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.eki.shipment.dao.AbstractRepositoryTest;
import com.eki.shipment.dao.CountryRepository;
import com.eki.shipment.run.MysqlBootApplication;

@SpringBootTest(classes = MysqlBootApplication.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestPropertySource(locations="classpath:application-test.properties")
public class ContextLoadsTest  {
	
	
	@Autowired
	CountryRepository countryRep;

	@Test
	public void contextLoads() {
	}

	@Test
	public void persistenceWorks() {
		
		assertThat(countryRep.findAll(), not(Matchers.emptyIterable()));
	}
}
