INCLUDES = daily_matrix.h random.h gen_val_sens.h sensores.h set_limit_values.h create_new_dinamic_array.h create_new_sensor.h export.h add_remove_sensors.h frequency_for_sensors.h
SOURCES =  main.c daily_matrix.c random.s sensores.s gen_val_sens.c set_limit_values.c create_new_dinamic_array.c create_new_sensor.c export.c add_remove_sensors.c frequency_for_sensors.c
OBJFILES = main.o daily_matrix.o random.o sensores.o gen_val_sens.o set_limit_values.o create_new_dinamic_array.o create_new_sensor.o export.o add_remove_sensors.o frequency_for_sensors.o
EXEC = prog

.SUFFIXES : .c .o 

.c.o:
	gcc -Wall -g -c $<
    
${EXEC}: ${OBJFILES}
	gcc -Wall -g -o ${EXEC} ${OBJFILES}

${OBJFILES}: ${SOURCES} ${INCLUDES}

run: ${EXEC}
	./${EXEC}

clean:
	rm -f ${OBJFILES} ${EXEC}