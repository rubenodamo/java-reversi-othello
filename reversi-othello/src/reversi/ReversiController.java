package reversi;

public class ReversiController implements IController {
	
	IModel model;
	IView view;
	
	/**
	 * Initialise the controller for a model and view
	 * @param model The model to use
	 * @param view The view to use.
	 */
	@Override
	public void initialise(IModel model, IView view) {
		
		this.model = model;
		this.view = view;

	}
	
	/**
	 * Start the game again - set up view and board appropriately
	 * See requirements - don't forget to set the player number and finished flag to the correct values as well as clearing the board.
	 */
	@Override
	public void startup() {
		
		// clears board
		model.clear(0);
		
		// sets up initial pieces
		model.setBoardContents(3, 3, 1);
		model.setBoardContents(4, 4, 1);
		model.setBoardContents(4, 3, 2);
		model.setBoardContents(3, 4, 2);
		
		// set starting player
		model.setPlayer(1);
		
		// set finished flag to false
		model.setFinished(false);
		
		// refresh all messages and frames
		view.feedbackToUser(1, "White player - choose where to put your piece");
		view.feedbackToUser(2, "Black player - not your turn");
		view.refreshView();

	}

	/**
	 * Implement this to update any state information.
	 * This is really used to simplify your testing (so that SimpleTestModel will work for you) and to simplify my testing.
	 * 
	 * Ideally this needs to do three things:
	 * If the controller uses the finished flag, then it should look at the board and set the finished flag or not according to whether the game has finished.
	 * If the controller use the player number, then it should check it in case it changed. (Probably nothing to do for this.) 
	 * Also set the feedback to the user according to which player is the current player (model.getPlayer()) and whether the game has finished or not.
	 * 
	 * Once you have implemented this, you may find it easy to just call it after each time you add a piece to the board and have changed the player, to update the player feedback and the finished status accordingly. 
	 * e.g. mine for Reversi does the following
	 * If current player cannot play, change to other player.
	 * If this player also cannot play then end the game - count the number of pieces, set the labels for players, and set the finished flag.
	 * If the game has not finished, send the correct feedback messages to players to tell them whose turn it is.
	 */
	public void update() {
		model.setFinished(false);
		
		int currentPlayer = model.getPlayer();
	    int otherPlayer = (currentPlayer == 1) ? 2 : 1;
	    
	    // sets labels depending on the player
	    if (currentPlayer == 1) {
        	view.feedbackToUser(1, "White player - choose where to put your piece");
    		view.feedbackToUser(2, "Black player - not your turn");
        }
        else {
        	view.feedbackToUser(2, "Black player - choose where to put your piece");
    		view.feedbackToUser(1, "White player - not your turn");
        }
	    
		//check if there are any available moves for either player
		// count the score for each player
		boolean currentPlayerCanMove = false;
		boolean otherPlayerCanMove = false;
		int whiteScore = 0;
		int blackScore = 0;
		
		for(int x = 0; x < model.getBoardWidth(); x++) {
			for(int y = 0; y < model.getBoardHeight(); y++) {
				if(model.getBoardContents(x, y) == 1) {
					whiteScore++;
				}
				else if(model.getBoardContents(x, y) == 2) {
					blackScore++;
				}
				else {
					if(count(model.getPlayer(), x, y, false) > 0) {
						currentPlayerCanMove = true;
						//currentPlayerMoves++;
					}
					if(count(otherPlayer, x, y, false) > 0) {
						otherPlayerCanMove = true;
						//otherPlayerMoves++;
					}	
				}
			}
		}
		
		// if the current player can't make a move, switch to the other player
		if(!currentPlayerCanMove) {
			model.setPlayer(otherPlayer);
		}
		
		// if neither player can make a move, the game is over
		if(!currentPlayerCanMove && !otherPlayerCanMove) {
			model.setFinished(true);
		}
				
		// set labels if finished is true
		if(model.hasFinished() == true) {
			// white wins
			if(whiteScore > blackScore) {
				view.feedbackToUser(1, "White won. White " + whiteScore + " to Black " + blackScore + ". Reset the game to replay.");
				view.feedbackToUser(2, "White won. White " + whiteScore + " to Black " + blackScore + ". Reset the game to replay.");
			}
			// black wins
			else if(blackScore > whiteScore) {
				view.feedbackToUser(1, "Black won. Black " + blackScore + " to White " + whiteScore + ". Reset the game to replay.");
				view.feedbackToUser(2, "Black won. Black " + blackScore + " to White " + whiteScore + ". Reset the game to replay.");
			}
			// draw
			else {
				view.feedbackToUser(1, "Draw. Both players ended with " + whiteScore + " pieces. Reset game to replay.");
				view.feedbackToUser(2, "Draw. Both players ended with " + whiteScore + " pieces. Reset game to replay.");
			}
		}
	
		view.refreshView(); 
	}


	
	/**
	 * Called by view when some position on the board is selected
	 * @param player Which player clicked the square, 1 (white) or 2 (black).
	 * @param x The x coordinate of the square which is clicked
	 * @param y The y coordinate of the square which is clicked
	 */
	@Override
	public void squareSelected(int player, int x, int y) {
		// checks if the game is finished or not
		if(model.hasFinished()) {
			return;
		}
		
		// checks if its the correct player's turn
		if(model.getPlayer() != player) {
			view.feedbackToUser(player, "It is not your turn!");
			return;
		}
		
		int currentPlayer = player;
	    int otherPlayer = (currentPlayer == 1) ? 2 : 1;
	    
		// checks if the move is valid and performs capture
		if(count(player, x, y, false) > 0)
		{
			count(player, x, y, true);
			model.setPlayer(otherPlayer);
			update();
		}
	}
	
