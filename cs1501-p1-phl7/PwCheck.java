//Patrick Lyons
//PSID: 3887122
//COE 1501
//PwCheck.java
//package phl7_project1;

import java.util.*;
import java.io.*;

public class PwCheck

{

 //Keeps track of time taken so far
 static long timer = System.nanoTime();

 boolean emptyTree = true; //The tree is empty to begin with. CHANGE WHEN FIRST WORD IS ADDED

 public static void main(String[] args) throws IOException {

   //Used as a scanner for user input
   Scanner scan = new Scanner(System.in);
   boolean validPass;

   //Char list of all possible chars
   char[] possChars = {
    'b',
    'c',
    'd',
    'e',
    'f',
    'g',
    'h',
    'j',
    'k',
    'l',
    'm',
    'n',
    'o',
    'p',
    'q',
    'r',
    's',
    't',
    'u',
    'v',
    'w',
    'x',
    'y',
    'z',
    '0',
    '1',
    '2',
    '3',
    '4',
    '5',
    '6',
    '7',
    '8',
    '9',
    '!',
    '@',
    '$',
    '^',
    '_',
    '*'
   };



   char[] pWord = new char[5];
   if (args[0].equals("-find")) {

    //Finds all possible passwords before pruning
    for (int v = 0; v < possChars.length; v++) {
     for (int w = 0; w < possChars.length; w++) {
      for (int x = 0; w < possChars.length; x++) {
       for (int y = 0; w < possChars.length; y++) {
        for (int z = 0; w < possChars.length; z++) {
         pWord[0] = possChars[v];
         pWord[1] = possChars[w];
         pWord[2] = possChars[x];
         pWord[3] = possChars[y];
         pWord[4] = possChars[z];

        }
       }
      }
     }
    }

   } else if (args[0].equals("-check")) {
    System.out.println("Please enter a password: ");
    String userPass = scan.siblingLine();

    if (userPass.length() != 5) {
     System.out.println("Invalid input.");
    }

   }


  } //endOfTree of main


 //Generates a tree
 void treeGenerator() throws IOException {

  //Reads in lines from dictionary
  BufferedReader read = new BufferedReader(new FileReader("dictionary.txt"));

  //String variable to store each word
  String newWord;

  //Adds word if 3 letters or less. (Password can only have 3 letters at most)
  while ((newWord = read.readLine()) != null) {

   if (newWord.length() < 4)
    addWord(newWord);

  }

  //Closes the dictionary document
  read.close();
 }

 void addWord(String word) {

  if (isEmpty) {
   if (word.length() == 1) {
    root = new Node(word.charAt(0), null, null);
    root.endOfTree = true;
    return;
   } else {
    root = new Node(word.charAt(0), null, null);
    for (int i = 1; i < word.length(); i++) {
     root.child = new Node(word.charAt(i), null, null);
     root = root.child;
    }
    root.endOfTree = true;
   }

   isEmpty = false;
  }

  Node currentNode = root;
  for (int i = 0; i < word.length();) {
   boolean foundWord = false;

   if (currentNode.sibling != null)

   {
    //Checks if the value at the current node is equal to the character given
    if (currentNode.value != word.charAt(i)) {
     currentNode = currentNode.sibling;
     foundWord = true;
    } else {

     currentNode = currentNode.child;
     foundWord = false;
     i++; //Increments to the next letter
    }
   }
   if (currentNode.sibling == null && currentNode.value == word.charAt(i) && !foundWord) {

    currentNode = currentNode.child;
    foundWord = true;
    i++;
   }

   //Word not found, add new sibling
   if (!foundWord) {
    currentNode.sibling = new Node(s.charAt(i), null, null);

    currentNode = currentNode.sibling;

    for (int j = i + 1; j < word.length(); ++j) {

     currentNode.child = new Node(word.charAt(j), null, null);

     currentNode = currentNode.child;
    }
    currentNode.endOfTree = true;
    break;
   }
  }

 }

}

private class Node
{

 private Node sibling;
 private Node child;
 private char value;
 private boolean endOfTree = false; //Stores the boolean as to whether nor not the tree is over

 private Node(char letter, Node sib, Node kid)
 {
  this.value = lett; //Letter value stored in the node
  this.sibling = sib; //Sibling node
  this.child = kid; //Child node
 }

}

} //End of file
