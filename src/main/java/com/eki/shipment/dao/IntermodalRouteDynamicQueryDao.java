package com.eki.shipment.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.hibernate.annotations.NamedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.eki.shipment.model.GeoScope;
import com.eki.shipment.model.KeyFigure;

@Repository
@Transactional

public class IntermodalRouteDynamicQueryDao {

	private static final Logger logger = LoggerFactory.getLogger(IntermodalRouteDynamicQueryDao.class);

	@PersistenceContext
	private EntityManager entityManager;

	public List<KeyFigure> saveAll(Iterable<KeyFigure> keyFigures) {
		Assert.notNull(keyFigures, "The given Iterable of entities not be null!");

		List<KeyFigure> result = new ArrayList<>();

		for (KeyFigure entity : keyFigures) {
			entityManager.persist(entity);
			result.add(entity);

		}

		return result;

	}

	public List<KeyFigure> searchKeyFigures(String inlandLocation, String countryCode, String geoScopeType,
			List<String> preferredPorts, boolean eq20, boolean eq40, String tpMode, String eqGroup) {

		final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		final CriteriaQuery<KeyFigure> cq = cb.createQuery(KeyFigure.class);
		final Root<KeyFigure> keyFigure = cq.from(KeyFigure.class);
		Join<KeyFigure, GeoScope> from = keyFigure.join("from", JoinType.INNER);
		Join<KeyFigure, GeoScope> to = keyFigure.join("to", JoinType.INNER);

		String country = countryCode;
		if (StringUtils.isEmpty(countryCode)) {
			country = inlandLocation.substring(0, 2);
		}
		// A Predicate is an Expression
		List<Predicate> predicates = new ArrayList<>();
		Predicate pCountry = cb.equal(from.get("countryCode"), country);
		Predicate pType = cb.equal(from.get("geoScopeType"), geoScopeType);
		Predicate pInland = cb.equal(from.get("locationCode"), inlandLocation);
		Predicate pAnd = cb.and(pCountry, pType, pInland);
		predicates.add(pAnd);
		Predicate p = buildEquSizeExpression(eq20, eq40, cb, keyFigure);
		if (Optional.ofNullable(p).isPresent()) {
			predicates.add(p);
		}
		if (!preferredPorts.isEmpty()) {
			Predicate ports = to.get("locationCode").in(preferredPorts);
			predicates.add(ports);
		}
		if (!StringUtils.isEmpty(tpMode)) {
			predicates.add(cb.and(cb.equal(keyFigure.get("transportMode"), tpMode)));

		}
		if (!StringUtils.isEmpty(eqGroup)) {
			predicates.add(cb.and(cb.equal(keyFigure.get("equipmentGroup"), eqGroup)));

		}
		Predicate finalPredicate = cb.and(predicates.toArray(new Predicate[predicates.size()]));
		cq.select(keyFigure);
		cq.where(finalPredicate);

		TypedQuery<KeyFigure> tq = entityManager.createQuery(cq);

		return tq.getResultList();

	}

	private Predicate buildEquSizeExpression(boolean eq20, boolean eq40, final CriteriaBuilder builder,
			final Root<KeyFigure> root) {
		Predicate p = null;
		if (eq20 && eq40) {
			p = root.get("equipmentSize").in("20", "40");
		} else if (eq20) {
			p = builder.and(builder.equal(root.get("equipmentSize"), "20"));
		} else if (eq40) {
			p = builder.and(builder.equal(root.get("equipmentSize"), "40"));
		}
		return p;
	}

