import java.io.*;
import java.util.ArrayList;

public class DCTest
{
	public static void main(String[] args)
	{
		ArrayList<String> locs = new ArrayList<String>();
		String loc = new String();
		
		int start = (int)(Math.random()*5);
		for (int i = 0; i < 3; i++) // location setter
		{
			loc = Integer.toString(start + i);	
			locs.add(loc);
		}
		
		DotCom dot = new DotCom();
		dot.setLocationCells(locs);

		GameHelper input = new GameHelper();			
		int guessCount = 0;
		
		while (dot.locationCells.isEmpty() != true)
		{
			String guess = input.getUserInput("Guess a number 0-6: ");
				
			String result = dot.checkSelf(guess);
			System.out.println(result);
			guessCount++;
		}

		System.out.println("Ayy Huego Terminado! Guesses taken: " + guessCount);
	}
}