package view;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import controller.ButtonMouseListener;
import controller.ImgManager;
import model.Board;
import model.Cell;

/**
 * this class represents button view which contains cell from model.cell 
 * @author Roman Grupskyi
 * @version 1.2 since 10.02.2017
 */
public class CellButtonView extends JButton {
	private Cell cell;
	private ImgManager imgManager;
	
	public CellButtonView() {
		super();
		imgManager = new ImgManager();
		Dimension preferredSize = new Dimension(18, 18);
		this.setPreferredSize(preferredSize);
		this.addMouseListener(new ButtonMouseListener(this));
	}


	public Cell getCell() {
		return cell;
	}

	public void setCell(Cell cell) {
		this.cell = cell;
	}
	/**
	 * opens all board and drows congratulations
	 */
	public void drowCongretulate() {
		Board bord = cell.getBord();
		CellButtonView[][] cellButtons = bord.getCellButtons();
		for (CellButtonView[] cells : cellButtons) {
			for (CellButtonView cellButtonView : cells) {
				if (cellButtonView.getCell().isSuggestBomb())
					cellButtonView.drawFlag();
				else
					cellButtonView.draw(true);
			}
		}
		JOptionPane.showMessageDialog(null, "congratulations! You Win!");
	}
	/**
	 * opens all empty neighbours
	 */
	public void showEmpty() {
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
		cell.setCurrentStateImgType("BUTTON_BANG");
		Board bord = cell.getBord();
		CellButtonView[][] cellButtons = bord.getCellButtons();
		for (CellButtonView[] cells : cellButtons) {
			for (CellButtonView cellButtonView : cells) {
				cellButtonView.getCell().findCellsArround();
				cellButtonView.draw(true);
			}
		}
		StartFrameView.setSadBtnSmileIcon();
		JOptionPane.showMessageDialog(null, "It was a bomb! You lose!");
	}
	/**
	 * draws button which shows cell value
	 * @param isReal
	 */
	public void draw(boolean isReal) {
		if (isReal) {
			if (cell.isBomb()) {
				drawBomb(); // draws bomb
				if ("BUTTON_BANG".equals(cell.getCurrentStateImgType())) {
					drawBang(); // draws bang
				}
			} else {
				drawCurrentNumber();// draw real cell value
			}
		} else {
			try {
				if (cell.isSuggestBomb()) {
					drawFlag(); // draws flag
				}

				else {
					if (cell.isSuggestEmpty())

					{
						drawCurrentNumber(); // draws real cell value
					} else {
						drawClosed();// draws closed cell
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	/**
	 * draws current number
	 */
	private void drawCurrentNumber() {
		this.setIcon(new ImgManager().getCurrentNumberImg(this));

	}
	/**
	 * draws closed button 
	 */
	private void drawClosed() {
		this.setIcon(imgManager.getCurrentNumberImg(this));
	}
	/**
	 * draws bang
	 */
	public void drawBang() {
		this.setIcon(imgManager.getImg(ImgManager.BUTTON_BANG));
	}
	/**
	 * draws bomb
	 */
	public void drawBomb() {
		this.setIcon(imgManager.getImg(ImgManager.BUTTON_BOMB));
	}
	/**
	 * draws flag
	 */
	public void drawFlag() {
		this.setIcon(imgManager.getImg(ImgManager.BUTTON_FLAG));
	}

}
