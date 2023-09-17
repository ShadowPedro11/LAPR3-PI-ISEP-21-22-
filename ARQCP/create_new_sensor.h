#ifndef CREATE_NEW_SENSOR_H
#define CREATE_NEW_SENSOR_H
#include "struct.h"
int create_sensors(Sensor *type_array, int nr_sensors, int duracao_analise, unsigned char sensor_type, unsigned short max_limit, 
unsigned short min_limit, unsigned long frequency);
#endif