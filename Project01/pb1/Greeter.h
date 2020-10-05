#ifndef __GREETER_H__
#define __GREETER_H__

#include "RecipeDB.h"
#include "PlanManager.h"

class Greeter {
private:
	RecipeDB rdb;							// 'rdb'variable value setting
	PlanManager pm;							// 'pm'variable value setting
	Greeter();
	~Greeter();

public:
	void browseDB();						//Casually browse the database of recipes
	void browsePlanManager();				//Casually browse the Plan
	void addRecipe(RecipeDB rdb);		    //Add a new recipe in rdb
	void editRecipe(RecipeDB rdb);			//Edit a recipe in rdb
	void annotateRecipe(RecipeDB rdb);		//annotate a recipe in rdb
	void reveiwPlan();						//Review a plan for several meals in pm
	void createPlan();						//Create a plan of meals in pm
};

#endif // !__GREETER_H__
