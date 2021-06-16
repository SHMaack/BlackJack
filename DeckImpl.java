//Created by Steven Maack
//6 - 16 - 21

package com.olympic.cis143.blackjack;

import java.util.*;


public class  DeckImpl
{

	private ArrayList<Card> deck = new ArrayList<Card>();

    public DeckImpl()
    {  	
    	//Creates 3 decks containing 3 of each card and assigns each a value
    	for (int i = 0; i <3; i++)
    	for (int j = 0; j < 4; j++) 
    	{ 
    	      for (int k = 0; k < 13; k++) 
    	      {                                                  
    	        if (k == 0) {
    	          Card card = new Card(i, k, 11); 
    	          deck.add(card); 
    	        }
    	        else if (k >= 10) { 
    	          Card card = new Card(j, k, 10); 
    	          deck.add(card); 
    	        }
    	        else 
    	        { 
    	          Card card = new Card(j, k, k+1); 
    	          deck.add(card); 
    	        } 
    	      }
    	    }
    }
    
public int getDeck()
{
	return deck.size();
}
    
    //Shuffles the deck
    public void shuffle() 
    {
    	ArrayList<Card> tempDeck = new ArrayList<Card>();
    	Random generator = new Random();
    	while (deck.size() > 0)
    	{
    		int i = generator.nextInt(deck.size());
    		tempDeck.add(deck.get(i));
    		deck.remove(i);
    	}
    	deck = tempDeck;
    }
    //"deals" the card and removes it from the stack

	public Card dealCard(int i)
	{	
		Card tempCard = deck.get(0);
		deck.remove(0);
		return tempCard;
	}


}
