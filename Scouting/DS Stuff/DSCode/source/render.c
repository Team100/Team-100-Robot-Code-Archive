/* Copyright, Nicholas Carlini. All rights reserved. */

#include <stdio.h>
#include <stdlib.h>
#include <nds.h>

#include "render.h"
#include "graphics.h"
#include "screen.h"
#include "config.h"
#include "main.h"


void render_checkbox(ScreenObject* sc) {
	renderControl(bgBot, 8*sc->x, 8*sc->y, (*sc->state!=0), &sprites[SC_BOX]);
  registerFunction(chr2px(sc->x), chr2px(sc->y), chr2px(2), chr2px(2), sc);
}


void render_updown_single(int x, int y, int state) {
	renderControl(bgBot, 8*x-4, 8*(y-1), 0, &sprites[SC_UP]);
	iprintf("\x1b[%d;%dH%d", y, x, state);
	renderControl(bgBot, 8*x-4, 8*(y+1), 0, &sprites[SC_DOWN]);
}

void render_updown(ScreenObject* sc) {
	int state = *(sc->state);
	int conf = sc->conf;
	int x = sc->x+(conf-1)*2;
	int j;
	//printf("%d %d %d %d\n", sc->x, sc->y, state, conf);
	for (j = 0; j < conf; ++j) {
		//printf("\x1b[%d;%dH%d", 5, 5, j);
		render_updown_single(x-j*2, sc->y, state%10);
		registerFunction(chr2px(x-j*2), chr2px(sc->y-1), chr2px(1), chr2px(1), sc);
		registerFunction(chr2px(x-j*2), chr2px(sc->y+1), chr2px(1), chr2px(1), sc);
		state /= 10;
	}
}

void render_radio(ScreenObject* sc) {
	Config* conf = (Config*)(sc->conf); /* Int? Nope! Pointer to config struct. */
	int i = 0;
	while (i < 10) { /* At most 10 options for a radio. */
		char xstr[] = {'x', '1'+i, 0};
		char ystr[] = {'y', '1'+i, 0};
		if (config_lookup_key(conf, xstr) == NULL || config_lookup_key(conf, ystr) == NULL) {
			break;
		}
		int x = atoi(config_lookup_key(conf, xstr));
		int y = atoi(config_lookup_key(conf, ystr));
		
		renderControl(bgBot, 8*x, 8*y, ((1<<i)&(*sc->state))!=0, &sprites[SC_BOX]);
		
		registerFunction(chr2px(x), chr2px(y), chr2px(2), chr2px(2), sc);
		i++;
	}
}

void render_rating(ScreenObject* sc) {
	int i = 0;
	while (i < 9) {
		renderControl(bgBot, 8*(sc->x+2*i), 8*sc->y, ((1<<i)&(*sc->state))!=0, &sprites[SC_RATING]);
		registerFunction(chr2px(sc->x+2*i), chr2px(sc->y), chr2px(2), chr2px(2), sc);
		i++;
	}
}


void render_dropdown(ScreenObject* sc) {
	Config* conf = (Config*)(sc->conf);
	int max = 0;
	int i = 0;
	while (i < 10) {
		char option[] = {'1'+i, 0};
		if (config_lookup_key(conf, option) != NULL) {
			int len = strlen(config_lookup_key(conf, option));
			max = max > len ? max : len;
		}
		i++;
	}
	if (*sc->state == 0) *sc->state = 1; /* Default is 0, but we need it to be 1 */
	int num = *sc->state;
	if (num < 0) { /* Draw the expanded dropdown. */
		i = 0;
		while (i < 10) {
			char option[] = {'1'+i, 0};
			if (config_lookup_key(conf, option) != NULL) {
				iprintf("\x1b[%d;%dH%s", sc->y+i+1, sc->x, config_lookup_key(conf, option));
				registerFunction(chr2px(sc->x), chr2px(sc->y+i+1), chr2px(max), chr2px(1), sc);
				renderColorRect(bgBot,sc->x*8-2,(sc->y+i+1)*8,max*8+4,7,RGB15(14,14,14) | BIT(15));
				renderColorRect(bgBot,sc->x*8-1,(sc->y+i+1)*8-1,max*8+2,9,RGB15(14,14,14) | BIT(15));
				renderColorRect(bgBot,sc->x*8-1,(sc->y+i+1)*8,max*8+2,7,RGB15(6,6,6) | BIT(15));
			} else {
				break;
			}
			i++;
		}
		num = -num;
	}
	registerFunction(chr2px(sc->x), chr2px(sc->y), chr2px(max), chr2px(1), sc);
	renderColorRect(bgBot,sc->x*8-2,sc->y*8,max*8+4,7,RGB15(5,31,5) | BIT(15));
	renderColorRect(bgBot,sc->x*8-1,sc->y*8-1,max*8+2,9,RGB15(5,31,5) | BIT(15));
	renderColorRect(bgBot,sc->x*8-1,sc->y*8,max*8+2,7,RGB15(3,18,3) | BIT(15));
	char option[] = {'0'+num, 0};
	iprintf("\x1b[%d;%dH%s", sc->y, sc->x, config_lookup_key(conf, option));
}

