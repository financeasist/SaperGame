package controller;

import javax.swing.JOptionPane;
import model.Board;
import model.Cell;
import view.CellButtonView;
import view.StartFrameView;

/**
 * This class contains drawing methods for CellButtonView
 * 
 * @author Roman Grupskyi
 * @version 1.0 since 13.02.2017
 */
public class CellButtonDrawManager {
	CellButtonView cellButtonView;
	ImgManager imgManager = new ImgManager();

	/**
	 * Constructor takes cellButtonView instance.
	 * 
	 * @param cellButtonView
	 */
	public CellButtonDrawManager(CellButtonView cellButtonView) {
		this.cellButtonView = cellButtonView;
	}

	/**
	 * opens all board and drows congratulations
	 */
	public void drowCongretulate() {
		Cell cell = cellButtonView.getCell();
		Board bord = cell.getBord();
		CellButtonView[][] cellButtons = bord.getCellButtons();
		for (CellButtonView[] cells : cellButtons) {
			for (CellButtonView cellButtonView : cells) {
				if (cellButtonView.getCell().isSuggestBomb()) {
					cellButtonView.getCell().setCurrentStateImgType("BUTTON_NEUTRALIZED");
					cellButtonView.draw(true);
				} else
					cellButtonView.draw(true);
			}
		}
		JOptionPane.showMessageDialog(null, "congratulations! You Win!");
	}

	/**
	 * opens all empty neighbours
	 */
	public void showEmpty() {
		Cell cell = cellButtonView.getCell();
		CellButtonView[][] cellButtons = cell.getBord().getCellButtons();
		for (CellButtonView[] cells : cellButtons) {
			for (CellButtonView cellButtonView : cells) {
				if (cellButtonView.getCell().isNeedsToOpen())
					cellButtonView.draw(true);
			}
		}
	}

	/**
	 * opens all board and drows explosion
	 */
	public void showBang() {
		Cell cell = cellButtonView.getCell();
		cell.setCurrentStateImgType("BUTTON_BANG");
		Board bord = cell.getBord();
		CellButtonView[][] cellButtons = bord.getCellButtons();
		for (CellButtonView[] cells : cellButtons) {
			for (CellButtonView cellButtonView : cells) {
				cellButtonView.getCell().findCellsArround();
				if (cellButtonView.getCell().isSuggestBomb()) {
					if (cellButtonView.getCell().isSuggestBomb() && cellButtonView.getCell().isBomb())
						cellButtonView.getCell().setCurrentStateImgType("BUTTON_NEUTRALIZED");
					cellButtonView.draw(true);
				} else
					cellButtonView.draw(true);
			}
		}
		StartFrameView.setSadBtnSmileIcon();
		JOptionPane.showMessageDialog(null, "It was a bomb! You lose!");
	}

	/**
	 * draws current number
	 */
	public void drawCurrentNumber() {
		cellButtonView.setIcon(imgManager.getCurrentNumberImg(cellButtonView));
	}

	/**
	 * draws closed button
	 */
	public void drawClosed() {
		cellButtonView.setIcon(null);
	}

	/**
	 * draws bang
	 */
	public void drawBang() {
		cellButtonView.setIcon(imgManager.getImg(ImgManager.BUTTON_BANG));
	}

	/**
	 * draws bomb
	 */
	public void drawBomb() {
		if ("BUTTON_NEUTRALIZED".equals(cellButtonView.getCell().getCurrentStateImgType())) {
			cellButtonView.setIcon(imgManager.getImg(ImgManager.BUTTON_NEUTRALIZED));
		} else
			cellButtonView.setIcon(imgManager.getImg(ImgManager.BUTTON_BOMB));
	}

	/**
	 * draws flag
	 */
	public void drawFlag() {
		cellButtonView.setIcon(imgManager.getImg(ImgManager.BUTTON_FLAG));
	}
}
