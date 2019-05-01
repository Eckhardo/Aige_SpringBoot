/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import com.eki.model.MissingRoutingsModel;

/**
 *
 * @author ekirschning
 */
public class MissingRoutingsData {

    public static List<MissingRoutingsModel> getData() {

        LocalDate validFrom = LocalDate.of(2018, 1, 1);
        LocalDate validTo = LocalDate.of(2018,12,31);

        MissingRoutingsModel mr1 = new MissingRoutingsModel("CNDLC", "", "KRPUS", "", "MYTPP", "", "", "", "NLRTM", "ANCC7000396", validFrom, validTo, "C", "NERA FAK", "100128685", "M+RFORWHKHKG", "EANEU", "00102", "MAX_ADD_TS_EXCEEDED", "The routing requires 2 transshipments which exceeds the maximum number of transshipments of 1.");
        MissingRoutingsModel mr2 = new MissingRoutingsModel("CNDLC", "", "KRPUS", "", "MYTPP", "", "", "", "NLRTM", "ANCC7000404", validFrom, validTo, "C", "MORRISON-BUNDLE NAC", "100146268", "MORRISOCNSHA", "EANEU", "00102", "MAX_ADD_TS_EXCEEDED", "The routing requires 2 transshipments which exceeds the maximum number of transshipments of 1.");
        MissingRoutingsModel mr3 = new MissingRoutingsModel("CNDLC", "", "KRPUS", "", "MYTPP", "", "", "", "NLRTM", "ANCC7000404", validFrom, validTo, "C", "MORRISON-BUNDLE NAC", "100146268", "M+MORRISOCNSHA", "EANEU", "00074", "MAX_ADD_TS_EXCEEDED", "The routing requires 2 transshipments which exceeds the maximum number of transshipments of 1.");
        MissingRoutingsModel mr4 = new MissingRoutingsModel("CNDLC", "", "KRPUS", "", "MYTPP", "", "", "", "NLRTM", "ANCC7000404", validFrom, validTo, "C", "MORRISON-BUNDLE NAC", "100146268", "M+MORRISOCNSHA", "EANEU", "00075", "MAX_ADD_TS_EXCEEDED", "The routing requires 2 transshipments which exceeds the maximum number of transshipments of 1.");
        MissingRoutingsModel mr5 = new MissingRoutingsModel("CNDLC", "", "KRPUS", "", "MYTPP", "", "", "", "NLRTM", "ANCC7000404", validFrom, validTo, "C", "MORRISON-BUNDLE NAC", "100146268", "MORRISOCNSHA", "EANEU", "00001", "MAX_ADD_TS_EXCEEDED", "The routing requires 2 transshipments which exceeds the maximum number of transshipments of 1.");
        MissingRoutingsModel mr6 = new MissingRoutingsModel("CNDLC", "", "KRPUS", "", "MYTPP", "", "", "", "NLRTM", "ANCC7000404", validFrom, validTo, "C", "MORRISON-BUNDLE NAC", "100146268", "M+MORRISOCNSHA", "EANEU", "00006", "MAX_ADD_TS_EXCEEDED", "The routing requires 2 transshipments which exceeds the maximum number of transshipments of 1.");
        MissingRoutingsModel mr7 = new MissingRoutingsModel("CNDLC", "", "KRPUS", "", "MYTPP", "", "", "", "NLRTM", "ANCC7000404", validFrom, validTo, "C", "MORRISON-BUNDLE NAC", "100146268", "M+MORRISOCNSHA", "EANEU", "00007", "MAX_ADD_TS_EXCEEDED", "The routing requires 2 transshipments which exceeds the maximum number of transshipments of 1.");
        MissingRoutingsModel mr8 = new MissingRoutingsModel("CNCWN", "", "", "", "", "", "", "", "PECLL", "ANCC5000011", validFrom, validTo, "C", "DELFIN (NAC) ZHONGCHENG & DOFI & COMMERCIAL AMERICA & TYRE", "100127603", "SHAN117CNSHA", "EASAW", "00497", "NVPRF", "No services available from port CNCWN with ID 11379.");
        MissingRoutingsModel mr9 = new MissingRoutingsModel("CNCWN", "", "", "", "", "", "", "", "PECLL", "ANCC5000011", validFrom, validTo, "C", "DELFIN (NAC) ZHONGCHENG & DOFI & COMMERCIAL AMERICA & TYRE", "100127603", "SHAN117CNSHA", "EASAW", "00498", "NVPRF", "No services available from port CNCWN with ID 11379.");
        MissingRoutingsModel mr10 = new MissingRoutingsModel("CNCWN", "", "", "", "", "", "", "", "PECLL", "ANCC5000011", validFrom, validTo, "C", "DELFIN (NAC) ZHONGCHENG & DOFI & COMMERCIAL AMERICA & TYRE", "100127603", "SHAN117CNSHA", "EASAW", "00499", "NVPRF", "No services available from port CNCWN with ID 11379.");
        MissingRoutingsModel mr11 = new MissingRoutingsModel("CNCWN", "", "", "", "", "", "", "", "PAMIT", "ANCC7000124", validFrom, validTo, "C", "NAC-ALL AMERICAN CONTAINER", "100128595", "BONVOYACNSHA", "EACCA", "00255", "NVPRF", "No services available from port CNCWN with ID 11379.");
        MissingRoutingsModel mr12 = new MissingRoutingsModel("CNCWN", "", "", "", "", "", "", "", "PAMIT", "ANCC7000124", validFrom, validTo, "C", "NAC-ALL AMERICAN CONTAINER", "100128595", "BONVOYACNSHA", "EACCA", "00256", "NVPRF", "No services available from port CNCWN with ID 11379.");
        MissingRoutingsModel mr13 = new MissingRoutingsModel("CNCWN", "", "", "", "", "", "", "", "PAMIT", "ANCC7000124", validFrom, validTo, "C", "NAC-ALL AMERICAN CONTAINER", "100128595", "BONVOYACNSHA", "EACCA", "00257", "NVPRF", "No services available from port CNCWN with ID 11379.");
        MissingRoutingsModel mr14 = new MissingRoutingsModel("CNCWN", "", "", "", "", "", "", "", "PAMIT", "ANCC7000124", validFrom, validTo, "C", "NAC-ALL AMERICAN CONTAINER", "100128595", "BONVOYACNSHA", "EACCA", "00271", "NVPRF", "No services available from port CNCWN with ID 11379.");
        MissingRoutingsModel mr15 = new MissingRoutingsModel("CNCWN", "", "", "", "", "", "", "", "PAMIT", "ANCC7000124", validFrom, validTo, "C", "NAC-ALL AMERICAN CONTAINER", "100128595", "BONVOYACNSHA", "EACCA", "00272", "NVPRF", "No services available from port CNCWN with ID 11379.");
        MissingRoutingsModel mr16 = new MissingRoutingsModel("CNCWN", "", "", "", "", "", "", "", "PAMIT", "ANCC7000124", validFrom, validTo, "C", "NAC-ALL AMERICAN CONTAINER", "100128595", "BONVOYACNSHA", "EACCA", "00273", "NVPRF", "No services available from port CNCWN with ID 11379.");
     MissingRoutingsModel mr17 = new MissingRoutingsModel("CNCWN", "", "", "", "", "", "", "", "PAMIT", "ANCC7000132", validFrom, validTo, "C", "NAC-ALL AMERICAN CONTAINER", "100128596", "GELOYCNSHA", "EACCA", "00001", "NVPRF", "No services available from port CNCWN with ID 11379.");
        MissingRoutingsModel mr18 = new MissingRoutingsModel("CNCWN", "", "", "", "", "", "", "", "PAMIT", "ANCC7000145", validFrom, validTo, "C", "NAC-ALL AMERICAN CONTAINER", "100128597", "LONOCNSHA", "EACCA", "00002", "NVPRF", "No services available from port CNCWN with ID 11379.");
        MissingRoutingsModel mr19 = new MissingRoutingsModel("CNCWN", "", "", "", "", "", "", "", "PAMIT", "ANCC7000167", validFrom, validTo, "C", "NAC-ALL AMERICAN CONTAINER", "100128598", "SHINTACNSHA", "EACCA", "0011", "NVPRF", "No services available from port CNCWN with ID 11379.");

        return Arrays.asList(mr1, mr2, mr3, mr4, mr5, mr6, mr7, mr8, mr9, mr10, mr11,mr12,mr13,mr14,mr15,mr16,mr17,mr18,mr19);
    }
}
