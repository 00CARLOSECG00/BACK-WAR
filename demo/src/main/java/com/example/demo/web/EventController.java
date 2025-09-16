package main.java.com.example.demo.web;


import com.example.war.dto.*;
import com.example.war.service.EventService;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api")
public class EventController {
  private final EventService svc;
  public EventController(EventService svc){ this.svc = svc; }

  @RequestMapping(value="/health", method={RequestMethod.HEAD, RequestMethod.GET})
  public void health(){}

  @GetMapping("/events")
  public PaginatedResponse<ConflictEvent> events(@RequestParam Map<String,String> q){
    return svc.page(q);
  }

  @GetMapping("/events/{id}")
  public ConflictEvent event(@PathVariable String id){
    return svc.one(id);
  }

  @GetMapping("/lookups")
  public Lookups lookups(){
    return svc.lookups();
  }

  @GetMapping("/stats/heat")
  public List<HeatCell> heat(@RequestParam Map<String,String> q){
    return svc.heat(q);
  }

  // Si HOY vas a usar PowerBI/BigQuery EMBEBIDO desde el front:
  // puedes mantener estos endpoints como fallback (PG) o devolver 204.
  @GetMapping("/stats/series")
  public List<TimePoint> series(@RequestParam Map<String,String> q){
    return svc.series(q); // o bien: throw new ResponseStatusException(HttpStatus.NO_CONTENT)
  }

  @GetMapping("/stats/by-region")
  public List<RegionAgg> byRegion(@RequestParam Map<String,String> q){
    return svc.byRegion(q); // idem comentario
  }
}
