# COSC 300: ASSEMBLY PROGRAMMING—LAB 8
#  MIPS code for factorial function using procedure

.data

prompt: .asciiz " \n \n Enter the value for N ( less than 13 and -ve value to break)   "
ans:    .asciiz " \n  N factorial is = "
end:    .asciiz  " You Have a Nice Weekend  "

.text
.globl main
main:



# MAIN PROGRAM 

addi $sp, $sp, -8

Loop:

	la $a0, prompt
	li $v0, 4
	syscall

	li $v0, 5
	syscall

	bltz $v0, Quit

	sw $v0, 0($sp)

	jal fact

	la $a0, ans
	li $v0, 4
	syscall

	
	lw $a0, 4($sp)
	
	li $v0, 1
	syscall

b Loop


Quit:
	addi $sp, $sp, 8
	
	la $a0, end
	li $v0, 4
	syscall

	li $v0, 10
	syscall

#*************************************************

# PROCEDURE PART 



fact:
	lw $a0, 0($sp)
	bltz $a0, zvalue

	addi $t1, $a0, -12
	bgtz $t1, zvalue

	addi $sp, $sp, -16
	sw $ra,12($sp)

	sw $a0,8($sp)
	slti $t0, $a0,2

	beqz $t0 go
	li $v0,1
	b factret


go:

	addi $a0, $a0, -1
	sw $a0, 0($sp)
	jal fact


	lw $v0, 4($sp)
	lw $ra, 12($sp)
	lw $a0, 8($sp)

 	mult $v0, $a0
	mflo $v0

factret:

	addiu $sp, $sp, 16
	sw $v0, 4($sp)
	jr $ra



zvalue:
	sw $0, 4($sp)
	jr $ra







