package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Timer;

import model.Board;
import model.Cell;
import view.CellButtonView;
import view.StartFrameView;
/**
 * This is listener(Controller) for CellButtonView. Takes data from this view and manage it.
 * @author Roman Grupskyi
 * @version 1.0 since 12.02.2017
 */
public class ButtonMouseListener implements MouseListener {

	private CellButtonView cellButtonView;
	private Cell cell;
	private int allFlagsCount; 
	private int availableFlagsCount;
	/**
	 * in the cinstrucnor we set cllButton instance which contains event data
	 * @param cellButtonView
	 */
	public ButtonMouseListener(CellButtonView cellButtonView) {
		super();
		this.cellButtonView = cellButtonView;
	}
	/**
	 * listen which mouse button was pressed and does logic depending on it
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		Timer timer = StartFrameView.getTimerInstance();
		timer.start();
		cell = cellButtonView.getCell();
		Board board = cell.getBord();
		allFlagsCount = board.getCountOfBombs();
		int mouseButton = e.getButton();
		if (mouseButton == 1) {
			if (!cell.isBomb()) {
				cell.setSuggestEmpty(true);
				cell.getBord().unSelectCells();
				cell.findCellsArround();
				cellButtonView.draw(true);
				if (cell.isEmpty()) {
					cellButtonView.showEmpty();
				}

			} else {
				StartFrameView.getTimerInstance().stop();
				cellButtonView.showBang();
			}
		}
		if (mouseButton == 3) {
			if (cell.isSuggestBomb()) {
				cell.setSuggestBomb(false);
				availableFlagsCount++;
				StartFrameView.setBombCountIntoControlPanel(availableFlagsCount);
				cellButtonView.draw(false);
			} else {
				availableFlagsCount = countAvailableFlags();
				if (availableFlagsCount > 0) {
					cell.setSuggestBomb(true);
					cellButtonView.draw(false);
					availableFlagsCount = countAvailableFlags();
					StartFrameView.setBombCountIntoControlPanel(availableFlagsCount);
					Board bord = cell.getBord();
					if (bord.isFinish(bord.getCountOfBombs())) {
						timer.stop();
						cellButtonView.drowCongretulate();
					}
				}
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {

	}
	/**
	 * counts availableFlags count
	 * @return count of available flags
	 */
	public int countAvailableFlags() {
		int countFlags = 0;
		Board bord = cell.getBord();
		Cell[][] cells = bord.getCells();
		for (Cell[] cellsRow : cells) {
			for (Cell cell : cellsRow) {
				if (cell.isSuggestBomb())
					countFlags++;
			}
		}
		if (countFlags <= allFlagsCount)
			return allFlagsCount - countFlags;
		else
			return 0;
	}
}
