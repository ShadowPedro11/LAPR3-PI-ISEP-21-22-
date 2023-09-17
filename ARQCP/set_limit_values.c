#include <stdio.h>
#include "gen_val_sens.h"
#include "sensores.h"
#include "random.h"

int set_limit_values(Sensor *sensors, int nr_sensors, int min_value, int max_value, int n_erros)
{
    for (int i = 0; i < nr_sensors; i++)
    {
        long nr_readings = (sensors + i)->readings_size;
        int conta_erros = 0;

        for (int y = 0; y < nr_readings; y++)
        {
            short reading = (sensors + i)->readings[y];
            if (reading < (short)min_value)
            {
                printf("\nWrong reading, the value %d is lesser than what is allowed!", reading);
                conta_erros++;
            }

            if (reading > (short)max_value)
            {
                printf("\nWrong reading, the value %d is bigget than what is allowed!", reading);
                conta_erros++;
            }

            if (conta_erros >= n_erros)
            {
                printf("\n----------------SENSOR RESET---------------\n Sensor number %d Values Have been reseted \n----------------SENSOR RESET---------------\n", i);
                for (int z = 0; z < nr_readings; z++)
                {
                    if (z == 0)
                    {
                        (sensors + i)->readings[z] = sens_temp(20, (char)random());
                        continue;
                    }
                    (sensors + i)->readings[z] = (short)sens_temp((sensors + i)->readings[z - 1], (char)random());
                }
                conta_erros = 0;
                y = -1;
            }
        }
    }
}
