# Rabbit problem using looping
# Written on Wed, October 31, 2018

.data

p5: .asciiz " Enter the number of months you want to calculate  " 

p1: .asciiz " \n\n Number of Rabbit at the end of first year or begining of  " 
p11: .asciiz  " month is = "

p2: .asciiz " \n\n Do Factorial Problem from your book  on Page 101 "
p3: .asciiz "  \n \n You have a nice weekend "

.text
.globl main
main:

la $a0, p5
li $v0, 4
syscall

li $v0,5
syscall
move $s0, $v0

la $a0, p1
li $v0, 4
syscall


move $a0, $s0
li $v0,1
syscall

la $a0, p11
li $v0, 4
syscall

move $a0, $s0

jal Rabbit

move $a0, $v0
li $v0,1
syscall



la $a0, p2
li $v0, 4
syscall

la $a0, p3
li $v0, 4
syscall


li $v0, 10
syscall


#  Rabbit Procedure using Loops
#  $a0 holds number of months
#  $t0 holds second last element value
#  $v0 holds last element value
#  $t2 holds current element value



Rabbit:

move $v0, $a0
blt $a0, 2, done

li $t0, 0
li $v0, 1

Loop:

add $t1, $t0, $v0
move $t0, $v0
move $v0, $t1
addi $a0, $a0, -1

bgt $a0, 1, Loop



done:
jr $ra



# Rabbit problem using Recursive method
# Written on Wed, October 31, 2018

.data

p6: .asciiz " Rabbit Program using recursive and using Fibonacci Series  " 

p5: .asciiz " \n\n\n Enter the number of months you want to calculate  " 

p1: .asciiz " \n\n Number of Rabbit at the end of first year or begining of  " 
p11: .asciiz  " month is = "

p2: .asciiz " \n\n Do Factorial Problem from your book  on Page 101 "
p3: .asciiz "  \n \n You have a nice weekend "

.text
.globl main
main:

la $a0, p6
li $v0, 4
syscall

la $a0, p5
li $v0, 4
syscall

li $v0,5
syscall
move $s0, $v0

la $a0, p1
li $v0, 4
syscall


move $a0, $s0
li $v0,1
syscall

la $a0, p11
li $v0, 4
syscall

move $a0, $s0

jal Rabbit

move $a0, $v0
li $v0,1
syscall



la $a0, p2
li $v0, 4
syscall

la $a0, p3
li $v0, 4
syscall


li $v0, 10
syscall


#  Rabbit Procedure using Recursive
#  $a0 holds number of months
#  $s0 holds Rabbit(n-1)
#  $v0 return value




Rabbit:

addi $sp, $sp, -12
sw $a0, 0($sp)
sw $s0, 4($sp)
sw $ra, 8($sp)


bgt $a0, 1, Notone
move $v0, $a0        #  fib0=0,  fib 1=1 
b Restore    




Notone:

addi $a0, $a0, -1

jal Rabbit     # fib(n-1)
move $s0, $v0

addi $a0, $a0, -1
jal Rabbit      #  fib(n-2)

add $v0, $v0, $s0      # fib (n)= fin(n-2)+fib(n-1)



Restore: 

lw $a0, 0($sp)
lw $s0, 4($sp)
lw $ra, 8($sp)
addi $sp, $sp, 12
jr $ra


