//DotComBust, some serious shit now.

import java.util.*;

public class DotComBust
{
	private GameHelper helper = new GameHelper();
	private ArrayList<DotCom> dotComsList = new ArrayList<DotCom>();
	private int numOfGuesses = 0;

	public void setUpGame()
	{
		DotCom dot1 = new DotCom("ARP");
		DotCom dot2 = new DotCom("FTP");
		DotCom dot3 = new DotCom("TCP");		

		dotComsList.add(dot1);
		dotComsList.add(dot2);
		dotComsList.add(dot3);
		
		System.out.println("Welcome to this simple game!");
		System.out.print("Your goal is to sink three ships: ");
		System.out.println(dot1.getName() + ", " + dot2.getName() + ", " + dot3.getName() + ".");

		for(DotCom dotToSet : dotComsList)
		{
			dotToSet.setLocationCells(helper.placeDotCom(3));
		}
	}
	
	public void startPlaying()
	{
		while (!dotComsList.isEmpty())
		{
			String input = helper.getUserInput("Enter a guess: ");
			checkUserGuess(input);
		}
		finishGame();
	}
	
	public void checkUserGuess(String guess)
	{
		numOfGuesses++;
		String result = DotCom.getMiss();

		for (DotCom dotToTest : dotComsList)
		{
			result = dotToTest.checkSelf(guess);
			if (result.equals(DotCom.getHit()))
			{
				break;
			}
			if (result.equals(DotCom.getKill()))
			{
				dotComsList.remove(dotToTest);
				break;
			}
		}
		System.out.println(result);
	}

	public void finishGame()
	{
		System.out.println("Ayy Huego Terminado!");
		if (numOfGuesses <= 18)
		{
			System.out.print("It only took you " + numOfGuesses + " guesses! ");
			System.out.println("Good job!");
		}
		else
		{
			System.out.print("It actually took you " + numOfGuesses + " guesses.. ");
			System.out.println("Not very lucky this time.");
		}
	}

	public static void main(String[] args)
	{
		DotComBust game = new DotComBust();
		game.setUpGame();
		game.startPlaying();
	}
}
