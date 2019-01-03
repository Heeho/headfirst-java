import java.util.ArrayList;

public class DotCom
{
	private String name;
	ArrayList<String> locationCells = new ArrayList<String>();

	public DotCom(String n)
	{
		name = n;
	}

	public String getName()
	{
		return name;
	}

	public void setLocationCells(ArrayList<String> loc)
	{
		locationCells = loc;
	}

	public String checkSelf(String userInput)
	{
		String result = miss;
		int index = locationCells.indexOf(userInput);

		if (index >= 0)
		{
			locationCells.remove(index);
			if (locationCells.isEmpty() == true)
			{
				result = kill;
				System.out.print("There she goes, you just sank " + name + "! ");
			}
			else
			{
				result = hit;
			}
		}
		return result;
	}

	private static String miss = "Miss..";
	private static String hit = "Hit!";
	private static String kill = "It's a Kill!!";
	public void setMissHitKill(String a, String b, String c)
	{
		miss = a;
		hit = b;
		kill = c;
	}
	public static String getMiss()
	{
		return miss;
	}
	public static String getHit()
	{
		return hit;
	}
	public static String getKill()
	{
		return kill;
	}
}