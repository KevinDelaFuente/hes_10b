.data

# Test Case # 1
N: .word 9
table: .word 3, -1, 6, 5, 7, -3, -15, 18, 2
# Test Case # 2
#N: .word 1
#table: .word 3

min_message:  .asciiz "Min value in table: "
max_message:  .asciiz "Max value in table: "
nl: .asciiz "\n"

.text
main: 
    # Load N and table address
    lw   $t5, N            # Load N into $t5
    la   $t4, table        # Load address of table
    
    # Initialize min and max to first element
    lw   $t0, 0($t4)       
    lw   $t1, 0($t4)       
    
    li   $t3, 1            # counter starts at 1 
    addi $t4, $t4, 4       # move pointer

loop: 
    bge  $t3, $t5, break_loop   # if counter >= N, exit loop
    lw   $t2, 0($t4)            # load current element (word)
    
    # Check if less than min
    blt  $t2, $t0, reduce_min
    
check_max:
    bgt  $t2, $t1, increase_max
    
next_iteration:
    addi $t3, $t3, 1            # counter++
    addi $t4, $t4, 4            # move to next word (4 bytes)
    b    loop

reduce_min:
    move $t0, $t2               # update min
    b    check_max              # still need to check max

increase_max:
    move $t1, $t2               # update max
    b    next_iteration

break_loop:

    li   $v0, 4
    la   $a0, min_message
    syscall
    
    li   $v0, 1
    move $a0, $t0
    syscall

    li   $v0, 4
    la   $a0, nl
    syscall

    li   $v0, 4
    la   $a0, max_message
    syscall
    
    li   $v0, 1
    move $a0, $t1
    syscall

    li   $v0, 10
    syscall