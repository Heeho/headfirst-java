
import java.util.*;
import static java.lang.System.out;
import static java.lang.Math.*;

public class NameGuess
{	
	public static void main(String[] args)
	{
		boolean end = false;
		Cat pp = new Cat();
		Owner dude = new Owner("Boris");		
	
		out.println(String.format("Today is %tA, %<td %<tb of %<tY.\n%<tR o'clock.\nIt's %d outside..\n", Make.date(), Make.temp()));
		
		while (end != true)
		{
			if (dude.getGuess() != pp.getName())
			{
				dude.setGuess();
				out.println(dude.getName() + ": " + dude.getGuess() + "!");
			}
			else
			{
				out.println(pp.getName() + ": " + "Meow ^^");
				end = true;
			}
		}
	}
}

abstract class Creature
{
	private String name = "(...)";
	private int weight;
	private int ballCount;
	private static int objCount;
	
	static
	{
		objCount = 0;
	}
	
	public Creature()
	{
		objCount++;
	}
	
	public Creature(String n)
	{
		this();
		setName(n);
	}
	
	public void setName(String n)
	{
		name = n;
	}
	
	public String getName()
	{
		return name;
	}
}

class Make
{
	private Make(){}
	
	public static Date date()
	{
		Calendar date = Calendar.getInstance();
		
		int yy = (int)(1950 + random()*99);
		
		int[] mM = {11, 0, 1};
		int mm = (mM[(int)(random()*3)]);
		
		int dd = (int)(random()*33);
		int hh = (int)(random()*24);
		int min = (int)(random()*60);
		int ss = (int)(random()*60);
		
		date.set(yy, mm, 1, hh, min, ss);
		date.roll(date.DATE, dd);
		return date.getTime();
	}
	
	public static int temp()
	{
		
		int x = (int) (3-random()*40);
		return x;	
	}
	
	public static String name()
	{
		
		String[] NAME = {"Willy", "Spilly", "Pilly", "Shilly", "Dilly"};
		
		int i = (int)(random()*NAME.length);
		return NAME[i];
	}
}

class Owner extends Creature
{
	private String guess;
	
	public Owner()
	{
		super();
	}
	
	public Owner(String n)
	{
		this();
		super.setName(n);
	}
	public void setGuess()
	{
		guess = Make.name();
	}
	
	public String getGuess()
	{
		return guess;
	}
}

class Cat extends Creature
{
	public Cat()
	{
		super(Make.name());
	}
}
		
	
