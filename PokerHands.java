/*
 * Etude 3: Poker Hands
 * Author : Mika Smith
 * 
 * This program takes in a line of input as 5 cards in a hand in poker,
 * validates it and prints the hand in ascending value. 
 */

import java.io.*;
import java.util.*;

public class PokerHands{
	
	private static int[] order;
	private static HashMap<Character, Integer> nums = new HashMap<Character, Integer>(); 
	private static ArrayList<Character> suits = new ArrayList<Character>(); 
	private static ArrayList<Character> seperators = new ArrayList<Character>();
	public static ArrayList<Card<Integer, Character>> hand;
	public static char seperatorType; 
	public static Boolean valid;
	
	/*
	 * Constructor to initialise values. 
	 */
	public PokerHands(){
		order = new int[]{2,3,4,5,6,7,8,9,10,11,12,13,1};
		hand = new ArrayList<Card<Integer, Character>>();
		
		nums.put('A',1);
		nums.put('T', 10);
		nums.put('J', 11);
		nums.put('Q', 12);
		nums.put('K', 13);
		
		suits.add('C');
		suits.add('D');
		suits.add('H');
		suits.add('S');
		
		seperators.add(' ');
		seperators.add('/');
		seperators.add('-');
	}
	
	/*
	 * The main method scans in lines as individual poker hands
	 * and sorts the cards in each line in ascending order of value. 
	 */
	public static void main(String[]args){
		
		Scanner sc = new Scanner(System.in);
		while(sc.hasNextLine()){
			PokerHands poker = new PokerHands(); 
			String line = sc.nextLine(); 
			
			Card<Integer,Character> card;
			valid = true; 
			String numString="";
			int num=0;
			char suit='#'; 
			seperatorType = '#';
			
			for(int i=0; i<line.length();i++){
				char c = Character.toUpperCase(line.charAt(i)); //scan in every char 
				
				if(Character.isDigit(c)){
					//Check if integer is in range
					numString += c; //add to current number as it could be a double digit
					num = Integer.parseInt(numString);
					if(num<= 0 || num > 13){
						valid = false; 
					}
				}else if(nums.containsKey(c)){
					num = nums.get(c); 
				}else if(suits.contains(c)){
					//Reached the end of a card so add it to the hand. 
					suit = c;
					card = new Card<Integer,Character>(num, suit); 
					hand.add(card);
				}else if(isSeperator(c)){
					//Reset variables for next card. 
					num = 0;
					numString = "";
					suit = '#';
				}else{ //Not a valid character 
					valid = false; 
				}
			}
			
			//Check validity and sort hand 
			sortHand();
			
			if(hand.size()!=5 || !valid){ 
				System.out.println("Invalid: " + line);
			}else{
				StringBuilder sb = new StringBuilder(); 
				
				//Print out as letter not number for K, Q, T, J, A
				for(Card<Integer, Character> c: hand){
					//print number as letter
					
					int n = c.getNumber(); 
					Boolean added = false; 
					//for each entry, if n is = to the value, then get the key 
					for(Map.Entry<Character, Integer> x : nums.entrySet()){
						if(n==x.getValue()){
							sb.append(x.getKey());
							added = true; 
							break; 
						}
					}
					if(!added){
						sb.append(n);
					}
					sb.append(c.getSuit()+" ");
				}
				System.out.println(sb.toString());
				//System.out.println();
			}				
		}
		
		sc.close();
	}
	
	/*
	 * Uses insertion sort to sort the hand as it is a small set of numbers with a 
	 * small range. While we are comparing, it also checks if the cards are identical,
	 * in which case the hand will be invalid. 
	 */
	public static void sortHand(){    
		//for each value in the hand. 
		for(int i=1; i < hand.size(); i++){
	        Card<Integer, Character> value = hand.get(i);
	        int j = i-1;
	        
	        //Checks if these are the same card. 
	        if(compare(value, hand.get(j))==0){
				valid = false; //The hand is invalid if there is a duplicate
			}
	        
	        while(j>=0 && compare(value, hand.get(j))==-1){
	        	//Swaps values. 
	        	Card<Integer, Character> temp = hand.get(j);
	        	hand.set(j, hand.get(j+1));
	        	hand.set(j+1, temp);
	        	j--;
	        }
	    }   
	}
	
	/*
	 * Compares two cards and returns -1 if x is less than y, 0 if they are equal and
	 * 1 if y is greater than x. 
	 */
	public static int compare(Card<Integer, Character> x, Card<Integer, Character> y){
		if(x.getNumber() != y.getNumber()){
			return compareNumValue(x.getNumber(), y.getNumber());
		}else{ //will only compare if numbers are equal 
			return compareSuit(x.getSuit(), y.getSuit());
		}
	}
	
	/* Compares two suits and returns -1 if x is less than y, 0 if they are equal and
	 * 1 if y is greater than x. 
	 */
	public static int compareSuit(char x, char y){
		int xcount = 0; 
		int ycount = 0; 
		boolean xfin = false;
		boolean yfin = false; 
		
		for(char c : suits){
			if(x == c){ 
				xfin = true; 
			}else{
				if(!xfin){
					xcount++;
				}
			}
			
			if(y == c){ 
				yfin = true; 
			}else{
				if(!yfin){
					ycount++;
				}
			}
		}
	
		if(xcount==ycount){
			return 0; 
		}else if(xcount < ycount){
			return -1;
		}else{
			return 1; 
		}
	}

	/*
	 * Compares two numbers and returns -1 if x is less value than y, 0 if they are equal value and
	 * 1 if y is greater value than x. 
	 */
	public static int compareNumValue(int x, int y){
		int xcount = 0; 
		int ycount = 0; 
		boolean xfin = false;
		boolean yfin = false; 
		for(int i: order){
			if(x == i){ 
				xfin = true; 
			}else{
				if(!xfin){
					xcount++;
				}
			}
			
			if(y == i){ 
				yfin = true; 
			}else{
				if(!yfin){
					ycount++;
				}
			}
		}
	
		if(xcount==ycount){
			return 0; 
		}else if(xcount < ycount){
			return -1;
		}else{
			return 1; 
		}
	}
	
	/*
	 * This method returns true if a character is a seperator and is the 
	 * specified type of seperator for this line 
	 */
	public static boolean isSeperator(char c){
		if(seperators.contains(c)){
			if(seperatorType=='#'){
				seperatorType = c; //set new seperator type as this char
				return true;
			}else if(c==seperatorType){
				return true; 
			}else{
				return false; 
			}
		}
		return false;
	}

	/* 
	 * A data structure which represents a single card and holds its number and suit. 
	 */
	public static class Card<Integer, Character>{
		private final int number;
		private final char suit; 
		
		public Card(int number, char suit){
			this.number = number;
			this.suit = suit; 
		}
		
		public int getNumber(){
			return number; 
		}
		
		public char getSuit(){
			return suit; 
		}
	}		
}