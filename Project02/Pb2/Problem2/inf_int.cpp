#include "inf_int.h"
#include <assert.h>

/*
Originally written by
��ǻ�Ͱ��к�
������
*/

inf_int::inf_int()
{
	this->digits = new char[2];	// �����Ҵ�

	this->digits[0] = '0';		// default �� 0 ����
	this->digits[1] = '\0';
	this->length = 1;
	this->thesign = true;
}

inf_int::inf_int(int n) {
	char buf[100];

	if (n<0) {		// ���� ó��
		this->thesign = false;
		n = -n;
	}
	else {
		this->thesign = true;
	}

	int i = 0;
	while (n>0) {			// ���ڸ� ���ڿ��� ��ȯ�ϴ� ����
		buf[i] = n % 10 + '0';

		n /= 10;
		i++;
	}

	if (i == 0) {	// ������ ������ 0�� ���
		new (this) inf_int();	// ������ ��ȣ��...gcc���� �����Ͽ����� �ִٰ� ��. inf_int()�� ��� ������ �ν��Ͻ��� ������. 
	}
	else {
		buf[i] = '\0';

		this->digits = new char[i + 1];
		this->length = i;
		strcpy(this->digits, buf);
	}
}

inf_int::inf_int(const char* str) {

	//��ȣó��
	if (str[0] == '-') {
		this->thesign = false;
	}
	else {
		this->thesign = true;
	}

	//���� ��ȯ
	if (this->thesign && str[0] == '0' && str[1] == '\0' || str[0] == '\0') {
		this->digits = new char[2];	// �����Ҵ�

		this->digits[0] = '0';		// default �� 0 ����
		this->digits[1] = '\0';
		this->length = 1;
		this->thesign = true;
	}
	else if (!this->thesign && str[1] == '0' && str[2] == '\0') {
		this->digits = new char[2];	// �����Ҵ�

		this->digits[0] = '0';		// default �� 0 ����
		this->digits[1] = '\0';
		this->length = 1;
		this->thesign = true;
	}
	else {
		if (this->thesign) {		//true ���
			for (this->length = 0; str[this->length] != '\0'; this->length++);
			this->digits = new char[this->length + 1];
			for (unsigned int i = 0; i < length; i++) {
				digits[i] = str[length - 1 - i];
				assert(this->length > 0);
			}
		}
		else {						//false ����
			for (this->length = 0; str[length + 1] != '\0'; this->length++);
			this->digits = new char[this->length + 1];
			for (unsigned int i = 0; i < length; i++) {
				digits[i] = str[length - i];
				assert(this->length > 0);
			}
			assert(this->length > 0);
		}


		assert(this->digits != NULL);

		//'100'�� '001'�� ��ȯ

		digits[length] = '\0';
	}
}

inf_int::inf_int(const inf_int& a) {
	this->digits = new char[a.length + 1];

	strcpy(this->digits, a.digits);
	this->length = a.length;
	this->thesign = a.thesign;
}

inf_int::~inf_int() {
	delete digits;		// �޸� �Ҵ� ����
}

inf_int& inf_int::operator=(const inf_int& a)
{
	if (this->digits) {
		delete this->digits;		// �̹� ���ڿ��� ���� ��� ����.
	}
	this->digits = new char[a.length + 1];

	strcpy(this->digits, a.digits);
	this->length = a.length;
	this->thesign = a.thesign;

	return *this;
}

bool operator==(const inf_int& a, const inf_int& b)
{
	// we assume 0 is always positive.
	if ((strcmp(a.digits, b.digits) == 0) && a.thesign == b.thesign)	// ��ȣ�� ����, ������ ��ġ�ؾ���.
		return true;
	return false;
}

bool operator!=(const inf_int& a, const inf_int& b)
{
	return !operator==(a, b);
}

bool operator>(const inf_int& a, const inf_int& b)
{
	if (a == b) return false; // a�� b�� ���� �� (a-b)�� ���� 0�ε� ���(true)�� �������⿡ ����ó���� ��  

							  //a,b�� ��ȣ�� ������ (a-b)�� ���� �� a�� ũ�� (a-b)�� ���(true) ������ ����(false)�̴�.
	if (a.thesign == b.thesign) {
		return (a - b).thesign;
	}
	else return a.thesign; //a,b�� ��ȣ�� �ٸ� ��
}


bool operator<(const inf_int& a, const inf_int& b)
{
	if (operator>(a, b) || operator==(a, b)) {
		return false;
	}
	else {
		return true;
	}
}

