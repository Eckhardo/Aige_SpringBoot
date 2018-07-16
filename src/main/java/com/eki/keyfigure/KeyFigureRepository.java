package com.eki.keyfigure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eki.model.KeyFigure;

@Repository
public interface KeyFigureRepository extends JpaRepository<KeyFigure, Long>{

	List<KeyFigure> findAllByFromLocationCodeAndFromCountryCodeAndFromGeoScopeType(String location, String country, String type);
	List<KeyFigure> findAllByFromCountryCode(String country);

}
