#include <stdlib.h>
#include "struct.h"

Sensor *new_array_sensor_type(short size)
{
    Sensor *ptr = malloc(size * sizeof(Sensor));
    return ptr;
}

short *new_readings_array(unsigned long size)
{
    short *ptr = malloc(size * sizeof(short));
    return ptr;
}

Sensor *change_array_sensors(Sensor *ptr, int number)
{
	Sensor *ptr_final=NULL;
	ptr_final =(Sensor *) realloc(ptr, number * sizeof(Sensor));

	if(ptr_final!=NULL){
		ptr = ptr_final;
	}
	free(ptr_final);
	return ptr;
}

unsigned short *change_array_frequency(short *ptr, unsigned long size)
{
    unsigned short *ptr_final = (unsigned short *) realloc(ptr, size * sizeof(short));

    if(ptr_final!=NULL){
		ptr = ptr_final;
	}
	free(ptr_final);
	return ptr;
}

void free_memory(Sensor *structs_array, int size)
{
	for (int i = 0; i < size; i++)
	{
		free((structs_array + i)->readings);
	}
	free(structs_array);
}