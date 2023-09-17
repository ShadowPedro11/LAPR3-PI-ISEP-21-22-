.section .data
.global state
.global inc

.section .text
.global random

random:
movq state(%rip),%r11           #long state
movq inc(%rip),%r10             #long inc
movq %r11,%r9                   #long oldstate = state

movq $6364136223846793005,%r8   #     
#shlq $64,%r8     				#2⁶⁴=6364136223846793005
orq $1,%r10      				#(inc|1)
imulq %r8,%r11  				#state = oldstate * 6364136223846793005(2⁶⁴) 
addq %r10,%r11   				#state = oldstate * 6364136223846793005 + (inc|1)
movq %r11,state(%rip)			#


movl %r11d,%edi  				#unsigned int xorshifted
movl %r11d,%edx  				#
shrl $18,%edx    				#(oldstate >> 18)
xorl %edi,%edx   				#(oldstate >> 18)^ oldstate)
shrl $27,%edx    				#((oldstate >> 18) ^ oldstate) >> 27

movl %r11d,%esi  				#unsigned int rot
shrl $59,%esi    				#unsigned int rot = oldstate >> 59

movl %esi,%ecx   				#(xorshifted >> rot) | (xorshifted << ((-rot) & 31))
rorl %cl,%edx    				#(xorshifted >> rot) | (xorshifted << ((-rot) & 31))

movl %edx, %eax  				#return

end:
ret