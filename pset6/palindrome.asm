## palindrome.asm -- reads a line of text and tests whether it is a palindrome.
## Added two subroutines to ignore non-alphanumeric characters and case differences.
## Register usage:
##	$t1	- A.
##	$t2	- B.
##	$t3	- the character *A.
##	$t4	- the character *B.
##	$v0	- syscall parameter / return values. 
##	$a0	- syscall parameters / subroutine parameters.
##	$a1	- syscall parameters.

		.globl  test_loop
        .globl  length_loop
        .globl  string_space
	    .text
main:				        		# SPIM starts by jumping to main.
					  				# read the string S:
	la      $a0, string_space
	li      $a1, 1024
	li      $v0, 8	            	# load "read_string" code into $v0.
	syscall

	la      $t1, string_space		# A <- S.

	la      $t2, string_space    	# we need to move B to the end
length_loop:			        	#	of the string:
	lb		$t3, ($t2)	           		# load the byte *B into $t3.
	beqz	$t3, end_length_loop       # if $t3 == 0, branch out of loop.
	addu	$t2, $t2, 1	        	# otherwise, increment B,
	b		length_loop		  			#  and repeat the loop.
end_length_loop:
	subu	$t2, $t2, 2	       		# subtract 2 to move B back past
				       				#  the '\0' and '\n'.
test_loop:
	bgeu    $t1, $t2, is_palin	 	# if A >= B, it is a palindrome.

skip_left:								# Skip non-alphanumeric from left
	lb      $t3, ($t1)         		
	move	$a0, $t3
	jal		is_alnum				# check if alphanumeric
	bnez	$v0, skip_right			# if yes, check right side
	addu	$t1, $t1, 1             # else skip this character
	b		test_loop

skip_right:								# Skip non-alphanumeric from right
	blt		$t2, $t1, is_palin		# if B < A, we're done
	lb      $t4, ($t2)	       		
	move	$a0, $t4
	jal		is_alnum				# check if alphanumeric
	bnez	$v0, compare_chars		# if yes, compare
	subu	$t2, $t2, 1             # else skip this character
	b		test_loop

compare_chars:
	move	$a0, $t3
	jal		to_lower				# convert *A to lowercase
	move	$t3, $v0
	
	move	$a0, $t4
	jal		to_lower				# convert *B to lowercase
	move	$t4, $v0
	
	bne     $t3, $t4, not_palin	  	# if not equal, not a palindrome
	addu	$t1, $t1, 1             # increment A
	subu	$t2, $t2, 1             # decrement B
	b		test_loop

# Subroutine: is_alnum
# Checks if a character is alphanumeric (0-9, A-Z, a-z)
# Input: $a0 = character
# Output: $v0 = 1 if alphanumeric, 0 otherwise
is_alnum:
	# Check '0'-'9' (48-57)
	blt		$a0, 48, not_alnum
	ble		$a0, 57, yes_alnum
	# Check 'A'-'Z' (65-90)
	blt		$a0, 65, not_alnum
	ble		$a0, 90, yes_alnum
	# Check 'a'-'z' (97-122)
	blt		$a0, 97, not_alnum
	ble		$a0, 122, yes_alnum
not_alnum:
	li		$v0, 0
	jr		$ra
yes_alnum:
	li		$v0, 1
	jr		$ra

# Subroutine: to_lower
# Converts uppercase letter to lowercase
# Input: $a0 = character
# Output: $v0 = lowercase character
to_lower:
	move	$v0, $a0
	blt		$a0, 65, to_lower_done	# if < 'A', return as-is
	bgt		$a0, 90, to_lower_done	# if > 'Z', return as-is
	addi	$v0, $a0, 32			# convert to lowercase
to_lower_done:
	jr		$ra

is_palin:	                   		# print the is_palin_msg, and exit.
	la         $a0, is_palin_msg
	li         $v0, 4
	syscall
	b          exit

not_palin:
	la         $a0, not_palin_msg	  # print the is_palin_msg, and exit.
	li         $v0, 4
	syscall

exit:			                  	# exit the program:
	li		$v0, 10	          		# load "exit" into $v0.
	syscall			          		# make the system call.

## Here is where the data for this program is stored:
	.data
string_space:	.space	1024  	# set aside 1024 bytes for the string.
is_palin_msg:	.asciiz "The string is a palindrome.\n"
not_palin_msg:	.asciiz "The string is not a palindrome.\n"
## end of palindrome.asm
