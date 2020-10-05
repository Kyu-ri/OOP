#include "student.h"
#include "StudentDB.h"

#define ERROR(msg) {cout << msg << endl;}

int main() {
	int state = 0, ch;
	string name, id, age, department, tel;
	vector<Student *> studentTemp;
	vector<Student *>::iterator itr;

	string fileName = string("file1.txt"); // file1.txt 

	StudentDB studentDB;
	studentDB.load(fileName); // Student DataBase

	//Student Information Management System Menu
	while (state >= 0) {
		bool error = false;
		system("cls");
		cout << "1. Insertion" << endl;
		cout << "2. Search" << endl;
		cout << "3. Deletion" << endl;
		cout << "4. Exit" << endl;
		cout << "> ";

		cin >> state;
		while((ch = getchar()) != '\n' && ch != EOF);
		system("cls");
		switch (state) {
		case 1:
			cout << "- Insertion -" << endl;
			cout << "Name ? ";
			getline(cin, name);
			if (name.length() > 15) {
				ERROR("Error : Exceed name length");
				system("pause");
				break;
			}
			for (int i = 0; i < name.length(); i++) {
				if (name.at(i) != ' ' && !isalpha(name.at(i))) {
					ERROR("Error : Not alphabet");
					system("pause");
					error = true;
					break;
				}
			}
			if (error) break; // insertion student name & maximum 15 alphabets 
			
			cout << "Student ID ? ";
			getline(cin, id);
			if (id.length() != 10) {
				ERROR("Error : Wrong id length");
				system("pause");
				break;
			}
			for (int i = 0; i < id.length(); i++) {
				if (!isdigit(id.at(i))) {
					ERROR("Error : Not digit");
					system("pause");
					error = true;
					break;
				}
			}
			if (error) break; // insertion student ID & only 10 digits

			cout << "Age ? ";
			getline(cin, age);
			if (age.length() > 3) {
				ERROR("Error : Exceed age length");
				system("pause");
				break;
			}
			for (int i = 0; i < age.length(); i++) {
				if (!isdigit(age.at(i))) {
					ERROR("Error : Not digit");
					system("pause");
					error = true;
					break;
				}
			}
			if (error) break; // insertion age & maximum 3 digits
			
			cout << "Department ? ";
			getline(cin, department);
			if (department.length() > 20) {
				ERROR("Error : Exceed department length");
				system("pause");
				break;
			}
			for (int i = 0; i < department.length(); i++) {
				if (department.at(i) != ' ' && !isalpha(department.at(i))) {
					ERROR("Error : Not alphabet");
					system("pause");
					error = true;
					break;
				}
			}
			if (error) break; // insertion department & maximum 20 alphabets

			cout << "Tel ? ";
			getline(cin, tel);
			if (tel.length() > 12) {
				ERROR("Error : Exceed tel length");
				system("pause");
				break;
			}
			for (int i = 0; i < tel.length(); i++) {
				if (!isdigit(tel.at(i))) {
					ERROR("Error : Not digit");
					system("pause");
					error = true;
					break;
				}
			}
			if (error) break; // insertion Tel & only digits

			if (studentDB.insStudent(name, id, age, department, tel)) studentDB.save(fileName);
			else {
				cout << "¡°Error : Already inserted" << endl;
				system("pause");
			}
			
			state = 1;
			break; // save in file1.txt & check if already inserted data

		case 2:
			cout << "- Search -" << endl;
			cout << "1. Search by name" << endl;
			cout << "2. Search by student ID (10 numbers)" << endl;
			cout << "3. Search by department name" << endl;
			cout << "4. Search by Age" << endl;
			cout << "5. List All" << endl;
			cout << "> ";

			cin >> state;
			while ((ch = getchar()) != '\n' && ch != EOF);

			if (state < 5) {
				cout << "Keyword ? ";
				getline(cin, name);

				studentTemp = studentDB.search(name, state);
			}
			else studentTemp = studentDB.getList();

			cout << "Name           Student ID  Dept                Age   Tel" << endl;

			for (itr = studentTemp.begin(); itr != studentTemp.end(); itr++) {
				Student *student = (Student *)*itr;
				int spaceNum = 0;
				cout << student->getName(); spaceNum = 15 - student->getName().length();
				for (int i = 0; i < spaceNum; i++) cout << " ";
				cout << student->getStudentID(); spaceNum = 12 - student->getStudentID().length();
				for (int i = 0; i < spaceNum; i++) cout << " ";
				cout << student->getDepartment(); spaceNum = 20 - student->getDepartment().length();
				for (int i = 0; i < spaceNum; i++) cout << " ";
				cout << student->getAge(); spaceNum = 6 - student->getAge().length();
				for (int i = 0; i < spaceNum; i++) cout << " ";
				cout << student->getTel();
				cout << endl;
			}
			state = 2;
			system("pause");
			break;

		case 3:
			cout << "- Deletion -" << endl;
			cout << "Student ID to delete : ";
			getline(cin, name);

			studentDB.delStudent(name);
			studentDB.save(fileName);

			state = 3;
			system("pause");
			break;

		default:
			state = -1;

			break;
		}
	}

	return 0;
}