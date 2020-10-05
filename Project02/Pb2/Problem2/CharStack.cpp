#include "CharStack.h"
#include <assert.h>
#include <iostream>

CharStack::CharStack() {
	top = 0;
	size = 2;
	stack = new char[size];
	assert(stack != NULL);
}

CharStack::CharStack(int size) {
	top = 0;
	this->size = size;
	stack = new char[size];
	assert(stack != NULL);
}

CharStack::CharStack(const CharStack& stack) {
	this->size = stack.size;
	this->stack = new char[this->size];
	for (int i = 0; i < stack.top; i++) this->stack[top++] = stack.stack[i];
}

//CharStack::~CharStack() { delete stack; }

//int CharStack::_size() const { return size; }

//bool CharStack::empty() const { return top; }

void CharStack::push(const char element) {
	if (top >= size) {
		char* temp = stack;
		stack = new char[size *= 2];
		assert(stack != NULL);
		for (int i = 0; temp[i]; i++) stack[i] = temp[i];
	}
	stack[top++] = element;
}

char CharStack::pop() {
	if (!empty()) return stack[--top];
	else return -1;
}