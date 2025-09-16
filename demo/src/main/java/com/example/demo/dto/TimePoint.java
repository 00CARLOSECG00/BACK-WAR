package com.example.demo.dto;

import lombok.Data;

@Data
public class TimePoint {
  public String period;
  public Integer events;
  public Integer deaths;
  public Integer civilians;
}
