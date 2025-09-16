package main.java.com.example.demo.dto;


import java.util.List; import lombok.Data;
@Data public class Lookups {
  public List<String> countries;
  public List<String> regions;
  public List<Integer> years;
  public List<String> sidesA;
  public List<String> sidesB;
  public List<Integer> violenceTypes;
}
