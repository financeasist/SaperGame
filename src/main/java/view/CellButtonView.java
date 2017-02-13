package view;

import java.awt.Dimension;

import javax.swing.JButton;

import controller.ButtonMouseListener;
import controller.CellButtonDrawManager;
import model.Cell;

/**
 * this class represents button view which contains cell from model.cell 
 * @author Roman Grupskyi
 * @version 1.2 since 10.02.2017
 */
public class CellButtonView extends JButton {
	private Cell cell;
	CellButtonDrawManager cellButtonDrawManager =  new CellButtonDrawManager(this);; 
	
	public CellButtonView(Cell cell) {
		super();
		this.cell = cell;
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
	 * draws button which shows cell value
	 * @param isReal
	 */
	public void draw(boolean isReal) {
		if (isReal) {
			if (cell.isBomb()) {
				cellButtonDrawManager.drawBomb(); // draws bomb
				if ("BUTTON_BANG".equals(cell.getCurrentStateImgType())) {
					cellButtonDrawManager.drawBang(); // draws bang
				}
			} else {
				cellButtonDrawManager.drawCurrentNumber();// draw real cell value
			}
		} else {
			try {
				if (cell.isSuggestBomb()) {
					cellButtonDrawManager.drawFlag(); // draws flag
				}

				else {
					if (cell.isSuggestEmpty())

					{
						cellButtonDrawManager.drawCurrentNumber(); // draws real cell value
					} else {
						cellButtonDrawManager.drawClosed();// draws closed cell
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
