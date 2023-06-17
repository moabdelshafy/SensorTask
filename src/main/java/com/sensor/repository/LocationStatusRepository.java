package com.sensor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sensor.entity.LocationStatus;

@Repository
public interface LocationStatusRepository extends JpaRepository<LocationStatus, Long> {

	public LocationStatus findByLocationId(Long locationId);
}
