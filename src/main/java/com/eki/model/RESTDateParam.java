/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eki.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.eki.ResourceNotFoundException;

public class RESTDateParam {
 
    // Declare the date format for the parsing to be correct
    private SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd" );
    private java.sql.Date date;
 
    /**
     * This is the default constructor which must take in one string parameter.
     * The parameter is no other than the one passed in through the REST
     * end-point. We'll see it later...
     */
    public RESTDateParam( String dateStr ) throws ResourceNotFoundException {
        try {
            date = new java.sql.Date( df.parse( dateStr ).getTime() );
        } catch ( final ParseException ex ) {
            // Wrap up any expection as javax.ws.rs.WebApplicationException
            throw new  ResourceNotFoundException("keyfigure/filter", "date", dateStr) ;
        }
    }
 
    /**
     * This is a getter method which returns the parsed date string value as
     * java.sql.Date
     *
     */
    public java.sql.Date getDate() {
        return date;
    }
 
    /**
     * For convenience of result checking
     */
    @Override
    public String toString() {
        if ( date != null ) {
            return date.toString();
        } else {
            return "";
        }
    }
}