	/**
	 * Counts the number of pieces that would be flipped in a certain direction
	 * if the current player placed a piece at a certain location on the board.
	 * Performs flip if the boolean parameter is true.
	 *
	 * @param player The current player
	 * @param x The x-coordinate of the location to check
	 * @param y The y-coordinate of the location to check
	 * @param doFlip flag to detail whether to flip or not
	 * @return The number of pieces that would be flipped in the desired direction
	 */
	private int count(int player, int x, int y, boolean doFlip) {
		int flipCount = 0;
		int otherPlayer = (player == 1) ? 2 : 1;
		
		// 2D array of flip positions
		int[][] flipPositions = new int[model.getBoardHeight()*model.getBoardWidth()][2];
		
		// checks if the square on the board is occupied by another piece
	    if(model.getBoardContents(x, y) != 0) {
	        return 0;
	    }
	    
		// selected square
		if(doFlip) {
			model.setBoardContents(x, y, player);
		}
		
		 /* deltaX The x-coordinate increment to move in the desired direction
			deltaY The y-coordinate increment to move in the desired direction */
		for (int dx = -1; dx <= 1; dx++) {
			for (int dy = -1; dy <= 1; dy++) {				
				boolean flag = true;
				int count = 0;
				int px = x;
				int py = y;
				
				if(dx == 0 && dy == 0) continue; // skip the current position

				// while flag is true
				while(flag) {
					// dynamic x and y position variables
					px += dx;
					py += dy;
					
					// checks if position is on the edge of the board
					if(px >= model.getBoardWidth() || py >= model.getBoardHeight()) {
						count = 0;
						break;
					}
					if(px < 0 || py < 0) {
						count = 0;
						break;
					}

					int contents = model.getBoardContents(px, py); 
					// if disk is the other player disk
					if(contents == otherPlayer) {
						// add to array and increment count as flip may occur
						flipPositions[count][0] = px;
						flipPositions[count][1] = py;
						count++;
					}
					// when current player disk is found
					else if(contents == player) {
						// if doFilp is true and count is greater than 0 perform the capture on positions in the array
						if(doFlip) {
							if(count > 0) {
								for(int i = 0; i < count; i++) {
									model.setBoardContents(flipPositions[i][0], flipPositions[i][1], player);
								}
							}	
						}
						// set flag to false
						flag = false;
					}
					// else square is empty
					else {
						count = 0;
						flag = false;
					}
				}
				// add to flip count 
				flipCount += count;
			}
		}
		
		// refresh view if flip is performed
		if(doFlip) {
			view.refreshView();
		}
		
		return flipCount;
	}
	

	/**
	 * Called when view requests an automated move.
	 * @param player Which player the automated move should happen for, 1 (white) or 2 (black).
	 */
	@Override
	public void doAutomatedMove(int player) {
		int aiX = 0;
		int aiY = 0;
		int flips = 0;
		
		for(int x = 0; x < model.getBoardWidth(); x++) {
			for(int y = 0; y < model.getBoardHeight(); y++) {
				int count = count(player, x, y, false);
				if( count > flips) {
					flips = count;
					aiX = x;
					aiY = y;
				}
			}
		}
		squareSelected(player, aiX, aiY);
	}
}

