package controller;

import model.Board;
import model.Cell;

/**
 * class created using Singleton pattern. This class counts how many flags user
 * can use.
 * 
 * @author Roman Grupskyi
 * @version 1.0 since 02.2017
 */
public class FlagsCountManager {
	private int allFlagsCount;
	private int availableFlagsCount;
	private static FlagsCountManager flagsControllerInstance;

	private FlagsCountManager(int allFlagsCount) {
		this.allFlagsCount = allFlagsCount;
	}

	/**
	 * allFlagsCount equals bombsCount on board
	 * 
	 * @param allFlagsCount
	 * @return
	 */
	public static FlagsCountManager getFlagsControllerInstance(int allFlagsCount) {
		if (flagsControllerInstance == null) {
			flagsControllerInstance = new FlagsCountManager(allFlagsCount);
		}
		return flagsControllerInstance;
	}

	public int getAllFlagsCount() {
		return allFlagsCount;
	}

	public void setAllFlagsCount(int allFlagsCount) {
		this.allFlagsCount = allFlagsCount;
	}

	public int getAvailableFlagsCount() {
		return availableFlagsCount;
	}

	public void setAvailableFlagsCount(int availableFlagsCount) {
		this.availableFlagsCount = availableFlagsCount;
	}

	/**
	 * counts availableFlags count
	 * 
	 * @return count of available flags
	 */
	public void countAvailableFlags(Board board) {
		int countFlags = 0;
		Cell[][] cells = board.getCells();
		for (Cell[] cellsRow : cells) {
			for (Cell cell : cellsRow) {
				if (cell.isSuggestBomb())
					countFlags++;
			}
		}
		if (countFlags <= allFlagsCount)
			availableFlagsCount = allFlagsCount - countFlags;
		else
			availableFlagsCount = 0;
	}

	/**
	 * increments availableFlagsCount;
	 */
	public void incrementAvailableFlagsCount() {
		this.availableFlagsCount++;
	}
}
