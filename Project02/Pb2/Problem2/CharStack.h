#ifndef __CHARSTACK_H__
#define __CHARSTACK_H__
class CharStack {
public:
	CharStack();
	CharStack(int);
	CharStack(const CharStack&);
	~CharStack() { delete stack; }

	int _size() const { return size; }
	bool empty() const { return !top; }

	void push(const char);
	char pop();
private:

	char* stack;
	int top;		//empty = 0
	int size;

};
#endif // !__CHARSTACK_H__

