package controller;

import java.awt.event.MouseEvent;
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
		log.debug("doRandimClick return  Cell[" + x + "][" + y + "]");
	}

	public void findNeighbours(Cell cell) {
		int x = cell.getPositionX();
		int y = cell.getPositionY();
		int bombAround = cell.getBombArround();
		log.debug("started findNeighbours(cell[" + x + "][" + y + "], bombArround = " + bombAround);
	}

	public Cell doRandomClick() {
		Random random = new Random();
		int randomY = random.nextInt(board.getBoardWidth());
		int randomX = random.nextInt(board.getBoardHeight());
		Cell cell = cells[randomX][randomY];
		return cell.doOpen();
	}

}
