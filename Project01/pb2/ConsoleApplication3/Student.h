#include <iostream>
#include <string>

using namespace std;

#ifndef __STUDENT_H__
#define __STUDENT_H__

// Student class

class Student
{
private:
	string name;
	string studentID;
	string age;
	string department;
	string tel;

public:
	Student();
	~Student() {};

	Student *clone();

	string getName();
	string getStudentID();
	string getAge();
	string getDepartment();
	string getTel();

	void setName(string &name);
	void setStudentID(string &id);
	void setAge(string &age);
	void setDepartment(string &department);
	void setTel(string &tel);
};
#endif /* __STUDENT_H__ */
