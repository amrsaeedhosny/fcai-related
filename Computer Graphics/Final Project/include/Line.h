#ifndef LINE_H
#define LINE_H
#include <windows.h>


class Line
{
    public:
        Line();
        void drawLineDDA(HDC, POINT, POINT, COLORREF);
        void drawLineParametric(HDC, POINT, POINT, COLORREF);
        void drawLineMidpoint(HDC, POINT, POINT, COLORREF);
    private:
};

#endif // LINE_H
