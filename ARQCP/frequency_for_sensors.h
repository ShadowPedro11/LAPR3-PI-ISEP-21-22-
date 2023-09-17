#ifndef FREQUENCY_FOR_SENSORS_H
#define FREQUENCY_FOR_SENSORS_H
#include "struct.h"

void select_sensors_for_frequency(int* valuesConfig, Sensor *temp_sensors_structs, Sensor *vel_vento_sensors_structs, Sensor *dir_vento_sensors_structs,
                      Sensor *hum_solo_sensors_structs, Sensor *hum_atm_sensors_structs, Sensor *pluvio_sensors_structs);
int frequency_to_change(int duration, int size, Sensor* sensor_structs);
               
#endif
