/* mygraphics.h */

#ifndef MY_GRAPHICS_H
#define MY_GRAPHICS_H

#include "tab.h"
#include "auto.h"
#include "box.h"
#include "down.h"
#include "end.h"
#include "finished.h"
#include "init.h"
#include "line.h"
#include "minus.h"
#include "numbers.h"
#include "numbers2.h"
#include "rating.h"
#include "slider.h"
#include "tele.h"
#include "up.h"
#include "PCXConvert.h"

#define SCREEN_WIDTH 256
#define SCREEN_HEIGHT 192

typedef enum {
	SC_TAB,
	SC_AUTO,
	SC_BOX,
	SC_DOWN,
	SC_END,
	SC_FINISHED,
	SC_INIT,
	SC_LINE,
	SC_MINUS,
	SC_NUMBERS,
	SC_NUMBERS2,
	SC_RATING,
	SC_SLIDER,
	SC_TELE,
	SC_UP,
	SC_MAX_CTRLS
} Images;

typedef struct {
	char *name;
	int width; // in pixels
	int height; // in pixels
	int numFrames;
	const unsigned short *BitmapData;
} Sprite;

extern Sprite sprites [];
void initVideo (PrintConsole * pTopConsole, 
			    PrintConsole *pBotConsole, 
				int * pTopBg,
				int * pBotBg);
void renderColorRect (int BgID, 
					int x,
					int y,
					int width,
					int height,
					u16 color);
void renderControl (int BgID,
					int x,
					int y,
					int frame,
					Sprite *ptrCtrl);
void renderImage (int BgID,
				  int x,
				  int y,
				  sImage *pimage);
bool renderPCX (int BgID, int x, int y, char * fileName);

#endif