.data

# Test Case # 1
N1: .word 9
table1: .word 3, -1, 6, 5, 7, -3, -15, 18, 2
# Test Case # 2
N2: .word 1
table2: .word 3

min_message:  .ascii "Min value in table: "
max_message:  .ascii "Max value in table: "
nl: .asciiz "\n"

.text
main: 
    move  $t0, table1   #initialize min to 0
    move  $t1, table1   #initialize max to 0
    move  $t3, $0   #initialize counter to 0
    la  $t4, table

loop: 
    bge $t3, N, break_loop
    lb  $t2, ($t4)
    blt ($t2), ($t0) reduce_min

    
    bgt ($t2), ($t1) increase_max
  
    addi $t3, $t3, 1
    addi $t4, $t4, 1 
    b loop

reduce_min:
    move ($t0), ($t2)
    b loop

increase_max:
    move ($t1), ($t2)
    b loop

break_loop:
    # print min_message
    li   $v0, 4
    la   $a0, min_message
    syscall
    # pring min value
    li   $v0, 1
    move   $a0, $t0
    syscall

    li   $v0, 4
    la   $a0, nl
    syscall

    # print max_message
    li   $v0, 4
    la   $a0, max_message
    syscall
    # pring max value
    li   $v0, 1
    move   $a0, $t1
    syscall

    # Exit
    li   $v0, 10
    syscall