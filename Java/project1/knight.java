import java.io.BufferedReader;

/**
 * The knight class provides a static main method to read the dimensions of a
 * board and print a solution of the knight tour.
 *
 * See <a href="http://en.wikipedia.org/wiki/Knight%27s_tour">Wikipedia:
 * Knight's tour</a> for more information.
 *
 * The algorithm employed is similar to the standard backtracking
 * <a href="http://en.wikipedia.org/wiki/Eight_queens_puzzle">eight queens
 * algorithm</a>.
 *
 */

public class knight {

	static long recurCalls = 0; // number of recursive calls to solve()
	static final int[][] direction = { { -2, -1 }, { -2, 1 }, { -1, -2 }, { -1, 2 }, { 1, -2 }, { 1, 2 }, { 2, -1 },
			{ 2, 1 } };
	static int M = 4;
	static int N = 4;
	static int[][] Board;
	static int[][] longestPartial;

	private static BufferedReader read;

	static boolean solve(int step, int i, int j) {
		// recursive backtrack search.
		recurCalls++;
		Board[i][j] = step;

		if (step == N * M)
			return true; // all positions are filled

		for (int k = 0; k < 8; k++) {
			int i1 = i + direction[k][0];
			int j1 = j + direction[k][1];
			if (isInBounds(i1, j1, true))
				if (solve(step + 1, i1, j1))
					return true;
		}

		Board[i][j] = 0; // no more next position, reset on backtrack
		return false;
	}

	static boolean isInBounds(int i, int j, boolean requireOpen) {
		boolean isInBounds = 0 <= i && i < N && 0 <= j && j < M;

		if (isInBounds && requireOpen)
			return Board[i][j] == 0;

		return isInBounds;
	}

	static boolean solveWarnsdorf(int step, int i, int j) {
		// recursive backtrack search.
		recurCalls++;
		Board[i][j] = step;

		if (step == N * M)
			return true; // all positions are filled

		int[][] sortedDirections = sortDirectionsByFewestOnwardMoves(i, j);

		for (int k = 0; k < 8; k++) {
			int i1 = i + sortedDirections[k][0];
			int j1 = j + sortedDirections[k][1];
			if (isInBounds(i1, j1, true))
				if (solveWarnsdorf(step + 1, i1, j1))
					return true;
		}

		Board[i][j] = 0; // no more next position, reset on backtrack
		return false;
	}

	static int[][] sortDirectionsByFewestOnwardMoves(int i, int j) {
		int[][] sortedDirections = copyArray(direction);

		// Selection sort
		int min;
		int onwardMovesOfMin;
		for (int x = 0; x < sortedDirections.length; x++) {
			// Assume first element is min
			min = x;
			onwardMovesOfMin = getOnwardMoves(i, j, sortedDirections[x]);
			for (int y = x + 1; y < sortedDirections.length; y++) {
				int onwardMovesOfCurr = getOnwardMoves(i, j, sortedDirections[y]);

				if (onwardMovesOfCurr < onwardMovesOfMin) {
					min = y;
					onwardMovesOfMin = getOnwardMoves(i, j, sortedDirections[min]);
				}
			}
			if (min != x) {
				int[] temp = sortedDirections[x];
				sortedDirections[x] = sortedDirections[min];
				sortedDirections[min] = temp;
			}
		}

		return sortedDirections;
	}

	static int[][] copyArray(int[][] array) {
		int[][] copy = new int[array.length][array[0].length];
		for (int x = 0; x < array.length; x++) {
			System.arraycopy(array[x], 0, copy[x], 0, array[0].length);
		}

		return copy;
	}

	static int getOnwardMoves(int i, int j, int[] coord) {
		i = i + coord[0];
		j = j + coord[1];

		int moves = 0;

		for (int k = 0; k < 8; k++) {
			int i1 = i + direction[k][0];
			int j1 = j + direction[k][1];

			if (isInBounds(i1, j1, true)) {
				moves++;
			}
		}

		return moves;
	}

	static boolean knightMoveAway(int i, int j) {
		for (int k = 0; k < 8; k++) {
			int i1 = i + direction[k][0];
			int j1 = j + direction[k][1];

			if (isInBounds(i1, j1, false) && Board[i1][j1] == 1) {
				return true;
			}
		}

		return false;
	}

