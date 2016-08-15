BFS:
TIME COMPLEXITY: O(b^d)
where b = max branching factor and d = overall depth of the tree
In this case it's O(4^d) because the max branching factor is 4.

DFS:
Basically the same as BFS
TIME COMPLEXITY: O(b^d)
where b = max branching factor and d = overall depth of the tree
In this case it's O(4^d) because the max branching factor is 4.

GBFS:

ASTAR:
TIME COMPLEXITY: O(b^d)
where b = max branching factor and d = overall depth of the tree
In this case it's O(4^d) because the max branching factor is 4.

This is the worst case time complexity. The AStar Algorithm has 2 heuristics:
h(1) = misplace tiles + tiles out of their column and row
h(2) = manhattan distance + tiles out of their column and row

With the addition of 

DLS:
TIME COMPLEXITY: O(b^l)
where b = max branching factor and l = limit provided for the tree
In this case O(4^l) because the max branching factor is 4.
