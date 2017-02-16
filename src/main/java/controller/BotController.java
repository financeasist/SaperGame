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
	private static BotController botInstance = null;
	private Board board;
	private CellButtonView[][] cellButtonViews;

	public BotController(Board board) {
		if (botInstance == null) {
			this.board = board;
			cellButtonViews = board.getCellButtons();
		}
//		}else
//			getBotInstance();
	}

	public static BotController getBotInstance() {
		return botInstance;
	}

	public void startBot() {
		log.debug("started method botStart");
		CellButtonView cellButtonView = doRandomClick();
		int x = cellButtonView.getCell().getPositionX();
		int y = cellButtonView.getCell().getPositionY();
		long currentTime = System.currentTimeMillis();
		log.debug("doClick() was called on button[" + x + "][" + y + "]");
		MouseEvent mouseEvent = new MouseEvent(cellButtonView, MouseEvent.MOUSE_CLICKED, currentTime,
				MouseEvent.BUTTON1, x, y, 1, false);
		log.debug("event with mouse button" + mouseEvent.getButton() + " and clickCount " + mouseEvent.getClickCount()
				+ " was created ");
		cellButtonView.dispatchEvent(mouseEvent);
		log.debug("mouseEvent was dispatched into Cellbutton[" + x + "][" + y + "]");
	}

	public void findNeighbours(Cell cell) {
		int x = cell.getPositionX();
		int y = cell.getPositionY();
		int bombAround = cell.getBombArround();
		log.debug("started findNeighbours(cell["+x+"]["+y+"], bombArround = "+bombAround);
	}

	public CellButtonView doRandomClick() {
		Random random = new Random();
		int randomY = random.nextInt(board.getBoardWidth());
		int randomX = random.nextInt(board.getBoardHeight());
		CellButtonView cellButtonView = cellButtonViews[randomX][randomY];
		cellButtonView.doClick();
		return cellButtonView;
	}

}
