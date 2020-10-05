#ifndef __RECIPE_H_DB
#define __RECIPE_H_DB

#include "Recipe.h"

class RecipeDB {
private:
	Recipe* recipe;							//recipe's variable vaule setting(dynamic allocation) 
	RecipeDB();
	~RecipeDB();
public:
	void browseDB();						//browse the recipe database in recipe
	void addRecipe(Recipe* recipe);			//add a new recipe in recipe
	void editRecipe(Recipe* recipe);		//edit an existing recipe in recipe
	void annotateRecipe(Recipe* recipe);	//annotate an existing recipe in recipe
};

#endif // !__RECIPE_H_DB
