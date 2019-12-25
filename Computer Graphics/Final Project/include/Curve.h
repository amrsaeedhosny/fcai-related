#ifndef CURVE_H
#define CURVE_H
#include <windows.h>
#include <vector>

using namespace std;

class Curve
{
    public:
        Curve();
        void drawFirstDegreeCurve(HDC, POINT, POINT, COLORREF);
        void drawSecondDegreeCurve(HDC, POINT, POINT, POINT, COLORREF);
        void drawHermiteCurve(HDC, POINT, POINT, POINT, POINT, COLORREF);
        void drawBezierCurve(HDC, POINT, POINT, POINT, POINT, COLORREF);
        void drawSplineCurve(HDC, vector <POINT>, double, COLORREF);
    private:
        template <size_t n>
        void multiplyMatByVect(double (&)[n][n], double (&)[n], double (&)[n]);
        template <size_t n>
        double dotProduct(double (&)[n], double (&)[n]);

};

#endif // CURVE_H
