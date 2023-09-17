#ifndef ADD_REMOVE_SENSORS_H
#define ADD_REMOVE_SENSORS_H
#include "struct.h"

void select_sensors(int status, int* valuesConfig);
int choose_number_of_sensors_to_add(int size);
int choose_number_of_sensors_to_remove(int size);
#endif
