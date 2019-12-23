/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eki.shipment.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.CollectionTable;

import javax.persistence.JoinColumn;
/**
 * OceanRoute [transitTime=P64D, routing=BRSSZ(565151) --> PACTB(1632414) -->
 * HKHKG(564077)] (52010SIM1917(842225),77030SIM0007(803165)) - ERRORS: []
 *
 * @author ekirschning
 */

@Entity
@Table(name = "oceanroute")

public class OceanRoute implements Serializable {

	private static final long serialVersionUID = -7182994144556273088L;

	public OceanRoute() {
		super();
	}

	public OceanRoute(String pol, String pod, String ts1, List<String> errors) {
		this.pol = pol;
		this.pod = pod;
		this.ts1=ts1;
		this.errors=errors;
	
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
	private long id;

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

	@ElementCollection(fetch=FetchType.EAGER)
    @CollectionTable(name="ocean_route_errors", joinColumns=@JoinColumn(name="ocean_route_id"))
    @Column(name="errors")
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
}
