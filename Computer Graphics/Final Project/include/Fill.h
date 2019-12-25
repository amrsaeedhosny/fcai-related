#ifndef FILL_H
#define FILL_H
#include <windows.h>
#include <vector>

using namespace std;

struct Entry {
    int xmin;
    int xmax;
};

class Fill
{
    public:
        Fill();
        void convexFill(HDC, vector <POINT>, COLORREF color);
    private:
        void initEntries(Entry *);
        void scanEdge(POINT, POINT, Entry *);
        void drawScanLines(HDC, Entry *, COLORREF);
};

#endif // FILL_H
