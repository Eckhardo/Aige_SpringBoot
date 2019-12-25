package com.eki.shipment.service.main;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.data.domain.Sort;

import com.eki.common.interfaces.IEntity;
import com.eki.shipment.model.KeyFigure;
import com.eki.shipment.service.IServiceOperations;
import com.eki.shipment.util.IDUtil;
import com.google.common.collect.Ordering;

public abstract class AbstractServiceIntegrationTest<T extends IEntity> {
	
	// find one
	@Test
	public final void givenResourceDoesNotExist_whenResourceIsRetrieved_thenNoResourceIsReceived() {
		// When
		final T createdResource = getApi().findOne(IDUtil.randomPositiveLong());

		// Then
		assertNull(createdResource);
	}

	@Test
	public void givenResourceExists_whenResourceIsRetrieved_thenResourceIsRetrievedCorrectly() {
		final T existingResource = persistNewEntity();
		final T retrievedResource = getApi().findOne(existingResource.getId());
		assertEquals(existingResource, retrievedResource);
	}
	
	// find all

	@Test
	public void givenAtLeastOneResourceExists_whenAllResourcesAreRetrieved_thenRetrievedResourcesAreNotEmpty() {
		persistNewEntity();

		// When
		final List<T> allResources = getApi().findAll();

		// Then
		assertThat(allResources, not(Matchers.<T>empty()));
	}

	@Test
	public void givenAnResourceExists_whenAllResourcesAreRetrieved_thenTheExistingResourceIsIndeedAmongThem() {
		final T existingResource = persistNewEntity();

		final List<T> resources = getApi().findAll();

		assertThat(resources, hasItem(existingResource));
	}

	@Test(expected = RuntimeException.class)
	public void whenNullResourceIsCreated_thenException() {
		getApi().create(null);
	}
	
	
	// update

	@Test
	public void givenResourceExists_whenResourceIsUpdated_thenNoExceptions() {
		// Given
		final T existingResource = persistNewEntity();

		// When
		getApi().update(existingResource);
	}

	@Test
	public void givenResourceExists_whenResourceIsUpdated_thenUpdatesArePersisted() {
		// Given
		final T existingResource = persistNewEntity();

		// When
		change(existingResource);
		getApi().update(existingResource);

		final T updatedResource = getApi().findOne(existingResource.getId());

		// Then
		assertEquals(existingResource, updatedResource);
	}
	
	// delete

	@Test
	public final void givenResourceExists_whenResourceIsDeleted_thenResourceNoLongerExists() {
		// Given
		final T existingResource = persistNewEntity();

		// When
		getApi().delete(existingResource.getId());

		// Then
		assertNull(getApi().findOne(existingResource.getId()));
	}
    @Test
    /**/public final void whenResourcesAreRetrievedSorted_thenResourcesAreIndeedOrdered() {
        persistNewEntity();
        persistNewEntity();

        // When
        final List<T> resourcesSorted = getApi().findAllSorted("ID", Sort.Direction.ASC.name());

        // Then
          List<Long> ids = resourcesSorted.stream().map(t-> t.getId()).collect(Collectors.toList());
          assertTrue(isSorted(ids));
    }
    
	private static boolean isSorted(List<Long> ids) {
	    return Ordering.<Long> natural().isOrdered(ids);
	}
	// template method

	protected abstract void invalidate(T invalidResource);

	protected abstract void change(T invalidResource);

	protected abstract T createNewEntity();

	protected abstract IServiceOperations<T> getApi();

	protected T persistNewEntity() {
		return getApi().create(createNewEntity());
	}

}
