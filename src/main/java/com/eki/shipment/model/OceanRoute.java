/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eki.shipment.model;

import java.util.Collections;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import com.eki.common.interfaces.IDto;
import com.eki.common.interfaces.IEntity;

/**
 * OceanRoute [transitTime=P64D, routing=BRSSZ(565151) --> PACTB(1632414) -->
 * HKHKG(564077)] (52010SIM1917(842225),77030SIM0007(803165)) - ERRORS: []
 *
 * @author ekirschning
 */

@Entity
@Table(name = "oceanroute")

public class OceanRoute implements IEntity, IDto {

	private static final long serialVersionUID = -7182994144556273088L;

	public OceanRoute() {
		super();
	}

	public OceanRoute(String pol, String pod, String ts1, List<String> errors) {
		this.pol = pol;
		this.pod = pod;
		this.ts1 = ts1;
		this.errors = errors;

	}

	public OceanRoute(int transitTime, String pol, String polFac, String pod, String podFac, String ts1, String ts1Fac,
			String ts2, String ts1Fac2, String ts3, String ts1Fac3, String prof, String prof2, String prof3,
			List<String> errors) {
		this.transitTime = transitTime;
		this.pol = pol;
		this.polFac = polFac;
		this.pod = pod;
		this.podFac = podFac;
		this.ts1 = ts1;
		this.tsFac1 = ts1Fac;
		this.ts2 = ts2;
		this.ts1Fac2 = ts1Fac2;
		this.ts3 = ts3;
		this.ts1Fac3 = ts1Fac3;
		this.prof = prof;
		this.prof2 = prof2;
		this.prof3 = prof3;
		this.errors = errors == null ? Collections.emptyList() : errors;
	}

	/**
	 * 
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "tt")
	private int transitTime;

	@Column(name = "pol")
	private String pol;
	@Column(name = "pol_fac")
	private String polFac;
	private String pod;
	@Column(name = "pod_fac")
	private String podFac;
	private String ts1;
	@Column(name = "ts_fac1")
	private String tsFac1;
	private String ts2;
	@Column(name = "ts1fac2")
	private String ts1Fac2;
	private String ts3;
	@Column(name = "ts1fac3")
	private String ts1Fac3;
	private String prof;
	private String prof2;
	private String prof3;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "ocean_route_errors", joinColumns = @JoinColumn(name = "ocean_route_id"))
	@Column(name = "errors")
	private List<String> errors;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;

	}

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

	public String getPolFac() {
		return polFac;
	}

	public String getPod() {
		return pod;
	}

	public String getPodFac() {
		return podFac;
	}

	public String getTs1() {
		return ts1;
	}

	public String getTsFac1() {
		return tsFac1;
	}

	public String getTs2() {
		return ts2;
	}

	public String getTs1Fac2() {
		return ts1Fac2;
	}

	public String getTs3() {
		return ts3;
	}

	public String getTs1Fac3() {
		return ts1Fac3;
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

		return this.ts1.equals(ts2);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pod == null) ? 0 : pod.hashCode());
		result = prime * result + ((podFac == null) ? 0 : podFac.hashCode());
		result = prime * result + ((pol == null) ? 0 : pol.hashCode());
		result = prime * result + ((polFac == null) ? 0 : polFac.hashCode());
		result = prime * result + ((prof == null) ? 0 : prof.hashCode());
		result = prime * result + ((prof2 == null) ? 0 : prof2.hashCode());
		result = prime * result + ((prof3 == null) ? 0 : prof3.hashCode());
		result = prime * result + transitTime;
		result = prime * result + ((ts1 == null) ? 0 : ts1.hashCode());
		result = prime * result + ((ts2 == null) ? 0 : ts2.hashCode());
		result = prime * result + ((ts3 == null) ? 0 : ts3.hashCode());
		result = prime * result + ((tsFac1 == null) ? 0 : tsFac1.hashCode());
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
		OceanRoute other = (OceanRoute) obj;
		if (pod == null) {
			if (other.pod != null)
				return false;
		} else if (!pod.equals(other.pod))
			return false;
		if (podFac == null) {
			if (other.podFac != null)
				return false;
		} else if (!podFac.equals(other.podFac))
			return false;
		if (pol == null) {
			if (other.pol != null)
				return false;
		} else if (!pol.equals(other.pol))
			return false;
		if (polFac == null) {
			if (other.polFac != null)
				return false;
		} else if (!polFac.equals(other.polFac))
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
		if (tsFac1 == null) {
			if (other.tsFac1 != null)
				return false;
		} else if (!tsFac1.equals(other.tsFac1))
			return false;
		return true;
	}

}
