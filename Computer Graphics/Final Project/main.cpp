#if defined(UNICODE) && !defined(_UNICODE)
    #define _UNICODE
#elif defined(_UNICODE) && !defined(UNICODE)
    #define UNICODE
#endif

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
#include "BMP.h"
#include "Line.h"
#include "Circle.h"
#include "Curve.h"
#include "Fill.h"

#define DRAWING_COLOR RGB(0,0,0)
#define POINT_COLOR RGB(255,0,0)
#define OPEN 1
#define SAVE 2
#define EXIT 3
#define WHITE_BACKGROUND_COLOR 4
#define LIGHTBLUE_BACKGROUND_COLOR 5
#define LIGHTGREEN_BACKGROUND_COLOR 6
#define LIGHTGRAY_BACKGROUND_COLOR 7
#define LINE_PARAMETRIC 8
#define LINE_DDA 9
#define LINE_MIDPOINT 10
#define CIRCLE_CARTESIAN 11
#define CIRCLE_POLAR 12
#define CIRCLE_ITERATIVE_POLAR 13
#define CIRCLE_MIDPOINT 14
#define CURVE_FIRST_DEGREE 15
#define CURVE_SECOND_DEGREE 16
#define CURVE_HERMITE 17
#define CURVE_BEZIER 18
#define CURVE_SPLINE 19
#define FILL_CONVEX 20
#define CLIP_POINT 21
#define CLIP_LINE 22

using namespace std;

LRESULT CALLBACK WindowProcedure (HWND, UINT, WPARAM, LPARAM);
void createMenuBar(HWND);
string getOpenFileName();
string getSaveFileName();
void setBackgroundColor(HWND, WORD);
void drawCircleArroundPoint(HDC hdc, POINT p);

TCHAR szClassName[ ] = _T("CodeBlocksWindowsApp");

int WINAPI WinMain (HINSTANCE hThisInstance,
                     HINSTANCE hPrevInstance,
                     LPSTR lpszArgument,
                     int nCmdShow)
{
    HWND hwnd;
    MSG messages;
    WNDCLASSEX wincl;

    wincl.hInstance = hThisInstance;
    wincl.lpszClassName = szClassName;
    wincl.lpfnWndProc = WindowProcedure;
    wincl.style = CS_DBLCLKS;
    wincl.cbSize = sizeof (WNDCLASSEX);
    wincl.hIcon = LoadIcon (NULL, IDI_APPLICATION);
    wincl.hIconSm = LoadIcon (NULL, IDI_APPLICATION);
    wincl.hCursor = LoadCursor (NULL, IDC_ARROW);
    wincl.lpszMenuName = NULL;
    wincl.cbClsExtra = 0;
    wincl.cbWndExtra = 0;
    wincl.hbrBackground =  CreateSolidBrush(RGB(211, 211, 211));

    if (!RegisterClassEx (&wincl))
        return 0;

    hwnd = CreateWindowEx (
           0,
           szClassName,
           _T("Code::Blocks Template Windows App"),
           WS_OVERLAPPEDWINDOW,
           CW_USEDEFAULT,
           CW_USEDEFAULT,
           544,
           375,
           HWND_DESKTOP,
           NULL,
           hThisInstance,
           NULL
           );

    ShowWindow (hwnd, nCmdShow);

    while (GetMessage (&messages, NULL, 0, 0))
    {
        TranslateMessage(&messages);
        DispatchMessage(&messages);
    }

    return messages.wParam;
}


