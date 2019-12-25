#include "Fill.h"
#include <windows.h>
#include <math.h>
#include <iostream>

#define MAX_TABLE_ENTRIES 1000
#define MAXINT 2000

using namespace std;

Fill::Fill()
{

}

void Fill:: convexFill(HDC hdc, vector <POINT> points, COLORREF color) {
    Entry * table = new Entry[MAX_TABLE_ENTRIES];
    initEntries(table);

    for(int i = 0; i < points.size(); i++) {
        scanEdge(points[i%points.size()], points[(i+1)%points.size()], table);
    }

    drawScanLines(hdc, table, color);
}

void Fill:: initEntries(Entry * table) {
    for(int i = 0; i < MAX_TABLE_ENTRIES; i++){
        table[i].xmin = MAXINT;
        table[i].xmax = -MAXINT;
    }
}

void Fill:: scanEdge(POINT p1, POINT p2, Entry * table) {
    if(p1.y == p2.y) return;

    if(p1.y > p2.y) {
        swap(p1.x, p2.x);
        swap(p1.y, p2.y);
    }

    double mi = (double)(p2.x - p1.x) / (p2.y - p1.y);

    double x = p1.x;
    for(int y = p1.y; y < p2.y; y++){
        if(x < table[y].xmin) table[y].xmin = (int) ceil(x);
        if(x > table[y].xmax) table[y].xmax = (int) floor(x);
        x += mi;
    }
}

void Fill:: drawScanLines(HDC hdc, Entry * table, COLORREF color) {
    for(int y = 0; y < MAX_TABLE_ENTRIES; y++){
        if(table[y].xmax >= table[y].xmin){
            for(int x = table[y].xmin; x <= table[y].xmax; x++){
                SetPixel(hdc, x, y, color);
            }
        }
    }

}



