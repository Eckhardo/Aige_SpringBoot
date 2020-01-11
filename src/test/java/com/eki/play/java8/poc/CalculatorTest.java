package com.eki.play.java8.poc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class CalculatorTest {

	@Test
	public void calculatePol() {
		// given
		List<LegComparable> list = new ArrayList<LegComparable>();
		list.add(new Leg("ocean", "shunting"));
		Calculable<LegComparable> polCalcualtor = new PolCalculator<>();
		String result = polCalcualtor.calculate(list);
		assertThat(result, is("oceanshunting"));
	}
}
