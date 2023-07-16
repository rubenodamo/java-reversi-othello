package reversi;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class BoardSquare extends JButton{
	
/* 
 * initialise instance variables
 * which control the appearance of the square
 */
	int borderSize;
	int diskWidth;
	int diskHeight;
	int diskBorderSize;
	
	Color drawColour;
	Color borderColour;
	Color diskColour;
	Color diskBorder;
	
	/**
	 * The first constructor takes multiple parameters to allow customisation of the appearance of 
	 * the square and disk. 
	 * @param width, @param height control size of button
	 * @param colour determines green square background
	 * @param borderWidth, @param borderCol control the border thickness and colour of the button
	 * @param circleWidth, @param circleHeight control the diameter of the circle to appear in
	 *  the middle of the button when clicked
	 * @param circleBorder determines the colour of the circle border (white/black)
	 * @param circleBorderWidth controls the width of the circle border
	 * @param circleColour determines the colour of the circle fill (white/black)
	 * @param controller - controller to use when button is clicked
	 * @param model - model to use 
	 */
	public BoardSquare(int width, int height, Color colour, int borderWidth, Color borderCol, int circleWidth, int circleHeight,
			Color circleBorder,  int circleBorderWidth, Color circleColour, IController controller, IModel model) {
		
		borderSize = borderWidth;
		diskWidth = circleWidth;
		diskHeight = circleHeight;
		diskBorderSize = circleBorderWidth;
		
		drawColour = colour;
		borderColour = borderCol;
		diskColour = circleColour;
		diskBorder = circleBorder;
		
		setMinimumSize(new Dimension(width, height));
		setPreferredSize(new Dimension(width, height));
	}
	
	// call the other constructor with some default values for green square with a disk
	public BoardSquare(Color diskColour, Color diskBorder, IController controller, IModel model) {
		this(50, 50, Color.green, 1, Color.black, 36, 36, diskColour, 1, diskBorder, controller, model);
	}
	
	// call the other constructor with some default values for empty green square
	public BoardSquare(IController controller, IModel model) {
		this(50, 50, Color.green, 1, Color.black, 0, 0, null, 0, null, controller, model);
	}
	
	/**
	 * Method to customise the rendering if a 'BoardSquare' object by painting a rectangular background, 
	 * an optional border, and an optional disk shape in the centre of the cell.
	 */
	protected void paintComponent(Graphics g) {
		if (borderColour != null) {
			g.setColor(borderColour);
			g.fillRect(0, 0, getWidth(), getHeight());
		}
		
		if (drawColour != null) {
			g.setColor(drawColour);
			g.fillRect(borderSize, borderSize, getWidth()-borderSize*2, getHeight()-borderSize*2);
		}
		
		if (diskBorder != null) {
	        g.setColor(diskBorder);
	        g.fillOval((getWidth()-diskWidth)/2 - diskBorderSize, (getHeight()-diskHeight)/2 - diskBorderSize, diskWidth + 2 * diskBorderSize, diskHeight + 2 * diskBorderSize);
	    }
	    
	    if (diskColour != null) {
	        g.setColor(diskColour);
	        g.fillOval((getWidth()-diskWidth)/2, (getHeight()-diskHeight)/2, diskWidth, diskHeight);
	    }
	}

}
