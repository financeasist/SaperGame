package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import model.Cell;
import view.CellButtonView;

/**
 * This class returns the desired imageIcon, depending on the set value ImgType
 * 
 * @author Roman Gruoskyi
 * @version 1.0 since 08/02/2017
 */
public class ImgManager {
	public static final String BUTTON_CLOSED = "src/main/resources/img/closed.gif";
	public static final String BUTTON_EMPTY = "src/main/resources/img/0.gif";
	public static final String BUTTON_ONE = "src/main/resources/img/1.gif";
	public static final String BUTTON_TWO = "src/main/resources/img/2.gif";
	public static final String BUTTON_THREE = "src/main/resources/img/3.gif";
	public static final String BUTTON_FOUR = "src/main/resources/img/4.gif";
	public static final String BUTTON_FIVE = "src/main/resources/img/5.gif";
	public static final String BUTTON_SIX = "src/main/resources/img/6.gif";
	public static final String BUTTON_SEVEN = "src/main/resources/img/7.gif";
	public static final String BUTTON_EIGHT = "src/main/resources/img/8.gif";
	public static final String BUTTON_BOMB = "src/main/resources/img/bomb.gif";
	public static final String BUTTON_BANG = "src/main/resources/img/bang.gif";
	public static final String BUTTON_FLAG = "src/main/resources/img/flag0.gif";
	
	private List<String> imgUrlList;

	public ImgManager() {
		imgUrlList = new ArrayList<>();
		imgUrlList.add(ImgManager.BUTTON_EMPTY);
		imgUrlList.add(ImgManager.BUTTON_ONE);
		imgUrlList.add(ImgManager.BUTTON_TWO);
		imgUrlList.add(ImgManager.BUTTON_THREE);
		imgUrlList.add(ImgManager.BUTTON_FOUR);
		imgUrlList.add(ImgManager.BUTTON_FIVE);
		imgUrlList.add(ImgManager.BUTTON_SIX);
		imgUrlList.add(ImgManager.BUTTON_SEVEN);
		imgUrlList.add(ImgManager.BUTTON_EIGHT);
	}
	/**
	 * returns current imgIcon depending on the set cellButton value
	 * @param cellButtonView
	 * @return
	 */
	public ImageIcon getCurrentNumberImg(CellButtonView cellButtonView) {
		int index = cellButtonView.getCell().getBombArround();
		String currentImg = imgUrlList.get(index);
		return getImg(currentImg);
	}
	/**
	 * returns current imgIcon depending on the set value ImgType
	 * @param ImgType
	 * @return
	 */
	public ImageIcon getImg(String ImgType) {
		ImageIcon img = null;
		try {
			img = new ImageIcon(ImageIO.read(new File(ImgType)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
}
