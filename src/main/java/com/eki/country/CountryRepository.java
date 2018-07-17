package com.eki.country;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eki.model.Country;

@Repository
public interface  CountryRepository extends CrudRepository<Country, Long> {

	
	    Country findByCode(String code);
	    @Query("SELECT c FROM Country c WHERE c.code LIKE %:searchTerm%")
	    public Collection<Country> findByCodeLike(@Param("searchTerm") String searchTerm);
	 
}
