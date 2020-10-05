#ifndef __MYVECTOR_H__
#define __MYVECTOR_H__
#include <math.h>

class MyVector {
private:
	float x;
	float z;

	float v;

public:
	MyVector() {
		this->x = 0;
		this->z = 0;
	}
	MyVector(float x, float z) {
		this->x = x;
		this->z = z;

		this->v = sqrt(pow(x, 2) + pow(z, 2));
	}
	MyVector(MyVector& v) {
		this->x = v.x;
		this->z = v.z;
		this->v = v.v;
	}
	~MyVector() {}

	friend MyVector operator+(const MyVector&, const MyVector&);

	friend MyVector operator-(const MyVector&, const MyVector&);

	friend MyVector operator*(const MyVector&, const float);
	friend MyVector operator*(const float ,const MyVector&);

	//friend MyVector operator*(const MyVector&, const MyVector&);	//³»Àû

	float innerProduct(const MyVector& a) const { return a.x*x + a.z*z; }

	//friend float distance(const MyVector&);

	MyVector orthogonal(const MyVector&);

	MyVector tVector(const MyVector&);

	void setVector(float x, float z) {
		this->x = x;
		this->z = z;
	}

	float getX() const { return x; }
	float getZ() const { return z; }
	float getV() const { return v; }
	
};
#endif // !__MYVECTOR_H__

