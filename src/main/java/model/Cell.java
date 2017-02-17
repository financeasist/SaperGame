package model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import org.apache.log4j.Logger;

import controller.CellButtonDrawManager;
import controller.FlagsCountManager;
import view.CellButtonView;
import view.StartFrameView;

/**
 * This class represents Cell. The basic idea is that each cell knows itself and
 * each cell on the board.
 * 
 * @author Roman Grupskyi
 * @version 1.2 since 12/02/2017
 */
public class Cell {
	static Logger log = Logger.getLogger(Cell.class.getName());
	private boolean hasBomb;
	private boolean suggestBomb = false;
	private boolean suggestEmpty = false;
	private int positionX;
	private int positionY;
	private int bombArround;
	private String currentStateImgType;
	private Board board;
	private boolean wasSelected;
	private boolean needsToOpen;

	/**
	 * in the constructor we pass parametr board, which contains information
	 * about all the cells it contains
	 * 
	 * @param hasBomb
	 * @param positionX
	 * @param positionY
	 * @param currentStateImgType
	 * @param bord
	 */
	public Cell(boolean hasBomb, int positionX, int positionY, String currentStateImgType, Board bord) {

		this.hasBomb = hasBomb;
		this.positionX = positionX;
		this.positionY = positionY;
		this.currentStateImgType = currentStateImgType;
		this.board = bord;
	}

	public boolean isBomb() {
		return hasBomb;
	}

	public void setHasBomb(boolean hasBomb) {
		this.hasBomb = hasBomb;
	}

	public boolean isSuggestBomb() {
		return suggestBomb;
	}

	public void setSuggestBomb(boolean suggestBomb) {
		this.suggestBomb = suggestBomb;
	}

	public boolean isWasSelected() {
		return wasSelected;
	}

	public void setWasSelected(boolean wasSelected) {
		this.wasSelected = wasSelected;
	}

	public boolean isSuggestEmpty() {
		return suggestEmpty;
	}

	public void setSuggestEmpty(boolean suggestEmpty) {
		this.suggestEmpty = suggestEmpty;
	}

	public int getPositionX() {
		return positionX;
	}

	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}

	public int getPositionY() {
		return positionY;
	}

	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}

	public int getBombArround() {
		return bombArround;
	}

	public void setBombArround(int bombArround) {
		this.bombArround = bombArround;
	}

	public Board getBord() {
		return board;
	}

	public void setBord(Board bord) {
		this.board = bord;
	}

	public String getCurrentStateImgType() {
		return currentStateImgType;
	}

	public void setCurrentStateImgType(String currentStateImgType) {
		this.currentStateImgType = currentStateImgType;
	}

	public boolean isNeedsToOpen() {
		return needsToOpen;
	}

	public void setNeedsToOpen(boolean needsToOpen) {
		this.needsToOpen = needsToOpen;
	}

	public boolean isEmpty() {

		return this.bombArround == 0;
	}

	/**
	 * this is the main method of this application. he finds all neighbours
	 * current cell and calculates field bombArround. Then, if the current cell
	 * is empty, calls itself recursively until all around empty cells will not
	 * open. (loging used for easier understanding how this work or debuging).
	 */
	public void findCellsArround() {
		Cell neibourCell;
		log.debug("start findCellsArround()  [" + positionX + "]" + "[" + positionY + "]");
		Cell[][] allCells = board.getCells();
		int boardWidth = board.getBoardWidth();
		int boardHeight = board.getBoardHeight();
		int bombCount = 0;
		List<Cell> neibourCells = new ArrayList<Cell>();
		for (int deltaX = -1; deltaX <= 1; deltaX++) {
			for (int deltaY = -1; deltaY <= 1; deltaY++) {
				int assumedX = positionX + deltaX;
				int assumedY = positionY + deltaY;
				if (assumedX >= 0 && assumedY >= 0 && assumedX < boardWidth && assumedY < boardHeight) {
					neibourCell = allCells[assumedX][assumedY];
					String logString = "take neibourCell [" + assumedX + "]" + "[" + assumedY + "]";
					if (!neibourCell.isWasSelected()) {
						if (neibourCell.hasBomb) {
							log.debug("neibourCell [" + assumedX + "]" + "[" + assumedY + "] is a bomb!");
							bombCount++;
						}
						log.debug(logString);
					}
				}
			}
		}
		this.bombArround = bombCount;
		needsToOpen = true;
		if (isEmpty()) {
			for (int deltaX = -1; deltaX <= 1; deltaX++) {
				for (int deltaY = -1; deltaY <= 1; deltaY++) {
					int assumedX = positionX + deltaX;
					int assumedY = positionY + deltaY;
					if (assumedX >= 0 && assumedY >= 0 && assumedX < boardWidth && assumedY < boardHeight) {
						neibourCell = allCells[assumedX][assumedY];
						String logString = "take neibourCell [" + assumedX + "]" + "[" + assumedY + "]";
						if (!neibourCell.isWasSelected() && !neibourCell.hasBomb) {
							neibourCells.add(neibourCell);
							neibourCell.wasSelected = true;
							logString = " neibourCell [" + assumedX + "]" + "[" + assumedY
									+ "] was marked and added to list";
						}
						log.debug(logString);
					}
					for (Cell cell : neibourCells) {
						if (cell.isEmpty())
							log.debug("take neibourCell[" + cell.getPositionX() + "][" + cell.getPositionY()
									+ "] from list");
						cell.findCellsArround();
					}
				}
			}
		}
	}
