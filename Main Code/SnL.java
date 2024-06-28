import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;

public class SnL {
    private int boardSize;
    private ArrayList<Player> players;
    private ArrayList<Snake> snakes;
    private ArrayList<Ladder> ladders;
    private ArrayList<Integer> drawTiles;
    private int gameStatus;
    private int currentTurn;
    private StringBuilder gameLog;

    public SnL(int size) {
        this.boardSize = size;
        this.snakes = new ArrayList<>();
        this.ladders = new ArrayList<>();
        this.players = new ArrayList<>();
        this.drawTiles = new ArrayList<>();
        this.gameStatus = 0;
        this.gameLog = new StringBuilder();
    }

    public void playSound(String soundFile) {
        try {
            File soundPath = new File(soundFile);
            if (soundPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(soundPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
            } else {
                System.out.println("Sound file not found: " + soundFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initiateGame() {
        int[][] laddersArray = {{2, 23}, 
                                {8, 34}, 
                                {20, 77}, 
                                {32, 68}, 
                                {41, 79}, 
                                {74, 88}, 
                                {82, 100}, 
                                {85, 95}};
        setLadders(laddersArray);

        int[][] snakesArray = {{47, 5}, 
                               {29, 9}, 
                               {38, 15}, 
                               {97, 25}, 
                               {53, 33}, 
                               {92, 70}, 
                               {86, 54}, 
                               {97, 25}};
        setSnakes(snakesArray);

        int[] drawTilePositions = {5, 15, 25, 35, 45, 55, 65, 75, 85, 95};
        setDrawTiles(drawTilePositions);
    }

    public Player getTurn() {
        return this.players.get(this.currentTurn);
    }

    public void setSizeBoard(int size) {
        this.boardSize = size;
    }

    public void addPlayer(Player p) {
        this.players.add(p);
    }

    public void setLadders(int[][] ladders) {
        for (int[] ladder : ladders) {
            this.ladders.add(new Ladder(ladder[0], ladder[1]));
        }
    }

    public void setSnakes(int[][] snakes) {
        for (int[] snake : snakes) {
            this.snakes.add(new Snake(snake[0], snake[1]));
        }
    }

    public void setDrawTiles(int[] tiles) {
        for (int tile : tiles) {
            this.drawTiles.add(tile);
        }
    }

    public int getBoardSize() {
        return this.boardSize;
    }

    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    public ArrayList<Snake> getSnakes() {
        return this.snakes;
    }

    public ArrayList<Ladder> getLadders() {
        return this.ladders;
    }

    public int getGameStatus() {
        return this.gameStatus;
    }

    public Card drawRandomCard() {
        Random rand = new Random();
        int cardType = rand.nextInt(2); // 0 for boost, 1 for trap
        Card card = null;

        switch (cardType) {
            case 0: // Boost card
                String[] boostNames = {"Leap of Fortune", 
                                       "Double Delight", 
                                       "Snake Stun", 
                                       "Jumpstart", 
                                       "Surprise", 
                                       "Draw Again"};
                String[] boostDescriptions = {
                    "Take a giant step forward, advancing 10 tiles!",
                    "Double your fun! Roll the die twice on your next turn.",
                    "The next snake you encounter is Stunned!",
                    "Leap ahead to the nearest ladder",
                    "Teleport to the leading player on the board",
                    "Draw 2 additional cards to boost your hand."
                };
                int boostIndex = rand.nextInt(boostNames.length);
                card = new Card(boostNames[boostIndex], boostDescriptions[boostIndex], "boost");
                break;
            case 1: // Trap card
                String[] trapNames = {"Retro Roll", 
                                      "Stand Still", 
                                      "Backstep Blues", 
                                      "Broken Ladder", 
                                      "Reverse Swap"};
                String[] trapDescriptions = {
                    "Roll backward instead of forward on your next turn.",
                    "Freeze in place and miss your next turn.",
                    "Take a step back in time, moving 3 spaces backward.",
                    "The next ladder you step won't do anything",
                    "Trade places with the player closest behind you."
                };
                int trapIndex = rand.nextInt(trapNames.length);
                card = new Card(trapNames[trapIndex], trapDescriptions[trapIndex], "trap");
                break;
        }
        return card;
    }

    public void play() {
        initiateGame();
        //Setting amount of player in each game
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of Players: ");
        int playercount = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter the number of Star Norma: ");
        int normacount = sc.nextInt();
        sc.nextLine();
        for (int i = 1; i <= playercount; i++) {
            System.out.println("Enter player " + i + " name:");
            String playerName = sc.nextLine();
            Player player = new Player(playerName);
            player.addCard(drawRandomCard());
            addPlayer(player);
        }

        Player nowPlaying;
        do {
            nowPlaying = getTurn();
            gameLog.append("----------------------------------------------\n");
            gameLog.append("Now Playing: " + nowPlaying.getName() + " | Current Position: " + nowPlaying.getPosition() + " | Stars: " + nowPlaying.getStars() + "\n");
            gameLog.append("Your cards: \n");
            for (Card card : nowPlaying.getCards()) {
                gameLog.append(card.getName() + ": " + card.getDescription() + "\n");
            }
            System.out.println("----------------------------------------------");
            System.out.println("Now Playing: " + nowPlaying.getName() + " | Current Position: " + nowPlaying.getPosition() + " | Stars: " + nowPlaying.getStars());
            System.out.println("Your cards: ");
            for (Card card : nowPlaying.getCards()) {
                System.out.println(card.getName() + ": " + card.getDescription());
            }
            System.out.println(nowPlaying.getName() + ", do you want to (1) roll the dice, or (2) use a card");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline
            
            switch (choice) {
                case 1:
                    if (nowPlaying.hasStandStill()) {
                        gameLog.append(nowPlaying.getName() + " is frozen and misses this turn.\n");
                        nowPlaying.setStandStill(false);
                    } else {
                        int x = nowPlaying.rollDice();
                        nowPlaying.incrementRollCount();
                        if (nowPlaying.hasDoubleRoll()) {
                            x += nowPlaying.rollDice();
                            nowPlaying.setDoubleRoll(false);
                        }
                        gameLog.append(nowPlaying.getName() + " is rolling dice and gets number: " + x + "\n");
                        System.out.println(nowPlaying.getName() + " is rolling dice and gets number: " + x);
                        movePlayer(nowPlaying, x);
                        gameLog.append(nowPlaying.getName() + " new position is " + nowPlaying.getPosition() + "\n");
                        System.out.println(nowPlaying.getName() + " new position is " + nowPlaying.getPosition());
                    }
                    break;
                case 2:
                    if (nowPlaying.getCards().size() > 0) {
                        System.out.println("Your cards: ");
                        for (int i = 0; i < nowPlaying.getCards().size(); i++) {
                            System.out.println((i + 1) + ": " + nowPlaying.getCards().get(i).getName() + ": " + nowPlaying.getCards().get(i).getDescription());
                        }
                        System.out.println("Choose a card to use:");
                        int cardChoice = sc.nextInt() - 1;
                        playSound("D:/Snake and Ladder Java/Sound Effect/DrawCardSound.wav");
                        sc.nextLine(); // consume newline

                        if (cardChoice >= 0 && cardChoice < nowPlaying.getCards().size()) {
                            Card card = nowPlaying.getCards().get(cardChoice);
                            card.apply(nowPlaying, this);
                            nowPlaying.incrementTotalCardsUsed();
                            nowPlaying.getCards().remove(cardChoice);
                            gameLog.append(nowPlaying.getName() + " used card: " + card.getName() + "\n");
                            System.out.println(nowPlaying.getName() + " used card: " + card.getName());
                        } else {
                            gameLog.append("Invalid card choice, skipping turn.\n");
                            System.out.println("Invalid card choice, skipping turn.");
                        }
                    } else {
                        gameLog.append(nowPlaying.getName() + " has no cards to use.\n");
                        System.out.println(nowPlaying.getName() + " has no cards to use.");
                    }
                    break;
                default:
                    gameLog.append("Invalid choice, skipping turn.\n");
                    System.out.println("Invalid choice, skipping turn.");
                    break;
            }

            // Log player status
            gameLog.append(nowPlaying.getName())
                   .append(" - Position: ")
                   .append(nowPlaying.getPosition())
                   .append(", Stars: ")
                   .append(nowPlaying.getStars())
                   .append("\n");

            // Check for game end condition
            if (nowPlaying.getStars() >= normacount) {
                playSound("D:/Snake and Ladder Java/Sound Effect/WinnerSound.wav");
                gameLog.append("The Game is Over, the winner is: " + nowPlaying.getName() + "\n");
                System.out.println("The Game is Over, the winner is: " + nowPlaying.getName());
                break;
            }

            // Move to next turn
            currentTurn = (currentTurn + 1) % players.size();

        } while (getGameStatus() != 2);

        displayStats(); //added zel

        // Write game log to file
        try {
            // Create a new File object representing the directory where the game log will be saved
            File dir = new File("D:/Snake and Ladder Java/Game History");
            // Check if the directory does not exist
            if (!dir.exists()) {
                // Create the directory, including any necessary but nonexistent parent directories
                dir.mkdirs();
            }
            // Create a new FileWriter object to write the game log to a new file within the specified directory
            // The file name is generated using the current system time in milliseconds to ensure uniqueness
            FileWriter writer = new FileWriter(new File(dir, "Game" + System.currentTimeMillis() + ".txt"));
            // Write the contents of the gameLog StringBuilder to the file
            writer.write(gameLog.toString());
            // Close the FileWriter to release system resources and ensure all data is written to the file
            writer.close();
        } catch (IOException e) {
            // If an IOException occurs, print an error message to the standard error stream
            System.out.println("Error writing game log to file: " + e.getMessage());
        }

        sc.close();
    }

    public void movePlayer(Player p, int x) {
        this.gameStatus = 1;
        p.moveAround(x, this.boardSize);

        playSound("D:/Snake and Ladder Java/Sound Effect/DiceRollSound.wav");
    
        for (Ladder l : this.ladders) {
            if (l.getFromPosition() == p.getPosition()) {
                p.setPosition(l.getToPosition());
                p.incrementLadderCount();
                playSound("D:/Snake and Ladder Java/Sound Effect/UpLadderSound.wav");
                gameLog.append(p.getName() + " got ladder and jumps to " + p.getPosition() + "\n");
                System.out.println(p.getName() + " got ladder and jumps to " + p.getPosition());
            }
        }
    
        for (Snake s : this.snakes) {
            if (s.getHead() == p.getPosition()) {
                if (p.hasStunnedSnake()) {
                    p.setStunnedSnake(false);
                    gameLog.append(p.getName() + " encountered a snake but it's stunned!\n");
                    System.out.println(p.getName() + " encountered a snake but it's stunned!");
                } else {
                    p.setPosition(s.getTail());
                    p.incrementSnakeCount();
                    playSound("D:/Snake and Ladder Java/Sound Effect/DownSnakeSound.wav");
                    gameLog.append(p.getName() + " got snake and slides down to " + p.getPosition() + "\n");
                    System.out.println(p.getName() + " got snake and slides down to " + p.getPosition());
                }
            }
        }
    
        for (int tile : this.drawTiles) {
            if (tile == p.getPosition()) {
                Card card = drawRandomCard();
                p.addCard(card);
                playSound("D:/Snake and Ladder Java/Sound Effect/DrawCardSound.wav");
                gameLog.append(p.getName() + " landed on a draw tile and received card: " + card.getName() + "\n");
                System.out.println(p.getName() + " landed on a draw tile and received card: " + card.getName());
            }
        }
    
        if (p.getPosition() == this.boardSize) {
            p.setPosition(0);
            p.incrementStars();
            playSound("D:/Snake and Ladder Java/Sound Effect/StarEarnedSound.wav");
            gameLog.append(p.getName() + " reached the end and goes back to start with an extra star!\n");
            System.out.println(p.getName() + " reached the end and goes back to start with an extra star!");
        }
    }
    public void displayStats() {
        System.out.println("----------------------------------------------");
        System.out.println("Player's Performance Dashboard");
        gameLog.append("----------------------------------------------" + "\n");
        gameLog.append("Player's Performance Dashboard" + "\n");
     
     
        //sort players based on stars collected (descending order)
        Collections.sort(players, new Comparator<Player>() {
            public int compare(Player p1, Player p2) {
                return Integer.compare(p2.getStars(), p1.getStars());
            }
        });
     
      
        for (Player p : this.players) {
            System.out.println("Player: " + p.getName());
            gameLog.append("Player: " + p.getName() + "\n");
            System.out.println("Rank: " + (players.indexOf(p) + 1));
            gameLog.append("Rank: " + (players.indexOf(p) + 1) + "\n");
            System.out.println("Current Stars: " + p.getStars());
            gameLog.append("Current Stars: " + p.getStars() + "\n");
            System.out.println("Total rolls: " + p.getRollCount());
            gameLog.append("Total rolls: " + p.getRollCount() + "\n");
            System.out.println("Total ladders climbed: " + p.getLadderCount());
            gameLog.append("Total ladders climbed: " + p.getLadderCount() + "\n");
            System.out.println("Total snakes encountered: " + p.getSnakeCount());
            gameLog.append("Total snakes encountered: " + p.getSnakeCount() + "\n");
            System.out.println("Total cards used: " + p.getTotalCardsUsed());
            gameLog.append("Total cards used: " + p.getTotalCardsUsed() + "\n");
            System.out.println("----------------------------------------------");
            gameLog.append("---------------------------------------------- " + "\n");
        }
     }
     
}
