import java.io.*;

public class GameHelper
{
	public String getUserInput(String prompt)
	{
		String inputLine = null;
 		System.out.print(prompt + " ");
	 	try
	 	{
			BufferedReader is = new BufferedReader(new InputStreamReader(System.in));
			inputLine = is.readLine();
			if (inputLine.length() == 0 )
				return null;
 		}
 		catch (IOException e)
 		{
			System.out.println("IOException: " + e);
	 	}

		int input = Integer.parseInt(inputLine);

		if ((input < 0) || (input > 6))
		{
			System.out.print("I'll take it as my lucky number which is 5. ");
			inputLine = "5";
			return inputLine;
		}
		else return inputLine;
	}
}
