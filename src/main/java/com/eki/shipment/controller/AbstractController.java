package com.eki.shipment.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.eki.common.interfaces.IDto;
import com.eki.common.interfaces.IEntity;
import com.eki.common.util.RestPreconditions;
import com.eki.shipment.exception.MyResourceNotFoundException;
import com.eki.shipment.service.IServiceOperations;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

public abstract class AbstractController<D extends IDto, E extends IEntity> {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected Class<D> clazz;

	@Autowired
	public AbstractController(final Class<D> clazzToSet) {
		super();
		Preconditions.checkNotNull(clazzToSet);
		clazz = clazzToSet;
	}

	// find - one

	protected final E findOneInternal(final Long id) {
		return (E) RestPreconditions.checkNotNull(getService().findOne(id));
	}

	// find - all

	protected final List<E> findAllInternal(final HttpServletRequest request) {
		if (request.getParameterNames().hasMoreElements()) {
			throw new MyResourceNotFoundException();
		}

		return  getService().findAll();
	}

	protected final List<E> findPaginatedAndSortedInternal(final int page, final int size, final String sortBy,
			final String sortOrder) {
		final Page<E> resultPage =getService().findAllPaginatedAndSorted(page, size, sortBy, sortOrder);
		if (page > resultPage.getTotalPages()) {
			throw new MyResourceNotFoundException();
		}

		return Lists.newArrayList(resultPage.getContent());
	}

	protected final List<E> findPaginatedInternal(final int page, final int size) {
		final Page< E > resultPage =getService().findAllPaginated(page, size);
		if (page > resultPage.getTotalPages()) {
			throw new MyResourceNotFoundException();
		}
		return Lists.newArrayList(resultPage.getContent());
	}

	protected final List< E > findAllSortedInternal(final String sortBy, final String sortOrder) {
		final List< E > resultPage = (List< E >) getService().findAllSorted(sortBy, sortOrder);
		return resultPage;
	}
	// save/create/persist

	protected final E createInternal(final E resource) {
		RestPreconditions.checkRequestElementNotNull(resource);
		RestPreconditions.checkRequestState(resource.getId() == null);
		final E existingResource = getService().create(resource);
		return existingResource;
	}

	// update

	/**
	 * - note: the operation is IDEMPOTENT <br/>
	 */
	protected final void updateInternal(final long id, final E resource) {
		RestPreconditions.checkRequestElementNotNull(resource);
		RestPreconditions.checkRequestElementNotNull(resource.getId());
		RestPreconditions.checkRequestState(resource.getId() == id);
		RestPreconditions.checkNotNull(getService().findOne(resource.getId()));

		getService().update(resource);
	}

	// delete/remove

	protected final void deleteByIdInternal(final long id) {
		getService().delete(id);
	}

	// count

	protected final long countInternal() {
		return getService().count();
	}

	// generic REST operations

	// count

	/**
	 * Counts all {@link Privilege} resources in the system
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/count")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public long count() {
		return countInternal();
	}

	// template method

	protected abstract IServiceOperations<E> getService();

	protected abstract List< E > findAllPaginatedAndSorted(final int page, final int size, final String sortBy,
			final String sortOrder);

	protected abstract List< E > findAllPaginated(final int page, final int size);

	protected abstract List< E > findAllSorted(final String sortBy, final String sortOrder);

}