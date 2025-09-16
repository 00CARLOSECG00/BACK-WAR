package com.example.demo.dto;

import lombok.Data;

@Data
public class ConflictEvent {
  public String id;
  public String relid;
  public Integer year;
  public Boolean active_year;
  public String code_status;
  public Integer type_of_violence;
  public Integer conflict_dset_id;
  public Integer conflict_new_id;
  public String conflict_name;
  public Integer dyad_dset_id;
  public Integer dyad_new_id;
  public String dyad_name;
  public Integer side_a_dset_id;
  public Integer side_a_new_id;
  public String side_a;
  public Integer side_b_dset_id;
  public Integer side_b_new_id;
  public String side_b;
  public Integer number_of_sources;
  public String source_article;
  public String source_office;
  public String source_date;
  public String source_headline;
  public String source_original;
  public Integer where_prec;
  public String where_coordinates;
  public String where_description;
  public String adm_1;
  public String adm_2;
  public Double latitude;
  public Double longitude;
  public Integer priogrid_gid;
  public String country;
  public Integer country_id;
  public String region;
  public Integer event_clarity;
  public Integer date_prec;
  public String date_start;
  public String date_end;
  public Integer deaths_a;
  public Integer deaths_b;
  public Integer deaths_civilians;
  public Integer deaths_unknown;
  public Integer best;
  public Integer high;
  public Integer low;
  public Integer gwnoa;
  public Integer gwnob;
}
