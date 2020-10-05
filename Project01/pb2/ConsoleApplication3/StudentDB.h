#ifndef __STUDENTDB_H__
#define __STUDENTDB_H__

#include "Student.h"
#include <fstream>
#include <vector>

class StudentDB {
private:
	vector <Student*> studentList;
public:
	StudentDB();
	~StudentDB();

	bool load(string &fileName); // when executing, file1.txt loads data 
	bool save(string &fileName); // when insertion, save data in file1.txt
	bool insStudent(string &name, string &id, string &age, string &department, string &tel); // insertion data
	bool delStudent(string &id); // deleting data
	vector<Student *> search(string &text, int state);
	vector<Student *> getList();
};

#endif // !__STUDENTDB_H__
