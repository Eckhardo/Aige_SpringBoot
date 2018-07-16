package com.eki;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eki.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

     Optional<Customer> findById(long id);
     Customer findByFirstName(String firstName);
     List<Customer> findByLastName(String lastName);
    
   

}