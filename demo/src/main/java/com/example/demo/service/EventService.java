package main.java.com.example.demo.service;

import com.example.war.dto.*;
import com.example.war.repo.EventRepository;
import org.springframework.stereotype.Service;
import java.util.List; import java.util.Map;

@Service
public class EventService {
  private final EventRepository repo;
  public EventService(EventRepository repo){ this.repo = repo; }

  public PaginatedResponse<ConflictEvent> page(Map<String,String> q){ return repo.pageEvents(q); }
  public ConflictEvent one(String id){ return repo.getById(id); }
  public Lookups lookups(){ return repo.lookups(); }
  public List<HeatCell> heat(Map<String,String> q){ return repo.heat(q); }

  // Fallback (si no usas PowerBI/BigQuery desde el front)
  public List<TimePoint> series(Map<String,String> q){ return repo.series(q); }
  public List<RegionAgg> byRegion(Map<String,String> q){ return repo.byRegion(q); }
}
