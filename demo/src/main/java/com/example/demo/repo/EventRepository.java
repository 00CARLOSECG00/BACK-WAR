package main.java.com.example.demo.repo;



import com.example.war.dto.*;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.*;

@Repository
public class EventRepository {
  private final JdbcTemplate jdbc;
  public EventRepository(JdbcTemplate jdbc){ this.jdbc = jdbc; }

  // Mappers
  private ConflictEvent mapEvent(ResultSet rs, int rn) throws java.sql.SQLException {
    ConflictEvent e = new ConflictEvent();
    e.id = rs.getString("id");
    e.relid = rs.getString("relid");
    e.year = (Integer)rs.getObject("year");
    e.active_year = (Boolean)rs.getObject("active_year");
    e.code_status = rs.getString("code_status");
    e.type_of_violence = (Integer)rs.getObject("type_of_violence");
    e.conflict_dset_id = (Integer)rs.getObject("conflict_dset_id");
    e.conflict_new_id = (Integer)rs.getObject("conflict_new_id");
    e.conflict_name = rs.getString("conflict_name");
    e.dyad_dset_id = (Integer)rs.getObject("dyad_dset_id");
    e.dyad_new_id = (Integer)rs.getObject("dyad_new_id");
    e.dyad_name = rs.getString("dyad_name");
    e.side_a_dset_id = (Integer)rs.getObject("side_a_dset_id");
    e.side_a_new_id = (Integer)rs.getObject("side_a_new_id");
    e.side_a = rs.getString("side_a");
    e.side_b_dset_id = (Integer)rs.getObject("side_b_dset_id");
    e.side_b_new_id = (Integer)rs.getObject("side_b_new_id");
    e.side_b = rs.getString("side_b");
    e.number_of_sources = (Integer)rs.getObject("number_of_sources");
    e.source_article = rs.getString("source_article");
    e.source_office = rs.getString("source_office");
    e.source_date = rs.getString("source_date");
    e.source_headline = rs.getString("source_headline");
    e.source_original = rs.getString("source_original");
    e.where_prec = (Integer)rs.getObject("where_prec");
    e.where_coordinates = rs.getString("where_coordinates");
    e.where_description = rs.getString("where_description");
    e.adm_1 = rs.getString("adm_1");
    e.adm_2 = rs.getString("adm_2");
    e.latitude = (Double)rs.getObject("latitude");
    e.longitude = (Double)rs.getObject("longitude");
    e.priogrid_gid = (Integer)rs.getObject("priogrid_gid");
    e.country = rs.getString("country");
    e.country_id = (Integer)rs.getObject("country_id");
    e.region = rs.getString("region");
    e.event_clarity = (Integer)rs.getObject("event_clarity");
    e.date_prec = (Integer)rs.getObject("date_prec");
    e.date_start = rs.getString("date_start");
    e.date_end = rs.getString("date_end");
    e.deaths_a = (Integer)rs.getObject("deaths_a");
    e.deaths_b = (Integer)rs.getObject("deaths_b");
    e.deaths_civilians = (Integer)rs.getObject("deaths_civilians");
    e.deaths_unknown = (Integer)rs.getObject("deaths_unknown");
    e.best = (Integer)rs.getObject("best");
    e.high = (Integer)rs.getObject("high");
    e.low = (Integer)rs.getObject("low");
    e.gwnoa = (Integer)rs.getObject("gwnoa");
    e.gwnob = (Integer)rs.getObject("gwnob");
    return e;
  }

