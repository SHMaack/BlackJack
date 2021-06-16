//Created by Steven Maack
//6 - 16 - 21
package com.olympic.cis143.blackjack;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;



public class Game extends JFrame implements ActionListener{

  ArrayList<Card> dealerHand = new ArrayList<Card>(); //this is the arraylist for the dealer's hand.
  ArrayList<Card> playerHand = new ArrayList<Card>(); //this is the arraylist for the player's hand.
  public boolean faceDown = false; //this will tell the program if we have the card facedown or faceup.
  public boolean dealerWon = false; //this will tell the program if dealer has won the round.
  public boolean roundOver = false; //this will tell the program if the round is over.
  int chipCount = 100;
  int betCount;
  int playerTotal;
  int dealerTotal;
  DeckImpl deck = new DeckImpl();

  JButton btnHit; //we have two buttons in this game and one to exit
  JButton btnStand;
  JButton btnSplit;
  JButton btnExit;
  static String fiveString = "$5";
  static String tenString = "$10";
  static String fiftyString = "$50";
  JRadioButton btnFive = new JRadioButton(fiveString);
  JRadioButton btnTen = new JRadioButton(tenString);
  JRadioButton btnFifty = new JRadioButton(fiftyString);

  String dealerStringHand = "Cards in dealers hand:";
  String playerStringHand = "Cards in player hand: ";
  JLabel label1 = new JLabel();
  JLabel label2 = new JLabel();
  JLabel label3 = new JLabel();
  JLabel label4 = new JLabel();

  	{
		Container canvas = this.getContentPane();
		this.setTitle("Black Jack");
			
		canvas.add(createCenterPanel(), BorderLayout.CENTER);
		canvas.add(createSouthPanel(), BorderLayout.SOUTH);
				
		this.setLocation(100, 100);
		this.setSize(1000, 375);
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		startGame();
	}
	public JPanel createCenterPanel()
	{
		JPanel panel = new JPanel(new GridLayout(3, 2));
		label1.setText("");
		label1.setFont(new Font("Helvetica", Font.BOLD, 13));
		panel.add(label1);
		
	    btnFive.setMnemonic(KeyEvent.VK_A);
	    btnFive.setActionCommand(fiveString);
	    btnTen.setMnemonic(KeyEvent.VK_B);
	    btnTen.setActionCommand(tenString);	 
	    btnFifty.setMnemonic(KeyEvent.VK_C);
	    btnFifty.setActionCommand(fiftyString);
		ButtonGroup group = new ButtonGroup();
	    group.add(btnFive);
	    group.add(btnTen);
	    group.add(btnFifty);
	    
	    btnFive.addActionListener(this);
	    btnTen.addActionListener(this);
	    btnFifty.addActionListener(this);
	    // done in html to allow for text wrapping in a JLabel
	    label3.setText("<html>" + "Rules: Beat the Dealers hand without going over 21. "
	    				+ "Dealer must hit on 16 and wins tied hands. Use the radial" +
	    				"dial to input your bet. Aces are worth either 11 or 1 are is "
	    				+ "based on which will give you a better hand. The player can "
	    				+ "only see one of the dealers cards and must decide to hit or "
	    				+ "stand prior to seeing what the dealer has. This game is built"
	    				+ " with three decks so feel free to attempt to try and count "
	    				+ "cards. Betting begins once an amount is selected" + "</html>");
		panel.add(label3);
		label2.setFont(new Font("Helvetica", Font.BOLD, 13));
		label2.setText("");
		panel.add(label2);

		label4.setText("Chip Amount: " + chipCount);	
		panel.add(label4);
		JPanel radioPanel = new JPanel(new GridLayout(0,1));
		radioPanel.add(btnFive);
		radioPanel.add(btnTen);
		radioPanel.add(btnFifty);
		panel.add(radioPanel);
		return panel;
	}
	public JPanel createSouthPanel()

	{
		JPanel panel = new JPanel();
		btnHit = new JButton("Hit me");
		btnHit.addActionListener(this);
		panel.add(btnHit);
		btnStand = new JButton("Stand");
		btnStand.addActionListener(this);
		panel.add(btnStand);	
		return panel;
	}

