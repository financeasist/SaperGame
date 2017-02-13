package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

/**
 * This class represents Timer Action Listener for Timer
 * 
 * @author Roman Grupskyi
 * @version 1.0 since 10/02/2017
 */
public class TimerActionListener implements ActionListener {
	private long count;
	private JTextField jt_time;

	@Override
	public void actionPerformed(ActionEvent e) {
		displayElapsedTime(count);
		count++;
	}

	/**
	 * sets textField
	 * 
	 * @param jt_time
	 */
	public void setTextField(JTextField jt_time) {
		this.jt_time = jt_time;
	}

	/**
	 * resets count
	 */
	public void resetCount() {
		count = 0;
	}

	/**
	 * displays count in needed form
	 * 
	 * @param elapsedTime
	 */
	private void displayElapsedTime(long elapsedTime) {
		if (elapsedTime >= 0 && elapsedTime < 9) {
			jt_time.setText("00" + elapsedTime);
		} else if (elapsedTime > 9 && elapsedTime < 99) {
			jt_time.setText("0" + elapsedTime);
		} else if (elapsedTime > 99 && elapsedTime < 999) {
			jt_time.setText("" + elapsedTime);
		}
	}
}
