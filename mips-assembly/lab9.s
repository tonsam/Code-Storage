# Reverse a String   WITH  STACK
# OCTOBER 18, 2018 

.data
str:  .asciiz "If GM had kept up with technology like the computer industry has, we would all be driving $25 cars   that got 1,000 MPG-- Bill Gates"
ans:  .asciiz "The Original Sting is =     "
count: .asciiz" \n\n String Count is =  "
ans1:  .asciiz "\n\n Reversed String is =      "
go : .asciiz " \n\n   YOU HAVE A NICE WEEKEND, SEE YOU ON TUESDAY   \n"

.text
.globl  main
main:

la $a0, ans
li $v0, 4
syscall

la $a0, str
li $v0, 4
syscall

la $t1, str
li $t3, 0

Loop:
lb $t0, 0($t1)
beqz $t0, strend
addi $sp, $sp, -1
sb $t0, 0($sp)
addi $t3, $t3, 1
addi $t1, $t1, 1
j Loop

strend: 

la $a0, count 
li $v0, 4
syscall

move  $a0, $t3 
li $v0, 1
syscall

la $t1, str

Loop1:
lb $t0, 0($t1)
beqz $t0, strend1

lb $t5, 0($sp)
sb $t5, 0($t1)

addi $sp, $sp, 1

addi $t1, $t1, 1
j Loop1
strend1: 

la $a0, ans1
li $v0, 4
syscall

la $a0, str
li $v0, 4
syscall

la $a0, go 
li $v0, 4
syscall

li $v0, 10
syscall