LRESULT CALLBACK WindowProcedure (HWND hwnd, UINT message, WPARAM wParam, LPARAM lParam)
{
    HDC hdc = GetDC(hwnd);
    static WORD drawCommand;
    static int pointNumber = 0;
    static vector <POINT> points;
    static BMP bmp;
    static Line line;
    static Circle circle;
    static Curve curve;
    static Fill fill;

    switch (message)
    {
        case WM_CREATE:{
            createMenuBar(hwnd);
            break;
        }
        case WM_COMMAND:{
            if(LOWORD(wParam) == OPEN){
                string path = getOpenFileName();
                bmp.loadBMPFile(hdc, path.c_str());
            }
            else if(LOWORD(wParam) == SAVE){
                string path = getSaveFileName();
                bmp.saveBMPFile(hdc, path.c_str());
            }
            else if(LOWORD(wParam) == EXIT){
                exit(0);
            }
            else if(LOWORD(wParam) >= WHITE_BACKGROUND_COLOR && LOWORD(wParam) <= LIGHTGRAY_BACKGROUND_COLOR){
                setBackgroundColor(hwnd, LOWORD(wParam));
            }
            else {
                drawCommand = LOWORD(wParam);
                pointNumber = 0;
                points.clear();
            }
            break;
        }
        case WM_LBUTTONDOWN:{

            if(drawCommand){
                POINT p;
                p.x = LOWORD(lParam);
                p.y = HIWORD(lParam);
                points.push_back(p);
                drawCircleArroundPoint(hdc, points[pointNumber]);
                pointNumber++;

                if(drawCommand == LINE_PARAMETRIC && points.size() == 2){
                    line.drawLineParametric(hdc, points[0], points[1], DRAWING_COLOR);
                    points.clear();
                    pointNumber = 0;
                }
                else if(drawCommand == LINE_DDA && points.size() == 2){
                    line.drawLineDDA(hdc, points[0], points[1], DRAWING_COLOR);
                    points.clear();
                    pointNumber = 0;
                }
                else if(drawCommand == LINE_MIDPOINT && points.size() == 2){
                    line.drawLineMidpoint(hdc, points[0], points[1], DRAWING_COLOR);
                    points.clear();
                    pointNumber = 0;
                }
                else if(drawCommand == CIRCLE_CARTESIAN && points.size() == 2){
                    circle.drawCircleCartesian(hdc, points[0], points[1], DRAWING_COLOR);
                    points.clear();
                    pointNumber = 0;
                }
                else if(drawCommand == CIRCLE_POLAR && points.size() == 2){
                    circle.drawCirclePolar(hdc, points[0], points[1], DRAWING_COLOR);
                    points.clear();
                    pointNumber = 0;
                }
                else if(drawCommand == CIRCLE_ITERATIVE_POLAR && points.size() == 2){
                    circle.drawCircleIterativePolar(hdc, points[0], points[1], DRAWING_COLOR);
                    points.clear();
                    pointNumber = 0;
                }
                else if(drawCommand == CIRCLE_MIDPOINT && points.size() == 2){
                    circle.drawCircleMidpoint(hdc, points[0], points[1], DRAWING_COLOR);
                    points.clear();
                    pointNumber = 0;
                }
                else if(drawCommand == CURVE_FIRST_DEGREE && points.size() == 2){
                    curve.drawFirstDegreeCurve(hdc, points[0], points[1], DRAWING_COLOR);
                    points.clear();
                    pointNumber = 0;
                }
                else if(drawCommand == CURVE_SECOND_DEGREE && points.size() == 3){
                    curve.drawSecondDegreeCurve(hdc, points[0], points[1], points[2], DRAWING_COLOR);
                    points.clear();
                    pointNumber = 0;
                }
                else if(drawCommand == CURVE_HERMITE && points.size() == 4){
                    curve.drawHermiteCurve(hdc, points[0], points[1], points[2], points[3], DRAWING_COLOR);
                    points.clear();
                    pointNumber = 0;
                }
                else if(drawCommand == CURVE_BEZIER && points.size() == 4){
                    curve.drawBezierCurve(hdc, points[0], points[1], points[2], points[3], DRAWING_COLOR);
                    points.clear();
                    pointNumber = 0;
                }
            }
            break;
        }
        case WM_RBUTTONDOWN:{
            if(drawCommand == CURVE_SPLINE && points.size() >= 4){
                curve.drawSplineCurve(hdc, points, 0.5,DRAWING_COLOR);
                points.clear();
                pointNumber = 0;
            }
            else if(drawCommand == FILL_CONVEX && points.size() >= 3){
                for(int i = 0; i < points.size(); i++){
                    line.drawLineMidpoint(hdc, points[i], points[(i+1)%points.size()], DRAWING_COLOR);
                }
                fill.convexFill(hdc, points, DRAWING_COLOR);
                points.clear();
                pointNumber = 0;
            }
            break;
        }
        case WM_DESTROY:
            PostQuitMessage (0);
            break;
        default:
            return DefWindowProc (hwnd, message, wParam, lParam);
    }

    return 0;
}

