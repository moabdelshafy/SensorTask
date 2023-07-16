package com.sensor.mapper;

import com.sensor.dto.LocationDTO;
import com.sensor.entity.Location;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface LocationMapper {


    LocationDTO mapToDto(Location location);

    Location mapToEntity(LocationDTO locationDTO);

    Location mapToEntity(LocationDTO locationDTO, @MappingTarget Location location);
}
