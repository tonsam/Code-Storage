# Program to do something or other
# Reads number of characters in a given string
# Created by David on that one day the power went out (9/6/2018)

.data 
str: .asciiz "Education is the most powerful weapon which you can use to change the world. -Nelson Mandela"
prompt: .asciiz "\nGiven string is: "
prompt2: .asciiz "\n\nHere is the string in reverse: \n"
ans: .asciiz "\nNumber of characters in given string (including spaces) is: "

.text
.globl main
main:
la $t1, str
li $t0, 0
Loop:
lb $t2, 0($t1)
beqz $t2, StringEnd
addi $t0, $t0, 1
addi $t1, $t1, 1
j Loop
StringEnd:
# print string
la $a0, prompt
li $v0, 4
syscall
la $a0, str
li $v0, 4
syscall
la $a0, ans
li $v0, 4
syscall
#li $a0, $t1
#li $v0, 4
syscall
# terminate
li $v0, 10
syscall



