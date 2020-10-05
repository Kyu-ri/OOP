#ifndef __MEAL_H__
#define __MEAL_H__

#include "Recipe.h"
#include "RecipeDB.h"

class Meal {
private:
	Recipe recipe;
	RecipeDB rdb;
public:
	void informationMeal(Recipe recipe);//information about a single meal
	void setMeal();						//sets number of people to be present at meal, recipes are automatically scaled in rdb & recipe
	void groceryList(Recipe recipe);	//produce grocery list for entire meal and combining grocery lists from individual scaled recipes
};

#endif // !__MEAL_H__
