#ifndef BMP_H
#define BMP_H
#include <tchar.h>
#include <windows.h>
#include <stdio.h>
#include <math.h>
#include <iostream>
#include <vector>
#include <commdlg.h>
#include <cstring>
#include <string>
#include <fstream>

using namespace std;

class BMP
{
    public:
        BMP();
        void saveBMPFile(HDC, LPCSTR);
        void loadBMPFile(HDC, LPCSTR);
    private:
        void writeBMPFile(HBITMAP, LPCSTR);
};

#endif // BMP_H
