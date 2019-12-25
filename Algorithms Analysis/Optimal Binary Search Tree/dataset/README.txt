Input file:
- Each input file contains one test case.
- The first line contains 1 integer N the number of <key, freq> pairs.
- Follows N lines, each line contains two integers the i-th key and its freq.
- The input is sorted.

Output file:
- The first line of the output file contains the total number of comparisons of the optimal BST.
- Follows N-1 lines describing the optimal tree structure, each of these lines contains two integers k1 and k2, which represent an edge between the node containg k1 and the node containing k2, i.e. k1 is the parent of k2 in the optimal tree.