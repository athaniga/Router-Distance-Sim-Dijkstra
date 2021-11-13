import java.util.ArrayList;

public class Router {

    private String name;
    private ArrayList<String> neighbors;
    private ArrayList<Integer> cost;
    private ArrayList<String> destination;
    private String[][] routTable;




    public Router() {

    }

    public Router(String name, ArrayList<String> neighbors, ArrayList<Integer> cost) {
        this.name = name;
        this.neighbors = neighbors;
        this.cost = cost;

    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(ArrayList<String> neighbors) {
        this.neighbors = neighbors;
    }

    public ArrayList<Integer> getCost() {
        return cost;
    }

    public void setCost(ArrayList<Integer> cost) {
        this.cost = cost;
    }

    public ArrayList<String> getDestination() {
        return destination;
    }

    public void setDestination(ArrayList<String> destination) {
        this.destination = destination;
    }

    public String[][] getRoutTable() {
        return routTable;
    }

    public void setRoutTable(String[][] routTable) {
        this.routTable = routTable;
    }

    public void changeCost(int neighborNum, int cost) {
        this.getCost().set(neighborNum - 1, cost);
    }




}
