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


public abstract class AbstractService<E extends IEntity> implements IServiceOperations<E> {

	private Class<E> clazz;

	public AbstractService(final Class<E> clazzToSet) {
		super();
		this.clazz = clazzToSet;
	}

	// API

	// find - one

	@Override
	@Transactional(readOnly = true)
	public E findOne(final long id) {
		Optional<E> entity = getDao().findById(id);
		return entity.orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<E> findAll() {
		return Lists.newArrayList(getDao().findAll());
	}

	@Override
	public List<E> findAllSorted(String sortBy, String sortOrder) {
		final Sort sort = constructSort(sortBy, sortOrder);
		return Lists.newArrayList(getDao().findAll(sort));
	}

	@Override
	public Page<E> findAllPaginated(int pageNo, int pageSize) {
		PageRequest page=	PageRequest.of(pageNo,pageSize);
		Page<E> findAllPaginated= getDao().findAll(	page);
		return findAllPaginated;
	}

	@Override
	public Page<E> findAllPaginatedAndSorted(int page, int size, String sortBy, String sortOrder) {
		final Sort sortInfo= constructSort(sortBy, sortOrder);
		Page<E> findAllSortedAnPaginated=getDao().findAll(PageRequest.of(page, size, sortInfo));
		return findAllSortedAnPaginated;
	}

	@Override
	public E create(E entity) {
		Preconditions.checkNotNull(entity);
		final E persistedEntity = getDao().save(entity);
		return persistedEntity;
	}

	@Override
	public void update(E entity) {
		Preconditions.checkNotNull(entity);
		getDao().save(entity);
	}

	@Override
	public void delete(long id) {
		final Optional<E> entity = getDao().findById(id);
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

	protected abstract PagingAndSortingRepository<E, Long> getDao();

	protected final Sort constructSort(final String sortBy, final String sortOrder) {
		Sort sortInfo = null;
		if (sortBy != null) {
			sortInfo = new Sort(Direction.fromString(sortOrder), sortBy);
		}
		return sortInfo;
	}
}
