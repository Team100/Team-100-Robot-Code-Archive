/* Copyright, Nicholas Carlini. All rights reserved. */

#include <nds.h>

#include <fat.h>
#include <stdio.h>
#include <stdlib.h>

#include "main.h"
#include "screen.h"
#include "graphics.h"


ScreenObject** screen;

int bgBot;
int bgTop;

PrintConsole topScreen;
PrintConsole bottomScreen;

/**
 * The main function loop. Repeat waiting for clicks.
 */
void mainLoop() {
  drawScreen(screen);
	touchPosition touchXY;
	int clickedLast = 0;
	while(1) {
		swiWaitForVBlank();
		
		updateNeeded();
		
		touchRead(&touchXY);
		if (touchXY.px && touchXY.py) {
			if (!clickedLast) {
				click(touchXY.px, touchXY.py);
			}
			clickedLast = 1;
		} else {
			clickedLast = 0;
		}
		
		scanKeys();
		
		int i = 0;
		int pressed = keysDown()&0xFFF;
		
		while (i < 12) { /* Loop through all the keys pressed this time */
			if ((pressed&(1<<i))) {
				keypress(i);
			}
			i++;
		}
	}
}


bool repaint;

//---------------------------------------------------------------------------------
void timerCallBack (void) {
//---------------------------------------------------------------------------------
	repaint = true;
}


int main(void) {	
  Config* conf;
	
	powerOn(POWER_ALL);
	initVideo (&topScreen, &bottomScreen, &bgTop, &bgBot);
	fatInitDefault();
	conf = configure();
	TIMER0_CR = TIMER_ENABLE|TIMER_DIV_1024;
	TIMER1_CR = TIMER_ENABLE|TIMER_CASCADE;
	
  screen = createScreen(conf);
  mainLoop();
  return 0;
}
