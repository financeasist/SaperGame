package controller;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;

import org.apache.log4j.Logger;

import model.Board;
import model.Cell;
import view.CellButtonView;

public class BotController {
	private Logger log = Logger.getLogger(BotController.class.getName());
	private static BotController botInstance;
	private Board board;
	private Cell[][] cells;

	public BotController(Board board) {
		if (botInstance == null) {
			this.board = board;
			cells = board.getCells();

		}
	}

	public static BotController getBotInstance() {
		return botInstance;
	}

	public void startBot() {
		log.debug("started method botStart");
		Cell cell = doRandomClick();
		int x = cell.getPositionX();
		int y = cell.getPositionY();
		int bombAround = cell.getBombArround();
		log.debug("doRandomClick return  Cell[" + x + "][" + y + "]" + "bombArround - " + bombAround);
		ArrayList<Cell> list = (ArrayList<Cell>) findNeighbours(cell);
	}

	public List<Cell> findNeighbours(Cell cell) {
		int x = cell.getPositionX();
		int y = cell.getPositionY();
		int bombAround = cell.getBombArround();
		log.debug("started findNeighbours(cell[" + x + "][" + y + "], bombArround = " + bombAround);

		Cell neibourCell;
		Cell[][] allCells = board.getCells();
		int boardWidth = board.getBoardWidth();
		int boardHeight = board.getBoardHeight();
		List<Cell> neibourCells = new ArrayList<Cell>();
		for (int deltaX = -1; deltaX <= 1; deltaX++) {
			for (int deltaY = -1; deltaY <= 1; deltaY++) {
				int assumedX = cell.getPositionX() + deltaX;
				int assumedY = cell.getPositionY() + deltaY;
				if (assumedX >= 0 && assumedY >= 0 && assumedX < boardWidth && assumedY < boardHeight) {
					neibourCell = allCells[assumedX][assumedY];
					neibourCells.add(neibourCell);
					log.debug("neibourCell [" + assumedX + "]" + "[" + assumedY + "] was added to list");
				}
			}
		}
		return neibourCells;
	}

	public Cell doRandomClick() {
		Random random = new Random();
		int randomY = random.nextInt(board.getBoardWidth());
		int randomX = random.nextInt(board.getBoardHeight());
		Cell cell = cells[randomX][randomY];
		return cell.doOpen();
	}

}
