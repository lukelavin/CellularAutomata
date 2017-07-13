# CellularAutomata
A series of small projects looking at Cellular Automata.

Cellular Automata are well-known computer models studied extensively in computer theory, mathematics, and physics. In general, cellular automata are grids of cells, with each cell in a certain state (e.g. on/off, 1/2/3). Generations advance and the grids are updated, with each cell changing states based on the states of nearby cells as specified by the ruleset.

### Elementary Cellular Automata
Elementary Cellular Automata, as popularized by Stephen Wolfram, are simple, 1-dimensional automata where each cell has 2 states: 0 or 1. The states of each cell between generations is determined by the cell's state in the previous generation as well as the state of the two adjacent cells in the previous generation. There are 8 permutations of these "neighborhood" cells:

![Alt text](/ElementaryCellularAutomata/photos/ruleset.png?raw=true "The 8 permutations")

The result of each permutation--a 0 or a 1--composes the 8 bit ruleset represented as a binary number. This is also how the rulesets are named. For example, the unique Rule 30 has an 8 bit ruleset of "00011110." Here's a look at some Elementary Cellular Automata:

![Alt text](/ElementaryCellularAutomata/photos/Elementary Cellular Automata0.02017-07-12T20_20_44.690.png?raw=true "Rule 30") 
![Alt text](/ElementaryCellularAutomata/photosElementary Cellular Automata0.02017-07-12T20_22_08.003.png?raw=true "Rule 18") 