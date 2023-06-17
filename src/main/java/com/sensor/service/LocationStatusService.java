package com.sensor.service;

import java.util.List;
import com.sensor.entity.LocationStatus;

public interface LocationStatusService {
	public List<LocationStatus> findAll();
	public void addLocationStatus(LocationStatus locationStatus);
	public LocationStatus findByLocationId(Long locationId);
}
