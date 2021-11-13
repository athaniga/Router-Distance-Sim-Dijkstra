import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.*;

public class Main {
    static int numOfRouters;
    static String choice = "e";
    static int[][] routingTable;
    static Scanner kbd = new Scanner(System.in);



    public static void main(String[] args) {
        FilePicker dialog = new FilePicker();
       // dialog.pack();
       // dialog.setVisible(true);
        File config = dialog.onOK();
        //File config = new File("C:\\Users\\ahani\\Desktop\\NEIU\\Previous Semesters\\CS331 Comp Networks\\config3.txt");
        ArrayList<Router> routers;
        routers = buildRouters(config);
        do {


            System.out.println("Reading from " + config.getName() + "\n");

            if (routers.size() == 0) {
                System.out.println("Error building router list");
            }
            routingTable = new int[numOfRouters][numOfRouters];
            //Show loaded routers and their info
            for (int i = 0; i < numOfRouters; i++) {
                System.out.println("=============================================================");
                System.out.println("Router: " + (i + 1));
                System.out.println(routers.get(i).getName());
                System.out.println(routers.get(i).getNeighbors());
                System.out.println(routers.get(i).getCost());
                System.out.println("=============================================================\n");
            }

            //Print Routing Tables
            for (int i = 0; i < numOfRouters; i++) {
                String packet = builder(routers.get(i));
                for (int j = 0; j < numOfRouters; j++) {
                    listener(packet, routers.get(i));
                }
            }

            for (int i = 0; i < routingTable.length; i++) {
                for (int j = 0; j < routingTable.length; j++) {
                    System.out.print("("+routingTable[i][j]+") ");
                }
                System.out.println();
            }

            System.out.println();
            for (int i = 0; i < numOfRouters; i++) {
                shortestPath(routingTable, i);
                System.out.println();
            }

            System.out.println();
            System.out.println("Enter 'c' to change a router and neighbor's cost.\nEnter 'e' to end program");
            choice = kbd.next();
            while (choice.equalsIgnoreCase("c")) {
                int routNum = 0;
                int neighborNum = 0;
                int costUpdate = 0;
                System.out.println("Which router # would you like to select?");
                routNum = kbd.nextInt();
                System.out.println("Which neighbor's cost would you like to change?");
                neighborNum = kbd.nextInt();

                System.out.println("What is the new cost you wish to update?");
                costUpdate = kbd.nextInt();

                routers.get(routNum - 1).changeCost(neighborNum, costUpdate);
                choice = "new";

            }


        } while(!choice.equalsIgnoreCase("e"));

    }


    public static ArrayList<Router> buildRouters(File config) {

        try {
            Scanner configReader = new Scanner(config);
            ArrayList<String> configData = new ArrayList<String>();
            while(configReader.hasNextLine()) {
                configData.add(configReader.nextLine());
            }

            numOfRouters = Integer.parseInt(configData.get(0));


            ArrayList<Router> routList = new ArrayList<Router>();
            ArrayList<String> destinations = new ArrayList<>();
            for (int i = 1; i <= numOfRouters; i++) {
                String[] textLine = configData.get(i).split(" ");


                String[] name = textLine[0].split(":");
                destinations.add(name[0]);
                ArrayList<String> neighbors = new ArrayList<String>();
                for (int j = 1; j < textLine.length; j+= 2) {
                    String[] newNeighbor = textLine[j].split("\\(");
                    String[] neighborName = newNeighbor[1].split(",");
                    neighbors.add(neighborName[0]);

                }
                ArrayList<Integer> costs = new ArrayList<Integer>();
                for (int j = 2; j <= textLine.length; j += 2) {
                    String[] newCost = textLine[j].split("\\)");
                    costs.add(Integer.parseInt(newCost[0]));
                }
                Router newRouter = new Router(name[0], neighbors, costs);
                routList.add(newRouter);
            }
            for (int i = 0; i < numOfRouters; i++) {
                routList.get(i).setDestination(destinations);
            }
            return routList;

        } catch (FileNotFoundException ex) {
            System.out.println("Error reading file");
        }


        ArrayList<Router> empty = new ArrayList<Router>();
        return empty;

    }

    public static void shortestPath(int[][] routingTable, int startRouter) {
        int[] shortestDistances = new int[numOfRouters];
        boolean[] added = new boolean[numOfRouters];

        for (int i  = 0; i < numOfRouters; i++) {
            shortestDistances[i] = Integer.MAX_VALUE;
            added[i] = false;
        }

        shortestDistances[startRouter] = 0;
        int[] parents = new int[numOfRouters];
        parents[startRouter] = -1;


        for (int i = 1; i < numOfRouters; i++) {

            int nearestRouter = 0;
            int shortestDistance = 200000000;
            for (int routerIndex = 0; routerIndex < numOfRouters; routerIndex++) {
                if(!added[routerIndex] && shortestDistances[routerIndex] < shortestDistance) {
                    nearestRouter = routerIndex;
                    shortestDistance = shortestDistances[routerIndex];
                }
            }
            added[nearestRouter] = true;
            for (int routerIndex = 0; routerIndex < numOfRouters; routerIndex++) {
                int edgeDistance = routingTable[nearestRouter][routerIndex];

                if (edgeDistance > 0 && ((shortestDistance + edgeDistance) < shortestDistances[routerIndex])) {
                    parents[routerIndex] = nearestRouter;
                    shortestDistances[routerIndex] = shortestDistance + edgeDistance;
                }
            }
        }

        displayTable(startRouter,shortestDistances,parents);
    }

    private static void displayTable(int currentRouter, int[] distances, int[] neighbor) {
        System.out.println("R" + (currentRouter + 1) + " routing table:");
        System.out.println("Destination      Line         Cost");
        System.out.println("------------------------------------------");
        for (int i = 0; i < numOfRouters; i++) {
            if (currentRouter != i) {

                System.out.print("R"+(i + 1) + "                ");
                if (distances[i] >= 200000000) {
                    System.out.print("-             -\n");
                }
                else {
                    if (neighbor[i] == 0) {
                        System.out.print("R" + (i + 1));
                    } else {
                        System.out.print("R" + (neighbor[i] + 1));
                    }
                    System.out.print("            " + distances[i] + "\n");
                }


            }
        }

    }


    public static String builder(Router router) {
        String packet = "";
        packet =  "Router name:" + router.getName() + ",Router neighbors:" + router.getNeighbors() +
                  ",Neighbor costs:" + router.getCost();
        return packet;

    }

    public static void listener(String packet, Router router) {
        String[] data = packet.split(",");
        String[] name = data[0].split(":");
        String[] numToTake = name[1].split("R");
        int row = Integer.parseInt(numToTake[1]);
        for (int i = 0; i < router.getNeighbors().size(); i++) {
            String neighbor = router.getNeighbors().get(i);
            String[] neighborNum = neighbor.split("R");
            int col = Integer.parseInt(neighborNum[1]);
            routingTable[row - 1][col - 1] = router.getCost().get(i);
        }

    }


}
