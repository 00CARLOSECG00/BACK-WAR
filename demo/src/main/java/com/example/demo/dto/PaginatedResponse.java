package com.example.demo.dto;


import java.util.List;
import lombok.Data;

@Data
public class PaginatedResponse<T> {
  public List<T> data;
  public Integer total;
  public Integer page;
  public Integer pageSize;
  public Integer totalPages;
}
