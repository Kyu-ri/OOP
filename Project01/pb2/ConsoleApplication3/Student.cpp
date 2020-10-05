#include "Student.h"


Student::Student() {}

Student *Student::clone() {
	Student *student = new Student();
	student->setName(name);
	student->setStudentID(studentID);
	student->setAge(age);
	student->setDepartment(department);
	student->setTel(tel);

	return student;
}

string Student::getName() {	return name;}

string Student::getStudentID() { return studentID; }

string Student::getAge() { return age; }

string Student::getDepartment() { return department;}

string Student::getTel() { return tel;}

void Student::setName(string &name) { this->name = name;}

void Student::setStudentID(string &id) { studentID = id;}

void Student::setAge(string &age) { this->age = age;}

void Student::setDepartment(string &department) { this->department = department;}

void Student::setTel(string &tel) { this->tel = tel;}

