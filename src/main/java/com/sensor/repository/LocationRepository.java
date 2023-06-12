package com.sensor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sensor.entity.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long>{

}
