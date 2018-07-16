package com.eki;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eki.model.Customer;

@RestController
@EnableJpaAuditing
public class DemoController {

	 @Autowired
	    private CustomerRepository repository;	
	  @RequestMapping("/all")
	    public Iterable<Customer> getCustomers(){
		  System.out.println("Get custoemr");
	        return repository.findAll();
	    }
	  @RequestMapping("/customers/{name}")
	    public Customer getRecognition(@PathVariable("name") String firstName){
	        return repository.findByFirstName(firstName);
	    }
	  @GetMapping("/customer/{id}")
	  public Customer getCustomerById(@PathVariable(value = "id") Long customerId) {
	      return repository.findById(customerId)
	              .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", customerId));
	  }
	  @PutMapping("/customers/{postId}")
	    public Customer updateCustomer(@PathVariable Long customerId, @Valid @RequestBody Customer postRequest) {
	        return repository.findById(customerId).map(customer -> {
	            customer.setFirstName(postRequest.getFirstName());
	            customer.setLastName(postRequest.getLastName());
	            return repository.save(customer);
	        }).orElseThrow(() ->  new ResourceNotFoundException("Customer", "id", customerId));
	    }
	  
	// Delete a Customer
	  @DeleteMapping("/customer/{id}")
	  public ResponseEntity<?> deleteNote(@PathVariable(value = "id") Long customerId) {
	      Customer customer = repository.findById(customerId)
	              .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", customerId));

	      repository.delete(customer);

	      return ResponseEntity.ok().build();
	  }
}
