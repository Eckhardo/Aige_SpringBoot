package com.eki.play.java8.poc;

import java.util.List;

public abstract class AbstractCalculator<E extends LegComparable> implements Calculable<E> {

	@Override
	public abstract String calculate(List<E> list);


	 String calculateSetup(String e, String f) {
		
		return  e + f;
	}

}
