package com.eki.shipment.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.eki.common.interfaces.IDto;

public class CountryDto implements IDto {
	private static final long serialVersionUID = -3009157732242241606L;

	private Long id;

	
	private String name;

	
	private String code;

	protected CountryDto() {
	}

	public CountryDto(@NotNull @Size(max = 50) String name, @NotNull @Size(max = 3) String code) {
		super();
		this.name = name;
		this.code = code;
	}

	public CountryDto(long id, @NotNull @Size(max = 50) String name, @NotNull @Size(max = 3) String code) {
		this(name, code);
		this.id = id;

	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;

	}


	public void setName(String name) {
		this.name = name;

	}

	
	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return String.format("Country[id=%d, name='%s', code='%s']", id, name, code);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CountryDto other = (CountryDto) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
