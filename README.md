# Java Reversi/Othello Game

The Reversi/Othello Game project is a Java-based implementation of the classic Reversi/Othello board game. The project adheres to the Model-View-Controller (MVC) architectural pattern to ensure a well-structured and modular design. It encompasses a graphical user interface (GUI) that enables players to interact with the game board, make moves, and compete against each other or an AI opponent. The program's components include the `GUIView` class, which handles the graphical interface, and the `ReversiController` class, responsible for controlling game logic. The project also utilises the `SimpleModel` class, which manages the game state, including current positions of pieces on the board. The project allows flexibility by supporting various combinations of controllers and views for testing purposes. It employs standard Swing/AWT GUI classes and follows programming best practices, providing players with a functional and engaging game experience.

## Key Features

- **MVC Architecture**: The program is organized into Model, View, and Controller components to ensure modularity and facilitate testing. The codebase introduces essential classes such as `GUIView` (derived from `IView`) for a user-friendly graphical interface and `ReversiController` (extending `IController`) for gameplay control. The MVC structure also allows for versatile game design, with various controller and view classes being able to be substituted for one another.

- **Controller and View Class**: Developed a `ReversiController` class that extends `IController` for controlling the game. Implemented a `GUIView` class that extends `IView` for the graphical user interface. It interacts with the rest of the program through `IView` methods only.

- **Utilization of Standard Swing/AWT**: Utilized standard Swing/awt GUI classes for creating the user interface. The provided classes and custom subclasses of Swing classes are employed to design the GUI elements.

- **Piece Color Handling**: The program automatically changes the color of pieces that are captured during the game, adhering to the game's rules.

- **Win Detection**: Implemented win detection functionality to identify the player who wins the game. The winner is determined based on the count of pieces of each color on the board.

- **Greedy AI Function**:  Included a simple 'greedy' AI for single-player mode. The AI makes decisions based on maximizing its piece count and capturing opponent pieces.

- **Visual Representation**: The final program visually resembles the Reversi/Othello game board, starting with an initial configuration of pieces, and updates the board as players make moves. The aesthetics of the interface are also reminiscent of the classic Reversi board game.


<p align="center">
  <figure>
    <img src="https://github.com/rubenodamo/java-reversi-othello/assets/93412774/e32fae5e-38ca-4132-a655-bf214576e385">
    <figcaption style="text-align:center">Example Initial Board State</figcaption>
  </figure>
</p>


