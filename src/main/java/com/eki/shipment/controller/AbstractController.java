package com.eki.shipment.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import com.eki.common.interfaces.IDto;
import com.eki.common.interfaces.IEntity;
import com.eki.common.util.QueryConstants;
import com.eki.common.util.RestPreconditions;
import com.eki.common.util.WebConstants;
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

 

    protected final D findOneInternal(final Long id) {
        return (D) RestPreconditions.checkNotNull(getService().findOne(id));
    }

    // find - all

    protected final List<D> findAllInternal(final HttpServletRequest request, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        if (request.getParameterNames().hasMoreElements()) {
            throw new MyResourceNotFoundException();
        }

        return (List<D>) getService().findAll();
    }

    protected final void findAllRedirectToPagination(final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        final String resourceName = clazz.getSimpleName().toString().toLowerCase();
        final String locationValue = uriBuilder.path(WebConstants.PATH_SEP + resourceName).build().encode().toUriString() + QueryConstants.QUESTIONMARK + "page=0&size=10";

        response.setHeader(HttpHeaders.LOCATION, locationValue);
    }

    protected final List<D> findPaginatedAndSortedInternal(final int page, final int size, final String sortBy, final String sortOrder, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        final Page<D> resultPage = (Page<D>) getService().findAllPaginatedAndSorted(page, size, sortBy, sortOrder);
        if (page > resultPage.getTotalPages()) {
            throw new MyResourceNotFoundException();
        }
 
        return Lists.newArrayList(resultPage.getContent());
    }

    protected final List<D> findPaginatedInternal(final int page, final int size, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        final Page<D> resultPage = (Page<D>) getService().findAllPaginated(page, size);
        if (page > resultPage.getTotalPages()) {
            throw new MyResourceNotFoundException();
        }
        return Lists.newArrayList(resultPage.getContent());
    }

    protected final List<D> findAllSortedInternal(final String sortBy, final String sortOrder) {
        final List<D> resultPage = (List<D>) getService().findAllSorted(sortBy, sortOrder);
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
        // InvalidDataAccessApiUsageException - ResourceNotFoundException
        // IllegalStateException - ResourceNotFoundException
        // DataAccessException - ignored
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
    
    protected abstract List<D> findAllPaginatedAndSorted(final int page, final int size, final String sortBy, final String sortOrder);
    protected abstract List<D> findAllPaginated(final int page, final int size);
       protected abstract List<D> findAllSorted(final String sortBy, final String sortOrder);

}
