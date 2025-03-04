package com.sist.main;

public class ChangeServlet {
	private static String [] pages={
		"",
		"FoodList",
		"FoodDetail",
		"FoodTypeFind",
		"FoodFind",
		"GenieList",
		"GenieDetail",
		"GenieTypeFind",
		"GenieFind"
	};
	public static String pageChange(int mode)
	{
		return pages[mode];
	}
}
