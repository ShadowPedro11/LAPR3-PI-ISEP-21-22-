#include "sensores.h"
#include "random.h"
#include "struct.h"

void gen_temp_sens(Sensor *temp_sensors_structs, int nr_sensors, int times_executed_before){
    for (int i = 0; i < nr_sensors; i++)
    {
        if (times_executed_before == 0)
        {
            (temp_sensors_structs + i)->readings[times_executed_before] = (short) sens_temp(20, (char) random());
            continue;
        }
        
        (temp_sensors_structs + i)->readings[times_executed_before] = (short) sens_temp((temp_sensors_structs + i)->readings[times_executed_before - 1], (char) random());
    }
}

void gen_vel_vento(Sensor *vel_vento_sensors_structs, int nr_sensors, int times_executed_before){
    for (int i = 0; i < nr_sensors; i++)
    {
        if (times_executed_before == 0)
        {
            (vel_vento_sensors_structs + i)->readings[times_executed_before] = (short) sens_velc_vento(15, (char) random());
            continue;
        }
        
        (vel_vento_sensors_structs + i)->readings[times_executed_before]  = (short) sens_velc_vento((vel_vento_sensors_structs + i)->readings[times_executed_before - 1], (char) random());
    }
}

void gen_dir_vento(Sensor *dir_vento_sensors_structs, int nr_sensors, int times_executed_before){
   for (int i = 0; i < nr_sensors; i++)
    {
        if (times_executed_before == 0)
        {
            (dir_vento_sensors_structs + i)->readings[times_executed_before] = (short) sens_dir_vento(20, (short) random());
            continue;
        }
        
        (dir_vento_sensors_structs + i)->readings[times_executed_before] = (short) sens_dir_vento((dir_vento_sensors_structs + i)->readings[times_executed_before - 1], (short) random());
    }
}

void gen_pluvio(Sensor *pluvio_sensors_structs, Sensor *temp_sensors_structs, int nr_sensors, int times_executed_before){
   for (int i = 0; i < nr_sensors; i++)
    {
        if (times_executed_before == 0)
        {
            (pluvio_sensors_structs + i)->readings[times_executed_before] = (short) sens_pluvio(15, 20,(char) random());
            continue;
        }
        
        (pluvio_sensors_structs + i)->readings[times_executed_before] = (short) sens_pluvio((pluvio_sensors_structs + i)->readings[times_executed_before - 1], (temp_sensors_structs)->readings[(temp_sensors_structs)->readings_size - 1], (char) random());;
    }
}

void gen_hum_solo(Sensor *hum_solo_sensor_structs, Sensor *pluvio_sensor_structs, int nr_sensors, int times_executed_before){
    for (int i = 0; i < nr_sensors; i++)
    {
        if (times_executed_before == 0)
        {
            (hum_solo_sensor_structs + i)->readings[times_executed_before] = (short) sens_humd_solo(15, 15, (char) random());
            continue;
        }
        
        (hum_solo_sensor_structs + i)->readings[times_executed_before] = (short) sens_humd_atm((hum_solo_sensor_structs + i)->readings[times_executed_before - 1], (pluvio_sensor_structs)->readings[(pluvio_sensor_structs)->readings_size - 1], (char) random());
    }
}

void gen_hum_atm(Sensor *hum_atm_sensor_structs, Sensor *pluvio_sensor_structs, int nr_sensors, int times_executed_before){
   for (int i = 0; i < nr_sensors; i++)
    {
        if (times_executed_before == 0)
        {
            (hum_atm_sensor_structs + i)->readings[times_executed_before] = (short) sens_humd_atm(15, 15, (char) random());
            continue;
        }
        
        (hum_atm_sensor_structs + i)->readings[times_executed_before] = (short) sens_humd_atm((hum_atm_sensor_structs + i)->readings[times_executed_before - 1], (pluvio_sensor_structs)->readings[(pluvio_sensor_structs)->readings_size - 1], (char) random());
    }
}
