import java.util.Scanner;

class Caixas {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Read the number of boxes and stacks for the first test case
        int numBoxes = scanner.nextInt();
        int numStacks = scanner.nextInt();

        // 2D array representing the stacks of boxes
        int[][] stacksArray = new int[numBoxes][numStacks];

        int numBoxesInStack; // Stores how many boxes are in each stack
        int boxesToRemove; // Stores the number of boxes that need to be removed
        int targetBoxRow = 0; // Stores the row where the target box (box 1) is

        do {
            // Read the boxes in each stack
            for (int currentStack = 0; currentStack < numStacks; currentStack++) {
                numBoxesInStack = scanner.nextInt();
                int rowPosition = 0;

                // Fill the stack with boxes, from bottom to top
                while (rowPosition < numBoxesInStack) {
                    stacksArray[rowPosition][currentStack] = scanner.nextInt();
                    rowPosition++;
                }

                // Fill the rest of the stack with zeros if there aren't enough boxes
                while (rowPosition < numBoxes) {
                    stacksArray[rowPosition][currentStack] = 0;
                    rowPosition++;
                }
            }

            // Search for the target box (box 1), iterating through stacks and rows
            boolean foundTargetBox = false;
            int row = 0;

            while (row < numBoxes && !foundTargetBox) {
                int currentStack = 0;
                while (currentStack < numStacks && !foundTargetBox) {
                    // If the target box is found, record its position
                    if (stacksArray[row][currentStack] == 1) {
                        foundTargetBox = true;
                        targetBoxRow = row;
                    }
                    currentStack++;
                }
                row++;
            }

            // Calculate the number of boxes that need to be removed to access the target box
            boxesToRemove = 0;
            row = numBoxes - 1;
            int currentStack = 0;
            foundTargetBox = false;

            // Iterate from top to bottom until the target box's position, counting boxes to be removed
            while (!foundTargetBox) {
                while (row >= targetBoxRow && !foundTargetBox) {
                    // If the target box is found, stop counting
                    if (stacksArray[row][currentStack] == 1) {
                        foundTargetBox = true;
                    }
                    // If the box is not the target, count it as a box to be removed
                    if (stacksArray[row][currentStack] != 0 && !foundTargetBox) {
                        boxesToRemove++;
                    }
                    row--;
                }
                currentStack++;
                row = numBoxes - 1;
            }

            // Check if it's worth unstacking from the right, comparing the effort
            if (boxesToRemove > numBoxes / 2) {
                int removeRight = 0;
                row = numBoxes - 1;
                currentStack = numStacks - 1;
                foundTargetBox = false;

                // Iterate from top to bottom of the last stack until finding the target box, calculating the number of removals from the right
                while (!foundTargetBox) {
                    while (row >= 0 && !foundTargetBox) {
                        if (stacksArray[row][currentStack] == 1) {
                            foundTargetBox = true;
                        }
                        if (stacksArray[row][currentStack] != 0 && !foundTargetBox) {
                            removeRight++;
                        }
                        row--;
                    }
                    currentStack--;
                    row = numBoxes - 1;
                }

                // If removing from the right is more efficient, update boxesToRemove
                boxesToRemove = Math.min(boxesToRemove, removeRight);
            }

            // Display the number of boxes that need to be removed to access the target box
            System.out.println(boxesToRemove);

            // Read the number of boxes and stacks for the next test case
            numBoxes = scanner.nextInt();
            numStacks = scanner.nextInt();

        // Continue until both box and stack values are 0
        } while (numBoxes != 0 || numStacks != 0);
    }
}