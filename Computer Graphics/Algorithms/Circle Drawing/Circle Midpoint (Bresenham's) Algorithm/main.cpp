#if defined(UNICODE) && !defined(_UNICODE)
    #define _UNICODE
#elif defined(_UNICODE) && !defined(UNICODE)
    #define UNICODE
#endif

#include <tchar.h>
#include <windows.h>
#include <math.h>
#include <stdio.h>

/*  Declare Windows procedure  */
LRESULT CALLBACK WindowProcedure (HWND, UINT, WPARAM, LPARAM);

/*  Make the class name into a global variable  */
TCHAR szClassName[ ] = _T("CodeBlocksWindowsApp");

int WINAPI WinMain (HINSTANCE hThisInstance,
                     HINSTANCE hPrevInstance,
                     LPSTR lpszArgument,
                     int nCmdShow)
{
    HWND hwnd;               /* This is the handle for our window */
    MSG messages;            /* Here messages to the application are saved */
    WNDCLASSEX wincl;        /* Data structure for the windowclass */

    /* The Window structure */
    wincl.hInstance = hThisInstance;
    wincl.lpszClassName = szClassName;
    wincl.lpfnWndProc = WindowProcedure;      /* This function is called by windows */
    wincl.style = CS_DBLCLKS;                 /* Catch double-clicks */
    wincl.cbSize = sizeof (WNDCLASSEX);

    /* Use default icon and mouse-pointer */
    wincl.hIcon = LoadIcon (NULL, IDI_APPLICATION);
    wincl.hIconSm = LoadIcon (NULL, IDI_APPLICATION);
    wincl.hCursor = LoadCursor (NULL, IDC_ARROW);
    wincl.lpszMenuName = NULL;                 /* No menu */
    wincl.cbClsExtra = 0;                      /* No extra bytes after the window class */
    wincl.cbWndExtra = 0;                      /* structure or the window instance */
    /* Use Windows's default colour as the background of the window */
    wincl.hbrBackground = (HBRUSH) COLOR_BACKGROUND;

    /* Register the window class, and if it fails quit the program */
    if (!RegisterClassEx (&wincl))
        return 0;

    /* The class is registered, let's create the program*/
    hwnd = CreateWindowEx (
           0,                   /* Extended possibilites for variation */
           szClassName,         /* Classname */
           _T("Code::Blocks Template Windows App"),       /* Title Text */
           WS_OVERLAPPEDWINDOW, /* default window */
           CW_USEDEFAULT,       /* Windows decides the position */
           CW_USEDEFAULT,       /* where the window ends up on the screen */
           544,                 /* The programs width */
           375,                 /* and height in pixels */
           HWND_DESKTOP,        /* The window is a child-window to desktop */
           NULL,                /* No menu */
           hThisInstance,       /* Program Instance handler */
           NULL                 /* No Window Creation data */
           );

    /* Make the window visible on the screen */
    ShowWindow (hwnd, nCmdShow);

    /* Run the message loop. It will run until GetMessage() returns 0 */
    while (GetMessage (&messages, NULL, 0, 0))
    {
        /* Translate virtual-key messages into character messages */
        TranslateMessage(&messages);
        /* Send message to WindowProcedure */
        DispatchMessage(&messages);
    }

    /* The program return-value is 0 - The value that PostQuitMessage() gave */
    return messages.wParam;
}

void draw8Points(HDC hdc, int xc, int yc, int x, int y, COLORREF color){
    SetPixel(hdc, xc+x, yc+y, color);
    SetPixel(hdc, xc+x, yc-y, color);
    SetPixel(hdc, xc-x, yc+y, color);
    SetPixel(hdc, xc-x, yc-y, color);
    SetPixel(hdc, xc+y, yc+x, color);
    SetPixel(hdc, xc+y, yc-x, color);
    SetPixel(hdc, xc-y, yc+x, color);
    SetPixel(hdc, xc-y, yc-x, color);
}

void drawCircle(HDC hdc, int xc, int yc, int r, COLORREF color) {
    int x = 0, y = r;
    draw8Points(hdc, xc, yc, x, y, color);
    double di = 5.0/4.0 - r;
    int d1i = 1, d2i = 1 - (2 * r);
    while(x < y) {
        if ( di < 0 ) {
            x++;
            d1i += 2;
            d2i += 2;
            di += d1i;
        }
        else {
            x++;
            y--;
            d1i += 2;
            d2i += 4;
            di += d2i;
        }
        draw8Points(hdc, xc, yc, x, y, color);
    }
}

/*  This function is called by the Windows function DispatchMessage()  */

LRESULT CALLBACK WindowProcedure (HWND hwnd, UINT message, WPARAM wParam, LPARAM lParam)
{
    HDC hdc;
    hdc = GetDC(hwnd);
    switch (message)                  /* handle the messages */
    {
        static int x1, y1, x2, y2, x3, y3;
        static int counter = 0;
        case WM_LBUTTONDOWN:
            if (counter == 0) {
                counter++;
                x1 = LOWORD(lParam);
                y1 = HIWORD(lParam);
            }
            else {
                counter = 0;
                x2 = LOWORD(lParam);
                y2 = HIWORD(lParam);
                double r = sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
                drawCircle(hdc, x1, y1, r, RGB(0, 0, 0));
            }
            ReleaseDC(hwnd, hdc);
            break;
        case WM_DESTROY:
            PostQuitMessage (0);       /* send a WM_QUIT to the message queue */
            break;
        default:                      /* for messages that we don't deal with */
            return DefWindowProc (hwnd, message, wParam, lParam);
    }

    return 0;
}
