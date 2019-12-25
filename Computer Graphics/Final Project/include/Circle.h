#ifndef CIRCLE_H
#define CIRCLE_H
#include <windows.h>

class Circle
{
    public:
        Circle();
        void drawCircleCartesian(HDC, POINT, POINT, COLORREF);
        void drawCirclePolar(HDC, POINT, POINT, COLORREF);
        void drawCircleIterativePolar(HDC, POINT, POINT, COLORREF);
        void drawCircleMidpoint(HDC, POINT, POINT, COLORREF);
    private:
        void draw8Points(HDC hdc, int, int, int, int, COLORREF color);
};

#endif // CIRCLE_H
