#include "BMP.h"
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

using namespace std;

BMP::BMP()
{

}

void BMP:: saveBMPFile (HDC hdc, LPCSTR path){
    HWND hwnd = WindowFromDC(hdc);
    RECT rect;
    GetClientRect(hwnd, &rect);

    ClientToScreen(hwnd, reinterpret_cast<POINT*>(&rect.left));
    ClientToScreen(hwnd, reinterpret_cast<POINT*>(&rect.right));

    int screenHeight =  rect.bottom - rect.top;
    int screenWidth = rect.right - rect.left;

    HDC hDesktop = GetDC(NULL);
    HDC hDest = CreateCompatibleDC(hdc);

    HBITMAP hbCapture = CreateCompatibleBitmap(hDesktop, screenWidth, screenHeight);
    SelectObject(hDest, hbCapture);

    BitBlt(hDest, 0,0, screenWidth, screenHeight, hDesktop, rect.left, rect.top, SRCCOPY);

    writeBMPFile(hbCapture, path);

    ReleaseDC(NULL, hDesktop);
    DeleteDC(hDest);
}

void BMP:: loadBMPFile (HDC hdc, LPCSTR bmpfile)
{
	if ((NULL == hdc) || (NULL == bmpfile)){
		return;
	}

	HANDLE hBmp = LoadImage (NULL, bmpfile, IMAGE_BITMAP, 0, 0, LR_LOADFROMFILE);

	if ( NULL == hBmp ){
		return;
	}

	HDC dcMem = CreateCompatibleDC(NULL);

	if (NULL == SelectObject(dcMem, hBmp)){
		DeleteDC (dcMem);
		return;
	}

    BITMAP bm;

	GetObject (hBmp, sizeof(bm), &bm);

	if (BitBlt(hdc, 0, 0, bm.bmWidth, bm.bmHeight, dcMem, 0, 0, SRCCOPY) == 0){
		DeleteDC(dcMem);
		return;
	}

	DeleteDC (dcMem);
}

void BMP:: writeBMPFile(HBITMAP bitmap, LPCSTR path)
{
    BITMAP bmp;
    PBITMAPINFO pbmi;
    WORD cClrBits;
    HANDLE hf;
    BITMAPFILEHEADER hdr;
    PBITMAPINFOHEADER pbih;
    LPBYTE lpBits;
    DWORD dwTotal;
    DWORD cb;
    BYTE *hp;
    DWORD dwTmp;

    if (!GetObject( bitmap, sizeof(BITMAP), (LPSTR)&bmp))
    {
        MessageBox(NULL, _T("Could not retrieve bitmap info."), _T("PRINSCREEN PROGRAM: Error"), 0);
        return;
    }

    cClrBits = (WORD)(bmp.bmPlanes * bmp.bmBitsPixel);

    if (cClrBits == 1){
        cClrBits = 1;
    }
    else if (cClrBits <= 4){
        cClrBits = 4;
    }
    else if (cClrBits <= 8){
        cClrBits = 8;
    }
    else if (cClrBits <= 16){
        cClrBits = 16;
    }
    else if (cClrBits <= 24){
        cClrBits = 24;
    }
    else{
        cClrBits = 32;
    }

    if (cClrBits != 24){
        pbmi = (PBITMAPINFO) LocalAlloc(LPTR, sizeof(BITMAPINFOHEADER) + sizeof(RGBQUAD) * (1<< cClrBits));
    }
    else{
        pbmi = (PBITMAPINFO) LocalAlloc(LPTR, sizeof(BITMAPINFOHEADER));
    }

    pbmi->bmiHeader.biSize = sizeof(BITMAPINFOHEADER);
    pbmi->bmiHeader.biWidth = bmp.bmWidth;
    pbmi->bmiHeader.biHeight = bmp.bmHeight;
    pbmi->bmiHeader.biPlanes = bmp.bmPlanes;
    pbmi->bmiHeader.biBitCount = bmp.bmBitsPixel;

    if (cClrBits < 24){
        pbmi->bmiHeader.biClrUsed = (1<<cClrBits);
    }

    pbmi->bmiHeader.biCompression = BI_RGB;

    pbmi->bmiHeader.biSizeImage = (pbmi->bmiHeader.biWidth + 7) / 8 * pbmi->bmiHeader.biHeight * cClrBits;

    pbmi->bmiHeader.biClrImportant = 0;

    pbih = (PBITMAPINFOHEADER) pbmi;
    lpBits = (LPBYTE) GlobalAlloc(GMEM_FIXED, pbih->biSizeImage);

    if (!lpBits)
    {
        MessageBox(NULL, _T("Could not allocate memory."), _T("PRINSCREEN PROGRAM: Error"), 0);
        return;
    }

    HDC hDC = CreateCompatibleDC(NULL);

    if (!GetDIBits(hDC, HBITMAP(bitmap), 0, (WORD) pbih->biHeight, lpBits, pbmi, DIB_RGB_COLORS)) // I also tested it with DIB_PAL_COLORS
    {
        MessageBox(NULL, _T("GetDIB error."), _T("PRINSCREEN PROGRAM: Error"), 0);
        return;
    }

    hf = CreateFile(path, GENERIC_READ | GENERIC_WRITE, (DWORD) 0, NULL, CREATE_ALWAYS, FILE_ATTRIBUTE_NORMAL, (HANDLE) NULL);
    if (hf == INVALID_HANDLE_VALUE)
    {
       MessageBox(NULL, _T("Could not create file for writing"), _T("Error"), 0);
       return;
    }

    hdr.bfType = 0x4d42;
    hdr.bfSize = (DWORD) (sizeof(BITMAPFILEHEADER) + pbih->biSize + pbih->biClrUsed * sizeof(RGBQUAD) + pbih->biSizeImage);
    hdr.bfReserved1 = 0;
    hdr.bfReserved2 = 0;

    hdr.bfOffBits = (DWORD) sizeof(BITMAPFILEHEADER) + pbih->biSize + pbih->biClrUsed * sizeof (RGBQUAD);

    if (!WriteFile(hf, (LPVOID) &hdr, sizeof(BITMAPFILEHEADER), (LPDWORD) &dwTmp, NULL))
    {
        MessageBox(NULL, _T("Could not write in to file (BITMAPFILEHEADER)."), _T("PRINSCREEN PROGRAM: Error"), 0);
        return;
    }

    if (!WriteFile(hf, (LPVOID) pbih, sizeof(BITMAPINFOHEADER) + pbih->biClrUsed * sizeof (RGBQUAD), (LPDWORD) &dwTmp, ( NULL)))
    {
        MessageBox(NULL, _T("Could not write in to file (BITMAPINFOHEADER and RGBQUAD)."), _T("PRINSCREEN PROGRAM: Error"), 0);
        return;
    }

    dwTotal = cb = pbih->biSizeImage;
    hp = lpBits;
    if (!WriteFile(hf, (LPSTR) hp, (int) cb, (LPDWORD) &dwTmp, NULL))
    {
        MessageBox(NULL, _T("Could not write in to file (array of color indices)."), _T("PRINSCREEN PROGRAM: Error"), 0);
        return;
    }

    if (!CloseHandle(hf))
    {
        MessageBox(NULL, _T("Could not close file."), _T("PRINSCREEN PROGRAM: Error"), 0);
        return;
    }

    GlobalFree((HGLOBAL)lpBits);
}
