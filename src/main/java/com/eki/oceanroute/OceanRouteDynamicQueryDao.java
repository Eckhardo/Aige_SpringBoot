package com.eki.oceanroute;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.hibernate.annotations.FetchMode;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.eki.model.GeoScope;
import com.eki.model.KeyFigure;
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
	
	public List<OceanRoute> findRoutes(String pol, String pod, String ts1, String ts2, String ts3, boolean isShunting){
		 Assert.notNull(pol, "pol mustn't be null");
		 Assert.notNull(pod, "pod mustn't be null");
		final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		final CriteriaQuery<OceanRoute> cq = cb.createQuery(OceanRoute.class);
	
		final Root<OceanRoute> route = cq.from(OceanRoute.class);
	
//	route.join("errors",JoinType.LEFT);
			List<Predicate> predicates=new ArrayList<>();
		Predicate pPol = cb.equal(route.get("pol"), pol);
		Predicate pPod = cb.equal(route.get("pod"), pod);
		Predicate pAnd = cb.and(pPol, pPod);
		predicates.add(pAnd);
		
		if(! StringUtils.isEmpty(ts1)) {
			Predicate pTs1=cb.equal(route.get("ts1"), ts1);
			predicates.add(pTs1);
		}

		if(! StringUtils.isEmpty(ts2)) {
			Predicate pTs2=cb.equal(route.get("ts2"), ts2);
			predicates.add(pTs2);
		}

		if(isShunting) {
			Predicate pShuntingNotNull = cb.and(cb.notEqual(route.get("ts1"), ""),cb.notEqual(route.get("ts2"), ""));
			Predicate pShunting = cb.and(cb.equal(route.get("ts1"),route.get("ts2")));
			Predicate pAndShunting = cb.and(pShuntingNotNull, pShunting);
			predicates.add(pAndShunting);
		}

		Predicate finalPredicate = cb.and(predicates.toArray(new Predicate[predicates.size()]));
	
		cq.select(route);
		cq.where(finalPredicate);
		TypedQuery<OceanRoute> tq = entityManager.createQuery(cq);

		return tq.getResultList();
	
	}
}
