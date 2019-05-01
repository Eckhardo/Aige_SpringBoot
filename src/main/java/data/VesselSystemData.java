/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.util.ArrayList;
import java.util.List;

import com.eki.model.VesselSystemModel;

/**
 *
 * @author ekirschning
 */
public class VesselSystemData {
   
    public static List<VesselSystemModel> getData() {
        List<VesselSystemModel> vesselSystems = new ArrayList<>();

        vesselSystems.add(new VesselSystemModel(1, "BEX", "BRAZIL EXPRESS"));
        vesselSystems.add(new VesselSystemModel(2, "RPEX", "RIVER PLATE EXPRESS"));
        vesselSystems.add(new VesselSystemModel(3, "MAE1", "MAERSK1"));
        vesselSystems.add(new VesselSystemModel(4, "MAE2", "MAERSK2"));
        vesselSystems.add(new VesselSystemModel(4, "MAE3", "MAERSK3"));
        
    
        return vesselSystems;

    } 
}
