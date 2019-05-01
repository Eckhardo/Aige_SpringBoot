package com.eki.oceanroute;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eki.model.OceanRoute;
import com.eki.model.RESTDateParam;
import com.eki.service.OceanRouteService;

@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders="*")
@RestController
public class OceanRouteController {

	private static final Logger logger = LoggerFactory.getLogger(OceanRouteController.class);

	@Autowired
	private OceanRouteService oceanRouteService;

	@GetMapping({ "/oceanroute/filter" })
	public List<OceanRoute> search(@RequestParam(value="includeInvalid", defaultValue = "false") boolean includeInvalid,
             @RequestParam(value="numberTs",defaultValue="1") String numberTs,
             @RequestParam(value="pol") String pol,
             @RequestParam(value="pod") String pod,
             @RequestParam(value="ts1") String ts1,
             @RequestParam(value="ts2") String ts2,
             @RequestParam(value="ts3") String ts3,
             @RequestParam(value="vs1") String vs1,
             @RequestParam(value="vs1") String vs2,
             @RequestParam(value="vs1") String vs3,
            @RequestParam(value="startDate") RESTDateParam startDate,
            @RequestParam(value="endDate") RESTDateParam endDate,
            @RequestParam(value = "page", required=false, defaultValue="0") int page
) {
		return  oceanRouteService.searchOceanRoutes(includeInvalid, numberTs, pol,
				pod, ts1, ts2, ts3, vs1 ,vs2,vs3, startDate,endDate,PageRequest.of(page, 5));
	
	}
}
