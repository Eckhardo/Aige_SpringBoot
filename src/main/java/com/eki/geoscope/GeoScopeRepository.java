package com.eki.geoscope;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eki.model.GeoScope;

@Repository
public interface GeoScopeRepository extends JpaRepository<GeoScope, Long> {

	
	
    GeoScope findByCountryCode(String name);
   GeoScope findByLocationCode(String code);
    @Query("SELECT g FROM  GeoScope g WHERE g.locationCode LIKE %:searchTerm%")
    public Collection<GeoScope> findByLocationCodeLike(@Param("searchTerm") String searchTerm);
    
    List<GeoScope> findAllByCountryCodeAndLocationCode(String countryCode, String locationCode);
    
   

}
