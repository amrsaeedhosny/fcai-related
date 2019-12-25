#include "Curve.h"
#include <windows.h>
#include <math.h>
#include <iostream>
#include "Line.h"

using namespace std;

Curve::Curve()
{

}

void Curve:: drawFirstDegreeCurve(HDC hdc, POINT p1, POINT p2, COLORREF color){
    double dt = 1.0 / max(abs(p2.x - p1.x), abs(p2.y - p1.y));
    double basisMatrix[2][2] = {{-1, 1},
                                {1, 0}};

    double xVector[2] = {p1.x, p2.x};
    double yVector[2] = {p1.y, p2.y};
    double xCVector[2], yCVector[2];
    double x, y;

    multiplyMatByVect(basisMatrix, xVector, xCVector);
    multiplyMatByVect(basisMatrix, yVector, yCVector);

    for(double t = 0; t <= 1.0; t += dt) {
        double tVector[2] = {t, 1};
        x = dotProduct(xCVector, tVector);
        y = dotProduct(yCVector, tVector);
        t == 0? MoveToEx(hdc, round(x), round(y), NULL): LineTo(hdc, round(x), round(y));
    }
}



void Curve:: drawSecondDegreeCurve(HDC hdc, POINT p1, POINT p2, POINT p3, COLORREF color){
    double dt = 1.0 / max(abs(p3.x - p1.x), abs(p3.y - p1.y));
    double basisMatrix[3][3] = {{2, -4, 2},
                                {-3, 4, -1},
                                {1, 0, 0}};

    double xVector[3] = {p1.x, p2.x, p3.x};
    double yVector[3] = {p1.y, p2.y, p3.y};
    double xCVector[3], yCVector[3];
    double x, y;

    multiplyMatByVect(basisMatrix, xVector, xCVector);
    multiplyMatByVect(basisMatrix, yVector, yCVector);

    for(double t = 0; t <= 1.0; t += dt){
        double tVector[3] = {t*t, t, 1};
        x = dotProduct(xCVector, tVector);
        y = dotProduct(yCVector, tVector);
        t == 0? MoveToEx(hdc, round(x), round(y), NULL): LineTo(hdc, round(x), round(y));
    }
}

void Curve:: drawHermiteCurve(HDC hdc, POINT p1, POINT t1, POINT p2, POINT t2, COLORREF color){
    double dt = 1.0 / max(abs(p2.x - p1.x), abs(p2.y - p1.y));
    double basisMatrix[4][4] = {{2, 1, -2, 1},
                                {-3, -2, 3, -1},
                                {0, 1, 0, 0},
                                {1, 0, 0, 0}};

    double xVector[4] = {p1.x, t1.x, p2.x, t2.x};
    double yVector[4] = {p1.y, t1.y, p2.y, t2.y};
    double xCVector[4], yCVector[4];
    double x, y;

    multiplyMatByVect(basisMatrix, xVector, xCVector);
    multiplyMatByVect(basisMatrix, yVector, yCVector);

    for(double t = 0; t <= 1.0; t += dt){
        double tVector[4] = {t*t*t ,t*t, t, 1};
        x = dotProduct(xCVector, tVector);
        y = dotProduct(yCVector, tVector);
        t == 0? MoveToEx(hdc, round(x), round(y), NULL): LineTo(hdc, round(x), round(y));
    }
}

void Curve:: drawBezierCurve(HDC hdc, POINT p1, POINT p2, POINT p3, POINT p4, COLORREF color) {
    POINT t1, t2;
    t1.x = 3*(p2.x - p1.x);
    t1.y = 3*(p2.y - p1.y);
    t2.x = 3*(p4.x - p3.x);
    t2.y = 3*(p4.y - p3.y);
    drawHermiteCurve(hdc, p1, t1, p4, t2, color);
}

void Curve:: drawSplineCurve(HDC hdc, vector<POINT> points, double c, COLORREF color){
    double c1 = 1 - c;
    POINT t1, t2;
    t1.x = c1*(points[2].x - points[0].x);
    t1.y = c1*(points[2].y - points[0].y);
    for(int i = 2; i < points.size()-1; i++){
        t2.x = c1*(points[i+1].x - points[i-1].x);
        t2.y = c1*(points[i+1].y - points[i-1].y);
        drawHermiteCurve(hdc, points[i-1], t1, points[i], t2, color);
        t1 = t2;
    }
}

template <size_t n>
void Curve:: multiplyMatByVect(double (&basisMatrix)[n][n], double (&pVector)[n], double (&cVector)[n]){
    for(int i = 0; i < n; i++){
         cVector[i] = 0;
        for(int j = 0; j < n; j++){
            cVector[i] += basisMatrix[i][j] * pVector[j];
        }
    }
}

template <size_t n>
double Curve:: dotProduct(double (&cVector)[n], double (&tVector)[n]){
    double sum = 0;
    for(int i = 0; i < n; i++){
        sum += cVector[i] * tVector[i];
    }
    return sum;
}

