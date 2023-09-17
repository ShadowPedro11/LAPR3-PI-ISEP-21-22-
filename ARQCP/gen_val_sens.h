#ifndef GEN_VAL_SENS_H
#define GEN_VAL_SENS_H
#include "struct.h"
void gen_temp_sens(Sensor *temp_sensors_structs, int nr_sensors, int times_executed_before);
void gen_vel_vento(Sensor *vel_vento_sensors_structs, int nr_sensors, int times_executed_before);
void gen_dir_vento(Sensor *dir_vento_sensors_structs, int nr_sensors, int times_executed_before);
void gen_pluvio(Sensor *pluvio_sensors_structs, Sensor *temp_sensors_structs, int nr_sensors, int times_executed_before);
void gen_hum_solo(Sensor *hum_solo_sensor_structs, Sensor *pluvio_sensor_structs, int nr_sensors, int times_executed_before);
void gen_hum_atm(Sensor *hum_atm_sensor_structs, Sensor *pluvio_sensor_structs, int nr_sensors, int times_executed_before);
#endif