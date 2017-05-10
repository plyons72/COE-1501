//Patrick Lyons
//Project 4
//COE 1501 - Spring 2017

import java.io.*;
import java.util.Scanner;

public class NetworkAnalysis {

 private static Graph graph;
 private static boolean quitting;
 private static int numVertices;
 private static File file;
 private static Scanner userInput = new Scanner(System.in);


 public static void main(String[] args) throws FileNotFoundException {

   file = new File(args[0]);

   if (!file.exists()) {
    System.out.println("File is not in present working directory.");
    System.exit(0);
   }

   Scanner fileInput = new Scanner(file);
   Graph networks = createGraph(fileInput);

   //Boolean value determining if program should quit
   quitting = false;

   while (!quitting) {
    System.out.println("1. Find lowest latency path.");
    System.out.println("2. Check if graph is copper connected");
    System.out.println("3. Find maximum transferrable data");
    System.out.println("4. Show the minimum average-latency spanning tree");
    System.out.println("5. Check graph connectivity after two vertices fail");
    System.out.println("6. Quit");
    System.out.println("Select one: ");

    switch (userInput.nextInt()) {
     case 1:
      System.out.println("Find lowest latency path\n");
      lowestLatency(networks);
      break;

     case 2:
      System.out.println("Check if graph is copper connected\n");
      copperConnect(networks);
      break;

     case 3:
      System.out.println("Find maximum transferrable data\n");
      maxData(networks);
      break;

     case 4:
      System.out.println("Show the minimum average-latency spanning tree\n");
      spanningTree(networks);
      break;

     case 5:
      System.out.println("Check graph connectivity after two vertices fail\n");
      connectCheck(networks);
      break;

     case 6:
      System.out.println("Quitting...");
      quitting = true;
      break;


     default:
      System.out.println("Invalid input.");
      quitting = true;
      break;

    } //End of switch case

   } //End of while loop

  } //End of main

 private static Graph createGraph(Scanner scan) {
  int vertices = scan.nextInt();

  System.out.println("Number of vertices: " + vertices);
  System.out.println("Valid vertices are numbered 0 to " + (vertices-1));

  Graph graph = new Graph(vertices);

  while (scan.hasNext()) {
   int startingV = scan.nextInt();
   int endingV = scan.nextInt();
   String type = scan.next();
   int bandwidth = scan.nextInt();
   int length = scan.nextInt();
   graph.addEdge(startingV, endingV, type, bandwidth, length);
  }

  scan.close();

  return graph;
 }

 private static void lowestLatency(Graph graph) {
  int startingV = -1;
  int endingV = -1;
  boolean validInput = true;

  System.out.print("First Vertex: ");
  startingV = userInput.nextInt();

  System.out.print("\nSecond Vertex: ");
  endingV = userInput.nextInt();

  if (startingV == endingV) {
   System.out.println("Invalid. Vertices are identical.\n");
   validInput = false;
  }
  if (startingV < 0 || startingV > graph.getVertices()) {
   System.out.println("Invalid. Must be between 0 and " + graph.getVertices() + ".\n");
   validInput = false;
  }
  if (endingV < 0 || endingV > graph.getVertices()) {
   System.out.println("Invalid. Must be between 0 and " + graph.getVertices() + ".\n");
   validInput = false;
  }

  if (validInput) {
   DijkstraSP allPaths = new DijkstraSP(graph, startingV);
   Iterable<Edge> lowestLatencyPath = allPaths.pathTo(endingV);

   if (lowestLatencyPath != null) {
    int minBand = -1;
    boolean minBandSet = false;
    System.out.println("The lowest latency from " + startingV + " to " + endingV + ":");
    System.out.print("" + startingV + " ");

    for (Edge edge: lowestLatencyPath) {
     System.out.print("" + edge.to() + " ");

     if (minBandSet == false) {
      minBand = edge.bandwidth();
      minBandSet = true;
     }

     if (edge.bandwidth() < minBand)
      minBand = edge.bandwidth();

    }
    System.out.println("\nMinimum bandwidth is " + minBand + "\n");
   }

   else
    System.out.println("No path found between vertices\n");
  }

  else //Invalid user input
    System.out.println("Invalid. Exiting...\n");

 }

 private static void copperConnect(Graph graph) {
  BFS bfs = new BFS(graph, 0, "Copper");
 }

 private static void maxData(Graph graph) {
   int startingV = -1;
   int endingV = -1;
   boolean validInput = true;

   System.out.print("First Vertex: ");
   startingV = userInput.nextInt();

   System.out.print("\nSecond Vertex: ");
   endingV = userInput.nextInt();

   if (startingV == endingV) {
    System.out.println("Invalid. Vertices are identical.\n");
    validInput = false;
   }
   if (startingV < 0 || startingV > graph.getVertices()) {
    System.out.println("Invalid. Must be between 0 and " + graph.getVertices() + ".\n");
    validInput = false;
   }
   if (endingV < 0 || endingV > graph.getVertices()) {
    System.out.println("Invalid. Must be between 0 and " + graph.getVertices() + ".\n");
    validInput = false;
   }

   if (validInput) {
    DijkstraSP allPaths = new DijkstraSP(graph, startingV);
    Iterable<Edge> lowestLatencyPath = allPaths.pathTo(endingV);

    if (lowestLatencyPath != null) {
     int minBand = -1;
     boolean minBandSet = false;
   for (Edge edge: lowestLatencyPath) {
   if (minBandSet == false) {
       minBand = edge.bandwidth();
       minBandSet = true;
      }

      if (edge.bandwidth() < minBand)
       minBand = edge.bandwidth();

     }
     System.out.println("\nMax data transfer is " + minBand + "MB\n");
    }

    else
     System.out.println("No path found between vertices\n");
   }

   else //Invalid user input
     System.out.println("Invalid. Exiting...\n");


 }

 private static void spanningTree(Graph graph) {
  PrimMST mst = new PrimMST(graph);
  System.out.println("The spanning tree with the lowest average latency per edge: ");
  mst.edges();
 }

 private static void connectCheck(Graph graph) {
  BFS bfs;
  for (int i = 0; i < graph.getVertices(); i++) {
   for (int j = 1; j < graph.getVertices(); j++) {
    bfs = new BFS(graph, i, "Remove Two");
   }
  }
 }
} //End of program
