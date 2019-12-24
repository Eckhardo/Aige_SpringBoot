package com.eki.shipment.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.eki.shipment.model.Country;
import com.eki.shipment.model.KeyFigure;
import com.eki.shipment.model.OceanRoute;

@Repository
public interface OceanRouteRepository extends JpaRepository<OceanRoute, Long>, JpaSpecificationExecutor<OceanRoute> {

	
	List<OceanRoute> findByPol(String pol);
	List<OceanRoute> findByPod(String pod);
	

}
