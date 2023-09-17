#include <stdio.h>
#include "frequency_for_sensors.h"
#include "struct.h"

void select_sensors_for_frequency(int *valuesConfig, Sensor *temp_sensors_structs, Sensor *vel_vento_sensors_structs, Sensor *dir_vento_sensors_structs,
								  Sensor *hum_solo_sensors_structs, Sensor *hum_atm_sensors_structs, Sensor *pluvio_sensors_structs)
{
	int choice;
	int new_frequency;
	printf("1. Temperature\n");
	printf("2. Wind Velocity\n");
	printf("3. Wind Direction\n");
	printf("4. Ground Humidity\n");
	printf("5. Atmospheric Humidity\n");
	printf("6. Rainfall\n");
	printf("Enter your choice: ");
	scanf("%d", &choice);

	switch (choice)
	{
	case 1:
		new_frequency = frequency_to_change(valuesConfig[12], valuesConfig[0], temp_sensors_structs);
		if (new_frequency != -1)
		{
			valuesConfig[6] = new_frequency;
		}
		break;
	case 2:
		new_frequency = frequency_to_change(valuesConfig[12], valuesConfig[1], vel_vento_sensors_structs);
		if (new_frequency != -1)
		{
			valuesConfig[7] = new_frequency;
		}
		break;
	case 3:
		new_frequency = frequency_to_change(valuesConfig[12], valuesConfig[2], dir_vento_sensors_structs);
		if (new_frequency != -1)
		{
			valuesConfig[8] = new_frequency;
		}
		break;
	case 4:
		new_frequency = frequency_to_change(valuesConfig[12], valuesConfig[3], hum_solo_sensors_structs);
		if (new_frequency != -1)
		{
			valuesConfig[9] = new_frequency;
		}
		break;
	case 5:
		new_frequency = frequency_to_change(valuesConfig[12], valuesConfig[4], hum_atm_sensors_structs);
		if (new_frequency != -1)
		{
			valuesConfig[10] = new_frequency;
		}
		break;
	case 6:
		new_frequency = frequency_to_change(valuesConfig[12], valuesConfig[5], pluvio_sensors_structs);
		if (new_frequency != -1)
		{
			valuesConfig[11] = new_frequency;
		}
		break;
	default:
		printf("Invalid choice.\n");
		break;
	}
}

int frequency_to_change(int duration, int size, Sensor *sensor_structs)
{
	int frequency;
	printf("What is the new frequency?\n");
	scanf("%d", &frequency);

	if (frequency > 0)
	{
		for (int i = 0; i < size; i++)
		{
			(sensor_structs + i)->frequency = frequency;
			(sensor_structs + i)->readings_size = duration / frequency;
		}
	}
	else
	{
		printf("It was not possible to change the frequency of the sensor\n");
		return -1;
	}
	return frequency;
}
