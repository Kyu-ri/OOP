#ifndef __PLANMANAGER_H__
#define __PLANMANAGER_H__

#include "Date.h"

class PlanManager {
private:
	Date date;
public:
	void selectDates(Date date);	//select a sequence of dates for planning in date
	void editPlan(Date date);		//edit an existing plan in date
};

#endif // !__PLANMANAGER_H__
