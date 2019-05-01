package com.eki.oceanroute;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.eki.model.OceanRoute;

@Repository
@Transactional
public class OceanRouteDynamicQueryDao {

	private static final Logger logger = LoggerFactory.getLogger(OceanRouteDynamicQueryDao.class);

	@PersistenceContext
	private EntityManager entityManager;


	public List<OceanRoute> saveAll(Iterable<OceanRoute> orList) {
		Assert.notNull(orList, "The given Iterable of entities not be null!");

		List<OceanRoute> result = new ArrayList<>();

		for (OceanRoute or : orList) {
			entityManager.persist(or);
			result.add(or);

		}

		return result;

	}
}
