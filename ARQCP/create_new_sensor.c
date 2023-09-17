#include "struct.h"
#include "create_new_dinamic_array.h"
#include "stdlib.h"

int create_sensors(Sensor *type_array, int nr_sensors, int duracao_analise, unsigned char sensor_type, unsigned short max_limit, 
unsigned short min_limit, unsigned long frequency)
{
    for (int i = 0; i < nr_sensors; i++)
    {
        (type_array + i)->id = i + 1;
        (type_array + i)->sensor_type = sensor_type;
        (type_array + i)->frequency = frequency;
        (type_array + i)->max_limit = max_limit;
        (type_array + i)->min_limit = min_limit;
        (type_array + i)->readings_size = duracao_analise / frequency;

        short * temp_ptr = NULL;
        temp_ptr = new_readings_array((type_array + i)->readings_size);

        if (temp_ptr == NULL)
        {
            return 0;
        }
        
        (type_array + i)->readings = temp_ptr;
    }
    return 1;
}