void createMenuBar(HWND hwnd){
    HMENU hMenubar = CreateMenu();

    /* CREATE FILE MENUE */
    HMENU hFile = CreateMenu();
    AppendMenu(hMenubar, MF_POPUP, (UINT_PTR) hFile, "File");
    AppendMenu(hFile, MF_STRING, OPEN, "Open");
    AppendMenu(hFile, MF_STRING, SAVE, "Save");
    AppendMenu(hFile, MF_STRING, EXIT, "Exit");

    /* CREATE BACKGROUND MENU */
    HMENU hBackground = CreateMenu();
    AppendMenu(hMenubar, MF_POPUP, (UINT_PTR) hBackground, "Background");
    HMENU hColor = CreateMenu();
    AppendMenu(hBackground, MF_POPUP, (UINT_PTR) hColor, "Color");
    AppendMenu(hColor, MF_STRING, WHITE_BACKGROUND_COLOR, "White");
    AppendMenu(hColor, MF_STRING, LIGHTBLUE_BACKGROUND_COLOR, "Light Blue");
    AppendMenu(hColor, MF_STRING, LIGHTGREEN_BACKGROUND_COLOR, "Light Green");
    AppendMenu(hColor, MF_STRING, LIGHTGRAY_BACKGROUND_COLOR, "Light Gray");

    /* CREATE DRAW MENU */
    HMENU hDraw = CreateMenu();
    AppendMenu(hMenubar, MF_POPUP, (UINT_PTR) hDraw, "Draw");
    HMENU hLine = CreateMenu();
    AppendMenu(hDraw, MF_POPUP, (UINT_PTR) hLine, "Line");
    AppendMenu(hLine, MF_STRING, LINE_PARAMETRIC, "Parametric");
    AppendMenu(hLine, MF_STRING, LINE_DDA, "DDA");
    AppendMenu(hLine, MF_STRING, LINE_MIDPOINT, "Midpoint");
    HMENU hCircle = CreateMenu();
    AppendMenu(hDraw, MF_POPUP, (UINT_PTR) hCircle, "Circle");
    AppendMenu(hCircle, MF_STRING, CIRCLE_CARTESIAN, "Cartesian");
    AppendMenu(hCircle, MF_STRING, CIRCLE_POLAR, "Polar");
    AppendMenu(hCircle, MF_STRING, CIRCLE_ITERATIVE_POLAR, "Iterative Polar");
    AppendMenu(hCircle, MF_STRING, CIRCLE_MIDPOINT, "Midpoint");
    HMENU hCurve = CreateMenu();
    AppendMenu(hDraw, MF_POPUP, (UINT_PTR) hCurve, "Curve");
    AppendMenu(hCurve, MF_STRING, CURVE_FIRST_DEGREE, "First Degree");
    AppendMenu(hCurve, MF_STRING, CURVE_SECOND_DEGREE, "Second Degree");
    AppendMenu(hCurve, MF_STRING, CURVE_HERMITE, "Hermite");
    AppendMenu(hCurve, MF_STRING, CURVE_BEZIER, "Bezier");
    AppendMenu(hCurve, MF_STRING, CURVE_SPLINE, "Spline");

    /* CREATE MENU FILL */

    HMENU hFill = CreateMenu();
    AppendMenu(hMenubar, MF_POPUP, (UINT_PTR) hFill, "Fill");
    AppendMenu(hFill, MF_STRING, FILL_CONVEX, "Convex Filling");

    /* CREATE MENU CLIP */

    HMENU hClip = CreateMenu();
    AppendMenu(hMenubar, MF_POPUP, (UINT_PTR) hClip, "Clip");
    AppendMenu(hClip, MF_STRING, CLIP_POINT, "Point Clipping");
    AppendMenu(hClip, MF_STRING, CLIP_LINE, "Line Clipping");

    /* CREATE HELP MENU */
    HMENU hHelp = CreateMenu();
    AppendMenu(hMenubar, MF_POPUP, (UINT_PTR) hHelp, "Help");

    SetMenu(hwnd, hMenubar);
}

string getOpenFileName()
{
    char filename[ MAX_PATH ];

    OPENFILENAME ofn;
    ZeroMemory( &filename, sizeof( filename ) );
    ZeroMemory( &ofn,      sizeof( ofn ) );
    ofn.lStructSize  = sizeof( ofn );
    ofn.hwndOwner    = NULL;
    ofn.lpstrFilter  = "Bmp Files\0*.bmp\0Any File\0*.*\0";
    ofn.lpstrFile    = filename;
    ofn.nMaxFile     = MAX_PATH;
    ofn.lpstrTitle   = "Select a File, yo!";
    ofn.Flags        = OFN_FILEMUSTEXIST;
    if (GetOpenFileNameA(&ofn))
        return string(filename);
    else
        return "";

}

string getSaveFileName()
{
    char filename[ MAX_PATH ];

    OPENFILENAME ofn;
    ZeroMemory( &filename, sizeof( filename ) );
    ZeroMemory( &ofn,      sizeof( ofn ) );
    ofn.lStructSize  = sizeof( ofn );
    ofn.hwndOwner    = NULL;
    ofn.lpstrFilter  = "Bmp Files\0*.bmp\0Any File\0*.*\0";
    ofn.lpstrFile    = filename;
    ofn.nMaxFile     = MAX_PATH;
    ofn.lpstrTitle   = "Select a File, yo!";
    ofn.Flags        = OFN_FILEMUSTEXIST;
    if (GetSaveFileNameA(&ofn))
        return string(filename);
    else
        return "";
}


void setBackgroundColor(HWND hwnd, WORD BACKGROUND_COLOR)
{
    COLORREF color;
    switch (BACKGROUND_COLOR){
        case WHITE_BACKGROUND_COLOR:
            color = RGB(255, 255, 255);
            break;
        case LIGHTBLUE_BACKGROUND_COLOR:
            color = RGB(173, 216, 230);
            break;
        case LIGHTGREEN_BACKGROUND_COLOR:
            color = RGB(144, 238, 144);
            break;
        case LIGHTGRAY_BACKGROUND_COLOR:
            color = RGB(211, 211, 211);
            break;
    }

    HBRUSH hBrush = CreateSolidBrush(color);
    SetClassLongPtr(hwnd, GCLP_HBRBACKGROUND, HandleToLong(hBrush));
    InvalidateRect(hwnd, NULL, TRUE);
}

void drawCircleArroundPoint(HDC hdc, POINT pc){
    Circle circle;
    POINT p;
    p.x = pc.x+2;
    p.y = pc.y+2;
    circle.drawCircleIterativePolar(hdc, pc, p, POINT_COLOR);
}


