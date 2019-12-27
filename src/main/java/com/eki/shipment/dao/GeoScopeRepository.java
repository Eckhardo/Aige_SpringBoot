package com.eki.shipment.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eki.common.interfaces.IByNameApi;
import com.eki.shipment.model.GeoScope;

@Repository
public interface GeoScopeRepository extends JpaRepository<GeoScope, Long>, JpaSpecificationExecutor<GeoScope>, IByNameApi<GeoScope> { 

   GeoScope findByLocationCode(String code);
    
    @Query("SELECT g FROM  GeoScope g WHERE g.locationCode LIKE %:searchTerm%")
     List<GeoScope> findByLocationCodeLike(@Param("searchTerm") String searchTerm);
   
    @Query("SELECT g FROM  GeoScope g WHERE g.port=true AND g.locationCode LIKE %:searchTerm%")
    public List<GeoScope> findPortsLike(@Param("searchTerm") String searchTerm);

    List<GeoScope> findAllByCountryCodeAndLocationCode(String countryCode, String locationCode);
    
}
