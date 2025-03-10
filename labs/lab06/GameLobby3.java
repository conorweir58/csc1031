import java.util.List;
import java.util.ArrayList;

interface Player 
{
    void joinGame();
    void leaveGame();
    void sendMessage(String message);
    void receiveMessage(String message);
    String getPlayerType();
    void setName(String name);
    String getName();
}

abstract class AbstractPlayer implements Player {
    protected String name;
    protected GameLobby lobby;

    public AbstractPlayer(String name, GameLobby lobby)
    {
        this.name = name;
        this.lobby = lobby;
    }

    // Getters + Setters
    @Override
    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public void joinGame()
    {
        lobby.registerPlayer(this);
    }

    @Override
    public void leaveGame()
    {
        lobby.removePlayer(this);
    }

    @Override
    public void sendMessage(String message)
    {
        if(getPlayerType().equals("Spectator"))
        {
            System.out.println("[GameLobby] Spectators cannot send messages.");
            return;
        }

        System.out.println(String.format("[%s] sends: \"%s\"", getName(), message));
        lobby.sendMessage(message, this);       
    }

    @Override
    public void receiveMessage(String message)
    {
        System.out.println(String.format("[%s] received: \"%s\"", getName(), message));
    }

    public abstract String getPlayerType();
}

class HumanPlayer extends AbstractPlayer
{
    // Constructor
    public HumanPlayer(String name, GameLobby lobby)
    {
        super(name, lobby);
    }

    @Override
    public String getPlayerType()
    {
        return "HumanPlayer";
    }
}

class AIPlayer extends AbstractPlayer
{
    // Constructor
    public AIPlayer(String name, GameLobby lobby)
    {
        super(name, lobby);
    }

    @Override
    public String getPlayerType()
    {
        return "AIPlayer";
    }
}

class Spectator extends AbstractPlayer
{
    // Constructor
    public Spectator(String name, GameLobby lobby)
    {
        super(name, lobby);
    }

    @Override
    public String getPlayerType()
    {
        return "Spectator";
    }
}

class AdminPlayer extends AbstractPlayer {
    public AdminPlayer(String name, GameLobby lobby) {
        super(name, lobby);
    }

    @Override
    public String getPlayerType() {
        return "AdminPlayer";
    }

    public void kickPlayer(String name) {
        lobby.kickPlayer(name, this);
    }
}

class GameLobby
{
    List<Player> players = new ArrayList<>();

    void registerPlayer(Player player)
    {
        players.add(player);
        System.out.println(String.format("[GameLobby] %s %s has joined the lobby.", player.getPlayerType(), player.getName()));
    }

    void removePlayer(Player player)
    {
        players.remove(player);
        System.out.println(String.format("[GameLobby] %s %s has left the lobby.", player.getPlayerType(), player.getName()));

    }

    void kickPlayer(String name, AdminPlayer admin)
    {
        for(int i=0; i < players.size(); i++)
        {
            if(players.get(i).getName() == name && !(players.get(i).getPlayerType().equals("AdminPlayer")))
            {

                Player player = players.get(i);

                System.out.println(String.format("[GameLobby] Admin %s kicked %s %s from the lobby.", admin.getName(), player.getPlayerType(), player.getName()));
                removePlayer(player);
                return;
            }
            
        }
        System.out.println(String.format("[GameLobby] Player %s not found.", name));
    }


    void sendMessage(String message, Player sender)
    {

        System.out.println(String.format("[GameLobby] Message from %s: \"%s\"", sender.getName(), message));

        
        for(int i=0; i < players.size(); i++)
        {
            if(!(players.get(i) == sender))
            {
                players.get(i).receiveMessage(message);
            }
        }

    }

    void startMatch()
    {
        List<String> active_players = new ArrayList<>();


        int count = 0;
        for (Player player : players) {
            if (player.getPlayerType().equals("HumanPlayer") || player.getPlayerType().equals("AIPlayer")) {
                active_players.add(player.getName());
                count++;
            }
        }

        if(count < 2)
        {
            System.out.println("[GameLobby] Not enough players to start a match.");
            return;
        }

        
        String playerNames = String.join(", ", active_players);

    
        
        System.out.println("[GameLobby] Starting game with players: " + playerNames);

    }

}

class PlayerFactory {
    public static Player createPlayer(String type, String name, GameLobby lobby) {
        switch (type.toLowerCase()) {
            case "human":
                return new HumanPlayer(name, lobby);
            case "ai":
                return new AIPlayer(name, lobby);
            case "spectator":
                return new Spectator(name, lobby);
            case "admin":
                return new AdminPlayer(name, lobby);
            default:
                return null;

        }
    }
}