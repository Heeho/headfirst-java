import java.util.*;

public class Sorter {
	
	public static void main(String[] args) {
		ArrayList<String> fruitList = new ArrayList<String>();
		String text = "Apple\nOrange\nPear\nBanana\nMango";
		String recipes = "Chicken Sandwich: chicken, bread, vegs\nFish Soup: fish, potatoes, vegs\nScrambled Eggs: eggs, tomatoe, sweet pepper";
		Sorter s= new Sorter();
		fruitList = s.parseText(text);
		
		System.out.println(fruitList);
		Collections.sort(fruitList);
		System.out.println(fruitList);
	}
	
	public ArrayList<String> parseText(String text) {
		ArrayList<String> list = new ArrayList<String>();
		String [] textRows = text.split("\n");
		
		for(int i = 0; i < textRows.length; i++) {
			list.add(textRows[i]);
		}
		return list;
	}
	
	public ArrayList<Recipe> parseRecipe(String text) {
		ArrayList<Recipe> recipeList = new ArrayList<Recipe>();
		String [] textRows = text.split("\n");
		String[] recipes;
		for(int i = 0; i < textRows.length; i++) {
			recipes = textRows[i].split(": ");
			Recipe r = new Recipe(recipes[0], recipes[1]);
			recipeList.add(r);
		}
		return recipeList;
	}
}

class Recipe implements Comparable {
	String name;
	String mats;
	
	public Recipe(String n, String m) {
		name = n;
		mats = m;
	}
	
	public int compareTo(Recipe r) {
		return this.getName().compareTo(r.getName());
	}
	
	public String getName() {
		return name;
	}
}		
	