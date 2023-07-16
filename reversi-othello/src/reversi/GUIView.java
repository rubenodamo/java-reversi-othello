package reversi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class GUIView  implements IView {
	
	IModel model;
	IController controller;

	/**
	 * Constructor
	 */
	public GUIView() {
		
	}
	
	// creates frame, label and panel objects
	JFrame frame1 = new JFrame();
	JFrame frame2 = new JFrame();
	
	JLabel label1 = new JLabel();
	JLabel label2 = new JLabel();
	
	JPanel boardPanel1 = new JPanel();
	JPanel boardPanel2 = new JPanel();
	
	@Override
	public void initialise(IModel model, IController controller) {
		
		this.model = model;
		this.controller = controller;
		
		// creates frames for separate players
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame1.setTitle("Reversi - white player");
		frame2.setTitle("Reversi - black player");
		
		frame1.setLocation(100,50);
		frame2.setLocation(600,50);
		
		
		//frame2.setLocationRelativeTo(frame1);
		
		frame1.getContentPane().setLayout(new BorderLayout());
		frame2.getContentPane().setLayout(new BorderLayout());
		
		frame1.setPreferredSize(new Dimension(400,450));
		frame2.setPreferredSize(new Dimension(400,450));
		
		label1.setText("White player - choose where to put your piece");
		label2.setText("Black player - choose where to put your piece");
		
		// creates AI buttons and adds action listeners
		JButton aiBtn1 = new JButton("Greedy AI (play white)");
		aiBtn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {controller.doAutomatedMove(1);}
		});
		JButton aiBtn2 = new JButton("Greedy AI (play black)");
		aiBtn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {controller.doAutomatedMove(2);}
		});
		
		// creates restart buttons and adds action listeners
		JButton restartBtn1 = new JButton("Restart");
		restartBtn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {controller.startup();}
		});
		JButton restartBtn2 = new JButton("Restart");
		restartBtn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {controller.startup();}
		});
		
		// adds buttons to respective panels
		JPanel btnsPanel1  = new JPanel(new GridLayout(2,model.getBoardWidth()));
		JPanel btnsPanel2  = new JPanel(new GridLayout(2,model.getBoardWidth()));
		btnsPanel1.add(aiBtn1);
		btnsPanel1.add(restartBtn1);
		btnsPanel2.add(aiBtn2);
		btnsPanel2.add(restartBtn2);
		
		// adds components to respective frames
		frame1.add(label1, BorderLayout.NORTH);
		frame2.add(label2, BorderLayout.NORTH);
		frame1.add(btnsPanel1, BorderLayout.SOUTH);
		frame2.add(btnsPanel2, BorderLayout.SOUTH);
		
		frame1.pack();
		frame2.pack();
		
		// frames made visible
		frame1.setVisible(true);
		frame2.setVisible(true);
	}
	
	/**
	 * Build the grid of buttons
	 * @return The JPanel boardPanel1
	 */
	public JPanel buildGrid() {
		int width = model.getBoardWidth();
		int height = model.getBoardHeight();
		JPanel returnGrid = new JPanel();
		
		returnGrid.setLayout(new GridLayout(width, height));
		
		for (int y = 0 ; y < height ; y++) {
			int yCoordinate = y; 
			for (int x = 0 ; x < width ; x++) {
				int xCoordinate = x;
				BoardSquare square;
				switch(model.getBoardContents(x,y)) {
					case 1:	{	
						square = new BoardSquare(Color.black, Color.white, controller, model);
						returnGrid.add(square);
						break;
					}
					case 2:	{	
						square = new BoardSquare(Color.white, Color.black, controller, model);
						returnGrid.add(square);
						break;
					}
					default: {
						square = new BoardSquare(controller, model);
						returnGrid.add(square);
						break;
					}
	
				}
				
				square.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						controller.squareSelected(1, xCoordinate, yCoordinate);
					}
				});
			}
		}
		
		return returnGrid;
	}
	
	/**
	 * Build the grid of buttons rotated for player 2
	 * @return The JPanel boardPanel2
	 */
	public JPanel buildReverseGrid() {
		int width = model.getBoardWidth();
		int height = model.getBoardHeight();
		JPanel returnGrid = new JPanel();
		
		returnGrid.setLayout(new GridLayout(width, height));
		
		for (int y = height - 1 ; y >= 0 ; y--) {
			int yCoordinate = y; 
			for (int x = width - 1; x >= 0 ; x--) {
				int xCoordinate = x;
				BoardSquare square;
				switch(model.getBoardContents(x,y)) {
					case 1:	{
						square = new BoardSquare(Color.black, Color.white, controller, model);
						returnGrid.add(square);
						break;
					}
					case 2:	{	
						square = new BoardSquare(Color.white, Color.black, controller, model);
						returnGrid.add(square);
						break;
					}
					default: {
						square = new BoardSquare(controller, model);
						returnGrid.add(square);
						break;
					}
	
				}
				
				square.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						controller.squareSelected(2, xCoordinate, yCoordinate);
					}
				});
			}
		}
		
		return returnGrid;
	}

	@Override
	public void refreshView() {
		
		/* 
		 * removes panel and adds update panel to the frame
		 * panel holds all of the button squares
		 * updates the look and feel of the user interface component hierarchy rooted
		 *  at specific panel
		 */
		frame1.remove(boardPanel1);
		boardPanel1 = buildGrid();
		frame1.add(boardPanel1, BorderLayout.CENTER);
		
		frame2.remove(boardPanel2);
		boardPanel2 = buildReverseGrid();
		frame2.add(boardPanel2, BorderLayout.CENTER);
		
		SwingUtilities.updateComponentTreeUI(boardPanel1);
		SwingUtilities.updateComponentTreeUI(boardPanel2);
	}

	@Override
	public void feedbackToUser(int player, String message) {
		// sets specific message depending on the current player
		if (player == 1) {
			label1.setText(message);
		}
		else {
			label2.setText(message);
		}
		
	}

}
