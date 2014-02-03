/* Copyright, Nicholas Carlini. All rights reserved. */


#include <nds.h>

#include <stdio.h>
#include <stdlib.h>

#include "screen.h"
#include "graphics.h"
#include "config.h"
#include "fns.h"
#include "render.h"
#include "main.h"
#include "defaultinitscreen.h"
#include "PCXConvert.h"

Config* conf;

int keynameToInt(char* key) {
	int keyval;
	
  if (strcmp(key, "") == 0) { return -1; // Do nothing
	} else if (strcmp(key, "a") == 0) { keyval = 1;
	} else if (strcmp(key, "b") == 0) { keyval = 2;
	} else if (strcmp(key, "select") == 0) { keyval = 3;
	} else if (strcmp(key, "start") == 0) { keyval = 4;
	} else if (strcmp(key, "right") == 0) { keyval = 5;
	} else if (strcmp(key, "left") == 0) { keyval = 6;
	} else if (strcmp(key, "up") == 0) { keyval = 7;
	} else if (strcmp(key, "down") == 0) { keyval = 8;
	} else if (strcmp(key, "r") == 0) { keyval = 9;
	} else if (strcmp(key, "l") == 0) { keyval = 10;
	} else if (strcmp(key, "x") == 0) { keyval = 11;
	} else if (strcmp(key, "y") == 0) { keyval = 12;
	} else { return -1; }
	return keyval-1;
}

int buttonToLocation[12];


void keypress(int key) {
	//printf("Pressed %d %d\n", key, buttonToLocation[key]);
	
	if (buttonToLocation[key] != -1)
		click(buttonToLocation[key]%256, buttonToLocation[key]/256);
}


/**
 * Click on a given square. 
 * Call the function at the given coordinate.
 */
void click(int x, int y) {
  //printf("Click %d %d\n", x, y);
  screen[x/8+y/8*256/8]->fn(screen[y/8*256/8+x/8], x, y);
}

typedef struct malloclist {
	ScreenObject* obj;
	struct malloclist* next;
} AllocList;

AllocList* alloclist = NULL;

ScreenObject* createObject() {
  ScreenObject* res = (ScreenObject*)malloc(sizeof(ScreenObject));
  res->fn = fn_void;
  res->state = NULL;
  res->x = 0;
  res->y = 0;
	
	AllocList* next = (AllocList*)malloc(sizeof(AllocList));
	next->obj = res;
	next->next = alloclist;
	alloclist = next;
	
  return res;
}

ScreenObject* createObjectNoFree() {
  ScreenObject* res = (ScreenObject*)malloc(sizeof(ScreenObject));
  res->fn = fn_void;
  res->state = NULL;
	res->conf = 0;
  res->x = 0;
  res->y = 0;
	
  return res;
}

void updateNeeded() {
	AllocList* cur = alloclist;
	while (cur != NULL) {
		ScreenObject* obj = cur->obj;
		if (obj->fn == fn_timer) {
			render_timer(obj);
		}
		cur = cur->next;
	}
}

void freeAll() {
	AllocList* ptr = alloclist;
	
	while (ptr != NULL) {
		AllocList* cur = ptr;
		ptr = cur->next;
		
		free(cur->obj);
		free(cur);
	}
	alloclist = NULL;
}

ScreenObject* voidObject;
ScreenObject* tab;

void clearScreen(ScreenObject** screen) {
  int i = 0;
  while (i < 192*256/64) {
    screen[i] = voidObject;
    i = i + 1;
  }
	i = 0;
	while (i < 12) {
		buttonToLocation[i] = -1;
		i++;
	}
	renderColorRect(bgBot,0,0,256,192,(unsigned short)0);
}

ScreenObject** createScreen(Config* c) {
  conf = c;

  ScreenObject** res = (ScreenObject**)malloc(sizeof(ScreenObject*)*(192*256/64));
  voidObject = createObjectNoFree();
  clearScreen(res);

  tab = createObjectNoFree();
  tab->fn = fn_tab;
  tab->x = 0;
  tab->y = 0;
	
	initVideo (&topScreen, &bottomScreen, &bgTop, &bgBot);
	
	consoleSelect(&topScreen);
	iprintf("\x1b[%d;%dH%s", 22, 0, "Team 100 Scouting Software");
	iprintf("\x1b[%d;%dH%s%s%s%s", 23, 5, "Built ", __TIME__, ", ", __DATE__);
	
	consoleSelect(&bottomScreen);
	
	alloclist = NULL;

  return res;
}