	public Page<KeyFigure> searchPageableKeyFigures(String inlandLocation, String countryCode, String geoScopeType,
			List<String> preferredPorts, boolean eq20, boolean eq40, String tpMode, String eqGroup,
			PageRequest pageRequest) {

		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<KeyFigure> cq = builder.createQuery(KeyFigure.class);
		final Root<KeyFigure> root = cq.from(KeyFigure.class);
		Join<KeyFigure, GeoScope> from = root.join("from", JoinType.LEFT);
		Join<KeyFigure, GeoScope> to = root.join("to", JoinType.LEFT);
		cq.select(root);
		List<Predicate> predicates = new ArrayList<>();
		String country = countryCode;
		if (StringUtils.isEmpty(countryCode)) {
			country = inlandLocation.substring(0, 2);
		}

		predicates.add(builder.and(builder.equal(from.get("countryCode"), country),
				builder.equal(from.get("geoScopeType"), geoScopeType),
				builder.equal(from.get("locationCode"), inlandLocation), to.get("locationCode").in(preferredPorts)));
		Predicate p = buildEquSizeExpression(eq20, eq40, builder, root);
		if (Optional.ofNullable(p).isPresent()) {
			predicates.add(p);
		}
		if (Optional.ofNullable(tpMode).isPresent()) {
			predicates.add(builder.and(builder.equal(root.get("transportMode"), tpMode)));

		}
		if (Optional.ofNullable(eqGroup).isPresent()) {
			predicates.add(builder.and(builder.equal(root.get("equipmentGroup"), eqGroup)));

		}

		Predicate finalPredicate = builder.and(predicates.toArray(new Predicate[predicates.size()]));
		cq.where(finalPredicate);

		TypedQuery<KeyFigure> query = entityManager.createQuery(cq);
		// Here you have to count the total size of the result
		int totalRows = query.getResultList().size();

		// Paging you don't want to access all entities of a given query but rather only
		// a page of them
		// (e.g. page 1 by a page size of 10). Right now this is addressed with two
		// integers that limit
		// the query appropriately.
		// (http://spring.io/blog/2011/02/10/getting-started-with-spring-data-jpa)
		query.setFirstResult(pageRequest.getPageNumber() * pageRequest.getPageSize());
		query.setMaxResults(pageRequest.getPageSize());

		PageImpl<KeyFigure> page = new PageImpl<KeyFigure>(query.getResultList(), pageRequest, totalRows);

		return page;

	}

	public List<KeyFigure> searchKeyFiguresJpa(String inlandLocation, String countryCode, String inlandGeoScopeType,
			List<String> preferredPorts) {

		StringBuilder builder = new StringBuilder();
		builder.append("SELECT kf FROM KeyFigure kf ");
		builder.append("JOIN kf.from f ");
		builder.append("JOIN kf.to t ");
		builder.append("WHERE ");
		builder.append("f.countryCode=(:countryCode) ");
		builder.append("AND f.locationCode=(:inlandLocation) ");
		builder.append("AND f.geoScopeType=(:inlandType) ");
		builder.append("and t.locationCode IN(:prefPorts)");

		TypedQuery<KeyFigure> query = entityManager.createQuery(builder.toString(), KeyFigure.class);
		query.setParameter("inlandLocation", inlandLocation);
		query.setParameter("countryCode", countryCode);
		query.setParameter("inlandType", inlandGeoScopeType);
		query.setParameter("prefPorts", preferredPorts);

		return query.getResultList();

	}

	public List<KeyFigure> searchKeyFiguresNamedJpa(String inlandLocation, List<String> preferredPorts) {
		TypedQuery<KeyFigure> query = entityManager.createNamedQuery("KeyFigure_findByInlandLocationAndPreferredPorts",
				KeyFigure.class);

		query.setParameter("inlandLocation", inlandLocation);
		query.setParameter("prefPorts", preferredPorts);

		return query.getResultList();

	}

	public List<KeyFigure> searchKeyFiguresSimple(String inlandLocation, String countryCode, String inlandGeoScopeType,
			List<String> preferredPorts) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<KeyFigure> query = builder.createQuery(KeyFigure.class);
		final Root<KeyFigure> root = query.from(KeyFigure.class);
		Join<KeyFigure, GeoScope> from = root.join("from", JoinType.LEFT);
		Join<KeyFigure, GeoScope> to = root.join("to", JoinType.LEFT);
		List<Predicate> predicates = new ArrayList<>();
		String country = countryCode;
		if (StringUtils.isEmpty(countryCode)) {
			country = inlandLocation.substring(0, 2);
		}

		predicates.add(builder.and(builder.equal(from.get("countryCode"), country),
				builder.equal(from.get("geoScopeType"), inlandGeoScopeType),
				builder.equal(from.get("locationCode"), inlandLocation), to.get("locationCode").in(preferredPorts)));

		Predicate preds = builder.and(predicates.toArray(new Predicate[predicates.size()]));
		query.select(root);

		query.where(preds);

		TypedQuery<KeyFigure> typedQuery = entityManager.createQuery(query);

		List<KeyFigure> kfs = typedQuery.getResultList();

		logger.trace("# of kfs in page: {}", kfs.size());

		return kfs;
	}
}
