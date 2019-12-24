package com.eki.shipment.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.eki.shipment.model.KeyFigure;

@Repository
public interface KeyFigureRepository extends JpaRepository<KeyFigure, Long>, JpaSpecificationExecutor<KeyFigure> {

	List<KeyFigure> findAllByFromLocationCodeAndFromCountryCodeAndFromGeoScopeType(String location, String country,
			String type);

	List<KeyFigure> findAllByFromCountryCode(String country);

}
