package com.eki.keyfigure;

import java.util.ArrayList;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.eki.model.GeoScope;
import com.eki.model.KeyFigure;

@Repository
@Transactional
public class KeyFigureDynamicQueryDao {

	private static final Logger logger = LoggerFactory.getLogger(KeyFigureDynamicQueryDao.class);

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

		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<KeyFigure> query = builder.createQuery(KeyFigure.class);
		final Root<KeyFigure> root = query.from(KeyFigure.class);
		Join<KeyFigure, GeoScope> from = root.join("from", JoinType.LEFT);
		Join<KeyFigure, GeoScope> to = root.join("to", JoinType.LEFT);

		String country = countryCode;
		if (StringUtils.isEmpty(countryCode)) {
			country = inlandLocation.substring(0, 2);
		}
		// A Predicate is an Expression
		List<Predicate> predicates = new ArrayList<>();
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
		Predicate preds = builder.and(predicates.toArray(new Predicate[predicates.size()]));
		query.select(root);
		query.where(preds);

		TypedQuery<KeyFigure> tq = entityManager.createQuery(query);
		System.out.println("# query:"+ query.toString());

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
		preferredPorts.stream().forEach((myPojo) -> {
			logger.warn(myPojo.toString());
		});

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

		Predicate preds = builder.and(predicates.toArray(new Predicate[predicates.size()]));
		cq.where(preds);

		TypedQuery<KeyFigure> query = entityManager.createQuery(cq);
		// Here you have to count the total size of the result
		int totalRows = query.getResultList().size();
		logger.warn("# of kfs: {}", totalRows);

		// Paging you don't want to access all entities of a given query but rather only
		// a page of them
		// (e.g. page 1 by a page size of 10). Right now this is addressed with two
		// integers that limit
		// the query appropriately.
		// (http://spring.io/blog/2011/02/10/getting-started-with-spring-data-jpa)
		query.setFirstResult(pageRequest.getPageNumber() * pageRequest.getPageSize());
		query.setMaxResults(pageRequest.getPageSize());

		PageImpl<KeyFigure> page = new PageImpl<KeyFigure>(query.getResultList(), pageRequest, totalRows);

		logger.warn("# of kfs in page: {}", page.getContent().size());

		return page;

	}

	public List<KeyFigure> searchKeyFiguresSimple(String inlandLocation, String countryCode, String inlandGeoScopeType,
			List<String> preferredPorts) {
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
				builder.equal(from.get("geoScopeType"), inlandGeoScopeType),
				builder.equal(from.get("locationCode"), inlandLocation), to.get("locationCode").in(preferredPorts)));
	

		Predicate preds = builder.and(predicates.toArray(new Predicate[predicates.size()]));
		cq.where(preds);

		TypedQuery<KeyFigure> query = entityManager.createQuery(cq);
	
		List<KeyFigure>  kfs = query.getResultList();

		logger.warn("# of kfs in page: {}", kfs.size());

		return kfs;
	}
}
