/* Copyright, Nicholas Carlini. All rights reserved. */

#define chr2px(a) ((a)*8)
#define chr2py(a) ((a)*7)

#ifndef SCREEN_LOADED
#define SCREEN_LOADED 1

#include "config.h"

typedef struct screenstruct {
  void (*fn)(struct screenstruct*, int, int);
  int* state;
	int conf; /* Used for any information this object may need. */
  int x;
  int y;
} ScreenObject;

extern ScreenObject* tab;

ScreenObject** createScreen();

ScreenObject* createObject();

ScreenObject* createObjectNoFree();

void updateNeeded();

void keypress(int key);

void click(int x, int y);

void drawScreen();

void Vblank();

void registerFunction(int x, int y, int width, int height, ScreenObject* obj);

extern Config *conf;

#endif