package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * @author : Ricardo Venâncio - 1210828
 **/
public record IrrigationTime(List<LocalTime> cycles, LocalDate date) {
}
