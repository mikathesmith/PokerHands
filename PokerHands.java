import java.io.*;
import java.util.*;
import java.util.Map.Entry;


public class PokerHands{
	
	private HashMap<Character, Integer> nums = new HashMap<Character, Integer>(); 
	private ArrayList<Character> suits = new ArrayList<Character>(); 
	
	public PokerHands(){
		nums.put('A',1);
		nums.put('J', 11);
		nums.put('Q', 12);
		nums.put('K', 13);
		
		suits.add('C');
		suits.add('D');
		suits.add('H');
		suits.add('S');
	}
	public ArrayList<Card<Integer, Character>> sortHand(ArrayList<Card<Integer, Character>> hand){
		ArrayList<Card<Integer, Character>> sortedHand = new ArrayList<Card<Integer, Character>>();
		
		//sort by number - find min number from 2 to 13
		for(Card<Integer, Character> c: hand){ //for every card in the hand 
			
			if(sortedHand.isEmpty()){
				sortedHand.add(c); //add the card straight to the sorted hand if its empty
			}else{ 
				int num = c.getNumber();//get the cards number
				Boolean added = false; 
				//System.out.print(num + "" + c.getSuit()+" ");
				int index = 0; 
				for(Card<Integer, Character> i: sortedHand){
					//TODO : this only works if 1 is added last. 
					if(num==1){ //If its an ace, it should be added to the end 
						break;
					}
					if(num < i.getNumber()){ //if our card is less than a card in the sorted Hand - maybe use a method here to indicate order? 
						sortedHand.add(index, c); //insert it at that index! 
						added=true;
						break; 
					}
					
					//if it is equal, we will need to compare the suits! 
					index++;
				}
				
				if(!added){
					sortedHand.add(c);
				}
			}
			
			//Print everything in the sorted hand so far! 

			for(Card<Integer, Character> x: sortedHand){
				System.out.print(x.getNumber() + "" + x.getSuit()+" ");
			}
			System.out.println();
			
			//find the right place in the arraylist to place it! 
		}
		
		//if hand has a 1, add at end 
		
		//sort by suit 
		return sortedHand; 
	}

	public static void main(String[]args){
		new PokerHands().start();
	}
	
	public void start(){
		Scanner sc = new Scanner(System.in);
		while(sc.hasNext()){
			String line = sc.nextLine(); 
			ArrayList<Card<Integer, Character>> hand = new ArrayList<Card<Integer, Character>>();
			Boolean valid = true; 
			//scan in two characters as a card and sort in list. 
			//TODO : double digit numbers -currenty only deals with 10 chars 
			for(int i=0; i< line.length();i+=2){
				int num;
				if(Character.isDigit(line.charAt(i))){
					num = Character.getNumericValue(line.charAt(i));
				}else{
					num = nums.get(line.charAt(i));
				}
		
				char suit = Character.toUpperCase(line.charAt(i+1));
				if(!suits.contains(suit)){
					valid = false; 
				}
				//TODO : check if valid suit
							
				Card<Integer,Character> card = new Card<Integer,Character>(num, suit); 
				hand.add(card);
			}
			
			if(hand.size()!=5 || !valid){
				System.out.println("Invalid: " + line);
			}else{
				/*System.out.println("Unsorted: ");
				for(Card<Integer, Character> c: hand){
					System.out.print(c.getNumber() + "" + c.getSuit()+" ");
				}*/
				hand = sortHand(hand);
				
				/*System.out.println("\nSorted: ");
				for(Card<Integer, Character> c: hand){
					System.out.print(c.getNumber() + "" + c.getSuit()+" ");
				}	*/			
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