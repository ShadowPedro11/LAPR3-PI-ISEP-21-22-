#include <stdio.h>
#include "add_remove_sensors.h"
#include "struct.h"

void select_sensors(int status, int* valuesConfig){
	
	int choice;
	
printf("1. Temperature\n");
printf("2. Wind Velocity\n");
printf("3. Wind Direction\n");
printf("4. Ground Humidity\n");
printf("5. Atmospheric Humidity\n");
printf("6. Rainfall\n");
printf("Enter your choice: ");
scanf("%d", &choice);
switch(choice) {
	case 1:
	if (status == 1){
		int new_size =choose_number_of_sensors_to_add(valuesConfig[0]);
		valuesConfig[0] = new_size;
	} else {
		int new_size =choose_number_of_sensors_to_remove(valuesConfig[0]);
		valuesConfig[0] = new_size;
	}
	break;
	case 2:
	if (status == 1){
		int new_size =choose_number_of_sensors_to_add(valuesConfig[1]);
		valuesConfig[1] = new_size;
	} else {
		int new_size =choose_number_of_sensors_to_remove(valuesConfig[1]);
		valuesConfig[1] = new_size;
	}
	break;
	case 3:
	if (status == 1){
		int new_size =choose_number_of_sensors_to_add(valuesConfig[2]);
		valuesConfig[2] = new_size;
	} else {
		int new_size =choose_number_of_sensors_to_remove(valuesConfig[2]);
		valuesConfig[2] = new_size;
	}
	break;
	case 4:
	if (status == 1){
		int new_size =choose_number_of_sensors_to_add(valuesConfig[3]);
		valuesConfig[3] = new_size;
	} else {
		int new_size =choose_number_of_sensors_to_remove(valuesConfig[3]);
		valuesConfig[3] = new_size;
	}
	break;
	case 5:
	if (status == 1){
		int new_size =choose_number_of_sensors_to_add(valuesConfig[4]);
		valuesConfig[4] = new_size;
	} else {
		int new_size =choose_number_of_sensors_to_remove(valuesConfig[4]);
		valuesConfig[4] = new_size;
	}
	break;
	case 6:
	if (status == 1){
		int new_size =choose_number_of_sensors_to_add(valuesConfig[5]);
		valuesConfig[5] = new_size;
	} else {
		int new_size =choose_number_of_sensors_to_remove(valuesConfig[5]);
		valuesConfig[5] = new_size;
	}
	break;
	default:
	printf("Invalid choice.\n");
	break;
	}
}

int choose_number_of_sensors_to_add(int size){
	int number;
	printf("How many sensors you want to add?\n");
	scanf("%d", &number);
	return size+ number;
}

int choose_number_of_sensors_to_remove(int size){
	int number;
	printf("How many sensors you want to remove?\n");
	scanf("%d", &number);
	if((size - number)<0){
		printf("It was not possible to remove the amount of sensors selected");
		return size;
	}
	return size- number;
}
	

