import java.io.*;
import java.util.*;
import java.util.Map.Entry;

import javax.smartcardio.Card;


public class PokerHands{
	
	private final int[] order;
	private HashMap<Character, Integer> nums = new HashMap<Character, Integer>(); 
	private ArrayList<Character> suits = new ArrayList<Character>(); 
	private ArrayList<Character> seperators = new ArrayList<Character>();
	//private ArrayList<Card<Integer, Character>> hand = new Arrayist<Card<Integer, Character>>());
	
	public PokerHands(){
		order = new int[]{2,3,4,5,6,7,8,9,10,11,12,13,1};
		
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

	public ArrayList<Card<Integer, Character>> sortHand(ArrayList<Card<Integer, Character>> hand){
		ArrayList<Card<Integer, Character>> sortedHand = new ArrayList<Card<Integer, Character>>();
		
		//sort by number - find min number from 2 to 13
		for(Card<Integer, Character> c: hand){ //for every card in the hand 
			
			if(sortedHand.isEmpty()){
				sortedHand.add(c); //add the card straight to the sorted hand if its empty
			}else{ 
				int num = c.getNumber();//get the cards number
				char suit = c.getSuit(); 
				Boolean added = false; 
				//System.out.print(num + "" + c.getSuit()+" ");
				int index = 0; 
				
				for(Card<Integer, Character> i: sortedHand){
					int numComparison = compareNumValue(num, i.getNumber());
					
					//if our card is less than a card in the sorted Hand
					if(numComparison == -1){ //if number is less than i
						sortedHand.add(index, c); //insert it at that index! 
						added=true;
						break; 
					}else if(numComparison == 0){ //number is same as i
						//look at suit!
						int suitComparison = compareSuit(suit, i.getSuit());
						if(suitComparison == -1){//our suit is less than the other 
							//sortedHand.add(index, c); 
							System.out.println("card is less value");
						}else{ //CANNOT be equal, so must be greater than. 
							//sortedHand.add(index+1, c);
							System.out.println("card is greater value");
							//swap cards 
						}
						
					}
					
					//if it is equal, we will need to compare the suits. 
					index++;
				}
				
				if(!added){
					sortedHand.add(c);
				}
			}
			
			//Print everything in the sorted hand so far! 

		/*	for(Card<Integer, Character> x: sortedHand){
				System.out.print(x.getNumber() + "" + x.getSuit()+" ");
			}
			System.out.println();*/
			
			//find the right place in the arraylist to place it! 
		}
		
		
		//sort by suit 
		return sortedHand; 
	}
	
/*	public void swap(Card x, Card, y){
		int indexX = hand. 
	}*/
	
	public boolean isSeperator(char c){
		return seperators.contains(c);
	}
	
	public int compareSuit(char x, char y){
		int xcount = 0; 
		int ycount = 0; 
		boolean xfin = false;
		boolean yfin = false; 
		
		System.out.println("Comparing " + x + " and " + y);
		
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
		
		System.out.println("xcount "+ xcount);
		System.out.println("ycount "+ ycount);
		
		if(xcount==ycount){
			return 0; 
		}else if(xcount < ycount){
			return -1;
		}else{
			return 1; 
		}
	}
	
	//TODO: check if this works 
	public int compareNumValue(int x, int y){

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
		
		
	//	System.out.println("xcount "+ xcount);
		//System.out.println("ycount "+ ycount);
		
		if(xcount==ycount){
			return 0; 
		}else if(xcount < ycount){
			return -1;
		}else{
			return 1; 
		}
	}
	

	public static void main(String[]args){
		new PokerHands().start();
	}
	
	public void start(){
		Scanner sc = new Scanner(System.in);
		while(sc.hasNext()){
			String line = sc.nextLine(); 
			ArrayList<Card<Integer, Character>> hand = new ArrayList<Card<Integer, Character>>();
			Card<Integer,Character> card;
			Boolean valid = true; 
			String numString="";
			//Random initialising. Must reset after each card! 
			int num=0;
			char suit='A'; 
			//NOT SCANNING IN LAST ONE
			
			for(int i=0; i<line.length();i++){
				char c = line.charAt(i); //scan in every char 
				
				if(Character.isDigit(c)){ 
					
					numString += c; //add to current number! could be double digit
					num = Integer.parseInt(numString); //ISSUE
					
				}else if(nums.containsKey(c)){ //could be a num or the suit. 
					num = nums.get(c); //ISSUE
				}else if(suits.contains(Character.toUpperCase(c))){
					suit = Character.toUpperCase(c);
					/*if(!suits.contains(suit)){
					//	valid = false; 
					}*/ 
					//TODO : check if valid suit
				}else if(isSeperator(c)){
				//	System.out.println("Found seperator");
					card = new Card<Integer,Character>(num, suit); 
					hand.add(card);
					//reset?
					num = 0;
					numString = "";
					suit = 'A';
				}else{
					valid = false; 
				}
			}
			
			if(hand.size()!=5 || !valid){
				System.out.println("Invalid: " + line);
				/*for(Card<Integer, Character> c: hand){
					System.out.print(c.getNumber() + "" + c.getSuit()+" ");
				}*/
			}else{
				System.out.println("Unsorted: ");
				for(Card<Integer, Character> c: hand){
					System.out.print(c.getNumber() + "" + c.getSuit()+" ");
				}
				
				hand = sortHand(hand);
				
				System.out.println("\nSorted: ");
				for(Card<Integer, Character> c: hand){
					System.out.print(c.getNumber() + "" + c.getSuit()+" ");
				}			
			}				
		}
		sc.close();
	}
	
		
	public class Card<Integer, Character>{
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