  // List paginada con filtros
  public PaginatedResponse<ConflictEvent> pageEvents(Map<String,String> q) {
    int page = Integer.parseInt(q.getOrDefault("page","1"));
    int pageSize = Integer.parseInt(q.getOrDefault("pageSize","50"));
    int offset = (page-1)*pageSize;

    StringBuilder sql = new StringBuilder("""
      SELECT id, relid, year, active_year, code_status, type_of_violence,
             conflict_dset_id, conflict_new_id, conflict_name,
             dyad_dset_id, dyad_new_id, dyad_name,
             side_a_dset_id, side_a_new_id, side_a,
             side_b_dset_id, side_b_new_id, side_b,
             number_of_sources, source_article, source_office, source_date, source_headline, source_original,
             where_prec, where_coordinates, where_description,
             adm_1, adm_2, latitude, longitude, priogrid_gid,
             country, country_id, region, event_clarity, date_prec,
             date_start, date_end, deaths_a, deaths_b, deaths_civilians, deaths_unknown,
             best, high, low, gwnoa, gwnob
      FROM vw_events WHERE 1=1
    """);
    List<Object> params = new ArrayList<>();

    // filtros
    if (q.containsKey("from")) { sql.append(" AND date_start::date >= to_date(?,'YYYY-MM-DD') "); params.add(q.get("from")); }
    if (q.containsKey("to"))   { sql.append(" AND date_start::date <= to_date(?,'YYYY-MM-DD') "); params.add(q.get("to")); }
    if (q.containsKey("countries")) { sql.append(" AND country = ANY(string_to_array(? ,',')) "); params.add(q.get("countries")); }
    if (q.containsKey("regions"))   { sql.append(" AND region  = ANY(string_to_array(? ,',')) "); params.add(q.get("regions")); }
    if (q.containsKey("adm1"))      { sql.append(" AND adm_1   = ANY(string_to_array(? ,',')) "); params.add(q.get("adm1")); }
    if (q.containsKey("violenceTypes")) { sql.append(" AND type_of_violence = ANY( string_to_array(?,',')::INT[] ) "); params.add(q.get("violenceTypes")); }
    if (q.containsKey("sidesA"))    { sql.append(" AND side_a  = ANY(string_to_array(? ,',')) "); params.add(q.get("sidesA")); }
    if (q.containsKey("sidesB"))    { sql.append(" AND side_b  = ANY(string_to_array(? ,',')) "); params.add(q.get("sidesB")); }
    if (q.containsKey("minDeaths")) { sql.append(" AND best >= ? "); params.add(Integer.parseInt(q.get("minDeaths"))); }
    if (q.containsKey("maxDeaths")) { sql.append(" AND best <= ? "); params.add(Integer.parseInt(q.get("maxDeaths"))); }
    if (q.containsKey("hasCivilians")) { sql.append(" AND (deaths_civilians IS NOT NULL AND deaths_civilians > 0) "); }
    if (q.containsKey("clarityMin")) { sql.append(" AND event_clarity >= ? "); params.add(Integer.parseInt(q.get("clarityMin"))); }
    if (q.containsKey("clarityMax")) { sql.append(" AND event_clarity <= ? "); params.add(Integer.parseInt(q.get("clarityMax"))); }

    // total
    String countSql = "SELECT COUNT(*) FROM ("+sql.toString()+") t";
    Integer total = jdbc.queryForObject(countSql, params.toArray(), Integer.class);

    // orden y paginación
    sql.append(" ORDER BY date_start NULLS LAST, id ");
    sql.append(" LIMIT ? OFFSET ? "); params.add(pageSize); params.add(offset);

    List<ConflictEvent> data = jdbc.query(sql.toString(), params.toArray(), this::mapEvent);

    PaginatedResponse<ConflictEvent> resp = new PaginatedResponse<>();
    resp.data = data; resp.total = total; resp.page = page; resp.pageSize = pageSize;
    resp.totalPages = (int)Math.ceil((total*1.0)/pageSize);
    return resp;
  }

  public ConflictEvent getById(String id){
    return jdbc.queryForObject("""
      SELECT id, relid, year, active_year, code_status, type_of_violence,
             conflict_dset_id, conflict_new_id, conflict_name,
             dyad_dset_id, dyad_new_id, dyad_name,
             side_a_dset_id, side_a_new_id, side_a,
             side_b_dset_id, side_b_new_id, side_b,
             number_of_sources, source_article, source_office, source_date, source_headline, source_original,
             where_prec, where_coordinates, where_description,
             adm_1, adm_2, latitude, longitude, priogrid_gid,
             country, country_id, region, event_clarity, date_prec,
             date_start, date_end, deaths_a, deaths_b, deaths_civilians, deaths_unknown,
             best, high, low, gwnoa, gwnob
      FROM vw_events WHERE id = ?
    """, this::mapEvent, id);
  }

