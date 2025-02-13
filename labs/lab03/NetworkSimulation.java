import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class NetworkSimulation
{
    // Classes
    static class CellTower
    {
        private String id;
        private int x;
        private int y;
        private double coverage;
        private Operator operator;
        private List<Client> connections = new ArrayList<>();

        // Constructors
        public CellTower (String id, int x, int y, double coverage)
        {
            this.id = id;
            this.x = x;
            this.y = y;
            this.coverage = coverage;
        }

        // Getters + Setters
        public void setOperator (Operator operator)
        {
            this.operator = operator;
        }

        public Operator getOperator ()
        {
            return operator;
        }

        public List<Client> getConnections ()
        {
            return connections;
        }

        // Helper Functions
        public boolean isinRange (int cx, int cy)
        {
            // System.out.println(Math.sqrt(Math.pow(x - cx, 2) + Math.pow(y - cy, 2)) + " <= " + coverage);
            return Math.sqrt(Math.pow(x - cx, 2) + Math.pow(y - cy, 2)) <= coverage;
        }

    }

    static class Operator
    {
        private String name;
        private List<Client> opClients = new ArrayList<>();
        private List<CellTower> cellTowers = new ArrayList<>();

        // Constructors
        public Operator (String name)
        {
            this.name = name;
        }

        // Getters + Setters
        public String getOpName ()
        {
            return name;
        }

        public List<Client> getClients ()
        {
            return opClients;
        }

        public List<CellTower> getTowers ()
        {
            return cellTowers;
        }
    }

    static class Client 
    {
        private String phoneNumber;
        private int x, y;
        private Operator operator;
        private CellTower connectedTower;

        // Constructor
        public Client(String phoneNumber, Operator operator, int x, int y) {
            this.phoneNumber = phoneNumber;
            this.operator = operator;
            this.x = x;
            this.y = y;
        }

        // Getters + Setters
        public void setLocation(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void setOperator(Operator operator) {
            this.operator = operator;
        }

        public void setConnectedTower(CellTower tower) {
            this.connectedTower = tower;
        }

        public CellTower getConnectedTower() {
            return connectedTower;
        }

        public Operator getOperator()
        {
            return operator;
        }

    }

    static class Network
    {
        private Map<String, CellTower> cellTowersMap = new HashMap<String, CellTower>();
        private Map<String, Operator> operatorsMap = new HashMap<String, Operator>();
        private Map<String, Client> clientsMap = new HashMap<String, Client>();

        public void addTower (String id, int x, int y, double coverage) // When we add a new tower -> Do we have to update all client connections to see if any are closer to the new tower?
        {
            cellTowersMap.put(id, new CellTower(id, x, y, coverage));
        }

        public void addOperator (String name)
        {
            operatorsMap.put(name, new Operator(name));
        }

        public void addClient (String phoneNumber, String name, int x, int y)
        {
            if(!(operatorsMap.containsKey(name)))
            {
                System.out.println("This Operator does not exist!");
                return;
            }

            Operator operator = operatorsMap.get(name);
            Client client = new Client(phoneNumber, operator, x, y);
            clientsMap.put(phoneNumber, client);
            operator.getClients().add(client);
            // *** Need to add function that will add this client to a Cell Towers client list based on distance *** DONE?
            updateConnectionClient(client);
        }

        public void registerOperatorTower(String name, String id)
        {
            if(!(operatorsMap.containsKey(name) || cellTowersMap.containsKey(id)))
            {
                System.out.println("Invalid Operator Tower Registration!");
                return;
            }

            CellTower cellTower = cellTowersMap.get(id);
            Operator operator = operatorsMap.get(name);

            cellTower.setOperator(operator);
            operator.getTowers().add(cellTower);
        }

        public void updateConnectionClient (Client client)
        {
            CellTower bestTower = null;
            double bestDistance = Double.MAX_VALUE;

            for(CellTower tower : cellTowersMap.values())
            {
                // The client and T2 are out of range !!!!!
                if(tower.isinRange(client.x, client.y) && (tower.getOperator() == client.getOperator()))
                {
                    double distance = Math.sqrt(Math.pow(tower.x - client.x, 2) + Math.pow(tower.y - client.y, 2));

                    if(distance < bestDistance)
                    {
                        bestDistance = distance;
                        bestTower = tower;
                    }
                    // if distances are equal, asign the client to the tower with the least amount of connections
                    else if (distance == bestDistance)
                    {
                        if(bestTower == null || tower.getConnections().size() <= bestTower.getConnections().size())
                        {
                            bestTower = tower;
                        }
                    }
                }
            }
            // After we find the best fitting CellTower

            // if the client already has a connected tower, remove it
            if(client.getConnectedTower() != null)
            {
                client.getConnectedTower().getConnections().remove(client);
            }
            
            // ERROR -> GETTING NO BEST TOWER AFTER DELETING A TOWER
            client.setConnectedTower(bestTower);
            // System.out.println(bestTower.id);
            
            if(bestTower != null)
            {
                // System.out.println("CLIENT " + client.phoneNumber +" added to " + bestTower.id);         TESTING
                bestTower.getConnections().add(client);
            }
        }

        // Come back to these and think of possible errors and all things that will need updating
        public void moveClient(String phoneNumber, int new_x, int new_y)
        {
            Client client = clientsMap.get(phoneNumber);

            client.setLocation(new_x, new_y);
            updateConnectionClient(client);
        }

        public void changeOperator(String phoneNumber, String new_op)
        {
            Client client = clientsMap.get(phoneNumber);
            Operator operator = operatorsMap.get(new_op);

            client.operator.getClients().remove(client);
            client.setOperator(operator);
            client.operator.getClients().add(client);

            updateConnectionClient(client);
        }

        public void towerClientCount(String id)
        {
            if(!cellTowersMap.containsKey(id))
            {
                System.out.println(0);
                return;
            }

            CellTower cellTower = cellTowersMap.get(id);

            System.out.println(cellTower.getConnections().size());
        }

        public void operatorSubscriberCount(String name)
        {
            if(!operatorsMap.containsKey(name))
            {
                return;
            }

            Operator operator = operatorsMap.get(name);

            System.out.println(operator.getClients().size());
        }

        public void removeClient(String phoneNumber)
        {
            if(!clientsMap.containsKey(phoneNumber))
            {
                return;
            }

            Client client = clientsMap.get(phoneNumber);

            client.operator.getClients().remove(client);

            if(client.getConnectedTower() != null)
            {
                client.getConnectedTower().getConnections().remove(client);
            }
            clientsMap.remove(phoneNumber);
            client = null;
        }

        public void removeTower(String id)
        {
            if(!cellTowersMap.containsKey(id))
            {
                return;
            }

            CellTower cellTower = cellTowersMap.get(id);

            cellTower.getOperator().getTowers().remove(cellTower);
            cellTower.setOperator(null);
            cellTowersMap.remove(id);

            // reacllocate all clients to new towers
            for(Client client : new ArrayList<>(cellTower.getConnections()))
            {
                // System.out.println(client.phoneNumber);                      TESTING
                updateConnectionClient(client);
            }

            cellTower = null;
        }

        public void noSignalCount()
        {
            for(Operator operator : operatorsMap.values())
            {
                int count = 0;

                for(Client client : operator.getClients())
                {
                    if(client.getConnectedTower() == null)
                    {
                        count++;
                    }
                }

                System.out.println(operator.name + ": " + count + " phones without signal.");
            }
        }
    }

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        Network network = new Network();

        while (scanner.hasNext()) {
            String command = scanner.next();
            switch (command) 
            {
                case "ADD_TOWER":
                    String towerId = scanner.next();
                    int x = scanner.nextInt();
                    int y = scanner.nextInt();
                    double coverage = scanner.nextDouble();
                    network.addTower(towerId, x, y, coverage);
                    break;
                case "ADD_OPERATOR":
                    String operatorName = scanner.next();
                    network.addOperator(operatorName);
                    break;
                case "ADD_CLIENT":
                    String phone = scanner.next();
                    String opName = scanner.next();
                    int px = scanner.nextInt();
                    int py = scanner.nextInt();
                    network.addClient(phone, opName, px, py);
                    break;
                case "MOVE_CLIENT":
                    phone = scanner.next();
                    int newX = scanner.nextInt();
                    int newY = scanner.nextInt();
                    network.moveClient(phone, newX, newY);
                    break;
                case "CHANGE_OPERATOR":
                    phone = scanner.next();
                    opName = scanner.next();
                    network.changeOperator(phone, opName);
                    break;
                case "REGISTER_OPERATOR_TOWER":
                    opName = scanner.next();
                    towerId = scanner.next();
                    network.registerOperatorTower(opName, towerId);
                    break;
                case "TOWER_CLIENT_COUNT":
                    towerId = scanner.next();
                    network.towerClientCount(towerId);
                    break;
                case "OPERATOR_SUBSCRIBER_COUNT":
                    opName = scanner.next();
                    network.operatorSubscriberCount(opName);
                    break;
                case "REMOVE_CLIENT":
                    phone = scanner.next();
                    network.removeClient(phone);
                    break;
                case "REMOVE_TOWER":
                    towerId = scanner.next();
                    network.removeTower(towerId);
                    break;
                case "NO_SIGNAL_COUNT":
                    network.noSignalCount();
                    break;

            }
        }

        scanner.close();

    }
}