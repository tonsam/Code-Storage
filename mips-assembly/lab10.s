# Write MIPS that Sum all the integers from 1 to given N
# October 25, 2018

.data

p:  .asciiz"Please Input a Value for N --> "
p3:  .asciiz"\n\nGiven N valus is = "
p1: .asciiz"\n\nThe Sum of Integers from 1 to N is = "
p2: .asciiz "\n \n\nHave a Nice weekend and Please attend Tuesday Cyber Security Day "

p4: .asciiz"\n\n ******* CHECKING OUR SUM WITH FORMULA N*(N+1)/2  ********** "
p5: .asciiz"\n\nThe Sum of Integers from 1 to N   using above formula is = "

.text
.globl main
main:

li $v0, 4     
la $a0, p
syscall

li $v0, 5     
syscall     
move $t2, $v0
move $t4, $v0

li $v0, 4     
la $a0, p3
syscall

move $a0, $t2
li $v0, 1     
syscall  

  
li $t0, 0 
beqz$t2, End

Loop: 
add $t0, $t0, $t2
addi $t2, $t2, -1
bnez $t2, Loop


End:
li $v0, 4     
la $a0, p1  
syscall 

move $a0, $t0
li $v0, 1     
syscall 

li $v0, 4     
la $a0, p4  
syscall 

li $v0, 4     
la $a0, p5  
syscall 

addi $t5, $t4, 1
mul $t6, $t4, $t5
div $t6, $t6, 2

move $a0, $t6
li $v0, 1     
syscall 


li $v0, 4     
la $a0, p2 
syscall

li $v0, 10  
syscall
