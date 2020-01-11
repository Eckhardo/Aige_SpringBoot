package com.eki.play.java8.poc;

import java.util.List;

public class PolCalculator<E extends LegComparable> extends AbstractCalculator<E> {

	@Override
	public String calculate(List<E> list) {
		
		return calculateSetup(list.get(0).getName(), list.get(0).getType());
	}


}
