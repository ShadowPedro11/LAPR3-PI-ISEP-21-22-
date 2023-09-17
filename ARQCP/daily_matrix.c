#include "struct.h"

float findMin(int nr_sensors, Sensor *sensors_array_structs)
{
	float min = sensors_array_structs->readings[0];
	for (int i = 0; i < nr_sensors; i++)
	{
		for (int j = 0; j < (sensors_array_structs)->readings_size; j++)
		{
			if ((sensors_array_structs + i)->readings[j] < min)
			{
				min = (sensors_array_structs + i)->readings[j];
			}
		}
	}

	return min;
}

float findMax(int nr_sensors, Sensor *sensors_array_structs)
{
	float max = 0;
	for (int i = 0; i < nr_sensors; i++)
	{
		for (int j = 0; j < (sensors_array_structs)->readings_size; j++)
		{
			if ((sensors_array_structs + i)->readings[j] > max)
			{
				max = (sensors_array_structs + i)->readings[j] ;
			}
		}
	}
	
	return max;
}

float findAvg(int nr_sensors, Sensor *sensors_array_structs)
{
	float sum = 0;
	for (int i = 0; i < nr_sensors; i++)
	{
		for (int j = 0; j < (sensors_array_structs)->readings_size; j++)
		{
			sum += (sensors_array_structs + i)->readings[j];
		}
	}
	
	return (sum / ((sensors_array_structs->readings_size)*nr_sensors));
}

void do_daily_matrix(float *ptr_Matrix, int row_matrix, int nr_sensors, Sensor *sensors_array)
{
	*(ptr_Matrix + row_matrix * 3 + 0) = findMin(nr_sensors, sensors_array);
	*(ptr_Matrix + row_matrix * 3 + 1) = findMax(nr_sensors, sensors_array);
	*(ptr_Matrix + row_matrix * 3 + 2) = findAvg(nr_sensors, sensors_array);
}
