/* Copyright, Nicholas Carlini. All rights reserved. */

#ifndef RENDER_LOADED
#define RENDER_LOADED 1

#include "screen.h"
#include "config.h"

void render_checkbox(ScreenObject* sc);

void render_radio(ScreenObject* sc);

void render_rating(ScreenObject* sc);

void render_updown(ScreenObject* sc);

void render_dropdown(ScreenObject* sc);

void render_counter(ScreenObject* sc);

void render_timer(ScreenObject* sc);

void render_finish(ScreenObject* sc);

void render_tabs(Config* conf, ScreenObject* tab);

#endif