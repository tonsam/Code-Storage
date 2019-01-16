# I love comments
# This one will reverse a string.
# Written by David Kornish, aka the snake of the festival
# Today is 9/13/18. Pretty nice.

.data
str: .asciiz "If GM had kept up with technology like the computer industry has, we would all be driving $25 cars that got 1,000 MPG-- Bill Gates"
prompt: .asciiz "The given string is = "
prompt1: .asciiz "\n\nString Count is = "
prompt2: .asciiz "\n\nRevesed String is = "
prompt3: .asciiz "\n\nSEE YOU ON THURSDAY"
array: .space 200

.text
.globl main
main:

# print prompt
la $a0, prompt
li $v0, 4
syscall

# print string
la $a0, str
li $v0, 4
syscall

li $t3, 0
la $t1, str

# get count
Loop:
lb $t2, 0($t1)
beqz $t2, StringEnd
addi $t3, $t3, 1
addi $t1, $t1, 1
j Loop

StringEnd:

# print second prompt
la $a0, prompt1
li $v0, 4
syscall

#add $a0, $t3, $zero

# print count
move $a0, $t3
li $v0, 1
syscall

# print third prompt
la $a0, prompt2
li $v0, 4
syscall

addi $t1, $t1, -1
la $t4, array

Loop2:
beqz $t3, loopEnd
lb $t5, 0($t1)
sb $t5, 0($t4)
addi $t1, $t1, -1
addi $t4, $t4, 1
addi $t3, $t3, -1
j Loop2

loopEnd:
# print reverse string
# move $a0, $t3
# li $v0, 1
la $a0, array
li $v0, 4
syscall

# print final prompt
la $a0, prompt3
li $v0, 4
syscall

li $v0, 10
syscall 



# load commands: move from memory to register
# i = immediate, a = address, b = byte(8), h = halfword(16), w = word(32), 
# lbu lhu lwu = unsigned
# n-bit unsigned range = (0 to 2^n-1)
# n-bit signed range = (-2^(n-1) to 2^(n-1)-1)
# offset(base address)

# store commands: move from register to memory
# commands are the same but with s instead of l
# addu, addiu, subu, etc = unsigned

# directives: anything with "." (.ascii, .asciiz, .data, .text, .byte, .word n1, n2, etc, .float, .double, .space, .globl etc)

# mul and div 
# if $t0 = 8, $t1 = 20, $t2 = destination
# mul $t2, $t0, $t1
# mult $t0, $t1 (saves half into hi and half into lo special registers)
# mfhi and mflo move from hi and lo
# mult $t0, $t1
# mflo $t2
# for div: lo = quotient and hi = remainder