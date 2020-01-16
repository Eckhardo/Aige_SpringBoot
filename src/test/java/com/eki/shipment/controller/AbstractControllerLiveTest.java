package com.eki.shipment.controller;

import java.util.List;

import org.springframework.data.domain.Page;

import com.eki.common.interfaces.IEntity;
import com.eki.shipment.service.IServiceOperations;

import com.google.common.base.Preconditions;

public abstract class AbstractControllerLiveTest<T extends IEntity> implements IServiceOperations<T> {

	protected final Class<T> clazz;

	public AbstractControllerLiveTest(final Class<T> clazzToSet) {
		super();
		Preconditions.checkNotNull(clazzToSet);
		clazz = clazzToSet;
	}

	@Override
	public T findOne(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> findAllSorted(String sortBy, String sortOrder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<T> findAllPaginated(int page, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<T> findAllPaginatedAndSorted(int page, int size, String sortBy, String sortOrder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T create(T resource) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(T resource) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	
}
