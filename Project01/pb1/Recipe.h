#ifndef __RECIPE_H__
#define __RECIPE_H__
#include <iostream>
using namespace std;

class Recipe {
private:
	string* list;					//list of ingredients
	string* annotate;				//possible annotate an existing recipe
public:
	void editRecipe(string* list);	//edit these data values using string*list
	void displayRecipe();			//interactively display itself on the output device
};
	
#endif // !__RECIPE_H__
