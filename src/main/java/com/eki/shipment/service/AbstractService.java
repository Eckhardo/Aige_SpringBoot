package com.eki.shipment.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import com.eki.common.interfaces.IEntity;
import com.eki.shipment.exception.MyEntityNotFoundException;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

@Transactional
public abstract class AbstractService<T extends IEntity> implements IServiceOperations<T> {

	private Class<T> clazz;

	public AbstractService(final Class<T> clazzToSet) {
		super();

		clazz = clazzToSet;
	}

	// API

	// find - one

	@Override
	@Transactional(readOnly = true)
	public T findOne(final long id) {
		Optional<T> entity = getDao().findById(id);
		return entity.orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<T> findAll() {
		return Lists.newArrayList(getDao().findAll());
	}

	@Override
	public List<T> findAllSorted(String sortBy, String sortOrder) {
		final Sort sort = constructSort(sortBy, sortOrder);
		return Lists.newArrayList(getDao().findAll(sort));
	}

	@Override
	public List<T> findAllPaginated(int page, int size) {
	
		Page<T> findAllPaginated= getDao().findAll(	PageRequest.of(page, size));
		return findAllPaginated.getContent();
	}

	@Override
	public List<T> findAllPaginatedAndSorted(int page, int size, String sortBy, String sortOrder) {
		final Sort sortInfo= constructSort(sortBy, sortOrder);
		Page<T> findAllSortedAnPaginated=getDao().findAll(PageRequest.of(page, size, sortInfo));
		return findAllSortedAnPaginated.getContent();
	}

	@Override
	public T create(T entity) {
		Preconditions.checkNotNull(entity);
		final T persistedEntity = getDao().save(entity);
		return persistedEntity;
	}

	@Override
	public void update(T entity) {
		Preconditions.checkNotNull(entity);
		getDao().save(entity);
	}

	@Override
	public void delete(long id) {
		final Optional<T> entity = getDao().findById(id);
		if (entity.isPresent()) {
			getDao().delete(entity.get());
		} else {
			throw new MyEntityNotFoundException();

		}
	}

	@Override
	public void deleteAll() {
		getDao().deleteAll();
	}

	@Override
	public long count() {
		return getDao().count();
	}

	// template method

	protected abstract PagingAndSortingRepository<T, Long> getDao();

	protected final Sort constructSort(final String sortBy, final String sortOrder) {
		Sort sortInfo = null;
		if (sortBy != null) {
			sortInfo = new Sort(Direction.fromString(sortOrder), sortBy);
		}
		return sortInfo;
	}
}
