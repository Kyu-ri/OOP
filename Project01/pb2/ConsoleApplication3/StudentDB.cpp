#include "StudentDB.h"
#include <algorithm>


bool SortCompare(const void *lhs, const void *rhs) {
	return ((Student *)lhs)->getName().compare(((Student *)rhs)->getName()) < 0;
}

StudentDB::StudentDB() { studentList.clear(); }

StudentDB::~StudentDB() {
	vector<Student *>::iterator itr;
	for (itr = studentList.begin(); itr != studentList.end(); itr++)
	{
		delete (*itr);
	}
	studentList.clear();
}

bool StudentDB::load(string &fileName) {
	string buffer;

	ifstream file(fileName.c_str());
	if (!file.is_open())
		return false;

	while (!file.eof())
	{
		getline(file, buffer);
		if (buffer.compare("") == 0)
			break;

		Student *student = new Student();
		student->setName(buffer);

		getline(file, buffer);
		student->setStudentID(buffer);

		getline(file, buffer);
		student->setDepartment(buffer);

		getline(file, buffer);
		student->setAge(buffer);

		getline(file, buffer);
		student->setTel(buffer);

		studentList.push_back(student);
	}
	file.close();

	sort(studentList.begin(), studentList.end(), SortCompare);

	return true;
}

bool StudentDB::save(string &fileName) {
	ofstream file(fileName.c_str());
	if (!file.is_open()) return false;

	vector<Student *>::iterator itr;
	for (itr = studentList.begin(); itr != studentList.end(); itr++) {
		Student *old = (Student *)*itr;

		file << old->getName() << endl;
		file << old->getStudentID() << endl;
		file << old->getDepartment() << endl;
		file << old->getAge() << endl;
		file << old->getTel() << endl;
	}

	file.close();

	return true;
}

bool StudentDB::insStudent(string &name, string &id, string &age, string &department, string &tel) {
	if (search(id, 2).size() > 0) return false;

	Student *student = new Student();
	student->setName(name);
	student->setStudentID(id);
	student->setAge(age);
	student->setDepartment(department);
	student->setTel(tel);

	studentList.push_back(student);
	sort(studentList.begin(), studentList.end(), SortCompare);

	return true;
}

bool StudentDB::delStudent(string &id) {
	vector<Student *>::iterator itr;
	for (itr = studentList.begin(); itr != studentList.end(); itr++) {
		Student *student = (Student *)*itr;
		if (student->getStudentID().compare(id) == 0) {
			studentList.erase(itr);
			break;
		}
	}
	return true;
}

vector<Student *> StudentDB::search(string &text, int state) {
	vector<Student *> findList;

	vector<Student *>::iterator itr;
	for (itr = studentList.begin(); itr != studentList.end(); itr++) {
		Student *student = (Student *)*itr;

		switch (state) {
			// Name
		case 1:
			if (student->getName().compare(text) == 0) findList.push_back(student);
			break;

			// Student ID
		case 2:
			if (student->getStudentID().compare(text) == 0) findList.push_back(student);
			break;

			// Department
		case 3:
			if (student->getDepartment().compare(text) == 0) findList.push_back(student);
			break;

			// Age
		case 4:
			if (student->getAge().compare(text) == 0) findList.push_back(student);
			break;

			// Tel
		default:
			if (student->getTel().compare(text) == 0) findList.push_back(student);
		}
	}

	return findList;
}

vector<Student *> StudentDB::getList() { return studentList; }