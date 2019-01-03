public class SimpleDotCom
{
	private int numOfHits;

	private String miss = "Miss..";
	private String hit = "Hit!";
	private String kill = "It's a Kill!!";

	private int[] locationCells;
	private int[] hitLoc = {7, 7, 7};
	
	boolean alreadyHit = false;
	
	public String getKill()					{return kill;}
	public int getNumOfHits()				{return numOfHits;}
	
	public void setLocationCells(int[] loc) 	{locationCells = loc;}
	public void setMissHitKill(String a, String b, String c)
	{
		miss = a;
		hit = b;
		kill = c;
	}

	public String checkSelf(String stringGuess)
	{
		int guess = Integer.parseInt(stringGuess);
		String result = miss;

		for (int cell:locationCells)
		{
			for(int hitloc:hitLoc)
			{
				if (guess == hitloc)
				{
					System.out.print("Already been there. ");
					alreadyHit = true;
					break;
				}
			}
			
			if (alreadyHit == true)
			{
				alreadyHit = false;
				break;
			}

			if (guess == cell)
			{
				result = "Hit!";
				hitLoc[numOfHits] = guess;
				numOfHits++;
				break;
			}
		}

		if (numOfHits == locationCells.length)
		{
			result = kill;
		}
		System.out.println(result);
		return result;
	}
}