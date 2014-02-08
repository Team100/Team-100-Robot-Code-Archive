#include <nds.h>

#include <fat.h>
#include <stdio.h>
#include <stdlib.h>

#include "defaultinitscreen.h"
#include "screen.h"
#include "graphics.h"
#include "config.h"
#include "fns.h"
#include "render.h"
#include "main.h"

int setup = 0;

int getDsNumber() {
  int num;
  FILE* fp = fopen("dsnumber.txt", "r");
		if (fp != NULL) {
			fscanf(fp, "%d", &num);
		}
		return num;
}

ScreenObject* matchNumber;
int dmatchNumber = 1;
ScreenObject* loadFile;
int dloadFile = 1;
ScreenObject* teamNumber;
int dteamNumber = 0;
ScreenObject* dsNumber;
int ddsNumber = 0;

void resetInitial() {
	dmatchNumber++;
	setFromFile();
}

void redraw() {
	iprintf("\x1b[%d;%dH%s", 6, 0, "Match Number:");
	render_updown(matchNumber);
	iprintf("\x1b[%d;%dH%s", 10, 0, "Load From File:");
	render_checkbox(loadFile);
	iprintf("\x1b[%d;%dH%s", 14, 0, "Team Number:");
	render_updown(teamNumber);
	//char tempString;
	//snprintf(&tempString, 3, "%d", 4);
	//iprintf("\x1b[%d;%dH%s", 20, 0, tempString);
	//render_updown(dsNumber);
}	

/**
 * Create the initial screen with objects tied to each other.
 */
void createInitial() {
	if (!setup) {
		matchNumber = createObjectNoFree();
		matchNumber->x = 14;
		matchNumber->y = 6;
		matchNumber->fn = fn_matchnum;
		matchNumber->conf = 3;
		matchNumber->state = &dmatchNumber;
		
		loadFile = createObjectNoFree();
		loadFile->x = 16;
		loadFile->y = 10;
		loadFile->fn = fn_loadfile;
		loadFile->state = &dloadFile;
		
		teamNumber = createObjectNoFree();
		teamNumber->x = 14;
		teamNumber->y = 14;
		teamNumber->fn = fn_teamnum;
		teamNumber->conf = 4;
		teamNumber->state = &dteamNumber;
		
		dsNumber = createObjectNoFree();
		dsNumber->x = 12;
		dsNumber->y = 20;
		dsNumber->fn = fn_dsnum;
		dsNumber->conf = 1;
		dsNumber->state = &ddsNumber;
		setup = 1;
	}
	setFromFile();
	
	redraw();
}


void setFromFile() {
	ddsNumber = getDsNumber();
	if (ddsNumber > 6) ddsNumber = 6;
	if (ddsNumber < 1) ddsNumber = 1;
	if (dmatchNumber < 1) dmatchNumber = 1;
	if (dloadFile) {
		int num = -1;
		int robot[] = {0,0,0,0,0,0};
		
		FILE* fp = fopen(config_lookup_key(config_lookup_section(conf, "Configuration"), "InputFile"), "r");
		if (fp != NULL) {
			int result = 0;
			while (num != dmatchNumber && (result != EOF)) {
				result = fscanf (fp, "%d,%d,%d,%d,%d,%d,%d\n\r",
					&num,
					&robot[0],
					&robot[1],
					&robot[2],
					&robot[3],
					&robot[4],
					&robot[5]);
			}

			consoleSelect(&bottomScreen);
			
			fclose(fp);
			
			dteamNumber = robot[ddsNumber-1];
		}
	}
	redraw();
}

void fn_matchnum(ScreenObject* sc, int x, int y) {
	fn_updown(sc, x, y);
	setFromFile();
}

void fn_loadfile(ScreenObject* sc, int x, int y) {
	fn_checkbox(sc, x, y);
	setFromFile();
}

void fn_teamnum(ScreenObject* sc, int x, int y) {
	fn_updown(sc, x, y);
	setFromFile();
}

void fn_dsnum(ScreenObject* sc, int x, int y) {
	fn_updown(sc, x, y);
	setFromFile();
}