#ifndef CREATE_NEW_DINAMIC_ARRAY_H
#define CREATE_NEW_DINAMIC_ARRAY_H
#include "struct.h"

Sensor * new_array_sensor_type(short size);
short * new_readings_array(unsigned long size);
Sensor * change_array_sensors(Sensor *ptr, int number);
unsigned short *change_array_frequency(short *ptr, unsigned long size);
void free_memory(Sensor *structs_array, int size);
#endif
