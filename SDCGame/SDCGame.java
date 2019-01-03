class SDCGame
{
	public static void main (String[] args)
	{
		SimpleDotCom dot = new SimpleDotCom();
		
		dot.setMissHitKill("Miss..", "Hit!", "It's a Kill!!");

		GameHelper input = new GameHelper();

		int i = (int)(Math.random()*5);
		int[] locations = {i, i+1, i+2};
		dot.setLocationCells(locations);

		int guessCount = 0;

		boolean isAlive = true;
		boolean alreadyHit = false;

		while (isAlive == true)
		{
			String guess = input.getUserInput("Guess a number 0-6: ");
				
			String result = dot.checkSelf(guess);
			guessCount++;
			if (result.equals(dot.getKill()))
			{
				isAlive = false;
				System.out.println("Ayy Huego Terminado! Guesses taken: " + guessCount);
			}
		}
	}
}
