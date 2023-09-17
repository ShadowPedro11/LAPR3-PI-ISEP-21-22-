#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "struct.h"


void export_readings_to_csv(Sensor temp_sensors_structs[], int nr_temp_sensors,
                            Sensor vel_vento_sensors_structs[], int nr_vel_vento_sensors,
                            Sensor dir_vento_sensors_structs[], int nr_dir_vento_sensors,
                            Sensor pluvio_sensors_structs[], int nr_pluvio_sensors,
                            Sensor hum_solo_sensors_structs[], int nr_hum_solo_sensors,
                            Sensor hum_atm_sensors_structs[], int nr_hum_atm_sensors) {
	char name[20];
	printf("Insert a name for the sensor csv file:");
	scanf("%s",name);
	strcat(name,".csv");

    FILE *file = fopen(name, "w");
    if (file == NULL) {
        // Error opening file
        return;
    }

	fprintf(file,"Id,Sensor_Type,Max_Limit,Min_Limit,Frequency,Reading_Size,Array_Of_Readings\n");

    for (int i = 0; i < nr_temp_sensors; i++) {
        fprintf(file, "%hu,%c,%hu,%hu,%lu,%lu,", temp_sensors_structs[i].id, temp_sensors_structs[i].sensor_type, temp_sensors_structs[i].max_limit, temp_sensors_structs[i].min_limit, temp_sensors_structs[i].frequency, temp_sensors_structs[i].readings_size);
        fprintf(file, "[");
        for (int j = 0; j < temp_sensors_structs[i].readings_size; j++) {
            fprintf(file, "%hu", temp_sensors_structs[i].readings[j]);
            if (j < temp_sensors_structs[i].readings_size - 1) {
                fprintf(file, ",");
            }
        }
        fprintf(file, "]\n");
    }
    for (int i = 0; i < nr_vel_vento_sensors; i++) {
        fprintf(file, "%hu,%c,%hu,%hu,%lu,%lu,", vel_vento_sensors_structs[i].id, vel_vento_sensors_structs[i].sensor_type, vel_vento_sensors_structs[i].max_limit, vel_vento_sensors_structs[i].min_limit, vel_vento_sensors_structs[i].frequency, vel_vento_sensors_structs[i].readings_size);
        fprintf(file, "[");
        for (int j = 0; j < vel_vento_sensors_structs[i].readings_size; j++) {
            fprintf(file, "%hu", vel_vento_sensors_structs[i].readings[j]);
            if (j < vel_vento_sensors_structs[i].readings_size - 1) {
                fprintf(file, ",");
            }
        }
        fprintf(file, "]\n");
    }
    for (int i = 0; i < nr_dir_vento_sensors; i++) {
        fprintf(file, "%hu,%c,%hu,%hu,%lu,%lu,", dir_vento_sensors_structs[i].id, dir_vento_sensors_structs[i].sensor_type, dir_vento_sensors_structs[i].max_limit, dir_vento_sensors_structs[i].min_limit, dir_vento_sensors_structs[i].frequency, dir_vento_sensors_structs[i].readings_size);
        fprintf(file, "[");
        for (int j = 0; j < dir_vento_sensors_structs[i].readings_size; j++) {
            fprintf(file, "%hu", dir_vento_sensors_structs[i].readings[j]);
            if (j < dir_vento_sensors_structs[i].readings_size - 1) {
                fprintf(file, ",");
            }
        }
        fprintf(file, "]\n");
    }
    for (int i = 0; i < nr_pluvio_sensors; i++) {
        fprintf(file, "%hu,%c,%hu,%hu,%lu,%lu,", pluvio_sensors_structs[i].id, pluvio_sensors_structs[i].sensor_type, pluvio_sensors_structs[i].max_limit, pluvio_sensors_structs[i].min_limit, pluvio_sensors_structs[i].frequency, pluvio_sensors_structs[i].readings_size);
        fprintf(file, "[");
        for (int j = 0; j < pluvio_sensors_structs[i].readings_size; j++) {
            fprintf(file, "%hu", pluvio_sensors_structs[i].readings[j]);
            if (j < pluvio_sensors_structs[i].readings_size - 1) {
                fprintf(file, ",");
            }
        }
        fprintf(file, "]\n");
    }
    for (int i = 0; i < nr_hum_solo_sensors; i++) {
        fprintf(file, "%hu,%c,%hu,%hu,%lu,%lu,", hum_solo_sensors_structs[i].id, hum_solo_sensors_structs[i].sensor_type, hum_solo_sensors_structs[i].max_limit, hum_solo_sensors_structs[i].min_limit, hum_solo_sensors_structs[i].frequency, hum_solo_sensors_structs[i].readings_size);
        fprintf(file, "[");
        for (int j = 0; j < hum_solo_sensors_structs[i].readings_size; j++) {
            fprintf(file, "%hu", hum_solo_sensors_structs[i].readings[j]);
            if (j < hum_solo_sensors_structs[i].readings_size - 1) {
                fprintf(file, ",");
            }
        }
        fprintf(file, "]\n");
    }
    for (int i = 0; i < nr_hum_atm_sensors; i++) {
        fprintf(file, "%hu,%c,%hu,%hu,%lu,%lu,", hum_atm_sensors_structs[i].id, hum_atm_sensors_structs[i].sensor_type, hum_atm_sensors_structs[i].max_limit, hum_atm_sensors_structs[i].min_limit, hum_atm_sensors_structs[i].frequency, hum_atm_sensors_structs[i].readings_size);
        fprintf(file, "[");
        for (int j = 0; j < hum_atm_sensors_structs[i].readings_size; j++) {
            fprintf(file, "%hu", hum_atm_sensors_structs[i].readings[j]);
            if (j < hum_atm_sensors_structs[i].readings_size - 1) {
                fprintf(file, ",");
            }
        }
        fprintf(file, "]\n");
    }



    fclose(file);
}

void export_daily_matrix_to_csv(int rows, int columns, float *daily_matrix){
	char name[20];
	printf("Insert a name for the Daily Matrix csv file:");
	scanf("%s",name);
	strcat(name,".csv");

	FILE *csv_file = fopen(name, "w");
	fprintf(csv_file,"Sensor,Minimum,Max,Average\n");
	char strings[6][20] = { "temp", "vel_vent", "dir_vent", "pluvio", "humd_solo", "humd_atm" };

    for (int i = 0; i < rows; i++) {
		 fprintf(csv_file,"%s,",strings[i]);
        for (int j = 0; j < columns; j++) {
            fprintf(csv_file, "%.2f,",daily_matrix[i * columns + j]);
        }
        fprintf(csv_file, "\n");
    }
    fclose(csv_file);
}
