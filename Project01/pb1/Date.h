#ifndef __DATE_H__
#define __DATE_H__
#include <iostream>
using namespace std;

#include "Meal.h"

class Date {
private:
	string* meal;
	Date* date;						//date's variable vaule setting(dynamic allocation) 
public:
	void editMeal(string* meal);	//edit specific meals in meal
	void annotateDate(Date*	date);	//annotate information about dates(birthday etc.)
	void printGrocerylist();		//print out grocery list for entire set of meals in meal's groceryList function
};

#endif // !__DATE_H__