inf_int operator+(const inf_int& a, const inf_int& b)
{
	inf_int c;
	unsigned int i;

	if (a.thesign == b.thesign) {	// ������ ��ȣ�� ���� ��� + �����ڷ� ����
		for (i = 0; i<a.length; i++) {
			c.Add(a.digits[i], i + 1);
		}
		for (i = 0; i<b.length; i++) {
			c.Add(b.digits[i], i + 1);
		}

		c.thesign = a.thesign;

		return c;
	}
	else {	// ������ ��ȣ�� �ٸ� ��� - �����ڷ� ����
		c = b;
		c.thesign = a.thesign;

		return a - c;
	}
}

inf_int operator-(const inf_int& a, const inf_int& b)
{
	inf_int c, d;
	unsigned int zeroCount = 0;

	if (a.thesign && b.thesign) {
		if (a.length > b.length) {
			c = a;

			for (int i = 0; i < b.length; i++) {
				if (c.digits[i] < b.digits[i]) {
					for (unsigned int j = i + 1; j < a.length; j++) {
						if (a.digits[j] == '0') zeroCount++;
						else {
							c.digits[j] -= 1;
							for (; zeroCount != 0; zeroCount--) {
								c.digits[--j] += 9;
							}
							assert(zeroCount == 0);
							break;
						}
					}
					c.digits[i] = c.digits[i] - (b.digits[i] - '0') + 10;
				}
				else c.digits[i] = c.digits[i] - (b.digits[i] - '0'); //c.Add((-b.digits[i]), i + 1);
			}
			return c;
		}

		//a�� b�� ���̰� ���� ���
		else if (a.length == b.length) {
			if (a == b) return inf_int();      //0

			for (int i = a.length - 1; i >= 0; i--) {
				if (a.digits[i] < b.digits[i]) break;	//b�� �� ŭ

														//a�� �� ū���
				c = a;
				for (int i = 0; i < c.length; i++) {
					if (c.digits[i] < b.digits[i]) {
						c.digits[i + 1] -= 1;
						c.digits[i] = c.digits[i] + 10 - (b.digits[i] - '0');
					}
					else c.digits[i] = c.digits[i] - (b.digits[i] - '0');
				}
				return c;
			}

			//b�� �� ū ���
			c = b;
			c = c - a;
			c.thesign = false;

			return c;

		}

		else {	// a < b
			c = b, d = a;
			c.thesign = true, d.thesign = true;
			c = c - d;
			c.thesign = false;
			return c;
		}
	}

	else if (a.thesign != b.thesign) {
		c = a, d = (b);
		c.thesign = true, d.thesign = true;
		c = c + d;
		c.thesign = a.thesign;

		return c;
	}

	else {
		c = (a), d = (b);
		c.thesign = true, d.thesign = true;
		return d - c;
	}
}

inf_int operator*(const inf_int& a, const inf_int& b)
{
	inf_int c, d = inf_int();
	unsigned int i, j;

	char carry;
	for (i = 0; i < a.length; i++) {
		carry = '0'; //carry�� 0���� �ʱ�ȭ

		for (j = 0; j < b.length; j++) {
			d = inf_int((a.digits[i] - '0') * (b.digits[j] - '0'));
			assert(d > 0 || d == 0);
			d.Add(carry, 0 + 1);

			if (d.length == 2) {
				carry = d.digits[1]; // carry�� �ι�° digits�� ����
			}
			else carry = '0';
			c.Add(d.digits[0], j + 1 + i);
		}
		if (carry != '0') c.Add(carry, j + i + 1);
	}
	c.thesign = a.thesign && b.thesign;

	return c;
}



ostream& operator<<(ostream& out, const inf_int& a)
{
	int i;

	if (a.thesign == false) {
		out << '-';
	}
	for (i = a.length - 1; i >= 0; i--) {
		out << a.digits[i];
	}
	return out;
}

void inf_int::Add(const char num, const unsigned int index)	// a�� index �ڸ����� n�� ���Ѵ�. 0<=n<=9, ex) a�� 391�϶�, a.Add("2", 2)�� ����� 411
{
	if (this->length<index) {
		this->digits = (char*)realloc(this->digits, index + 1);

		if (this->digits == NULL) {		// �Ҵ� ���� ����ó��
			cout << "Memory reallocation failed, the program will terminate." << endl;

			exit(0);
		}

		this->length = index;					// ���� ����
		this->digits[this->length] = '\0';	// �ι��� ����
	}

	if (this->digits[index - 1]<'0') {	// ���� ���� '0'���� ���� �ƽ�Ű���� ��� 0���� ä��. �������� �ʾҴ� ���ο� �ڸ����� ��� �߻�
		this->digits[index - 1] = '0';
	}

	this->digits[index - 1] += num - '0';	// �� ����


	if (this->digits[index - 1]>'9') {	// �ڸ��ø��� �߻��� ���
		this->digits[index - 1] -= 10;	// ���� �ڸ������� (�ƽ�Ű��) 10�� ����
		Add('1', index + 1);			// ���ڸ��� 1�� ���Ѵ�
	}
}
