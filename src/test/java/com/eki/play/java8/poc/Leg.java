package com.eki.play.java8.poc;

public class Leg implements LegComparable {
	
	private String name;
	
	private String type;

	public Leg(String name, String type) {
		super();
		this.name = name;
		this.type = type;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getType() {
	return type;
	}

}
