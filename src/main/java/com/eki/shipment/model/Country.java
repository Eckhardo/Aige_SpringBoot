package com.eki.shipment.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import com.eki.common.interfaces.INameableDto;
import com.eki.common.interfaces.INameableEntity;

@Entity
@Table(name = "country")
@XmlRootElement
public class Country implements INameableEntity, INameableDto {

	private static final long serialVersionUID = -3009157732242241606L;

	@Id
	 @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

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

	public Country(@NotNull @Size(max = 50) String name, @NotNull @Size(max = 3) String code) {
		super();
		this.name = name;
		this.code = code;
	}

	public Country(long id, @NotNull @Size(max = 50) String name, @NotNull @Size(max = 3) String code) {
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

	@Override
	public void setName(String name) {
		this.name = name;

	}

	@Override
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
		Country other = (Country) obj;
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
