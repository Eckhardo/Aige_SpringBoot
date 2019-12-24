package com.eki.shipment.dao;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eki.common.interfaces.IByNameApi;
import com.eki.shipment.model.Country;

@Repository
public interface  CountryRepository extends JpaRepository<Country, Long>, JpaSpecificationExecutor<Country>, IByNameApi<Country> { 

	
	    Country findByCode(String code);
	    
	    
	    @Query("SELECT c FROM Country c WHERE c.code LIKE %:searchTerm%")
	    public Collection<Country> findByCodeLike(@Param("searchTerm") String searchTerm);
	 
}
