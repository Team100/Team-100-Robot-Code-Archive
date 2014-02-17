/* Copyright, Nicholas Carlini. All rights reserved. */

#ifndef CONFIG_LOADED
#define CONFIG_LOADED 1

/**
 * A Key/Value pair with a pointer to the next one in the list.
 */
typedef struct kvp {
  char* key;
  char* value;
  struct kvp* next;
} KVPair;

/**
 * A configuration node with a linked list of Key/Value pairs and child nodes.
 * The key is the name defined when it is created. It's children are other
 *  configuration nodes which are defined under it.
 * The object pointer is the data that will be stored in it within the actual
 *  use of the configuration nodes.
 */
typedef struct conf {
  char* key;
  KVPair* values;
  struct conf* children;
  struct conf* next;
  struct conf* parent;
  int objptr;
} Config;

/**
 * See function definition in config.c for the below three definitions.
 */
Config* configure();

char* config_lookup_key(Config* conf, char* key);

Config* config_lookup_section(Config* conf, char* name);

void printConf(Config* conf, int indent);

#endif
