package com.eki.keyfigure;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Parameter;
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
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.eki.model.GeoScope;
import com.eki.model.KeyFigure;
import com.eki.model.SearchCriteria;
import com.eki.service.KeyFigureService;

@Repository
@Transactional
public class KeyFigureDynamicQueryDao {

	private static final Logger logger = LoggerFactory.getLogger(KeyFigureDynamicQueryDao.class);

	@PersistenceContext
	private EntityManager entityManager;

	public List<KeyFigure> searchKeyFigures(List<SearchCriteria> params) {

		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<KeyFigure> query = builder.createQuery(KeyFigure.class);
		final Root<KeyFigure> r = query.from(KeyFigure.class);
		Predicate predicate = builder.conjunction();

		for (SearchCriteria param : params) {
			if (param.getOperation().equalsIgnoreCase(">")) {
				predicate = builder.and(predicate,
						builder.greaterThanOrEqualTo(r.get(param.getKey()), param.getValue().toString()));
			} else if (param.getOperation().equalsIgnoreCase("<")) {
				predicate = builder.and(predicate,
						builder.lessThanOrEqualTo(r.get(param.getKey()), param.getValue().toString()));
			} else if (param.getOperation().equalsIgnoreCase(":")) {
				if (r.get(param.getKey()).getJavaType() == String.class) {
					predicate = builder.and(predicate,
							builder.like(r.get(param.getKey()), "%" + param.getValue() + "%"));
				} else {
					predicate = builder.and(predicate, builder.equal(r.get(param.getKey()), param.getValue()));
				}
			}
		}
		query.where(predicate);
		
	
		List<KeyFigure> result = entityManager.createQuery(query).getResultList();
		return result;
	}

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
			List<String> preferredPorts, boolean eq20, boolean eq40, String tpMode) {
		logger.warn("params:");
		logger.warn("inlandLocation: {}", inlandLocation);
		logger.warn("country: {}", countryCode);
		logger.warn("type: {}", geoScopeType);
		logger.warn("tpMode: {}", tpMode);
		preferredPorts.stream().forEach((myPojo) -> {logger.warn(myPojo.toString());});
		logger.warn("eq20: {}", eq20);
		logger.warn("eq40: {}", eq40);

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
				builder.equal(from.get("locationCode"), inlandLocation), 
				to.get("locationCode").in(preferredPorts)));
		if (eq20 && eq40) {
			predicates.add(root.get("equipmentSize").in("20", "40"));
		}
		else if (eq20) {
			predicates.add(builder.and(builder.equal(root.get("equipmentSize"), "20")));
		}
		else if (eq40) {
			predicates.add(builder.and(builder.equal(root.get("equipmentSize"), "40")));
		}
		 if (Optional.ofNullable(tpMode).isPresent()) {
			predicates.add(builder.and(builder.equal(root.get("transportMode"), tpMode)));

		}
		Predicate preds = builder.and(predicates.toArray(new Predicate[predicates.size()]));
		cq.where(preds);

		TypedQuery<KeyFigure> tq = entityManager.createQuery(cq);

		return tq.getResultList();

	}
}
