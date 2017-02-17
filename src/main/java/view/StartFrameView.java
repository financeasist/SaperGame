package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import commons.Constants;
import controller.BotController;
import controller.TimerActionEventManager;
import model.Board;

/**
 * Class represents start Window for game.
 * 
 * @author Roman Grupskyi
 * @version 1.1 since 28.01.2017
 */
public class StartFrameView {
	static Logger log = Logger.getLogger(StartFrameView.class.getName());
	private JFrame frame;
	private JPanel controlPanel;
	private Board board;
	private BoardPanelView boardPanelView;
	private static JTextField jt_mines;
	private JTextField jt_time;
	private static JButton btnsmile;
	private static Timer timer;
	private boolean intermediateState;
	private boolean beginerState;
	private boolean expertState;
	private boolean botState;
	BotController botInstance;

	/**
	 * initializes a start window ControlPanel and BoardPanel
	 */
	public StartFrameView() {
		initFrame();
		initAndAddComponentsToControlePanel();
		addCellButtonsToBoardPanel(Constants.BOARD_WIDTH_EASY, Constants.BOARD_HEIGHT_EASY,
				Constants.COUNT_OF_BOMBS_EASY);
	}

	public boolean isIntermediateState() {
		return intermediateState;
	}

	public void setIntermediateState(boolean intermediateState) {
		this.intermediateState = intermediateState;
	}

	public boolean isBeginerState() {
		return beginerState;
	}

	public void setBeginerState(boolean beginerState) {
		this.beginerState = beginerState;
	}

	public boolean isExpertState() {
		return expertState;
	}

	public void setExpertState(boolean expertState) {
		this.expertState = expertState;
	}