  public void startGame() 
  { //this method starts the game and deals the first two cards. ensures deck is randomized
	
    deck.shuffle();

    for(int i = 0; i<2; i++) //deals the dealers opening hand and assigns both the suit and
    {						 //rank of the card. The first iteration doesn't tell the player
    						 //what the dealer drew
    	if (i == 0)
    	{
    		dealerHand.add(deck.dealCard(i));
    		dealerStringHand = dealerStringHand + " Face Down ";
    		label1.setText(dealerStringHand);
    	}
        else
        {
    	String tempRank = "";
    	String tempSuit = "";
    	dealerHand.add(deck.dealCard(i));
    	int findRank = dealerHand.get(i).getRank();
    	tempRank = getCardRank(findRank);	
    	int findSuit =dealerHand.get(i).getSuit();
    	tempSuit = getCardSuit(findSuit);
    	dealerStringHand = dealerStringHand + tempRank + " of " + tempSuit;
    	label1.setText("<html>" + dealerStringHand + "<html>");
        }
    }
    System.out.println("got here");
    for(int i = 0; i<2; i++) //deals players opening hand
    {
    	String tempRank = "";
    	String tempSuit = "";
    	playerHand.add(deck.dealCard(i));
    	int findRank = playerHand.get(i).getRank();
    	tempRank = getCardRank(findRank);	
    	int findSuit = playerHand.get(i).getSuit();
    	tempSuit = getCardSuit(findSuit);
    	playerStringHand = playerStringHand + tempRank + " of " + tempSuit;
    	label2.setText("<html>" + playerStringHand + "<html>");
    }

    checkHand(dealerHand); //checks both hands for blackjack
    checkHand(playerHand);

  }

	//we add a action listener to the hit button.
	public void actionPerformed(ActionEvent e)
      {
    	  if(e.getSource() == btnHit)
  			{//adds a card to the player and checks for blackjack or bust
    		  addCard(playerHand);
    		  Card c = playerHand.get(playerHand.size()-1);
    		  String tempRank = "";
    	      String tempSuit = "";
    	      int findRank = c.getRank();
    	      tempRank = getCardRank(findRank);	
    	      int findSuit = c.getSuit();
    	      tempSuit = getCardSuit(findSuit);
    	      playerStringHand = playerStringHand + tempRank + " of " + tempSuit;
    	      label2.setText("<html>" + playerStringHand + "<html>");
    	      checkHand(playerHand);
  			}
    	  else if (e.getSource() == btnStand)
    	  {//checks dealers total and forces hit on 16 and lower then checks totals if no bust
    	   //or black jack
    		    System.out.println(getSumOfHand(dealerHand));
    		  while (getSumOfHand(dealerHand) < 17)
    		  {
    			  Card c = deck.dealCard(0);
    			  dealerHand.add(c);
    			  String tempRank = "";
        	      String tempSuit = "";
        	      int findRank = c.getRank();
        	      tempRank = getCardRank(findRank);	
        	      int findSuit = c.getSuit();
        	      tempSuit = getCardSuit(findSuit);
        	      dealerStringHand = dealerStringHand + tempRank + " of " + tempSuit;
        	      label1.setText("<html>" + dealerStringHand + "<html>");
    		  }

    		  if ((getSumOfHand(dealerHand)) >= getSumOfHand(playerHand)
    				  && (getSumOfHand(dealerHand)) <= 21)	  
    		  {//every game ending condition clears the value from the hands and restarts game
    			  JOptionPane.showMessageDialog(null, "Dealer wins, you lose");
    			  chipCount = chipCount - betCount;
    			  endGame();
    			  startGame();			  
    		  }
    		  else if (getSumOfHand(dealerHand) > 21)
    		  {
    		    	JOptionPane.showMessageDialog(null, "Dealer BUSTED " + getSumOfHand(dealerHand) + ", you win" );
    		    	chipCount = chipCount + betCount;
    				 endGame();
    				 startGame();
    		  }
    		  else
    		  {
    			  JOptionPane.showMessageDialog(null, "You win, Dealer loses");
    			  chipCount = chipCount + betCount;
    			  endGame();
    			  startGame();
    		  }
    	  }//updates how much the player wants to gamble
    	  else if (e.getSource() == btnFive)
    	  {
    		  betCount = 5;
    	  }
    	  else if (e.getSource() == btnTen)
    	  {
    		  betCount = 10;
    	  }
    	  else if (e.getSource() == btnFifty)
    	  {
    		  betCount = 50;
    	  }
  }

