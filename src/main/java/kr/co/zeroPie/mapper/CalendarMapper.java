package kr.co.zeroPie.mapper;

import kr.co.zeroPie.dto.CalendarDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CalendarMapper {
    public void updateEvent(@Param("stfNo") String stfNo, @Param("calendarDTO") CalendarDTO calendarDTO);
}