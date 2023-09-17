.section .data
    .equ IS_RAINNING, 20
    .equ HIGH_TEMP, 10

.section .text
    .global sens_temp
    .global sens_velc_vento
    .global sens_dir_vento
    .global sens_humd_atm
    .global sens_humd_solo
    .global sens_pluvio

sens_temp:
    cmp $0, %sil
    je is_zero_temp

    movsbl %sil, %eax
    cltd

    movl $5, %ecx

    idivl %ecx

    movb %dl, %al
    incb %dil
    addb %dil, %al

    jmp end_temp

is_zero_temp:
    addb $2, %sil
    jmp sens_temp

end_temp:
ret

#=================================

sens_velc_vento:
    cmp $0, %sil
    je is_zero_vel_vent

    movsbl %sil, %eax
    cltd

    movl $25, %ecx

    idivl %ecx

    movb %dl, %al
    incb %dil
    addb %dil, %al

    jmp end_vel_vent

is_zero_vel_vent:
    addb $8, %sil
    jmp sens_velc_vento

end_vel_vent:
ret

#=================================

sens_dir_vento:
    cmp $0, %si
    je is_zero_dir_vento

    movswl %si, %eax
    cltd

    movl $10, %ecx

    idivl %ecx

    movw %dx, %ax
    incw %di
    addw %di, %ax
    jmp is_greater_than_359

is_zero_dir_vento:
    movw %di, %ax
    incw %ax
    jmp is_greater_than_359

is_greater_than_359:
    cmp $359, %ax
    jl end_dir_vent

    movswl %ax, %eax
    cltd

    movl $359, %ecx
    idivl %ecx

    movw %dx, %ax
end_dir_vent:
ret

#=================================

sens_humd_atm:
    cmp $0, %dl
    je is_zero_humd_atm

    movsbl %dl, %eax
    cbtw

    movl $0, %edx
    cmp $IS_RAINNING, %sil
    jg high_pluv_atm

    movl $5, %ecx
    idivl %ecx
    jmp cal_humd_atm
    
high_pluv_atm:
    movl $50, %ecx
    idivl %ecx

cal_humd_atm:
    movb %dl, %al
    incb %dil
    addb %dil, %al
    cbtw
    movw $101, %cx
    idivw %cx
    movb %dl, %al
    jmp end_humd_atm

is_zero_humd_atm:
    movb %dil, %al
    incb %al

end_humd_atm:

ret

#=================================

sens_humd_solo:
    cmp $0, %dl
    je is_zero_humd_solo

    mov %dl, %al
    cbtw

    movw $0, %dx
    cmp $IS_RAINNING, %sil
    jg high_pluv_solo

    movw $5, %cx
    idivw %cx
    jmp cal_humd_solo

high_pluv_solo:
    movw $50, %cx
    idivw %cx

cal_humd_solo:
    movb %dl, %al
    incb %dil
    addb %dil, %al
    cbtw
    movw $101, %cx
    idivw %cx
    movb %dl, %al
    jmp end_humd_solo

is_zero_humd_solo:
    movb %dil, %al
    incb %al

end_humd_solo:

ret

#=================================

sens_pluvio:
    movw %dx, %ax
    movw $0, %dx
    cmp $HIGH_TEMP, %sil
    jg high_temp_pluv

low_temp:
    movw $11, %cx
    idivw %cx

    cmp $0, %dil
    je null_pluv

    movb %dil, %al
    addb %dl, %al
    incb %al
    jmp end_pluv

high_temp_pluv:
    movw $7, %cx
    idivw %cx

    cmp $0, %dil
    je null_pluv

    movb %dil, %al
    addb %dl, %al
    incb %al
    jmp end_pluv

null_pluv:
    cmp $0, %dl
    jg neg_random_comp

    movb %dil, %al
    incb %al
    addb %dl, %al
    jmp end_pluv

neg_random_comp:
    movb %dil, %al
    incb %al

end_pluv:
ret
