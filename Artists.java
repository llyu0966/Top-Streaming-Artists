/*
 * Class: CISC 3130
 * Section: TY2
 * EmplId: 23809622
 * Name: Liyu Lin
 */
package artists;

import java.io.*;
import java.util.*;
/**
 *
 * @author linliyu
 */

//The List TopStreamingArtists is composed of a series of artist names
class TopStreamingArtists{
    //nested class node
    //A node represents an artist
    class Artist{
        String name;
        Artist next;
    
        //no-arg constructor
        Artist(){
            name = null; next = null;
        }
        //constructors with arguements
        Artist(String name){
           this.name = name;
           next = null;
        }
        Artist(String name, Artist next){
           this.name = name;
           this.next = next;
        }
    }
    
    public void TopStreamingAritists(){
        first = null;
    }
    
    public boolean isEmpty(){
        return(first == null);
    }
    
    //Function to add Artist names into a sorted linked list
    public void add(String newName){
        //make the new node to insert into list
        Artist newArtist = new Artist(newName);
        //first see if the list is empty
        if(first == null){
            first = newArtist;
        }
        //there is something in the list
        //now check to see if it belongs in front
        //else if(newName.compareTo(first.name)<0)
        else if(newName.trim().substring(0, 2).compareToIgnoreCase(first.name.trim().substring(0, 2))<0){
            newArtist.next = first;
            first = newArtist;
        }
        //otherwise, step down the list
        else{
            Artist after = first.next;
            Artist before = first;
            while(after != null){
                if(newName.trim().substring(0, 2).compareToIgnoreCase(after.name.trim().substring(0, 2))<0)
                    break;
                before = after;
                after = after.next;
            }
            //insert between before & after
            newArtist.next = before.next;
            before.next = newArtist;
        }
    }
    
    //public void displayList(Artist first){
    public void displayList(){
            System.out.println("\nStage Names(in alphabetical order):");
        while(first != null){
            System.out.println(first.name + " ");
            first = first.next;
        }
    }
    
    /**
     *Unused function
     *Function to insert Artist to the end of the list
     *It turns out the artist list with the original order
     */
    public Artist insert(Artist first, String name){
        Artist temp = new Artist();
        temp.name = name;
        temp.next = first;
        first = temp;
        return first;
    }
    
    //Unused function
    public Artist arrayToList(String[][] artistList, int n){
       first = null;
       for(int i = 0; i < n; i++)
           first = insert(first, artistList[i][2].replaceAll("^\"|\"$", ""));
       return first;
    }
    
    static Artist first;
}//end of class TopStreamingArtists

/**
 *This class contains the main class
 *aims to opening and reading the data file,
 *and uses available methods above to access data.
 */
public class Artists {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
       
        File file = new File("regional-us-weekly-2020-01-24--2020-01-31.csv");
       
        //Read in the text file and then save the CSV file format into a nested Java array
          int cols = 3;
          int rows = 200;
          String[][] artistList = new String[rows][cols];
        
        //try-catch block to handle exceptions when opening and reading files
        try
        {    
        Scanner sc = new Scanner(new BufferedReader(new FileReader(file)));
        
            while(sc.hasNextLine()){
                for(int i = 0; i < rows; i++){
                    String[] line = sc.nextLine().trim().split(",");
                    for(int j = 0; j < cols; j++){
                        artistList[i][j] = line[j];
                    }
                }
            }
            sc.close();
        } catch (Exception e){
            System.out.print("");
        }
// PART 1: see which artists appear on the list and their rakings
//      for(int i = 0;i < rows; i++){   
//          System.out.println(artistList[i][0] + " " + artistList[i][2].replaceAll("^\"|\"$", ""));
//          System.out.println();
//       }
        //to count the frequency of the artist names on the list
         ArrayList<String> aList = new ArrayList<>(rows);
         for(int i = 0; i < rows; i++){
             aList.add(artistList[i][2].replaceAll("^\"|\"$", ""));
         }
         Set<String> distinct = new HashSet<>(aList);
         System.out.println("Stage Names: Occurrence Frequency");
         distinct.forEach((s) -> {
             System.out.println(s + ": " + Collections.frequency(aList, s));
        });
        
//PART 2: see who are the music artists in alphabetical order
         TopStreamingArtists artistNames = new TopStreamingArtists();
         for(int i = 0; i < rows; i++){
             artistNames.add(artistList[i][2].replaceAll("^\"|\"$", ""));
         }
         artistNames.displayList();   
         //Artist first = artistNames.arrayToList(artistList, rows);
         //artistNames.displayList(first); 
         
    }
}// end of public class Artists
