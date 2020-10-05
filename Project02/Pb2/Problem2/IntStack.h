#ifndef __INTSTACK_H__
#define __INTSTACK_H__
#include "inf_int.h"

class IntStack {
public:
	IntStack();
	IntStack(int);
	IntStack(const IntStack&);
	//~IntStack() { delete stack; }


	int _size() const { return size; }
	bool empty() const { return !top; }

	void push(const inf_int&);
	inf_int pop();
private:

	inf_int* stack;
	int top;		//empty = 0
	int size;
};

#endif // !__IntStack_H__