	/**
	 * initializes a main Frame
	 */
	public void initFrame() {
		frame = new JFrame();
		frame.setTitle("Saper by Roman Grupskyi");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());
		centre(frame);
		setmenu();

	}

	/**
	 * initializes a Timer
	 */
	public void initTimer() {
		Timer timer = StartFrameView.getTimerInstance();
		timer.stop();
		ActionListener[] actionListeners = timer.getActionListeners();
		TimerActionEventManager listener = (TimerActionEventManager) actionListeners[0];
		listener.resetCount();
		listener.setTextField(jt_time);
	}

	/**
	 * Initializes and adds components to Control Panel (place for timer, button
	 * for quike restart and counter of available flags)
	 */
	public void initAndAddComponentsToControlePanel() {
		jt_mines = new JTextField();
		jt_time = new JTextField();
		btnsmile = new JButton("");
		controlPanel = new JPanel();
		jt_mines.setColumns(3);
		jt_mines.setFont(new Font("DigtalFont.TTF", Font.BOLD, 20));
		jt_mines.setBorder(BorderFactory.createLoweredBevelBorder());
		jt_mines.setHorizontalAlignment(SwingConstants.LEFT);
		jt_mines.setForeground(Color.RED);
		jt_mines.setBackground(Color.black);
		jt_time.setColumns(3);
		jt_time.setBorder(BorderFactory.createLoweredBevelBorder());
		jt_time.setFont(new Font("DigtalFont.TTF", Font.BOLD, 20));
		jt_time.setForeground(Color.RED);
		jt_time.setBackground(Color.black);
		jt_time.setText("000");
		btnsmile.setIcon(new ImageIcon("src\\main\\resources\\img\\new game.gif"));
		btnsmile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!botState) {
					btnsmile.setIcon(new ImageIcon("src\\main\\resources\\img\\new game.gif"));
					insertBoardPanelDependsOnSelectedMenu();
				}else{
//					btnsmile.setIcon(new ImageIcon("src\\main\\resources\\img\\new game.gif"));
//					insertBoardPanelDependsOnSelectedMenu();
					botInstance.startBot();
				}
			}
		});
		controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.X_AXIS));
		controlPanel.setBorder(BorderFactory.createLoweredBevelBorder());
		controlPanel.add(jt_mines);
		controlPanel.add(btnsmile);
		controlPanel.add(jt_time);
		frame.getContentPane().add(controlPanel, BorderLayout.NORTH);
		initTimer();
		frame.setVisible(true);
	}

	/**
	 * this method creates and initializes BordPanel with CellButtons, and adds
	 * it into frame. here we initialize a board and add it into BoardPanel,and
	 * than add BoardPanel into frame.
	 * 
	 * @param width
	 * @param height
	 * @param bombCount
	 */
	public void addCellButtonsToBoardPanel(int width, int height, int bombCount) {
		log.debug(" start addCellButtonsToBoardPanel()");
		setBombCountIntoControlPanel(bombCount);
		if (boardPanelView != null) {
			frame.getContentPane().remove(boardPanelView);
		}
		board = new Board(width, height, bombCount);
		boardPanelView = new BoardPanelView(board);
		CellButtonView[][] cellButtons = board.getCellButtons();
		if (cellButtons != null) {
			for (int x = 0; x != cellButtons.length; x++) {
				for (int y = 0; y != cellButtons[0].length; y++) {
					boardPanelView.add(cellButtons[x][y]);
				}
			}
		}
		boardPanelView.revalidate();
		frame.getContentPane().add(boardPanelView, BorderLayout.CENTER);
		frame.pack();
	}

	/**
	 * creates and sets menu with ActionListeners into Frame;
	 */
	public void setmenu() {
		final JMenuBar bar = new JMenuBar();
		final JMenu game = new JMenu("Game");
		final JMenu help = new JMenu("Help");
		final JMenuItem newGame = new JMenuItem("new game");
		final JMenuItem helpitem = new JMenuItem("help");
		final JMenuItem exit = new JMenuItem("Exit");
		final JCheckBoxMenuItem beginner = new JCheckBoxMenuItem("Begineer");
		final JCheckBoxMenuItem intermediate = new JCheckBoxMenuItem("Intermediate");
		final JCheckBoxMenuItem expert = new JCheckBoxMenuItem("Expert");
		final JCheckBoxMenuItem bot = new JCheckBoxMenuItem("Bot");
		ButtonGroup status = new ButtonGroup();
		status.add(beginner);
		status.add(intermediate);
		status.add(expert);
		status.add(bot);
		game.add(newGame);
		game.addSeparator();
		game.add(beginner);
		game.add(intermediate);
		game.add(expert);
		game.addSeparator();
		game.add(bot);
		game.addSeparator();
		game.add(exit);
		help.add(helpitem);
		bar.add(game);
		bar.add(help);
		frame.setJMenuBar(bar);

		newGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insertBoardPanelDependsOnSelectedMenu();

			}
		});
		beginner.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				beginerState = beginner.getState();
				setIntermediateState(false);
				setExpertState(false);
				addCellButtonsToBoardPanel(Constants.BOARD_WIDTH_EASY, Constants.BOARD_HEIGHT_EASY,
						Constants.COUNT_OF_BOMBS_EASY);
				initTimer();

			}
		});
		intermediate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				intermediateState = intermediate.getState();
				setBeginerState(false);
				setExpertState(false);
				addCellButtonsToBoardPanel(Constants.BOARD_WIDTH_MEDIUM, Constants.BOARD_HEIGHT_MEDIUM,
						Constants.COUNT_OF_BOMBS_MEDIUM);
				initTimer();

			}
		});
		expert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				expertState = expert.getState();
				setBeginerState(false);
				setIntermediateState(false);
				addCellButtonsToBoardPanel(Constants.BOARD_WIDTH_EXPERT, Constants.BOARD_HEIGHT_EXPERT,
						Constants.COUNT_OF_BOMBS_EXPERT);
				initTimer();

			}
		});
		bot.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				botState = bot.getState();
				setBeginerState(false);
				setIntermediateState(false);
				setExpertState(false);
				addCellButtonsToBoardPanel(Constants.BOARD_WIDTH_EASY, Constants.BOARD_HEIGHT_EASY,
						Constants.COUNT_OF_BOMBS_EASY);
				botInstance = new BotController(board);
				initTimer();
			}

		});
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		helpitem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "instruction");
			}
		});
	}

	/**
	 * Placing specified as parameter window at the center of the screen
	 * 
	 * @param w
	 *            centered window
	 */
	public static void centre(Window w) {
		Dimension us = w.getSize();
		Dimension them = Toolkit.getDefaultToolkit().getScreenSize();
		// Coordinates top left corner for application window
		int newX = (them.width - us.width) / 2;
		int newY = (them.height - us.height) / 2;
		w.setLocation(newX, newY);
	}

	/**
	 * this is static method which sets number of avaible flags into current
	 * place in ControlPanel
	 * 
	 * @param flagCount
	 */
	public static void setBombCountIntoControlPanel(Integer flagCount) {
		jt_mines.setText(flagCount.toString());
	}

	/**
	 * This method inserts the appropriate size of the board in BordPanel
	 * depending on the selected menu
	 */
	public void insertBoardPanelDependsOnSelectedMenu() {
		jt_time.setText("000");
		if (isExpertState()) {
			addCellButtonsToBoardPanel(Constants.BOARD_WIDTH_EXPERT, Constants.BOARD_HEIGHT_EXPERT,
					Constants.COUNT_OF_BOMBS_EXPERT);
		} else if (isIntermediateState()) {
			addCellButtonsToBoardPanel(Constants.BOARD_WIDTH_MEDIUM, Constants.BOARD_HEIGHT_MEDIUM,
					Constants.COUNT_OF_BOMBS_MEDIUM);
		} else if (isBeginerState()) {
			addCellButtonsToBoardPanel(Constants.BOARD_WIDTH_EASY, Constants.BOARD_HEIGHT_EASY,
					Constants.COUNT_OF_BOMBS_EASY);
		} else
			addCellButtonsToBoardPanel(Constants.BOARD_WIDTH_EASY, Constants.BOARD_HEIGHT_EASY,
					Constants.COUNT_OF_BOMBS_EASY);
		initTimer();
	}

	/**
	 * this is static method, which sets sad smile icon into btnSmileIcon.
	 */
	public static void setSadBtnSmileIcon() {
		btnsmile.setIcon(new ImageIcon("src\\main\\resources\\img\\crape.gif"));

	}

	/**
	 * method returns instance of Timer. in the field - 'milisec' we can set a
	 * timer speed.
	 * 
	 * @return
	 */
	public static Timer getTimerInstance() {
		int milisec = 1000;
		ActionListener listener = new TimerActionEventManager();
		if (timer == null) {
			timer = new Timer(milisec, listener);
		}
		return timer;
	}

}
