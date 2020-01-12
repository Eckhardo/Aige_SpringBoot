package com.eki.shipment.test.restclient;

import org.springframework.stereotype.Component;

import com.eki.common.interfaces.IEntity;


@Component
public class UriMapper implements IUriMapper {

    public UriMapper() {
        super();
    }

    // API
    @Override
    public <T extends IEntity> String getUriBase(final Class<T> clazz) {
        String simpleName = clazz.getSimpleName().toString().toLowerCase();
        if (simpleName.endsWith("dto")) {
            simpleName = simpleName.substring(0, simpleName.length() - 3);
        }
        return simpleName + "s";
    }

}
