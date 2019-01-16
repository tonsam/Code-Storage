# I love comments
# This program converts Celcius to Fahrenheit
# Written by David Kornish, aka the snake of the festival
# Today is 9/20/2018. Pretty nice.

.data #let processor know we will be submitting data to program now

Celcius: .word 44

Prompt1: .asciiz "Enter a temperature in Celcius: " #in unused memory store string
Prompt2: .asciiz "Given temp in Celcius: " #in unused memory store this string
Prompt3: .asciiz " degrees Celcius. \n\nThe temp in Fahrenheit: " #in unused memory store this string

.text #enables text input / output, kind of like String.h in C++

.globl main

main: #main function is always called in any mips program, so the program will start here with actual assembly code

	la $a0, Prompt1 #load address Ask_Input from memory and store it into arguement register 0
	li $v0, 4 #loads the value 4 into register $v0 which is the op code for print string
	syscall #reads register $v0 for op code, sees 4 and prints the string located in $a0

	la $a0, Celcius #sets $a0 to point to the space allocated for writing a word
	li $v0, 5 #load op code for getting an integer from the user into register $v0
	syscall #reads register $v0 for op code, sees 8 and asks user to input a string, places string in reference to $a0

	move $t0, $v0
	
	# print prompt 2
	la $a0, Prompt2
	li $v0, 4
	syscall
	
	# Printing out the number
    li $v0, 1
    move $a0, $t0
    syscall

	mul $t1, $t0, 9
	div $t2, $t1, 5
	add $t3, $t2, 32
	
	la $a0, Prompt3
	li $v0, 4
	syscall
	
	move $a0, $t3
	li $v0, 1
	syscall

	li $v0, 10 #loads op code into $v0 to exit program
	syscall #reads $v0 and exits program