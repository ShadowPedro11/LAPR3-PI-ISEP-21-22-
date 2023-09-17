#ifndef DAILY_MATRIX_H
#define DAILY_MATRIX_H
#include "struct.h"
float findMin(int nr_sensors, Sensor *sensors_array_structs);
float findMax(int nr_sensors, Sensor *sensors_array_structs);
float findAvg(int nr_sensors, Sensor *sensors_array_structs);
void do_daily_matrix(float *ptr_Matrix, int row_matrix, int nr_sensors, Sensor *sensors_array);
#endif