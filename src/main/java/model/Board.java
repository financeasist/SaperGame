package model;

import java.util.Random;

import controller.ImgManager;
import view.CellButtonView;

/**
 * This class represents the game board. Boad contains buttons[][] which
 * contains cell[][].
 * 
 * @author Roman Grupskyi
 * @version 1.1 since 12/02/2017
 */
public class Board {
	private int countOfBombs;
	private int boardWidth;
	private int boardHeight;
	private Cell[][] cells;
	private CellButtonView[][] cellButtonViews;
	private boolean isReal = false;

	/**
	 * In the constructor we pass the size of the playing board and how many
	 * bombs it will contain.
	 * 
	 * @param width
	 * @param height
	 * @param countOfBombs
	 */
	public Board(int width, int height, int countOfBombs) {
		this.boardWidth = width;
		this.boardHeight = height;
		this.cells = new Cell[width][height];
		this.countOfBombs = countOfBombs;
		fillBoardEmptyCells();
		//setBombs();
		setDefBombs();
		unSelectCells();
		cellButtonViews = new CellButtonView[width][height];
		for (int x = 0; x != cellButtonViews.length; x++) {
			for (int y = 0; y != cellButtonViews[0].length; y++) {
				Cell cell = cells[x][y];
				cellButtonViews[x][y] = new CellButtonView(cell);
			
			}
		}

	}

	public int getCountOfBombs() {
		return countOfBombs;
	}

	public void setCountOfBombs(int countOfBombs) {
		this.countOfBombs = countOfBombs;
	}

	public int getWidth() {
		return boardWidth;
	}

	public void setWidth(int width) {
		this.boardWidth = width;
	}

	public int getHeight() {
		return boardHeight;
	}

	public void setHeight(int height) {
		this.boardHeight = height;
	}

	public Cell[][] getCells() {
		return cells;
	}

	public void setCell(Cell[][] cells) {
		this.cells = cells;
	}

	public boolean isReal() {
		return isReal;
	}

	public void setReal(boolean real) {
		this.isReal = real;
	}

	public int getBoardWidth() {
		return boardWidth;
	}

	public void setBoardWidth(int boardWidth) {
		this.boardWidth = boardWidth;
	}

	public int getBoardHeight() {
		return boardHeight;
	}

	public void setBoardHeight(int boardHeight) {
		this.boardHeight = boardHeight;
	}

	public CellButtonView[][] getCellButtons() {
		return cellButtonViews;
	}

	public void setCellButtons(CellButtonView[][] cellButtons) {
		this.cellButtonViews = cellButtons;
	}

	public void setCells(Cell[][] cells) {
		this.cells = cells;
	}

	/**
	 * does all cells on board unselect
	 */
	public void unSelectCells() {
		for (Cell[] cellsWidth : cells) {
			for (Cell cell : cellsWidth) {
				cell.setWasSelected(false);

			}
		}
	}

	/**
	 * initializes board with empty cells values;
	 */
	public void fillBoardEmptyCells() {
		for (int x = 0; x < boardWidth; x++) {
			for (int y = 0; y < boardHeight; y++) {
				cells[x][y] = new Cell(false, x, y, ImgManager.BUTTON_EMPTY, this);
			}
		}
	}

	/**
	 * method sets bombs on the board by chance, according to countOfBombs;
	 */
	public void setBombs() {
		int bombCount = 0;
		Random random = new Random();
		while (bombCount < countOfBombs) {
			int randomY = random.nextInt(boardWidth);
			int randomX = random.nextInt(boardHeight);
			if (cells[randomX][randomY].isBomb() != true) {
				cells[randomX][randomY].setHasBomb(true);
				bombCount++;
			}
		}
	}
	
	public void setDefBombs(){
		cells[1][1].setHasBomb(true);	
		cells[0][4].setHasBomb(true);
		cells[5][1].setHasBomb(true);
		cells[6][6].setHasBomb(true);
		cells[0][9].setHasBomb(true);
		cells[8][0].setHasBomb(true);
		cells[9][7].setHasBomb(true);
		cells[9][9].setHasBomb(true);
		cells[5][5].setHasBomb(true);
		cells[2][4].setHasBomb(true);
	}
	/**
	 * cheks shoud game finish or not
	 * 
	 * @param bombCount
	 * @return
	 */
	public boolean isFinish(int bombCount) {
		boolean finish = false;
		int count = 0;
		for (Cell[] row : cells) {
			for (Cell cell : row) {
				boolean isRightBomb = cell.isSuggestBomb() && cell.isBomb();
				if (isRightBomb)
					count++;
			}
		}
		if (count == bombCount) {
			finish = true;
		}
		return finish;
	}
}