void render_counter(ScreenObject* sc) {
	iprintf("\x1b[%d;%dH%03d-", sc->y, sc->x, *sc->state);
	renderColorRect(bgBot,sc->x*8-2,sc->y*8,4*8+2,8,RGB15(15,15,15) | BIT(15));
	renderColorRect(bgBot,sc->x*8-1,sc->y*8-1,4*8,10,RGB15(15,15,15) | BIT(15));
	renderColorRect(bgBot,sc->x*8-1,sc->y*8,3*8,8,RGB15(8,8,8) | BIT(15));
	renderColorRect(bgBot,(sc->x+3)*8,sc->y*8,7,8,RGB15(8,8,8) | BIT(15));
	registerFunction(chr2px(sc->x), chr2px(sc->y), chr2px(4), chr2px(1), sc);
}

void render_timer(ScreenObject* sc) {
	iprintf("\x1b[%d;%dH      ", sc->y, sc->x);
	if (*sc->state >= 0) {
		iprintf("\x1b[%d;%dH%d", sc->y, sc->x, *sc->state);
	} else {
		float curTime = (float)((TIMER1_DATA*(1<<16))+TIMER0_DATA)/32.7285;
		iprintf("\x1b[%d;%dH%d", sc->y, sc->x, (int)curTime+*sc->state);
	}
	registerFunction(chr2px(sc->x), chr2px(sc->y), chr2px(4), chr2px(1), sc);
}

void render_finish(ScreenObject* sc) {
	renderControl(bgBot, 8*sc->x, 8*sc->y, 0, &sprites[SC_FINISHED]);
  registerFunction(chr2px(sc->x), chr2px(sc->y), chr2px(8), chr2px(4), sc);
}

void render_tabs(Config* conf, ScreenObject* tab) {
	iprintf("\x1b[%d;%dH%s", 1, 2, 
		config_lookup_key(config_lookup_section(conf, config_lookup_key(config_lookup_section(conf, "Screens"), "1")), "name"));
	iprintf("\x1b[%d;%dH%s", 1, 10, 
		config_lookup_key(config_lookup_section(conf, config_lookup_key(config_lookup_section(conf, "Screens"), "2")), "name"));
	iprintf("\x1b[%d;%dH%s", 1, 18, 
		config_lookup_key(config_lookup_section(conf, config_lookup_key(config_lookup_section(conf, "Screens"), "3")), "name"));
	iprintf("\x1b[%d;%dH%s", 1, 26, 
		config_lookup_key(config_lookup_section(conf, config_lookup_key(config_lookup_section(conf, "Screens"), "4")), "name"));
	
	renderControl(bgBot, 0, 0, (int)tab->state==0, &sprites[SC_TAB]);
	renderControl(bgBot, 64, 0, (int)tab->state==1, &sprites[SC_TAB]);
	renderControl(bgBot, 128, 0, (int)tab->state==2, &sprites[SC_TAB]);
	renderControl(bgBot, 192, 0, (int)tab->state==3, &sprites[SC_TAB]);
	
  registerFunction(0, 0, 256, 32, tab);
}