# recursive Towers of Hanoi

import time

n = input("How many disks are there in your Towers of Hanoi?\n")
numMoves = 0

def move (f,t):
  print("Moving disc from {} to {}!".format(f,t))
  global numMoves
  numMoves = numMoves + 1

def hanoi(n,f,h,t):
  if n==0:
    pass
  else:
    hanoi(n-1,f,t,h)
    move(f,t)
    hanoi(n-1,h,f,t)

start = time.time()
hanoi(n, "A", "B", "C")
end = time.time()

print("\nCompleted {} moves in {} seconds".format(numMoves, end-start))
