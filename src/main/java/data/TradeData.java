/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.util.ArrayList;
import java.util.List;

import com.eki.shipment.model.Trade;

/**
 *
 * @author ekirschning
 */
public class TradeData {
     public static List<Trade> getTrades() {
        List<Trade> trades = new ArrayList<>();
     trades.add(new Trade("", "EACCA"));
        trades.add(new Trade("", "EANEU"));
        trades.add(new Trade("", "NACCA"));
        trades.add(new Trade("", "NECCA"));
        trades.add(new Trade("", "MESEC"));
        trades.add(new Trade("", "EASAW"));
  return trades;
     }
}
