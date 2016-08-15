BFS:
TIME COMPLEXITY: O(b^d)
where b = max branching factor and d = overall depth of the tree
In this case it's O(4^d) because the max branching factor is 4.

DFS:
Basically the same as BFS
TIME COMPLEXITY: O(b^d)
where b = max branching factor and d = overall depth of the tree
In this case it's O(4^d) because the max branching factor is 4.

DLS:
TIME COMPLEXITY: O(b^l)
where b = max branching factor and l = limit provided for the tree
In this case O(4^l) because the max branching factor is 4.

GBFS:
TIME COMPLEXITY: O(b^d)
where b = max branching factor and d = overall depth of the tree
In this case it's O(4^d) because the max branching factor is 4.

This is the worst case complexity. The GBFS algorithm has 2 heuristics:
h(1): misplaced tiles
h(2): manhattan distance of each tile

While the worst case has been established, by using the 2 heuristics GBFS
runs much fast that BFS and DFS

ASTAR:
TIME COMPLEXITY: O(b^d)
where b = max branching factor and d = overall depth of the tree
In this case it's O(4^d) because the max branching factor is 4.

This is the worst case time complexity. The AStar Algorithm has 2 heuristics:
h(1): misplaced tiles + tiles out of their column and row
h(2): manhattan distance of each tile + tiles out of their column and row

With the addition of the tiles out of their column and row to the same
heuristics used by GBFS, there is additional time needed for finding the
optimal path. Yet, the path is always shorter.
