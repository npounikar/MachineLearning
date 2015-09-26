 

import java.util.Random;
import java.util.Scanner;

public class GameOfCraps {
	
	public Scanner inputBet;
	public Random r;
	public FileUtility fu;
	
	//Starts the game
	void startGame() {


		fu = new FileUtility();
		int round = 1;
		while(round < 6) {
			System.out.println("-----------------------------------------------------------------------------------Round : "+round);
			fu.writeFile(round);
			
			int strategy = 1;
			while(strategy < 4) {

				int startBalance = 1000;
				int currentBalance = startBalance;
				int count = 1;
				int betAmount = 100;	
				int dice1Value, dice2Value,dieValue;

				System.out.println("Your Starting Balance is  $"+startBalance);
				
				do {

					boolean win = false;

					System.out.println("Rolling Die ............");
					System.out.println(" Wager ==================>"+betAmount);
					dice1Value = getDieValue();
					dice2Value = getDieValue();
					dieValue = add(dice1Value, dice2Value);
					System.out.println("Dice 1 = "+dice1Value+"   : Dice 2 = "+dice2Value);
					System.out.println("Die Value = "+dieValue);

					if(dieValue == 7 || dieValue == 11) {
						System.out.println("You won the bet worth $"+betAmount);
						currentBalance = add(currentBalance, betAmount);
						win = true;
					}

					else if(dieValue == 2 || dieValue == 3 || dieValue == 12 ) {
						System.out.println("You lost the bet worth $"+betAmount);
						currentBalance = substract(currentBalance, betAmount);
						win = false;
					} 

					else {


						final int shootPoint = dieValue;
						int shootdie1, shootdie2, shootdievalue;
						System.out.println("Shoot point set to "+shootPoint);

						shootdie1 = getDieValue();
						shootdie2 = getDieValue();
						shootdievalue = add(shootdie1, shootdie2);
						System.out.println("Shoot Dice 1 = "+shootdie1+"   : Shoot Dice 2 = "+shootdie2);
						System.out.println("Shoot Die Value = "+shootdievalue);

						while(shootdievalue != shootPoint && shootdievalue != 7) {
							shootdie1 = getDieValue();
							shootdie2 = getDieValue();
							shootdievalue = add(shootdie1, shootdie2);
							System.out.println("Shoot Dice 1 = "+shootdie1+"   : Shoot Dice 2 = "+shootdie2);
							System.out.println("Shoot Die Value = "+shootdievalue);
						}

						if(shootdievalue == shootPoint) {
							System.out.println("You won the bet worth $"+betAmount);
							currentBalance = add(currentBalance, betAmount);
							win = true;
						}
						if(shootdievalue == 7) {
							System.out.println("You lost the bet worth $"+betAmount);
							currentBalance = substract(currentBalance, betAmount);
							win = false;
						}

					}

					// Set the flag to change the next bet value, according to player's strategy

					if(strategy == 1)
						betAmount = 100;


					if(strategy == 2) {
						if(win) {
							betAmount = 100;
						} 
						if(!win) {
							betAmount = 2 * betAmount;
							if (betAmount > currentBalance) {
								betAmount = currentBalance;
							}
						}
					}



					if(strategy == 3) {
						if(!win) {
							betAmount = 100;
						} 
						if(win) {
							betAmount = 2 * betAmount;
							if (betAmount > currentBalance) {
								betAmount = currentBalance;
							}
						}
					}


					System.out.println("You current Balance is : $"+currentBalance);
					count++;
				}while(!(currentBalance <= 0 || count > 10 ));

				System.out.println("=========================This Round is Ended====================");
				System.out.println("Strategy :"+strategy);
				System.out.println("Ending Balance : $"+currentBalance);
				System.out.println("Total no of games played : "+(count-1));
				System.out.println("=================================================================");
				
				fu.writeToFile(strategy, currentBalance, count-1);

				strategy++;
			}
			round++;
		}
	}
	 
	 
	 
	 // Get the value of a dice randomly
	 int getDieValue() {
		 
		 int min = 1;
		 int max = 6;

		 r = new Random();
		 int val = r.nextInt(max - min + 1) + min;
		 
		 return val;
	 }

	 // Addition of any two values
	 int add(int a, int b) {
		 return a + b;
	 }
	 
	// Subtraction of any two values
	int substract(int a, int b) {
		return a - b;
	}
	 
}
