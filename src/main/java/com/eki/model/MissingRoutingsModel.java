/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eki.model;

import java.time.LocalDate;

/**
 *
 * @author ekirschning
 */
public class MissingRoutingsModel {

    private final String POL;
    private final String SYSTEM1;
    private final String TS1;
    private final String SYSTEM2;
    private final String TS2;
    private final String SYSTEM3;
    private final String TS3;
    private final String SYSTEM4;
    private final String POD;
    private final String AGREEMENT_NO;
    private final LocalDate VALID_FROM;
    private final LocalDate VALID_TO;
    private final String CONTRACT_TYPE;
    private final String REF_NO;
    private final String PARTNER_NUMBER;
    private final String PARTNER_CODE;
    private final String TRADE;
    private final String PRODUCT_SEQNO;
    private final String REASON_CODE;
    private final String EXPLANATION;

    public MissingRoutingsModel(String POL, String SYSTEM1, String TS1, 
            String SYSTEM2, String TS2, String SYSTEM3, 
            String TS3, String SYSTEM4, String POD, 
            String AGREEMENT_NO, LocalDate VALID_FROM, LocalDate VALID_TO, 
            String CONTRACT_TYPE, String REF_NO, String PARTNER_NUMBER, 
            String PARTNER_CODE, String TRADE, String PRODUCT_SEQNO, 
            String REASON_CODE, String explanation) {
        this.POL = POL;
        this.SYSTEM1 = SYSTEM1;
        this.TS1 = TS1;
        this.SYSTEM2 = SYSTEM2;
        this.TS2 = TS2;
        this.SYSTEM3 = SYSTEM3;
        this.TS3 = TS3;
        this.SYSTEM4 = SYSTEM4;
        this.POD = POD;
        this.AGREEMENT_NO = AGREEMENT_NO;
        this.VALID_FROM = VALID_FROM;
        this.VALID_TO = VALID_TO;
        this.CONTRACT_TYPE = CONTRACT_TYPE;
        this.REF_NO = REF_NO;
        this.PARTNER_NUMBER = PARTNER_NUMBER;
        this.PARTNER_CODE = PARTNER_CODE;
        this.TRADE = TRADE;
        this.PRODUCT_SEQNO = PRODUCT_SEQNO;
        this.REASON_CODE = REASON_CODE;
        this.EXPLANATION =explanation;
    }

    public String getPOL() {
        return POL;
    }

    public String getSYSTEM1() {
        return SYSTEM1;
    }

    public String getTS1() {
        return TS1;
    }

    public String getSYSTEM2() {
        return SYSTEM2;
    }

    public String getTS2() {
        return TS2;
    }

    public String getSYSTEM3() {
        return SYSTEM3;
    }

    public String getTS3() {
        return TS3;
    }

    public String getSYSTEM4() {
        return SYSTEM4;
    }

    public String getPOD() {
        return POD;
    }

    public String getAGREEMENT_NO() {
        return AGREEMENT_NO;
    }

    public LocalDate getVALID_FROM() {
        return VALID_FROM;
    }

    public LocalDate getVALID_TO() {
        return VALID_TO;
    }

    public String getCONTRACT_TYPE() {
        return CONTRACT_TYPE;
    }

    public String getREF_NO() {
        return REF_NO;
    }

    public String getPARTNER_NUMBER() {
        return PARTNER_NUMBER;
    }

    public String getPARTNER_CODE() {
        return PARTNER_CODE;
    }

    public String getTRADE() {
        return TRADE;
    }

    public String getPRODUCT_SEQNO() {
        return PRODUCT_SEQNO;
    }

    public String getREASON_CODE() {
        return REASON_CODE;
    }

    public String getEXPLANATION() {
        return EXPLANATION;
    }

}
