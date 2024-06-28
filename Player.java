import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name; // Player's name
    private int position; // Current position on the board
    private int stars; // Number of stars the player has earned
    private List<Card> cards; // List of cards the player holds
    private boolean doubleRoll; // Indicates if the player has a double roll
    private boolean stunnedSnake; // Indicates if the player has a stunned snake
    private boolean retroRoll; // Indicates if the player has a retro roll
    private boolean standStill; // Indicates if the player must stand still
    private boolean brokenLadder; // Indicates if the player has a broken ladder
    private int rollCount; // Number of dice rolls the player has made
    private int ladderCount; // Number of ladders the player has climbed
    private int snakeCount; // Number of snakes the player has encountered
    private int totalCardsUsed; // Total number of cards the player has used

    
    //Constructor for the Player class.
    //Initializes the player with a name and default values for other fields. 
    //name The player's name
     
    public Player(String name) {
        this.name = name;
        this.position = 0;
        this.stars = 0;
        this.rollCount = 0;
        this.ladderCount = 0;
        this.snakeCount = 0;
        this.totalCardsUsed = 0;
        this.cards = new ArrayList<>();
        this.doubleRoll = false;
        this.stunnedSnake = false;
        this.retroRoll = false;
        this.standStill = false;
        this.brokenLadder = false;
    }

    // Getter for the player's name
    public String getName() {
        return name;
    }

    // Getter for the player's position
    public int getPosition() {
        return position;
    }

    // Setter for the player's position
    public void setPosition(int position) {
        this.position = position;
    }

    // Increments the player's roll count by 1
    public void incrementRollCount() {
        this.rollCount++;
    }

    // Increments the player's ladder count by 1
    public void incrementLadderCount() {
        this.ladderCount++;
    }

    // Increments the player's snake count by 1
    public void incrementSnakeCount() {
        this.snakeCount++;
    }

    // Increments the total cards used by the player by 1
    public void incrementTotalCardsUsed() {
        this.totalCardsUsed++;
    }

    // Getter for the player's stars
    public int getStars() {
        return stars;
    }

    // Increments the player's stars by 1
    public void incrementStars() {
        this.stars++;
    }

    // Getter for the player's cards
    public List<Card> getCards() {
        return cards;
    }

    // Adds a card to the player's hand
    public void addCard(Card card) {
        this.cards.add(card);
    }

    // Getter for the double roll status
    public boolean hasDoubleRoll() {
        return doubleRoll;
    }

    // Setter for the double roll status
    public void setDoubleRoll(boolean doubleRoll) {
        this.doubleRoll = doubleRoll;
    }

    // Getter for the stunned snake status
    public boolean hasStunnedSnake() {
        return stunnedSnake;
    }

    // Setter for the stunned snake status
    public void setStunnedSnake(boolean stunnedSnake) {
        this.stunnedSnake = stunnedSnake;
    }

    // Getter for the retro roll status
    public boolean hasRetroRoll() {
        return retroRoll;
    }

    // Setter for the retro roll status
    public void setRetroRoll(boolean retroRoll) {
        this.retroRoll = retroRoll;
    }

    // Getter for the stand still status
    public boolean hasStandStill() {
        return standStill;
    }

    // Setter for the stand still status
    public void setStandStill(boolean standStill) {
        this.standStill = standStill;
    }

    // Getter for the broken ladder status
    public boolean hasBrokenLadder() {
        return brokenLadder;
    }

    // Setter for the broken ladder status
    public void setBrokenLadder(boolean brokenLadder) {
        this.brokenLadder = brokenLadder;
    }

    // Rolls a dice and returns a value between 1 and 6
    public int rollDice() {
        return (int) ((Math.random() * 6) + 1);
    }

    // Getter for the roll count
    public int getRollCount() {
        return this.rollCount;
    }

    // Getter for the ladder count
    public int getLadderCount() {
        return this.ladderCount;
    }

    // Getter for the snake count
    public int getSnakeCount() {
        return this.snakeCount;
    }

    // Getter for the total cards used
    public int getTotalCardsUsed() {
        return this.totalCardsUsed;
    }

    
     //Moves the player around the board.
     //If the new position exceeds the board size, the player goes back.
     //x The number of steps to move
     //boardSize The size of the board
     
    public void moveAround(int x, int boardSize) {
        if (this.position + x > boardSize) {
            this.position = (boardSize - this.position) + (boardSize - x);
        } else {
            this.position = this.position + x;
        }
    }
}