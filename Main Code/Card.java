public class Card {
    private String name; // The name of the card
    private String description; // The description of what the card does
    private String type; // The type of card, either "boost" or "trap"

    
     //Constructor for the Card class.
     
     //name The name of the card
     //description The description of the card's effect
     //type The type of the card ("boost" or "trap")
    public Card(String name, String description, String type) {
        this.name = name;
        this.description = description;
        this.type = type;
    }

    
    //Gets the name of the card. 
    //return The name of the card
    
    public String getName() {
        return name;
    }

    
    // Gets the description of the card.
    //The description of the card
     
    public String getDescription() {
        return description;
    }

    //Gets the type of the card.  
    //return The type of the card ("boost" or "trap")

    public String getType() {
        return type;
    }

     //Applies the card's effect to the given player in the context of the given game.
     //player to whom the card's effect is applied
     //The game in which the card's effect is applied
     
    public void apply(Player player, SnL game) {
        switch (name) {
            case "Leap of Fortune":
                // Move the player 10 tiles forward
                player.moveAround(10, game.getBoardSize());
                break;
            case "Double Delight":
                // Enable double roll for the player on their next turn
                player.setDoubleRoll(true);
                break;
            case "Snake Stun":
                // Stun the next snake the player encounters
                player.setStunnedSnake(true);
                break;
            case "Jumpstart":
                // Move the player to the nearest ladder above their current position
                for (Ladder ladder : game.getLadders()) {
                    if (ladder.getFromPosition() > player.getPosition()) {
                        player.setPosition(ladder.getFromPosition());
                        break;
                    }
                }
                break;
            case "Surprise":
                // Move the player to the position of the leading player
                int maxPosition = 0;
                for (Player p : game.getPlayers()) {
                    if (p.getPosition() > maxPosition) {
                        maxPosition = p.getPosition();
                    }
                }
                player.setPosition(maxPosition);
                break;
            case "Draw Again":
                // Give the player two additional random cards
                player.addCard(game.drawRandomCard());
                player.addCard(game.drawRandomCard());
                break;
            case "Retro Roll":
                // Enable retro roll, where the player rolls backward on their next turn
                player.setRetroRoll(true);
                break;
            case "Stand Still":
                // Freeze the player, causing them to miss their next turn
                player.setStandStill(true);
                break;
            case "Backstep Blues":
                // Move the player 3 tiles backward
                player.moveAround(-3, game.getBoardSize());
                break;
            case "Broken Ladder":
                // Disable the next ladder the player steps on
                player.setBrokenLadder(true);
                break;
            case "Reverse Swap":
                // Swap positions with the player closest behind the current player
                int closestBehind = -1;
                int minDistance = Integer.MAX_VALUE;
                for (Player p : game.getPlayers()) {
                    int distance = player.getPosition() - p.getPosition();
                    if (distance > 0 && distance < minDistance) {
                        closestBehind = p.getPosition();
                        minDistance = distance;
                    }
                }
                if (closestBehind != -1) {
                    int tempPosition = player.getPosition();
                    player.setPosition(closestBehind);
                    for (Player p : game.getPlayers()) {
                        if (p.getPosition() == closestBehind) {
                            p.setPosition(tempPosition);
                            break;
                        }
                    }
                }
                break;
        }
    }
}