/**
 * method opens cell on board
 * @return this Cell
 */
	public Cell doOpen() {
		Timer timer = StartFrameView.getTimerInstance();		
		CellButtonView[][] cellButtonViews = board.getCellButtons();
		CellButtonView cellButtonView = cellButtonViews[positionX][positionY];
		CellButtonDrawManager cellButtonDrawManager = new CellButtonDrawManager(cellButtonView);
		timer.start();
		if (!isBomb()) {
			setSuggestEmpty(true);
			getBord().unSelectCells();
			findCellsArround();
			cellButtonView.draw(true);
			if (isEmpty()) {
				cellButtonDrawManager.showEmpty();
			}
		} else {
			StartFrameView.getTimerInstance().stop();
			cellButtonDrawManager.showBang();

		}
		return this;
	}
	/**
	 * method marks cell like a flag(suggestBomb)
	 * @returns  this cell
	 */
	public Cell doFlag(){
		Timer timer = StartFrameView.getTimerInstance();
		CellButtonView[][] cellButtonViews = board.getCellButtons();
		CellButtonView cellButtonView = cellButtonViews[positionX][positionY];
		CellButtonDrawManager cellButtonDrawManager = new CellButtonDrawManager(cellButtonView);
		FlagsCountManager flagsControllerInstance = FlagsCountManager.getFlagsControllerInstance(board.getCountOfBombs());
		if (isSuggestBomb()) {
			setSuggestBomb(false);
			flagsControllerInstance.incrementAvailableFlagsCount();
			StartFrameView.setBombCountIntoControlPanel(flagsControllerInstance.getAvailableFlagsCount());
			cellButtonDrawManager.drawClosed();
		} else {
			flagsControllerInstance.countAvailableFlags(board);
			if (flagsControllerInstance.getAvailableFlagsCount() > 0) {
				setSuggestBomb(true);
				cellButtonView.draw(false);
				flagsControllerInstance.countAvailableFlags(board);
				StartFrameView.setBombCountIntoControlPanel(flagsControllerInstance.getAvailableFlagsCount());
				if (board.isFinish(board.getCountOfBombs())) {
					timer.stop();
					cellButtonDrawManager.drowCongretulate();
				}
			}
		}	
		return this;	
	}
}
