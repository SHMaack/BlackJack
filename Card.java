package com.olympic.cis143.blackjack;
import com.olympic.cis143.blackjack.Card;

public class Card 
{
	private int suit; //suit of the card (Clubs[0], Hearts[1], Spades[3], or Diamonds[4]) 
	private int rank; //rank of the card (Ace[0], 2[1], 3[2], 4[3], 5[4], 6[5], 7[6], 8[7], 9[8], 10[9], Jack[10], Queen[11], or King[12])
	private int value;
    
	public Card() 
	{ //a default constructor of Card
		suit = 0;
		rank = 0; 
		value = 0; 
	}
		  
	public Card (int s, int r, int v) 
	{ //a constructor of Card that initializes the main attributes
		suit = s;
		rank = r;
		value = v;
}
   
    public int getSuit() 
    {
        return suit;
    }
    public int getValue() 
    {
        return value;
    }
    public int getRank() 
    {
        return rank;
    }

    public boolean equals(final Card card)
    {
        if (card.value == this.value && card.suit == this.suit && card.rank == this.rank) 
        {
            return true;
        }
        return false;
    }

}