void registerFunction(int x, int y, int width, int height, ScreenObject* obj) {
  int i = 0;
  while (i < width) {
    int j = 0;
    while (j < height) {
      screen[(y+j)/8*256/8+(x+i)/8] = obj;
      ++j;
    }
    ++i;
  }
}

void declareTouch(char* key, int x, int y) {
	int keyval = keynameToInt(key);
	if (keyval != -1)
		buttonToLocation[keyval] = (y)*256+(x);
}

void drawScreen() {
	freeAll();
	consoleClear();
  int screen_mode = (int)tab->state;
  clearScreen(screen);
  render_tabs(conf, tab);
	

	/* Test File Read and .pcx file rendering */
	int rendered;
	printf(" ");
	
	char str[30]; //Big enough. Max size of 12+number of characters in 2 billion+minus sign.
	
	renderColorRect(bgTop,0,0,256,192,(unsigned short)0);
	if (screen_mode == 0) {
		snprintf(str, 30, "logo.pcx");
	} else {
		snprintf(str, 30, "Images/%d.pcx", dteamNumber);
	}
	rendered = renderPCX (bgTop, 0, 0, str);
	printf(" ");
	consoleSelect(&topScreen);
	consoleClear();
	if (!rendered) {
		iprintf("\x1b[%d;%dH%s%s%s", 15, 0, "PCX Logo file load failure\nImage ", str, " failed.");
	}
	if (screen_mode == 0)
	{
		iprintf("\x1b[%d;%dH%s", 22, 0, "Team 100 Scouting Software");
		iprintf("\x1b[%d;%dH%s%s%s%s", 23, 5, "Built ", __TIME__, ", ", __DATE__);
	}
	else
	{
		iprintf("\x1b[%d;%dH%s",10,26,"Team:");
		iprintf("\x1b[%d;%dH%4d",12,27, dteamNumber);
	}
	consoleSelect(&bottomScreen);
	
	
  char chr[] = {'1'+screen_mode, 0}; /* Create a null terminated string of length one. */
  Config* current = config_lookup_section(conf, config_lookup_key(config_lookup_section(conf, "Screens"), chr));
  Config* nodes = current->children;
  while (nodes != NULL) {
    int x = 0;
		if (config_lookup_key(nodes, "x")) x = atoi(config_lookup_key(nodes, "x"));
    int y = 0;
		if (config_lookup_key(nodes, "y")) y = atoi(config_lookup_key(nodes, "y"));
		
    ScreenObject* newObj = createObject();
    newObj->x = x;
    newObj->y = y;
    newObj->state = &(nodes->objptr);
		
    if (strcmp(nodes->key, "PlainText") == 0) {
			iprintf("\x1b[%d;%dH%s", y, x, config_lookup_key(nodes, "value"));
    } else if (strcmp(nodes->key, "Checkbox") == 0) {
      newObj->fn = fn_checkbox;
			declareTouch(config_lookup_key(nodes, "toggle"), chr2px(x), chr2px(y));
			render_checkbox(newObj);
    } else if (strcmp(nodes->key, "Radio") == 0) {
			newObj->conf = (int)nodes;
			newObj->fn = fn_radio;
			render_radio(newObj);
    } else if (strcmp(nodes->key, "UpDown") == 0) {
      newObj->fn = fn_updown;
			int width = 1;
			if (config_lookup_key(nodes, "width") != NULL) {
				width = atoi(config_lookup_key(nodes, "width"));
			}
			newObj->conf = width;
			render_updown(newObj);
    } else if (strcmp(nodes->key, "DropDown") == 0) {
			newObj->fn = fn_dropdown;
			newObj->conf = (int)nodes;
			render_dropdown(newObj);
		} else if (strcmp(nodes->key, "Counter") == 0) {
			newObj->fn = fn_counter;
			declareTouch(config_lookup_key(nodes, "increment"), chr2px(x), chr2px(y));
			declareTouch(config_lookup_key(nodes, "decrement"), chr2px(x), chr2px(y));
			render_counter(newObj);
		} else if (strcmp(nodes->key, "Rating") == 0) {
			newObj->fn = fn_rating;
			render_rating(newObj);
		} else if (strcmp(nodes->key, "Timer") == 0) {
			newObj->fn = fn_timer;
			declareTouch(config_lookup_key(nodes, "press"), chr2px(x), chr2px(y));
			render_timer(newObj);
		} else if (strcmp(nodes->key, "Finish") == 0) {
			newObj->fn = fn_finish;
			render_finish(newObj);
		}
		
		if (strcmp(nodes->key, "ShowScoutingInitial") == 0) {
			createInitial();
		}
    nodes = nodes->next;
  }
}
