#include "IntStack.h"
#include <assert.h>
#include <iostream>

using namespace std;

IntStack::IntStack() {
	top = 0;
	size = 2;
	stack = new inf_int[size];
	assert(stack != NULL);
}

IntStack::IntStack(int size) {
	top = 0;
	this->size = size;
	stack = new inf_int[size];
	assert(stack != NULL);
}

IntStack::IntStack(const IntStack& stack) {
	this->size = stack.size;
	this->stack = new inf_int[this->size];
	for (int i = 0; i < stack.top; i++) this->stack[top++] = stack.stack[i];
}

//IntStack::~IntStack() { delete stack; }

//int IntStack::_size() const { return size; }

//bool IntStack::empty() const { return top; }

void IntStack::push(const inf_int& element) {
	if (top >= size) {
		assert(top >= size);
		inf_int* temp = stack;
		stack = new inf_int[size * 2];
		assert(stack != NULL);
		for (int i = 0; i < size; i++) stack[i] = temp[i];
		size *= 2;
	}
	stack[top++] = element;
}

inf_int IntStack::pop() {
	if (!empty()) return stack[--top];
	else return 0;
}