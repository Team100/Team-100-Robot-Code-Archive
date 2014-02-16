/* Copyright, Nicholas Carlini. All rights reserved. */

#include <stdio.h>
#include <stdlib.h>
#include <nds.h>
#include <fat.h>

#include "render.h"
#include "screen.h"
#include "config.h"
#include "fns.h"
#include "main.h"
#include "defaultinitscreen.h"

/**
 * The void function. Does nothing.
 */
void fn_void(ScreenObject* sc, int x, int y) {
  //printf("void (%d,%d)\n", x, y);
}

/**
 * The function for a checkbox.
 * If it was unchecked, then check it.
 * If it was checked, make it unchecked.
 */
void fn_checkbox(ScreenObject* sc, int x, int y) {
  *sc->state = !*sc->state;
	render_checkbox(sc);
}

void fn_radio(ScreenObject* sc, int x, int y) {
	Config* conf = (Config*)(sc->conf); /* Int? Nope! Pointer to config struct. */
	int i = 0;
	while (i < 10) { /* At most 10 options for a radio. */
		char xstr[] = {'x', '1'+i, 0};
		char ystr[] = {'y', '1'+i, 0};
		if (config_lookup_key(conf, xstr) == NULL || config_lookup_key(conf, ystr) == NULL) {
			break;
		}
		int tx = atoi(config_lookup_key(conf, xstr));
		int ty = atoi(config_lookup_key(conf, ystr));
		int valx = tx - 2*(x/16);
		int valy = ty - 2*(y/16);
		if (valx*valx <= 1 && valy*valy <= 1) {
			*sc->state = 1<<i;
		}
		i++;
	}
	render_radio(sc);
}

void fn_rating(ScreenObject* sc, int x, int y) {
  int whichTouched = (x/8-sc->x)/2;
	*sc->state = 1<<whichTouched;
	render_rating(sc);
}

void fn_updown(ScreenObject* sc, int x, int y) {
	int val = *sc->state;
	int whichDigit = sc->conf-1-((x/8)-sc->x)/2;
	while (whichDigit-- > 0) {
		val /= 10;
	}
	
	val = val % 10; /* Get the current digit. */
	int origval = val;
  if (y-chr2px(sc->y) < 4) {
    val++;
  } else {
    val--;
  }
	val = val%10; /* Mod by 10 to cut off overflow. */
	if (val < 0) val += 10; /* Negative -> positive */
	
	int adjustBy = val-origval;
	
	whichDigit = sc->conf-1-((x/8)-sc->x)/2;
	while (whichDigit-- > 0) {
		adjustBy *= 10;
	}
	*sc->state += adjustBy;
	
	render_updown(sc);
}

void fn_dropdown(ScreenObject* sc, int x, int y) {
	if (*sc->state < 0) { /* Dropdown is currently expanded. */
		if ((y/8-sc->y) > 0)
			*sc->state = (y/8-sc->y);
		else
			*sc->state = -*sc->state; /* When you click the current selected one, pick what you had last */
		drawScreen(screen);
	} else {
		*sc->state = -*sc->state;
		render_dropdown(sc);
	}
}

void fn_counter(ScreenObject* sc, int x, int y) {
	if (x-sc->x*8 >= 3*8) {
		*sc->state -= 1;
	} else {
		*sc->state += 1;
	}
	if (*sc->state < 0) *sc->state = 0;
	if (*sc->state > 999) *sc->state = 999;
	render_counter(sc);
}

void fn_timer(ScreenObject* sc, int x, int y) {
	float curTime = (float)((TIMER1_DATA*(1<<16))+TIMER0_DATA)/32.7285;
	if (*sc->state >= 0) {
		*sc->state = -(int)curTime;
	} else {
		*sc->state = (int)(curTime + (float)(*sc->state));
	}
	render_timer(sc);
}

void fn_finish(ScreenObject* sc, int x, int y) {
	FILE* fp = fopen(config_lookup_key(config_lookup_section(conf, "Configuration"), "Foo"), "ab");
	printf("Print to %x, %s.\n", (int)config_lookup_section(conf, "Configuration"), config_lookup_key(config_lookup_section(conf, "Configuration"), "Foo"));
	
	consoleClear();
	int screen = 0;
	while (screen < 4) {
		char chr[] = {'1'+screen, 0}; // Create a null terminated string of length one. 
		Config* current = config_lookup_section(conf, config_lookup_key(config_lookup_section(conf, "Screens"), chr));
		Config* nodes = current->children;
		while (nodes != NULL) {
			if (strcmp(nodes->key, "PlainText") == 0) {
			} else if (strcmp(nodes->key, "Finish") == 0) {
			} else if (strcmp(nodes->key, "ShowScoutingInitial") == 0) {
				fprintf(fp, "(matchnum, %d), ", dmatchNumber);
				fprintf(fp, "(teamnum, %d), ", dteamNumber);
				resetInitial();
			} else {
				fprintf(fp, "(%s, %d), ", config_lookup_key(nodes, "name"), nodes->objptr);
				nodes->objptr = 0;
			}
			nodes = nodes->next;
		}
		screen++;
	}
	tab->state = 0;
	
	fprintf(fp, "\r\n");
	fclose(fp);
	click(0, 0);
}

void fn_tab(ScreenObject* sc, int x, int y) {
  /* 0 means 0<=x<=63, 1 means 64<=x<=127, etc. */
  sc->state = (int*)(x/64);

  //printf("Now on tab %d\n", (int)sc->state);
  drawScreen(screen);
}
