#ifndef EXPORT_H
#define EXPORT_H
#include "struct.h"
void export_readings_to_csv(Sensor temp_sensors_structs[], int nr_temp_sensors,
                            Sensor vel_vento_sensors_structs[], int nr_vel_vento_sensors,
                            Sensor dir_vento_sensors_structs[], int nr_dir_vento_sensors,
                            Sensor pluvio_sensors_structs[], int nr_pluvio_sensors,
                            Sensor hum_solo_sensors_structs[], int nr_hum_solo_sensors,
                            Sensor hum_atm_sensors_structs[], int nr_hum_atm_sensors);
void export_daily_matrix_to_csv(int rows, int columns, float *daily_matrix);
#endif
