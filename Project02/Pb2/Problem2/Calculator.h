#ifndef __CALCULATOR_H__
#define __CALCULATOR_H__
#include "IntStack.h"
#include "CharStack.h"

class Calculator {
public:
	Calculator();

private:
	IntStack intStack;
	CharStack charStack;
};
#endif // !__CALCULATOR_H__


