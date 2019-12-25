#include "Circle.h"
#include <windows.h>
#include <iostream>
#include <math.h>

using namespace std;

Circle::Circle()
{

}

void Circle:: draw8Points(HDC hdc, int xc, int yc, int x, int y, COLORREF color){
    SetPixel(hdc, xc+x, yc+y, color);
    SetPixel(hdc, xc+x, yc-y, color);
    SetPixel(hdc, xc-x, yc+y, color);
    SetPixel(hdc, xc-x, yc-y, color);
    SetPixel(hdc, xc+y, yc+x, color);
    SetPixel(hdc, xc+y, yc-x, color);
    SetPixel(hdc, xc-y, yc+x, color);
    SetPixel(hdc, xc-y, yc-x, color);
}

void Circle:: drawCircleCartesian(HDC hdc, POINT pc, POINT p, COLORREF color){
    double r = sqrt((pc.x - p.x) * (pc.x - p.x) + (pc.y - p.y) * (pc.y - p.y));
    double y = r;

    for(int x = 0; x <= y; x++){
        draw8Points(hdc, pc.x, pc.y, x, round(y), color);
        y = sqrt((r * r)-(x * x));
    }
}

void Circle:: drawCirclePolar(HDC hdc, POINT pc, POINT p, COLORREF color){
    double r = sqrt((pc.x - p.x) * (pc.x - p.x) + (pc.y - p.y) * (pc.y - p.y));
    double dTheta = 1.0/r;
    double pi = 3.14;
    double x, y;

    for(double theta = 0; theta <= pi/4.0; theta += dTheta){
        x = r * sin(theta);
        y = r * cos(theta);
        draw8Points(hdc, pc.x, pc.y, round(x), round(y), color);
    }
}

void Circle:: drawCircleIterativePolar(HDC hdc, POINT pc, POINT p, COLORREF color) {
    double r = sqrt((pc.x - p.x) * (pc.x - p.x) + (pc.y - p.y) * (pc.y - p.y));
    double dTheta = 1.0 / r;
    double cosDTheta = cos(dTheta);
    double sinDTheta = sin(dTheta);
    double x = 0, y = r;
    while (x <= y) {
        draw8Points(hdc, pc.x, pc.y, round(x), round(y), color);
        int xTemp = x;
        x = x * cosDTheta - y * sinDTheta;
        y = y * cosDTheta + xTemp * sinDTheta;
    }
}

void Circle:: drawCircleMidpoint(HDC hdc, POINT pc, POINT p, COLORREF color) {
    double r = sqrt((pc.x - p.x) * (pc.x - p.x) + (pc.y - p.y) * (pc.y - p.y));
    double d = 5.0/4.0 - r;
    int d1 = 3, d2 = 5 - 2 * r;
    double x = 0, y = r;
    while(x <= y) {
        draw8Points(hdc, pc.x, pc.y, x, y, color);
        if ( d < 0 ) {
            d += d1;
            d2 += 2;
        }
        else {
            d += d2;
            d2 += 4;
            y--;
        }
        d1 += 2;
        x++;
    }
}