  public void checkHand (ArrayList<Card> hand) 
  {// this method checks for blackjack or bust, otherwise standing checks who won
    if (hand.equals(playerHand)) 
    { 
      if(getSumOfHand(hand) == 21)
      {
    	  JOptionPane.showMessageDialog(null, "Black Jack! You win");
    	  chipCount = chipCount + betCount;
    	  endGame();
		  startGame();
      }
      else if (getSumOfHand(hand) > 21) 
      { 
    	  JOptionPane.showMessageDialog(null, "BUSTED " +getSumOfHand(hand)+  ", you lose");
    	  chipCount = chipCount - betCount;
    	  endGame();
		  startGame();
      }
    }
    else if (hand.equals(dealerHand)) 
    {// Checks for dealer black jack
      if(getSumOfHand(hand) == 21) 
      { 
    	  JOptionPane.showMessageDialog(null, "Black Jack! Dealer wins");
    	  chipCount = chipCount - betCount;
		  endGame();
		  startGame();
      }
    }
  }

  public void addCard(ArrayList<Card> hand) 
  {//this method adds a card to the hand.
    hand.add(deck.dealCard(0));
  }
  //A method is needed to check for an ace in hand 
  public boolean hasAceInHand(ArrayList<Card> hand) 
  {
    for (int i = 0; i < hand.size(); i++)
    {
      if(hand.get(i).getValue()==11) 
        return true;
    }
    return false;
  }

  public int aceCountInHand(ArrayList<Card> hand)
  {//this method finds the total aces found in the hand.
    int aceCount = 0; 
    for (int i = 0; i < hand.size(); i++) 
      if(hand.get(i).getValue() == 11) 
        aceCount++; 
    return aceCount;   
  }

  public int getSumWithHighAce(ArrayList<Card> hand) 
  {//this methods treats the aces as 11's
    int handSum = 0;
    for (int i = 0; i < hand.size(); i++)
      handSum = handSum + hand.get(i).getValue();
    return handSum;
  }

  public int getSumOfHand (ArrayList<Card> hand) 
  {//checks the sum of the hand for all cases
    if(hasAceInHand(hand))
    { 
      if(getSumWithHighAce(hand) <= 21) 
      {
        return getSumWithHighAce(hand);
      }
      else
      {
        for (int i = 0; i < aceCountInHand(hand); i++) 
        { //this allows the program to lower aces to 1 if required to not bust
          int sumOfHand = getSumWithHighAce(hand)-(i+1)*10; 
          if(sumOfHand <= 21) 
          {
            return sumOfHand;
          }
        }
      }
    }
    else 
    { // without any aces
      int sumOfHand = 0;
      for (int i = 0; i < hand.size(); i++) 
      {
        sumOfHand = sumOfHand + hand.get(i).getValue();
      }
      return sumOfHand;
    }
    return 22; // return "bust"
  }
  public String getCardSuit(int i)
  {//used to update strings for seeing suits
	 String suit = "";
	 int temp = i;
	 if (temp == 0)
		 suit = "Clubs ";
	 if (temp == 1)
		 suit = "Hearts ";
	 if (temp == 2)
		 suit = "Spades ";
	 if (temp == 3)
		 suit = "Diamonds ";
	return suit;
  }
  public String getCardRank(int i)
  {//used to update string for seeing cards
     String rank = "";
	 int temp = i;
	 if (temp == 0)
		 rank = "Ace";
	 if (temp == 1)
		 rank = "2";
	 if (temp == 2)
		 rank = "3";
	 if (temp == 3)
		 rank = "4";
	 if (temp == 4)
		 rank = "5";
	 if (temp == 5)
		 rank = "6";
	 if (temp == 6)
		 rank = "7";
	 if (temp == 7)
		 rank = "8";
	 if (temp == 8)
		 rank = "9";
	 if (temp == 9)
		 rank = "10";
	 if (temp == 10)
		 rank = "Jack";
	 if (temp == 11)
		 rank = "Queen";
	 if (temp == 12)
		 rank = "King";
	 return rank;
  }

  void endGame()
  {//Resets both hands and the string to show a new hand for dealer and player. updates chip count and closes app if you are out of money
	  dealerHand.clear();
	  playerHand.clear();
	  dealerStringHand = "Cards in dealers hand:";
	  playerStringHand = "Cards in player one's hand: ";
	  label4.setText("Chip Amount: " + chipCount);
	  if (chipCount <= 0)
	  {
    	  JOptionPane.showMessageDialog(null, "YOU LOSE!");
    	  System.exit(0);
	  }
  }
 }
