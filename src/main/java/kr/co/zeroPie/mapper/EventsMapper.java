package kr.co.zeroPie.mapper;

import kr.co.zeroPie.dto.EventsDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EventsMapper {
    public void updateEvent(@Param("stfNo") String stfNo, @Param("eventsDTO") EventsDTO eventsDTO);
}