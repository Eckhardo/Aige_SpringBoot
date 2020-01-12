package com.eki.shipment.test.restclient;

import org.springframework.http.ResponseEntity;

import com.eki.common.interfaces.IEntity;

public interface IRestClient<T extends IEntity>  {

	T findOne(long id);

	ResponseEntity<T> findOneAsResponse(long id);
	
	T create(long id);

	ResponseEntity<T> createAsResponse(long id);

}
