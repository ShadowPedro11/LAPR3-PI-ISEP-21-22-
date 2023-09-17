#include "random.h"
#include "sensores.h"
#include "daily_matrix.h"
#include <stdio.h>
#include <unistd.h>
#include <string.h>
#include "gen_val_sens.h"
#include <stdint.h>
#include "set_limit_values.h"
#include "create_new_dinamic_array.h"
#include "create_new_sensor.h"
#include "export.h"

unsigned long state = 0;
unsigned long inc = 0;

int generateRandomValues()
{
  uint32_t buffer[64];
  FILE *f;
  int result;
  f = fopen("/dev/urandom", "r");
  if (f == NULL)
  {
    printf("Error: open() failed to open /dev/random for reading\n");
    return 1;
  }
  result = fread(buffer, sizeof(uint32_t), 64, f);
  if (result < 1)
  {
    printf("error , failed to read and words\n");
    return 1;
  }
  //printf("Read %d words from /dev/urandom\n", result);
  state = buffer[0];
  inc = buffer[1];
  return 0;
}

int readConfigFile(int *ptrValues)
{
  FILE *ptrFile = fopen("config.conf", "r");

  char str[50] = {0};
  unsigned int num;
  int success = 0;

  if (ptrFile != NULL)
  {
    success = 1;
    while (fscanf(ptrFile, "%[^=]=%d", str, &num) != -1)
    {
      *ptrValues = num;
      ptrValues++;
    }
  }

  fclose(ptrFile);
  return success;
}

int initialize_sensor(int valuesConfig[25], Sensor *temp_sensors_structs, Sensor *vel_vento_sensors_structs, Sensor *dir_vento_sensors_structs,
                      Sensor *hum_solo_sensors_structs, Sensor *hum_atm_sensors_structs, Sensor *pluvio_sensors_structs)
{
  if (create_sensors(temp_sensors_structs, valuesConfig[0], valuesConfig[12], 't', valuesConfig[13], valuesConfig[14], valuesConfig[6]) == 0)
  {
    printf("Something went wrong creating the temperature sensors!");
    return 0;
  }

  if (create_sensors(vel_vento_sensors_structs, valuesConfig[1], valuesConfig[12], 'v', valuesConfig[15], valuesConfig[16], valuesConfig[7]) == 0)
  {
    printf("Something went wrong creating the wind velocity sensors!");
    return 0;
  }

  if (create_sensors(dir_vento_sensors_structs, valuesConfig[2], valuesConfig[12], 'd', valuesConfig[17], valuesConfig[18], valuesConfig[8]) == 0)
  {
    printf("Something went wrong creating the wind direction sensors!");
    return 0;
  }

  if (create_sensors(hum_solo_sensors_structs, valuesConfig[3], valuesConfig[12], 's', valuesConfig[21], valuesConfig[22], valuesConfig[9]) == 0)
  {
    printf("Something went wrong creating the soil humidity sensors!");
    return 0;
  }

  if (create_sensors(hum_atm_sensors_structs, valuesConfig[4], valuesConfig[12], 'a', valuesConfig[19], valuesConfig[20], valuesConfig[10]) == 0)
  {
    printf("Something went wrong creating the atmosferic humidity sensors!");
    return 0;
  }

  if (create_sensors(pluvio_sensors_structs, valuesConfig[5], valuesConfig[12], 'p', valuesConfig[23], valuesConfig[24], valuesConfig[11]) == 0)
  {
    printf("Something went wrong creating the rainfall humidity sensors!");
    return 0;
  }

  return 1;
}

