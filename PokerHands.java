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
	
	public static void main(String[]args){
		new PokerHands().start();
	}
	
	public void start(){
		Scanner sc = new Scanner(System.in);
		while(sc.hasNext()){
			String line = sc.nextLine(); 
			ArrayList<Card<Integer, Character>> hand = new ArrayList<Card<Integer, Character>>();
			
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
				//TODO : check if valid suit
							
				Card<Integer,Character> card = new Card<Integer,Character>(num, suit); 
				hand.add(card);
			}
			
			if(hand.size()!=5){
				System.out.println("Invalid:");
			}else{
				
				for(Card<Integer, Character> c: hand){
					System.out.print(c.getNumber() + "" + c.getSuit()+" ");
				}
				
				//Sort hand 
				
			}
					
		}
		
		public ArrayList<Card<Integer,Character>> sortHand(Card<Integer,Character>){
			
		}
		
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