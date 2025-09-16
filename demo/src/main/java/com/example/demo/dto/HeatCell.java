package com.example.demo.dto;

import lombok.Data;

@Data
public class HeatCell {
  public String geohash6;
  public Double lat;
  public Double lng;
  public Integer count;
}