  public Lookups lookups(){
    Lookups l = new Lookups();
    l.countries = jdbc.queryForList("SELECT DISTINCT country FROM vw_events WHERE country <> '' ORDER BY 1", String.class);
    l.regions   = jdbc.queryForList("SELECT DISTINCT region  FROM vw_events WHERE region  <> '' ORDER BY 1", String.class);
    l.years     = jdbc.queryForList("SELECT DISTINCT year FROM vw_events WHERE year IS NOT NULL ORDER BY 1", Integer.class);
    l.sidesA    = jdbc.queryForList("SELECT DISTINCT side_a FROM vw_events WHERE side_a <> '' ORDER BY 1", String.class);
    l.sidesB    = jdbc.queryForList("SELECT DISTINCT side_b FROM vw_events WHERE side_b <> '' ORDER BY 1", String.class);
    l.violenceTypes = jdbc.queryForList("SELECT DISTINCT type_of_violence FROM vw_events WHERE type_of_violence IS NOT NULL ORDER BY 1", Integer.class);
    return l;
  }

  public List<HeatCell> heat(Map<String,String> q){
    // Simple: sin filtros → vista precalculada
    return jdbc.query("SELECT geohash6, lat, lng, count FROM vw_heat_geohash6",
      (rs,rn)->{
        HeatCell h = new HeatCell();
        h.geohash6 = rs.getString("geohash6");
        h.lat = rs.getDouble("lat");
        h.lng = rs.getDouble("lng");
        h.count = rs.getInt("count");
        return h;
      });
  }

  // Fallback de series desde Postgres (por si decides no llamar PowerBI)
  public List<TimePoint> series(Map<String,String> q){
    StringBuilder s = new StringBuilder("""
      SELECT to_char(date_start::date,'YYYY-MM') AS period,
             COUNT(*)::int AS events,
             COALESCE(SUM(best),0)::int AS deaths,
             COALESCE(SUM(deaths_civilians),0)::int AS civilians
      FROM vw_events WHERE 1=1
    """);
    List<Object> p = new ArrayList<>();
    if (q.containsKey("from")){ s.append(" AND date_start::date >= to_date(?,'YYYY-MM-DD') "); p.add(q.get("from")); }
    if (q.containsKey("to"))  { s.append(" AND date_start::date <= to_date(?,'YYYY-MM-DD') "); p.add(q.get("to")); }
    s.append(" GROUP BY 1 ORDER BY 1 ");
    return jdbc.query(s.toString(), p.toArray(), (rs,rn)->{
      TimePoint t = new TimePoint();
      t.period = rs.getString("period");
      t.events = rs.getInt("events");
      t.deaths = rs.getInt("deaths");
      t.civilians = rs.getInt("civilians");
      return t;
    });
  }

  public List<RegionAgg> byRegion(Map<String,String> q){
    StringBuilder s = new StringBuilder("""
      SELECT country AS key,
             COUNT(*)::int AS events,
             COALESCE(SUM(best),0)::int AS deaths,
             COALESCE(SUM(deaths_civilians),0)::int AS civilians
      FROM vw_events WHERE 1=1
    """);
    List<Object> p = new ArrayList<>();
    if (q.containsKey("from")){ s.append(" AND date_start::date >= to_date(?,'YYYY-MM-DD') "); p.add(q.get("from")); }
    if (q.containsKey("to"))  { s.append(" AND date_start::date <= to_date(?,'YYYY-MM-DD') "); p.add(q.get("to")); }
    s.append(" GROUP BY 1 ORDER BY events DESC ");
    return jdbc.query(s.toString(), p.toArray(), (rs,rn)->{
      RegionAgg r = new RegionAgg();
      r.key = rs.getString("key");
      r.events = rs.getInt("events");
      r.deaths = rs.getInt("deaths");
      r.civilians = rs.getInt("civilians");
      return r;
    });
  }
}
