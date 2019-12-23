package com.eki.shipment.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.hateoas.ResourceSupport;


@Entity
@Table(name = "country")
public class Country   {

	
	private static final long serialVersionUID = -3009157732242241606L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	
	@NotNull
    @Size(max = 50)
    @Column(unique = true)
	private String name;

	@NotNull
    @Size(max = 3)
    @Column(unique = true)
	private String code;


	protected Country() {
	}


	public Country(String name, String code) {
		super();
		this.name = name;
		this.code = code;
	}

	public Country(long id, @NotNull @Size(max = 50) String name, @NotNull @Size(max = 3) String code) {
		this(name, code);
		this.id = id;
		
	}


	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	public String getCode() {
		return code;
	}
	 @Override
	    public String toString() {
	        return String.format(
	                "Country[id=%d, name='%s', code='%s']",
	                id, name, code);
	    }
}