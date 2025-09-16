package com.example.demo.dto;

import lombok.Data;

@Data
public class RegionAgg {
  public String key;
  public Integer events;
  public Integer deaths;
  public Integer civilians;
}
