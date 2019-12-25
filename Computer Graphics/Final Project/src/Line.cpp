#include "Line.h"
#include <windows.h>
#include <iostream>
#include <math.h>

using namespace std;

Line::Line()
{

}

void Line:: drawLineDDA(HDC hdc, POINT ps, POINT pe, COLORREF color) {
    int dx = pe.x - ps.x;
    int dy = pe.y - ps.y;

    if(abs(dy) < abs(dx)) {
        double m = dy * 1.0 / dx;
        if (ps.x > pe.x) {
            swap(ps.x, pe.x);
            swap(ps.y, pe.y);
        }

        double y = ps.y;
        for (int x = ps.x; x <= pe.x; x++) {
            SetPixel(hdc, x, round(y), color);
            y += m;
        }
    }
    else {
        double m = dx * 1.0 / dy;
        if (ps.y > pe.y) {
            swap(ps.x, pe.x);
            swap(ps.y, pe.y);
        }

        double x = ps.x;
        for (int y = ps.y; y <= pe.y; y++) {
            SetPixel(hdc, round(x), y, color);
            x += m;
        }
    }
}

void Line:: drawLineParametric(HDC hdc, POINT ps, POINT pe, COLORREF color) {
    int n = abs(pe.x - ps.x) > abs(pe.y - ps.y) ? abs(pe.x - ps.x) : abs(pe.y - ps.y);
    double dt = 1.0 / n;
    double dx = dt * (pe.x - ps.x);
    double dy = dt * (pe.y - ps.y);
    double x = ps.x, y = ps.y;
    for (int i = 0; i < n; i++) {
        SetPixel(hdc, round(x), round(y), color);
        x += dx;
        y += dy;
    }
}

void Line:: drawLineMidpoint(HDC hdc, POINT ps, POINT pe, COLORREF color) {
    double m = (double) (pe.y - ps.y) / (pe.x - ps.x);

    if(m >= 0 && m <= 1){
        if(ps.x > pe.x){
            swap(ps.x, pe.x);
            swap(ps.y, pe.y);
        }

        int dx = pe.x - ps.x;
        int dy = pe.y - ps.y;
        int x = ps.x;
        int y = ps.y;
        int d = dx / 2.0 - dy;
        int d1 = - dy;
        int d2 = dx - dy;

        while(x <= pe.x){
            SetPixel(hdc, x, y, color);
            if(d > 0){
                x++;
                d += d1;
            }
            else{
                x++;
                y++;
                d += d2;
            }
        }
    }
    else if(m > 1){
        if(ps.y > pe.y){
            swap(ps.x, pe.x);
            swap(ps.y, pe.y);
        }

        int dx = pe.x - ps.x;
        int dy = pe.y - ps.y;
        int x = ps.x;
        int y = ps.y;
        int d = dx - dy / 2.0;
        int d1 = dx - dy;
        int d2 = dx;

        while(y <= pe.y){
            SetPixel(hdc, x, y, color);
            if(d > 0){
               x++;
               y++;
               d += d1;
            }
            else{
               y++;
               d += d2;
            }
        }
    }
    else if(m < 0 && m >= -1){
        if(ps.x > pe.x){
            swap(ps.x, pe.x);
            swap(ps.y, pe.y);
        }

        int dx = pe.x - ps.x;
        int dy = pe.y - ps.y;
        int x = ps.x;
        int y = ps.y;
        int d = - dx / 2.0 - dy;
        int d1 = - dx - dy;
        int d2 = - dy;

        while(x <= pe.x){
            SetPixel(hdc, x, y, color);
            if(d > 0){
                x++;
                y--;
                d += d1;
            }
            else{
                x++;
                d += d2;
            }
        }
    }
    else if (m < -1){
        if(ps.y < pe.y){
            swap(ps.x, pe.x);
            swap(ps.y, pe.y);
        }

        int dx = pe.x - ps.x;
        int dy = pe.y - ps.y;
        int x = ps.x;
        int y = ps.y;
        int d = - dx - dy / 2.0;
        int d1 = - dx;
        int d2 = - dx - dy;

        while(y >= pe.y){
            SetPixel(hdc, x, y, color);
            if(d > 0){
              y--;
              d += d1;
            }
            else{
              x++;
              y--;
              d += d2;
            }
        }
    }
}


