package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import org.apache.log4j.Logger;

import model.Board;
import model.Cell;

/**
 * class describes bot.
 * 
 * @author Roman Grupskyi
 * @version 1.1 since 17.02.2017
 */
public class BotController {
	private Logger log = Logger.getLogger(BotController.class.getName());
	private static BotController botInstance;
	private Board board;
	private Cell[][] cells;
	Float[][] probabilities;

	public BotController(Board board) {
		if (botInstance == null) {
			this.board = board;
			cells = board.getCells();
			probabilities = new Float[board.getBoardWidth()][board.getBoardHeight()];
		}
	}

	public static BotController getBotInstance() {
		return botInstance;
	}

	/**
	 * method does random clisk on board and gets clicked cell. Then finds all
	 * neighbours arround that cell and calculates a probability to find in them
	 * bomb
	 * 
	 */
	public void startBot() {
		log.debug("started method botStart");
		Cell cell = doRandomClick();
		int x = cell.getPositionX();
		int y = cell.getPositionY();
		int bombAround = cell.getBombArround();
		log.debug("doRandomClick return  Cell[" + x + "][" + y + "]" + "bombArround = " + bombAround);
		resetProbabilities();
		doNextClick();

		ArrayList<Cell> list = (ArrayList<Cell>) findNeighbours(cell);

	}

	/**
	 * fills array probabilities by zero
	 */
	public void resetProbabilities() {
		for (Float[] row : probabilities) {
			for (Float value : row) {
				value = 0f;
			}
		}
	}

	/**
	 * finds allneighbours arround cell, passed in parameter
	 * 
	 * @param cell
	 * @return list<Cell>
	 */
	public List<Cell> findNeighbours(Cell cell) {
		int x = cell.getPositionX();
		int y = cell.getPositionY();
		log.debug("started findNeighbours(cell[" + x + "][" + y + "], bombArround = " + cell.getBombArround());
		int boardWidth = board.getBoardWidth();
		int boardHeight = board.getBoardHeight();
		List<Cell> neibourCells = new ArrayList<Cell>();
		Cell neibourCell;
		for (int deltaX = -1; deltaX <= 1; deltaX++) {
			for (int deltaY = -1; deltaY <= 1; deltaY++) {
				int assumedX = x + deltaX;
				int assumedY = y + deltaY;
				if (deltaX == 0 && deltaY == 0) {
				} else {
					if (assumedX >= 0 && assumedY >= 0 && assumedX < boardWidth && assumedY < boardHeight) {
						if (cells[assumedX][assumedY].isNeedsToOpen()) {
							neibourCell = cells[assumedX][assumedY];
							neibourCells.add(neibourCell);
							log.debug("neibourCell [" + assumedX + "]" + "[" + assumedY + "] was added to list");
						}
					}
				}
			}
		}
		return neibourCells;
	}

	/**
	 * does random click on board
	 * 
	 * @return clicked cell
	 */
	public Cell doRandomClick() {
		Random random = new Random();
		int randomY = random.nextInt(board.getBoardWidth());
		int randomX = random.nextInt(board.getBoardHeight());
		Cell cell = cells[randomX][randomY];
		Cell openedCell = cell.doOpen();
		return openedCell;
	}

	public void doNextClick() {

		for (Cell[] row : cells) {
			for (Cell cell : row) {
				if (!cell.isNeedsToOpen()) {
					setProbability(cell);
				}
			}
		}
		for (int i = 0; i < board.getBoardWidth(); i++) {
			for (int j = 0; j < board.getBoardHeight(); j++) {
				if (probabilities[i][j] != null) {
					if (probabilities[i][j] == 1f) {
						cells[i][j].doFlag();
					}
				}
			}
		}
	}

	public boolean isPredicat1(Cell cell) {
		boolean predicat1 = false;
		int x = cell.getPositionX();
		int y = cell.getPositionY();
		if ((x + 1) < board.getBoardWidth() && (y + 1) < board.getBoardHeight() && (x - 1) > 0 && (y - 1) > 0) {
			boolean bottomRightCorner = (cells[x + 1][y].getBombArround() > 0)
					&& (cells[x + 1][y - 1].getBombArround() > 0) && cells[x][y - 1].getBombArround() > 0;
			boolean upperRightСorner = cells[x + 1][y].getBombArround() > 0 && cells[x + 1][y + 1].getBombArround() > 0
					&& cells[x][y + 1].getBombArround() > 0;
			boolean upperLeftCorner = cells[x][y + 1].getBombArround() > 0 && cells[x - 1][y + 1].getBombArround() > 0
					&& cells[x - 1][y].getBombArround() > 0;
			boolean bottomLeftCorner = cells[x - 1][y].getBombArround() > 0 && cells[x - 1][y - 1].getBombArround() > 0
					&& cells[x][y - 1].getBombArround() > 0;
			if (bottomRightCorner || upperRightСorner || upperLeftCorner || bottomLeftCorner) {
				predicat1 = true;
			}
		}
		return predicat1;
	}

	public void setProbability(Cell cell) {
		int x = cell.getPositionX();
		int y = cell.getPositionY();
		if (isPredicat1(cell)) {
			probabilities[x][y] = 1f;
		}
	}

}
