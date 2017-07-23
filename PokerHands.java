import java.io.*;
import java.util.*;
import java.util.Map.Entry;

import javax.smartcardio.Card;


public class PokerHands{
	
	private static int[] order;
	private static HashMap<Character, Integer> nums = new HashMap<Character, Integer>(); 
	private static ArrayList<Character> suits = new ArrayList<Character>(); 
	private static ArrayList<Character> seperators = new ArrayList<Character>();
	public static ArrayList<Card<Integer, Character>> hand;
	
	public PokerHands(){
		order = new int[]{2,3,4,5,6,7,8,9,10,11,12,13,1};
		hand = new ArrayList<Card<Integer, Character>>();
		
		nums.put('A',1);
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
	
	public static boolean isSeperator(char c){
		return seperators.contains(c);
	}
	
	public static void main(String[]args){
		
		Scanner sc = new Scanner(System.in);
		while(sc.hasNextLine()){
			PokerHands poker = new PokerHands(); 
			String line = sc.nextLine(); 
			
			Card<Integer,Character> card;
			Boolean valid = true; 
			String numString="";
			//Random initialising. Must reset after each card! 
			int num=0;
			char suit='#'; 
			//TODO: NOT SCANNING IN LAST ONE DUE TO NOT FINDING A SEPERATOR- after count 5 then stop? 
		//	boolean isThisSeperator; to stick to one type of seperator
			
			for(int i=0; i<line.length();i++){
				char c = line.charAt(i); //scan in every char 
				
				if(Character.isDigit(c)){ 
					numString += c; //add to current number as it could be a double digit
					num = Integer.parseInt(numString);
				}else if(nums.containsKey(c)){
					num = nums.get(c); 
				}else if(suits.contains(Character.toUpperCase(c))){
					suit = Character.toUpperCase(c);
				}else if(isSeperator(c)){
					
				//	System.out.println("Found seperator");
					card = new Card<Integer,Character>(num, suit); 
			//TODO: this does not work as cards have different hashcodes. 
					if(hand.contains(card)){ //hand doesn't contain this card
						System.out.println("Duplicate card!");
						valid = false; 
					}else{
						hand.add(card);
					}
					
				//	System.out.println("Hand size is "+ hand.size());
					//Resetting variables
					num = 0;
					numString = "";
					suit = '#';
				}else{
					valid = false; 
				}
				
			
			}
			
			if(hand.size()!=5 || !valid){
				System.out.println("Invalid: " + line);
			}else{
			/*	System.out.println("Unsorted: ");
				for(Card<Integer, Character> c: hand){
					System.out.print(c.getNumber() + "" + c.getSuit()+" ");
				}
				System.out.println();*/
				sortHand();
				
				//System.out.println("\nSorted: ");
				for(Card<Integer, Character> c: hand){
					System.out.print(c.getNumber() + "" + c.getSuit()+" ");
				}
				System.out.println();
				System.out.println();
			}				
		}
		
		sc.close();
	}
	
	/*
	 * Uses insertion sort to sort the hand as it is a small set of numbers with a 
	 * small range. 
	 */
	public static void sortHand(){    
		//for each value in the hand. 
		for(int i=1; i < hand.size(); i++){
	        Card<Integer, Character> value = hand.get(i);
	        int j = i-1;
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
		}else{
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
		//System.out.println("Comparing " + x + " and " + y);
		
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