package com.eki.geoscope;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eki.model.GeoScope;

public interface TestRepository extends JpaRepository<GeoScope, Long> {}