	static boolean solveClosed(int step, int i, int j) {
		// recursive backtrack search.
		recurCalls++;
		Board[i][j] = step;

		if (step == N * M) {
			boolean is1MoveAway = knightMoveAway(i, j);

			if (is1MoveAway) {
				return true;
			} else {
				Board[i][j] = 0;
				return false;
			}
		}

		int[][] sortedDirections = sortDirectionsByFewestOnwardMoves(i, j);

		for (int k = 0; k < 8; k++) {
			int i1 = i + sortedDirections[k][0];
			int j1 = j + sortedDirections[k][1];
			if (isInBounds(i1, j1, true))
				if (solveClosed(step + 1, i1, j1))
					return true;
		}

		Board[i][j] = 0; // no more next position, reset on backtrack
		return false;
	}

	static boolean solveClosedFixed() {
		int i = 0;
		int j = 0;

		// Set initial step
		int step = 1;
		Board[i][j] = step;

		// Fix the first step
		step++;
		i += 2;
		j += 1;
		Board[i][j] = step;

		// Recursively find the closed tour
		return solveClosed(step, i, j);
	}

	static boolean solveLongestPartial(int step, int i, int j, int longest) {
		// recursive backtrack search.
		recurCalls++;
		Board[i][j] = step;

		if (step == N * M)
			return true; // all positions are filled

		if (step > longest) {
			longest = step;
			longestPartial = copyArray(Board);
		}

		int[][] sortedDirections = sortDirectionsByFewestOnwardMoves(i, j);

		for (int k = 0; k < 8; k++) {
			int i1 = i + sortedDirections[k][0];
			int j1 = j + sortedDirections[k][1];
			if (isInBounds(i1, j1, true))
				if (solveLongestPartial(step + 1, i1, j1, longest))
					return true;
		}

		Board[i][j] = 0; // no more next position, reset on backtrack
		return false;
	}

	static void printBoard(int[][] solution, String name) {
		System.out.println(name);

		for (int i = 0; i < N; ++i) {
			for (int j = 0; j < M; j++)
				System.out.print("------");
			System.out.println("-");

			for (int j = 0; j < M; ++j)
				System.out.format("| %3d ", solution[i][j]);
			System.out.println("|");
		}

		for (int j = 0; j < M; j++)
			System.out.print("------");
		System.out.println("-");
	}

	/*
	 * read in the dimensions of Knight's tour board and try to find one.
	 */
	public static void main(String[] args) {
		/*
		 * read = new BufferedReader(new InputStreamReader(System.in)); try {
		 * System.out.print("Please enter the number of rows : "); N =
		 * Integer.parseInt(read.readLine());
		 * 
		 * System.out.print("Please enter the number of columns : "); M =
		 * Integer.parseInt(read.readLine()); } catch (Exception ex) {
		 * System.err.println("Error: " + ex); ex.printStackTrace(); } if (N >
		 * M) { int i = N; N = M; M = i; }
		 */
		// 5010
		// 25

		for (int x = 3; x <= 6; x++) {
			for (int y = 3; y <= 6; y++) {
				N = x;
				M = y;

				// create Board and set each entry to 0
				Board = new int[N][M];

				System.out.println("---------------------------" + N + "x" + M + "-----------------------------");

				// Regular
				resetBoard();
				if (solve(1, 0, 0))
					printBoard(Board, "Regular");
				else
					System.out.println("No tour was found.");
				System.out.println("Number of recursive calls = " + recurCalls);

				// Warnsdorf
				resetBoard();
				if (solveWarnsdorf(1, 0, 0))
					printBoard(Board, "Warnsdorf");
				else
					System.out.println("No tour was found.");
				System.out.println("Number of recursive calls = " + recurCalls);

				// Closed
				resetBoard();
				if (solveClosed(1, 0, 0))
					printBoard(Board, "Closed");
				else
					System.out.println("No tour was found.");
				System.out.println("Number of recursive calls = " + recurCalls);
				// 98850173

				resetBoard();
				if (solveClosedFixed())
					printBoard(Board, "Closed Fixed");
				else
					System.out.println("No tour was found.");
				System.out.println("Number of recursive calls = " + recurCalls);

				resetBoard();
				if (solveLongestPartial(1, 0, 0, 0))
					printBoard(Board, "Longest Partial");
				else {
					printBoard(longestPartial, "Longest Partial");
				}
				System.out.println("Number of recursive calls = " + recurCalls);
			}
		}
	}

	static void resetBoard() {
		recurCalls = 0;
		for (int i = 0; i < N; i++)
			for (int j = 0; j < M; j++)
				Board[i][j] = 0;
	}

}