#include "inf_int.h"
#include <assert.h>

/*
Originally written by
컴퓨터공학부
정진경
*/

inf_int::inf_int()
{
	this->digits = new char[2];	// 동적할당

	this->digits[0] = '0';		// default 값 0 설정
	this->digits[1] = '\0';
	this->length = 1;
	this->thesign = true;
}

inf_int::inf_int(int n) {
	char buf[100];

	if (n<0) {		// 음수 처리
		this->thesign = false;
		n = -n;
	}
	else {
		this->thesign = true;
	}

	int i = 0;
	while (n>0) {			// 숫자를 문자열로 변환하는 과정
		buf[i] = n % 10 + '0';

		n /= 10;
		i++;
	}

	if (i == 0) {	// 숫자의 절댓값이 0일 경우
		new (this) inf_int();	// 생성자 재호출...gcc에서 컴파일에러가 있다고 함. inf_int()의 경우 별개의 인스턴스가 생성됨. 
	}
	else {
		buf[i] = '\0';

		this->digits = new char[i + 1];
		this->length = i;
		strcpy(this->digits, buf);
	}
}

inf_int::inf_int(const char* str) {

	//부호처리
	if (str[0] == '-') {
		this->thesign = false;
	}
	else {
		this->thesign = true;
	}

	//길이 반환
	if (this->thesign && str[0] == '0' && str[1] == '\0' || str[0] == '\0') {
		this->digits = new char[2];	// 동적할당

		this->digits[0] = '0';		// default 값 0 설정
		this->digits[1] = '\0';
		this->length = 1;
		this->thesign = true;
	}
	else if (!this->thesign && str[1] == '0' && str[2] == '\0') {
		this->digits = new char[2];	// 동적할당

		this->digits[0] = '0';		// default 값 0 설정
		this->digits[1] = '\0';
		this->length = 1;
		this->thesign = true;
	}
	else {
		if (this->thesign) {		//true 양수
			for (this->length = 0; str[this->length] != '\0'; this->length++);
			this->digits = new char[this->length + 1];
			for (unsigned int i = 0; i < length; i++) {
				digits[i] = str[length - 1 - i];
				assert(this->length > 0);
			}
		}
		else {						//false 음수
			for (this->length = 0; str[length + 1] != '\0'; this->length++);
			this->digits = new char[this->length + 1];
			for (unsigned int i = 0; i < length; i++) {
				digits[i] = str[length - i];
				assert(this->length > 0);
			}
			assert(this->length > 0);
		}


		assert(this->digits != NULL);

		//'100'을 '001'로 변환

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
	delete digits;		// 메모리 할당 해제
}

inf_int& inf_int::operator=(const inf_int& a)
{
	if (this->digits) {
		delete this->digits;		// 이미 문자열이 있을 경우 제거.
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
	if ((strcmp(a.digits, b.digits) == 0) && a.thesign == b.thesign)	// 부호가 같고, 절댓값이 일치해야함.
		return true;
	return false;
}

bool operator!=(const inf_int& a, const inf_int& b)
{
	return !operator==(a, b);
}

bool operator>(const inf_int& a, const inf_int& b)
{
	if (a == b) return false; // a와 b가 같을 때 (a-b)의 값은 0인데 양수(true)로 여겨지기에 예외처리를 함  

							  //a,b의 부호가 같으면 (a-b)를 했을 때 a가 크면 (a-b)는 양수(true) 작으면 음수(false)이다.
	if (a.thesign == b.thesign) {
		return (a - b).thesign;
	}
	else return a.thesign; //a,b의 부호가 다를 때
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

	if (a.thesign == b.thesign) {	// 이항의 부호가 같을 경우 + 연산자로 연산
		for (i = 0; i<a.length; i++) {
			c.Add(a.digits[i], i + 1);
		}
		for (i = 0; i<b.length; i++) {
			c.Add(b.digits[i], i + 1);
		}

		c.thesign = a.thesign;

		return c;
	}
	else {	// 이항의 부호가 다를 경우 - 연산자로 연산
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

		//a와 b의 길이가 같은 경우
		else if (a.length == b.length) {
			if (a == b) return inf_int();      //0

			for (int i = a.length - 1; i >= 0; i--) {
				if (a.digits[i] < b.digits[i]) break;	//b가 더 큼

														//a가 더 큰경우
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

			//b가 더 큰 경우
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
		carry = '0'; //carry는 0으로 초기화

		for (j = 0; j < b.length; j++) {
			d = inf_int((a.digits[i] - '0') * (b.digits[j] - '0'));
			assert(d > 0 || d == 0);
			d.Add(carry, 0 + 1);

			if (d.length == 2) {
				carry = d.digits[1]; // carry는 두번째 digits에 저장
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

void inf_int::Add(const char num, const unsigned int index)	// a의 index 자리수에 n을 더한다. 0<=n<=9, ex) a가 391일때, a.Add("2", 2)의 결과는 411
{
	if (this->length<index) {
		this->digits = (char*)realloc(this->digits, index + 1);

		if (this->digits == NULL) {		// 할당 실패 예외처리
			cout << "Memory reallocation failed, the program will terminate." << endl;

			exit(0);
		}

		this->length = index;					// 길이 지정
		this->digits[this->length] = '\0';	// 널문자 삽입
	}

	if (this->digits[index - 1]<'0') {	// 연산 전에 '0'보다 작은 아스키값인 경우 0으로 채움. 쓰여지지 않았던 새로운 자리수일 경우 발생
		this->digits[index - 1] = '0';
	}

	this->digits[index - 1] += num - '0';	// 값 연산


	if (this->digits[index - 1]>'9') {	// 자리올림이 발생할 경우
		this->digits[index - 1] -= 10;	// 현재 자릿수에서 (아스키값) 10을 빼고
		Add('1', index + 1);			// 윗자리에 1을 더한다
	}
}
