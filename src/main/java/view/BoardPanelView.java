package view;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import model.Board;
/**
 * this class represents view for game board. 
 * @author Roman Grupskyi
 * @version 1.0 since 10/02/2017
 */
public class BoardPanelView extends JPanel {
	private Board board;
	
	public BoardPanelView(Board board) {
		super();
		this.board = board;
		this.setLayout(new GridLayout(board.getWidth(),board.getHeight(),0,0));
		this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10),
				BorderFactory.createLoweredBevelBorder()));
	}


}
