package kr.co.zeroPie.repository.custom;

import kr.co.zeroPie.entity.Events;

import java.util.List;

public interface EventsRepositoryCustom {
    public List<Events> getEvents(String stfNo);
}
