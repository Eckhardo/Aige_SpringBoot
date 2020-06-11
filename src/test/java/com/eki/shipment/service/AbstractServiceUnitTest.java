package com.eki.shipment.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.eki.common.interfaces.IEntity;
import com.eki.shipment.util.IDUtil;
import com.google.common.collect.Lists;

public abstract class AbstractServiceUnitTest<T extends IEntity> {

	public void before() {
		when(getDAO().findAll()).thenReturn(Lists.newArrayList());
	}

	@Test
	public final void whenGetIsTriggered_thenEntityIsRetrieved() {
		stubDaoGetOne(1l);

		// When
		getApi().findOne(1l);

		// Then
		verify(getDAO()).findById(1l);
	}

	@Test
	public void whenGetAllIsTriggered_thenAllEntitiesAreRetrieved() {
		// When
		getApi().findAll();

		// Then
		verify(getDAO()).findAll();
	}

	@Test
	public void whenCreatingANewEntity_thenEntityIsSaved() {
		// Given
		final T entity = stubDaoSave(createNewEntity());

		// When
		getApi().create(entity);

		// Then
		verify(getDAO()).save(entity);
	}
	
	   @Test
	    public void whenUpdateIsTriggered_thenEntityIsUpdated() {
	        // When
	        final T entity = createSimulatedExistingEntity();
	        getApi().update(entity);

	        // Then
	        verify(getDAO()).save(entity);
	    }

	@Test
	public void whenDeleteAllEntities_thenEntitiesAreDeleted() {
		// When
		getApi().deleteAll();

		// Then
		verify(getDAO()).deleteAll();
	}

	@Test
	public void whenDeleteIsTriggeredAndGivenResourceExists_thenEntityIsDeleted() {
		// Given
		final long id = IDUtil.randomPositiveLong();
		final T entityToBeDeleted = givenEntityExists(id);

		// When
		getApi().delete(id);

		// Then
		verify(getDAO()).delete(entityToBeDeleted);
	}
	  /**
     * Creates and returns the instance of entity that is existing (ie ID is not null).
     * 
     * @return the created entity
     */
	protected final T givenEntityExists(long id) {
		final T entity = createNewEntity();
		entity.setId(id);
		when(getDAO().findById(id)).thenReturn(Optional.of(entity));
		return entity;
	}
	  /**
     * Creates and returns the instance of entity that is existing (ie ID is not null).
     * 
     * @return the created entity
     */
    protected T createSimulatedExistingEntity() {
        final T entity = createNewEntity();
        entity.setId(IDUtil.randomPositiveLong());

        when(getDAO().findById(entity.getId())).thenReturn(Optional.of(entity));
        return entity;
    }
	  /**
     * Stubs the dao's save call.
     * 
     * @return the created entity
     */
	protected T stubDaoSave(T entity) {
		when(getDAO().save(entity)).thenReturn(entity);
		return entity;
	}


	// template

	protected abstract T createNewEntity();

	protected abstract void changeEntity(final T entity);

	protected abstract T stubDaoGetOne(final long id);

	/**
	 * Gets the service that is need to be tested.
	 * 
	 * @return the service.
	 */
	protected abstract IServiceOperations<T> getApi();

	/**
	 * Gets the DAO mock.
	 * 
	 * @return the DAO mock.
	 */
	protected abstract PagingAndSortingRepository<T, Long> getDAO();

}
