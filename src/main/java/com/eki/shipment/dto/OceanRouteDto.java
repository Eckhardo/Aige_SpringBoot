package com.eki.shipment.dto;

import java.util.Collections;
import java.util.List;

import com.eki.common.interfaces.IDto;




public class OceanRouteDto implements IDto {

	private static final long serialVersionUID = -7182994144556273088L;

	public OceanRouteDto() {
		super();
	}

	public OceanRouteDto(String pol, String pod, String ts1, List<String> errors) {
		this.pol = pol;
		this.pod = pod;
		this.ts1 = ts1;
		this.errors = errors;

	}

	public OceanRouteDto(int transitTime, String pol, String polFac, String pod, String podFac, String ts1, String ts1Fac,
			String ts2, String ts1Fac2, String ts3, String ts1Fac3, String prof, String prof2, String prof3,
			List<String> errors) {
		this.transitTime = transitTime;
		this.pol = pol;
	
		this.pod = pod;
		
		this.ts1 = ts1;
		
		this.ts2 = ts2;
	
		this.ts3 = ts3;
	
		this.prof = prof;
		this.prof2 = prof2;
		this.prof3 = prof3;
		this.errors = errors == null ? Collections.emptyList() : errors;
	}

	private Long id;

	private int transitTime;

	private String pol;
	private String pod;
	private String ts1;
	private String ts2;
	private String ts3;
	private String prof;
	private String prof2;
	private String prof3;


	private List<String> errors;



	public int getTransitTime() {
		return transitTime;
	}

	public String getPol() {
		return pol;
	}

	public void setPol(String pol) {
		this.pol = pol;
	}

	public void setPod(String pod) {
		this.pod = pod;
	}

	public void setTs1(String ts1) {
		this.ts1 = ts1;
	}


	public String getPod() {
		return pod;
	}



	public String getTs1() {
		return ts1;
	}

	

	public String getTs2() {
		return ts2;
	}

	

	public String getTs3() {
		return ts3;
	}


	public String getProf() {
		return prof;
	}

	public String getProf2() {
		return prof2;
	}

	public String getProf3() {
		return prof3;
	}

	public List<String> getErrors() {
		return errors;
	}

	public boolean isValid() {
		return this.errors == null || this.errors.isEmpty();
	}

	public int getNumberOfTsPorts() {
		int count = 0;
		if (ts1 != null) {
			count++;
		}

		return count;
	}

	public boolean isShunting() {

		if (ts1 != null && ts2 != null) {
			return ts1.equals(ts2);
		}
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pod == null) ? 0 : pod.hashCode());
		result = prime * result + ((pol == null) ? 0 : pol.hashCode());
		result = prime * result + ((prof == null) ? 0 : prof.hashCode());
		result = prime * result + ((prof2 == null) ? 0 : prof2.hashCode());
		result = prime * result + ((prof3 == null) ? 0 : prof3.hashCode());
		result = prime * result + transitTime;
		result = prime * result + ((ts1 == null) ? 0 : ts1.hashCode());
		result = prime * result + ((ts2 == null) ? 0 : ts2.hashCode());
		result = prime * result + ((ts3 == null) ? 0 : ts3.hashCode());
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
		OceanRouteDto other = (OceanRouteDto) obj;
		if (pod == null) {
			if (other.pod != null)
				return false;
		} else if (!pod.equals(other.pod))
			return false;
		if (pol == null) {
			if (other.pol != null)
				return false;
		} else if (!pol.equals(other.pol))
			return false;
	
		if (prof == null) {
			if (other.prof != null)
				return false;
		} else if (!prof.equals(other.prof))
			return false;
		if (prof2 == null) {
			if (other.prof2 != null)
				return false;
		} else if (!prof2.equals(other.prof2))
			return false;
		if (prof3 == null) {
			if (other.prof3 != null)
				return false;
		} else if (!prof3.equals(other.prof3))
			return false;
		if (transitTime != other.transitTime)
			return false;
		if (ts1 == null) {
			if (other.ts1 != null)
				return false;
		} else if (!ts1.equals(other.ts1))
			return false;
		if (ts2 == null) {
			if (other.ts2 != null)
				return false;
		} else if (!ts2.equals(other.ts2))
			return false;
		if (ts3 == null) {
			if (other.ts3 != null)
				return false;
		} else if (!ts3.equals(other.ts3))
			return false;
	
		return true;
	}

	@Override
	public Long getId() {
	return id;
	}

	@Override
	public void setId(Long id) {
		this.id=id;
		
	}
}