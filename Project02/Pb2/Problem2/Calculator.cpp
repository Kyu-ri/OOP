#include <iostream>
#include <sstream>
#include <string>
#include <assert.h>
#include "Calculator.h"


using namespace std;

Calculator::Calculator() {
	string a;
	inf_int result;
	char b = '0';
	while (1) {
		cout << "Input : ";
		getline(cin, a);
		stringstream iss(a);
		do
		{
			string split;
			iss >> split;
			if (split.compare("+") == 0 || split.compare("-") == 0) charStack.push(split.at(0));
			else if (split.compare("*") == 0) {
				string split;
				iss >> split;
				intStack.push(intStack.pop() * inf_int(split.c_str()));
			}
			else if (split.compare("0") == 0) intStack.push(inf_int());
			else if (split.compare("") != 0) intStack.push(inf_int(split.c_str()));
		} while (iss);


		result = intStack.pop();

		while (!charStack.empty()) {
			if (charStack.pop() == '+') result = intStack.pop() + result;
			else result = intStack.pop() - result;
		}
		if (!(result.getDigits()[0] == '0' && result.getDigits()[1] == '\0')) {
			unsigned int resultLength = 0;
			bool sign = result.getSign();

			for (; result.getDigits()[resultLength] != '\0'; resultLength++);
			for (unsigned int i = resultLength - 1; i >= 0; i--) {
				if (result.getDigits()[i] != '0') {
					if (sign) {
						unsigned int length = i + 1;
						char* temp = new char[length + 1];
						for (unsigned int j = 0; j < length; j++) temp[j] = result.getDigits()[i - j];
						temp[length] = '\0';
						result = inf_int(temp);
						break;
					}
					else {
						unsigned int length = i + 2;
						char* temp = new char[length + 1];
						temp[0] = '-';
						for (unsigned int j = 1; j < length; j++) temp[j] = result.getDigits()[i - j + 1];
						temp[length] = '\0';
						result = inf_int(temp);
						break;
					}

				}
			}
		}
		cout << "Output : " << result << endl;
	}


}
