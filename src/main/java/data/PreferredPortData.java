/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.util.ArrayList;
import java.util.List;

import com.eki.shipment.model.PreferredPort;

/**
 *
 * @author ekirschning
 */
public class PreferredPortData {

    public static List<PreferredPort> getData() {
        List<PreferredPort> geoscopes = new ArrayList<>();

        geoscopes.add(new com.eki.shipment.model.PreferredPort(1, "L", "DEBRV"));
        geoscopes.add(new PreferredPort(2, "L", "DEHAM"));
        geoscopes.add(new PreferredPort(3, "L", "NLRTM"));
        geoscopes.add(new PreferredPort(4, "L", "BEANR"));
        geoscopes.add(new PreferredPort(4, "L", "PLGDY"));

        geoscopes.add(new PreferredPort(7, "L", "BRSSZ"));
        geoscopes.add(new PreferredPort(8, "L", "BRMAO"));
        geoscopes.add(new PreferredPort(9, "L", "BRRIO"));
        geoscopes.add(new PreferredPort(10, "L", "BRSUA"));

        geoscopes.add(new PreferredPort(11, "L", "CNDLC"));
        geoscopes.add(new PreferredPort(12, "L", "CNCWN"));
        geoscopes.add(new PreferredPort(13, "L", "CNHKG"));
        geoscopes.add(new PreferredPort(14, "L", "CNSHG"));
        
        geoscopes.add(new PreferredPort(15, "L", "PAMIT"));
        geoscopes.add(new PreferredPort(16, "L", "PAPAC"));
        geoscopes.add(new PreferredPort(17, "L", "PAPTY"));
        geoscopes.add(new PreferredPort(18, "L", "PAPES"));

        geoscopes.add(new PreferredPort(19, "L", "KRPUS"));
        geoscopes.add(new PreferredPort(20, "L", "PECLL"));
        geoscopes.add(new PreferredPort(21, "L", "PECAB"));
        geoscopes.add(new PreferredPort(22, "L", "PECHY"));
        geoscopes.add(new PreferredPort(19, "L", "HKHKG"));

        return geoscopes;

    }
}
