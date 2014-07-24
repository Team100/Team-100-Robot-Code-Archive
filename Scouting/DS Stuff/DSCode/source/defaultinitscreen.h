#ifndef DEFAULTINITSCREEN
#define DEFAULTINITSCREEN 1

#include "screen.h"


extern int dmatchNumber;
extern ScreenObject* matchNumber;
extern int dteamNumber;

void resetInitial();

void createInitial();

void setFromFile();

void fn_matchnum(ScreenObject* sc, int x, int y);

void fn_loadfile(ScreenObject* sc, int x, int y);

void fn_teamnum(ScreenObject* sc, int x, int y);

void fn_dsnum(ScreenObject* sc, int x, int y);

#endif