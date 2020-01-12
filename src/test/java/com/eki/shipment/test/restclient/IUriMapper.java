package com.eki.shipment.test.restclient;

import com.eki.common.interfaces.IEntity;

public interface IUriMapper {

	<T extends IEntity> String getUriBase(final Class<T> clazz);

}