int main(void)
{
  generateRandomValues();
  int valuesConfig[25];

  if (readConfigFile(valuesConfig) == 1)
  {
    int nr_temp_sensors = valuesConfig[0];
    int nr_vel_vento_sensors = valuesConfig[1];
    int nr_dir_vento_sensors = valuesConfig[2];
    int nr_hum_solo_sensors = valuesConfig[3];
    int nr_hum_atm_sensors = valuesConfig[4];
    int nr_pluvio_sensors = valuesConfig[5];

    Sensor *temp_sensors_structs = new_array_sensor_type(nr_temp_sensors);
    Sensor *vel_vento_sensors_structs = new_array_sensor_type(nr_vel_vento_sensors);
    Sensor *dir_vento_sensors_structs = new_array_sensor_type(nr_dir_vento_sensors);
    Sensor *hum_solo_sensors_structs = new_array_sensor_type(nr_hum_solo_sensors);
    Sensor *hum_atm_sensors_structs = new_array_sensor_type(nr_hum_atm_sensors);
    Sensor *pluvio_sensors_structs = new_array_sensor_type(nr_pluvio_sensors);

    int check_values[6];

        for(int i =0; i<6; i++){
    		check_values[i]= valuesConfig[i];
    	}


        int choice = 0;
        while (choice != 1)
        {
          int subchoice;

          printf("Option menu:\n");
          printf("1. Show more options\n");
          printf("2. Add or remove sensors\n");
          printf("Enter your choice: ");
          scanf("%d", &choice);

          switch (choice)
          {
          case 1:
            printf("You chose Option 1.\n");
            break;
          case 2:
            printf("You chose Option 2.\n");
            printf("1. Add sensors\n");
            printf("2. Remove sensors\n");
            printf("Enter your choice: ");
            scanf("%d", &subchoice);
            int status;

            switch (subchoice)
            {
            case 1:
              printf("Which sensor do you want to add\n");
              status = 1;
              select_sensors(status, valuesConfig);
              break;
            case 2:
              printf("Which sensor do you want to remove\n");
              status = 2;
              select_sensors(status, valuesConfig);
              break;
            default:
              printf("Invalid choice.\n");
              break;
            }
            break;
          default:
            printf("Invalid choice.\n");
            break;
          }
        }

        for(int i=0; i<6;i++){
    		if(check_values[i]!= valuesConfig[i]){
    			if(i==0){
    				temp_sensors_structs=change_array_sensors(temp_sensors_structs, valuesConfig[i]);
    			} else if(i==1){
    				vel_vento_sensors_structs=change_array_sensors(vel_vento_sensors_structs, valuesConfig[i]);
    			} else if(i==2){
    				dir_vento_sensors_structs=change_array_sensors(dir_vento_sensors_structs, valuesConfig[i]);
    			} else if(i==3){
    				hum_solo_sensors_structs=change_array_sensors(hum_solo_sensors_structs, valuesConfig[i]);
    			} else if(i==4){
    				hum_atm_sensors_structs=change_array_sensors(hum_atm_sensors_structs, valuesConfig[i]);
    			} else if(i==5){
    				pluvio_sensors_structs=change_array_sensors(pluvio_sensors_structs, valuesConfig[i]);
    			}
    		}
    	}

        nr_temp_sensors = valuesConfig[0];
    	nr_vel_vento_sensors = valuesConfig[1];
    	nr_dir_vento_sensors = valuesConfig[2];
    	nr_hum_solo_sensors = valuesConfig[3];
    	nr_hum_atm_sensors = valuesConfig[4];
    	nr_pluvio_sensors = valuesConfig[5];

    int max_temp = valuesConfig[13];
    int min_temp = valuesConfig[14];
    int max_velc_vent = valuesConfig[15];
    int min_velc_vent = valuesConfig[16];
    int max_dir_vent = valuesConfig[17];
    int min_dir_vent = valuesConfig[18];
    int max_humd_atm = valuesConfig[19];
    int min_humd_atm = valuesConfig[20];
    int max_humd_solo = valuesConfig[21];
    int min_humd_solo = valuesConfig[22];
    int max_pluvio = valuesConfig[23];
    int min_pluvio = valuesConfig[24];
    int nr_max_errors = valuesConfig[25];

    if (initialize_sensor(valuesConfig, temp_sensors_structs, vel_vento_sensors_structs, dir_vento_sensors_structs, hum_solo_sensors_structs, hum_atm_sensors_structs, pluvio_sensors_structs) == 0)
    {
      return 0;
    }

    int check_frequencies[6];

    for(int i =0; i<6; i++){
    		check_frequencies[i]= valuesConfig[i+6];
    }


    choice = 0;
        while (choice != 1)
        {
          printf("Option menu:\n");
          printf("1. Run Program\n");
          printf("2. Change the frequency of readings for a sensor\n");
          printf("Enter your choice: ");
          scanf("%d", &choice);

          switch (choice)
          {
          case 1:
            printf("You chose Option 1.\n");
            break;
          case 2:
            printf("Which sensor would you like to change the frequency?\n");
            select_sensors_for_frequency(valuesConfig, temp_sensors_structs, vel_vento_sensors_structs, dir_vento_sensors_structs, hum_solo_sensors_structs, hum_atm_sensors_structs, pluvio_sensors_structs);
            break;
          default:
            printf("Invalid choice.\n");
            break;
          }
    	}

    	for(int i=0; i<6;i++){
    		if(check_frequencies[i]!= valuesConfig[i+6]){
    			if(i==0){
					for(int j=0; j<valuesConfig[j]; j++){
						(temp_sensors_structs+j)->readings=change_array_frequency((temp_sensors_structs + j)->readings, (temp_sensors_structs + j)->readings_size);
					}
    			} else if(i==1){
					for(int j=0; j<valuesConfig[j]; j++){
						(vel_vento_sensors_structs + j)->readings=change_array_frequency((vel_vento_sensors_structs + j)->readings, (vel_vento_sensors_structs + j)->readings_size);
					}
    			} else if(i==2){
					for(int j=0; j<valuesConfig[j]; j++){
						(dir_vento_sensors_structs + j)->readings=change_array_frequency((dir_vento_sensors_structs + j)->readings, (dir_vento_sensors_structs + j)->readings_size);
					}
    			} else if(i==3){
					for(int j=0; j<valuesConfig[j]; j++){
						(hum_solo_sensors_structs + j)->readings=change_array_frequency((hum_solo_sensors_structs + j)->readings, (hum_solo_sensors_structs + j)->readings_size);
					}
    			} else if(i==4){
					for(int j=0; j<valuesConfig[j]; j++){
						(hum_atm_sensors_structs + j)->readings=change_array_frequency((hum_atm_sensors_structs + j)->readings, (hum_atm_sensors_structs + j)->readings_size);
					}
    			} else if(i==5){
					for(int j=0; j<valuesConfig[j]; j++){
						(pluvio_sensors_structs + j)->readings=change_array_frequency((pluvio_sensors_structs + j)->readings, (pluvio_sensors_structs + j)->readings_size);
					}
    			}
    		}
    	}

    int rows = 6;
    float daily_matrix[rows][3];
    float *ptr_daily_matrix = &daily_matrix[0][0];

    int i;

    int count_temp = valuesConfig[6];
    int count_vel_vento = valuesConfig[7];
    int count_dir_vento = valuesConfig[8];
    int count_pluvio = valuesConfig[11];
    int count_humd_atm = valuesConfig[10];
    int count_humd_solo = valuesConfig[9];

    int times_exec_temp = 0;
    int times_exec_vel_vent = 0;
    int times_exec_dir_vent = 0;
    int times_exec_pluvio = 0;
    int times_exec_humd_atm = 0;
    int times_exec_humd_solo = 0;

    int seconds = valuesConfig[12];
    printf("Generating values...\n");
    while (seconds > 0)
    {
      //printf("%d: %d\n", i + 1, (int)random());

      sleep(1);

      if (count_temp == 1) {
        //printf("generating temp\n");
        gen_temp_sens(temp_sensors_structs, nr_temp_sensors, times_exec_temp);
        count_temp = valuesConfig[6];
        times_exec_temp++;
      } else {
        count_temp--;
      }

      if (count_vel_vento == 1) {
        //printf("generating vento speed\n");
        gen_vel_vento(vel_vento_sensors_structs, nr_vel_vento_sensors, times_exec_vel_vent);
        count_vel_vento = valuesConfig[7];
        times_exec_vel_vent++;
      } else {
        count_vel_vento--;
      }

      if (count_dir_vento == 1) {
        //printf("generating vento dir\n");
        gen_dir_vento(dir_vento_sensors_structs, nr_dir_vento_sensors, times_exec_dir_vent);
        count_dir_vento = valuesConfig[8];
        times_exec_dir_vent++;
      } else {
        count_dir_vento--;
      }

      if (count_pluvio == 1) {
        //printf("generating pluv\n");
        gen_pluvio(pluvio_sensors_structs, temp_sensors_structs, nr_pluvio_sensors, times_exec_pluvio);
        count_pluvio = valuesConfig[11];
        times_exec_pluvio++;
      } else {
        count_pluvio--;
      }

      if (count_humd_atm == 1) {
        //printf("generating atm\n");
        gen_hum_atm(hum_atm_sensors_structs, pluvio_sensors_structs, nr_hum_atm_sensors, times_exec_humd_atm);
        count_humd_atm = valuesConfig[10];
        times_exec_humd_atm++;
      } else {
        count_humd_atm--;
      }

      if (count_humd_solo == 1) {
        //printf("generating solo\n");
        gen_hum_solo(hum_solo_sensors_structs, pluvio_sensors_structs, nr_hum_solo_sensors, times_exec_humd_solo);
        count_humd_solo = valuesConfig[9];
        times_exec_humd_solo++;
      } else {
        count_humd_solo--;
      }

      seconds--;
    }

    printf("\nCHECKING TEMPERATURE SENSORS...\n");
    set_limit_values(temp_sensors_structs, nr_temp_sensors, min_temp, max_temp, nr_max_errors);

    for (int i = 0; i < nr_temp_sensors; i++)
    {
      printf("\nValores sensor %d:[", i);
      for (int j = 0; j < (temp_sensors_structs + i)->readings_size; j++)
      {
        printf("%d,", (temp_sensors_structs + i)->readings[j]);
      }
      printf("]\n");
    }

    sleep(1);

    printf("\nCHECKING WIND VELOCITY SENSORS...\n");
    set_limit_values(vel_vento_sensors_structs, nr_vel_vento_sensors, min_velc_vent, 50, nr_max_errors);

    for (int i = 0; i < nr_temp_sensors; i++)
    {
      printf("\nValores sensor %d:[", i);
      for (int j = 0; j < (vel_vento_sensors_structs + i)->readings_size; j++)
      {
        printf("%d,", (vel_vento_sensors_structs + i)->readings[j]);
      }
      printf("]\n");
    }

    sleep(1);

    printf("\nCHECKING WIND DIRECTION SENSORS...\n");
    set_limit_values(dir_vento_sensors_structs, nr_dir_vento_sensors, min_dir_vent, max_dir_vent, nr_max_errors);

    for (int i = 0; i < nr_temp_sensors; i++)
    {
      printf("\nValores sensor %d:[", i);
      for (int j = 0; j < (dir_vento_sensors_structs + i)->readings_size; j++)
      {
        printf("%d,", (dir_vento_sensors_structs + i)->readings[j]);
      }
      printf("]\n");
    }

    sleep(1);

    printf("\nCHECKING PLUVIOSITY SENSORS...\n");
    set_limit_values(pluvio_sensors_structs, nr_pluvio_sensors, min_pluvio, max_pluvio, nr_max_errors);

    for (int i = 0; i < nr_temp_sensors; i++)
    {
      printf("\nValores sensor %d:[", i);
      for (int j = 0; j < (pluvio_sensors_structs + i)->readings_size; j++)
      {
        printf("%d,", (pluvio_sensors_structs + i)->readings[j]);
      }
      printf("]\n");
    }

    sleep(1);

    printf("\nCHECKING ATMOSFERIC HUMIDITY SENSORS....\n");
    set_limit_values(hum_atm_sensors_structs, nr_hum_atm_sensors, min_humd_atm, max_humd_atm, nr_max_errors);

    for (int i = 0; i < nr_temp_sensors; i++)
    {
      printf("\nValores sensor %d:[", i);
      for (int j = 0; j < (hum_atm_sensors_structs + i)->readings_size; j++)
      {
        printf("%d,", (hum_atm_sensors_structs + i)->readings[j]);
      }
      printf("]\n");
    }

    sleep(1);

    printf("\n----------------''---------------\nCHECKING SOIL HUMIDITY SENSORS\n----------------''---------------\n");
    set_limit_values(hum_solo_sensors_structs, nr_hum_solo_sensors, min_humd_solo, max_humd_solo, nr_max_errors);

    for (int i = 0; i < nr_temp_sensors; i++)
    {
      printf("\nValores sensor %d:[", i);
      for (int j = 0; j < (hum_solo_sensors_structs + i)->readings_size; j++)
      {
        printf("%d,", (hum_solo_sensors_structs + i)->readings[j]);
      }
      printf("]\n");
    }

    sleep(1);


    do_daily_matrix(ptr_daily_matrix, 0, nr_temp_sensors, temp_sensors_structs);
    do_daily_matrix(ptr_daily_matrix, 1, nr_vel_vento_sensors, vel_vento_sensors_structs);
    do_daily_matrix(ptr_daily_matrix, 2, nr_dir_vento_sensors, dir_vento_sensors_structs);
    do_daily_matrix(ptr_daily_matrix, 3, nr_pluvio_sensors, pluvio_sensors_structs);
    do_daily_matrix(ptr_daily_matrix, 4, nr_hum_solo_sensors, hum_solo_sensors_structs);
    do_daily_matrix(ptr_daily_matrix, 5, nr_hum_atm_sensors, hum_atm_sensors_structs);

    printf("|  Sensor  |  Minimum\t|  Max\t\t|  Average\t|\n");
    printf("| temp     |  %.2f\t|  %.2f\t|  %.2f\t|\n", *(ptr_daily_matrix + 0 * 3 + 0), *(ptr_daily_matrix + 0 * 3 + 1), *(ptr_daily_matrix + 0 * 3 + 2));
    printf("| vel_vent |  %.2f\t|  %.2f\t|  %.2f\t|\n", *(ptr_daily_matrix + 1 * 3 + 0), *(ptr_daily_matrix + 1 * 3 + 1), *(ptr_daily_matrix + 1 * 3 + 2));
    printf("| dir_vent |  %.2f\t|  %.2f\t|  %.2f\t|\n", *(ptr_daily_matrix + 2 * 3 + 0), *(ptr_daily_matrix + 2 * 3 + 1), *(ptr_daily_matrix + 2 * 3 + 2));
    printf("| pluvio   |  %.2f\t|  %.2f\t|  %.2f\t|\n", *(ptr_daily_matrix + 3 * 3 + 0), *(ptr_daily_matrix + 3 * 3 + 1), *(ptr_daily_matrix + 3 * 3 + 2));
    printf("| humd_solo|  %.2f\t|  %.2f\t|  %.2f\t|\n", *(ptr_daily_matrix + 4 * 3 + 0), *(ptr_daily_matrix + 4 * 3 + 1), *(ptr_daily_matrix + 4 * 3 + 2));
    printf("| humd_atm |  %.2f\t|  %.2f\t|  %.2f\t|\n", *(ptr_daily_matrix + 5 * 3 + 0), *(ptr_daily_matrix + 5 * 3 + 1), *(ptr_daily_matrix + 5 * 3 + 2));

    export_readings_to_csv(temp_sensors_structs, nr_temp_sensors,vel_vento_sensors_structs,nr_vel_vento_sensors,dir_vento_sensors_structs,nr_dir_vento_sensors,pluvio_sensors_structs,nr_pluvio_sensors,hum_solo_sensors_structs,nr_hum_solo_sensors,hum_atm_sensors_structs,nr_hum_atm_sensors);
    export_daily_matrix_to_csv(rows, 3, ptr_daily_matrix);

    /*for (int i = 0; i < nr_temp_sensors; i++)
    {
      free()
    }*/

  }
  else
  {
    printf("Config file not found!\n");
  }

  return 0;
}
