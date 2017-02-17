package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import model.Cell;
import view.CellButtonView;
/**
 * This is listener(Controller) for CellButtonView. Takes data from this view and manage it.
 * @author Roman Grupskyi
 * @version 1.0 since 12.02.2017
 */
public class CellButtonMouseEventsManager implements MouseListener {
	private CellButtonView cellButtonView;
	private Cell cell;


	/**
	 * in the cinstrucnor we set cllButton instance which contains event data
	 * @param cellButtonView
	 */
	public CellButtonMouseEventsManager(CellButtonView cellButtonView) {
		super();
		this.cellButtonView = cellButtonView;
	}
	/**
	 * listen which mouse button was pressed and does logic depending on it
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		cell = cellButtonView.getCell();
		int mouseButton = e.getButton();
		if (mouseButton == 1) {		
			cell.doOpen();
		}
		if (mouseButton == 3) {
			cell.doFlag();
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

}
