#include "MyVector.h"
#include <math.h>

MyVector operator+(const MyVector &a, const MyVector &b) {
	MyVector c(0, 0);
	c.x = a.x + b.x;
	c.z = a.z + b.z;

	return c;
}

MyVector operator-(const MyVector &a, const MyVector &b) {
	MyVector c(0, 0);
	c.x = a.x - b.x;
	c.z = a.z - b.z;
	
	return c;
}

MyVector operator*(const MyVector &a, const float num) {
	return MyVector(a.x * num, a.z * num);
}

MyVector operator*(const float num, const MyVector &a) {
	return a*num;
}

MyVector MyVector::orthogonal(const MyVector& base) {
	if (this->v == 0) return MyVector();
	return MyVector((this->innerProduct(base) / base.innerProduct(base) * base));
}

MyVector MyVector::tVector(const MyVector& orthogonal) { return *this - orthogonal; }