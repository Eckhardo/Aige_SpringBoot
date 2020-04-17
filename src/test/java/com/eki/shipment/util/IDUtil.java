package com.eki.shipment.util;

import java.util.Random;

/**
 * A utility class for the generation of randomly created IDs.
 * 
 * @author eckha
 *
 */
public class IDUtil {
	   private IDUtil() {
	        throw new AssertionError();
	    }

	    // API

	    public final static String randomPositiveLongAsString() {
	        return Long.toString(randomPositiveLong());
	    }

	    public final static String randomNegativeLongAsString() {
	        return Long.toString(randomNegativeLong());
	    }

	    public final static long randomPositiveLong() {
	        long id = new Random().nextLong() * 10000;
	        id = (id < 0) ? (-1 * id) : id;
	        return id;
	    }
	    public final static long randomPositiveInteger() {
	        int id = new Random().nextInt() * 1000;
	        id = (id < 0) ? (-1 * id) : id;
	        return id;
	    }

	    public final static long randomNegativeLong() {
	        long id = new Random().nextLong() * 10000;
	        id = (id > 0) ? (-1 * id) : id;
	        return id;
	    }

}
