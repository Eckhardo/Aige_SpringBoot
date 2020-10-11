package com.eki.shipment.dao;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.eki.common.interfaces.IEntity;
import com.eki.shipment.run.MysqlBootApplication;

@SpringBootTest(classes = MysqlBootApplication.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestPropertySource(locations="classpath:application-test.properties")
@Transactional
public abstract class AbstractRepositoryTest<T extends IEntity,  ID > {

	@Test
	public void givenResourceExists_whenResourceIsSaved_thenNoExceptions() {
		// Given
		final T existingResource = createNewEntity();

		// When
		getApi().save(existingResource);
	}

	@Test
	public final void givenResourceExists_whenResourceIsDeleted_thenResourceNoLongerExists() {
		// Given
		final T existingResource = createNewEntity();
		 T savedResource=	getApi().save(existingResource);
		// When
		getApi().delete(savedResource);
		
		ID id= (ID) savedResource.getId();

		// Then
	java.util.Optional<T> retrievedResource=	getApi().findById(id);
	Assert.assertFalse(retrievedResource.isPresent());
	}
	
	protected abstract T createNewEntity();

	protected abstract PagingAndSortingRepository<T,ID> getApi();
}
