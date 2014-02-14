/* Copyright, Nicholas Carlini. All rights reserved. */

/** Read values from config file.
  *
  * A @c Config describes screens and questions to display on a Nintendo DS.
  * A @c Config contains a sequence of sections. Sections contain either nested sections or key/value pairs.
  *
  * USAGE:
  * Call @c configure to read a file and return the @c Config* it represents.
  * Call @c config_lookup_section to lookup the section for a name.
  * Call @c config_lookup_key to lookup the value for a name.
  * 
  * SYNTAmX:
  *
  * EXAMPLE:
  * For example:
  *     Section1
  *         Section 1A
  *             key1: value1
  *             key2: value2
  *         Section 1B
  *             key3: value1
  *     Section2
  *         Section 2A
  *             key4: value1
  *         Section 2B
  *             key5: value1
  *             key6: value2
  *    ...
  *
**/

#include <stdio.h>
#include <stdlib.h>
#include <nds.h>

#include "config.h"
#include "parse.h"
#include "lex.h"


char*  result = "\
Configuration\n\
	InputFile: DS.txt # The input file of match numbers and teams playing.\n\
	Foo: file.out # The file to put the output of the scouting.\n\
\n\
Screens\n\
	1:InitialScreen\n\
	2:AutoScreen\n\
	3:TeleScreen\n\
	4:EndScreen\n\
\n\
InitialScreen\n\
	name: Init\n\
	ShowScoutingInitial\n\
\n\
AutoScreen\n\
	name: Auto\n\
	PlainText\n\
		x:0\n\
		y:5\n\
		value:\"Starting Place\"\n\
	PlainText\n\
		x:0\n\
		y:6\n\
		value:\"Left\"\n\
	PlainText\n\
		x:6\n\
		y:6\n\
		value:\"Middle\"\n\
	PlainText\n\
		x:14\n\
		y:6\n\
		value:\"Right\"\n\
	Radio\n\
		x1:1\n\
		y1:7\n\
		x2:8\n\
		y2:7\n\
		x3:15\n\
		y3:7\n\
		name:startPos\n\
	PlainText\n\
		x: 0\n\
		y: 10\n\
		value: \"Penalties\"\n\
	UpDown\n\
		x:10\n\
		y:10\n\
		name:autopenalties\n\
	PlainText\n\
		x: 0\n\
		y: 13\n\
		value: \"Overall Rating\"\n\
	Rating\n\
		x: 14\n\
		y: 13\n\
		name: autoRating\n\
		width: 1\n\
	PlainText\n\
		x: 0\n\
		y: 17\n\
		value: \"Uber-Tube on:\"\n\
	PlainText\n\
		x: 14\n\
		y: 17\n\
		value: \"Top\"\n\
	PlainText\n\
		x: 18\n\
		y: 17\n\
		value: \"Mid\"\n\
	PlainText\n\
		x: 22\n\
		y: 17\n\
		value: \"Low\"\n\
	PlainText\n\
		x: 26\n\
		y: 17\n\
		value: \"None\"\n\
	Radio\n\
		x1:14\n\
		y1:18\n\
		x2:18\n\
		y2:18\n\
		x3:22\n\
		y3:18\n\
		x4:27\n\
		y4:18\n\
		name:uberPlacement\n\
	PlainText\n\
		x:0\n\
		y:20\n\
		value:\"Did nothing/disabled\"\n\
	Checkbox\n\
		x:11\n\
		y:21\n\
		name:autoNothing\n\
\n\
TeleScreen\n\
	name: Tele\n\
	PlainText\n\
		x:1\n\
		y:4\n\
		value:\"Robot job:\"\n\
	DropDown\n\
		x: 1\n\
		y: 5\n\
		1: \"    Offensive Robot    \"\n\
		2: \"    Defensive Robot    \"\n\
		3: \"Offensive AND Defensive\"\n\
		4: \"      Did Nothing      \"\n\
		name:positions\n\
	PlainText\n\
		x:23\n\
		y:7\n\
		value:\"Penalties\"\n\
	UpDown\n\
		x:27\n\
		y:9\n\
		name:telepenalties\n\
	PlainText\n\
		x:0\n\
		y:7\n\
		value: \"Scored on:\"\n\
	PlainText\n\
		x:0\n\
		y:8\n\
		value:\"Top\"\n\
	PlainText\n\
		x:6\n\
		y:8\n\
		value:\"Mid\"\n\
	PlainText\n\
		x:12\n\
		y:8\n\
		value:\"Low\"\n\
	PlainText\n\
		x:18\n\
		y:8\n\
		value:\"???\"\n\
	Counter\n\
		x: 0\n\
		y: 9\n\
		increment:x\n\
		name: scorestop\n\
	Counter\n\
		x: 6\n\
		y: 9\n\
		increment:y\n\
		name: scoresmid\n\
	Counter\n\
		x: 12\n\
		y: 9\n\
		increment:b\n\
		name: scoreslow\n\
	Counter\n\
		x:18\n\
		y:9\n\
		name: unknownscores\n\
		increment:a\n\
	PlainText\n\
		x:0\n\
		y:11\n\
		value:\"Completes logos?\"\n\
	Checkbox\n\
		x:5\n\
		y:12\n\
		name:logos\n\
	Checkbox\n\
		x:25\n\
		y:12\n\
		name:groundPickup\n\
	PlainText\n\
		x:0\n\
		y:14\n\
		value:\"Misses\"\n\
	Counter\n\
		x:7\n\
		y:14\n\
		name:misses\n\
		increment:r\n\
	PlainText\n\
		x:0\n\
		y:17\n\
		value:\"Grabs:\"\n\
	PlainText\n\
		x:6\n\
		y:16\n\
		value:\"Circle\"\n\
	PlainText\n\
		x:14\n\
		y:16\n\
		value:\"Square\"\n\
	PlainText\n\
		x:22\n\
		y:16\n\
		value:\"Triangle\"\n\
	Checkbox\n\
		x:8\n\
		y:17\n\
		name:grabsCirc\n\
	Checkbox\n\
		x:16\n\
		y:17\n\
		name:grabsSqr\n\
	Checkbox\n\
		x:25\n\
		y:17\n\
		name:grabsTri\n\
	PlainText\n\
		x:0\n\
		y:19\n\
		value:\"Maneuverability\"\n\
	PlainText\n\
		x:8\n\
		y:20\n\
		value:\"Rating\"\n\
	Rating\n\
		x:14\n\
		y:20\n\
		name:manueverRating\n\
	PlainText\n\
		x:0\n\
		y:22\n\
		value:\"Manipulability\"\n\
	PlainText\n\
		x:0\n\
		y:23\n\
		value:\"(of arm)\"\n\
	Rating\n\
		x:14\n\
		y:22\n\
		name:amRating\n\
	PlainText\n\
		x:18\n\
		y:11\n\
		value:\"Picks up from\"\n\
	PlainText\n\
		x:18\n\
		y:12\n\
		value:\"Ground\"\n\
	PlainText\n\
		x:12\n\
		y:13\n\
		value:\"Status\"\n\
	DropDown\n\
		x:12\n\
		y:14\n\
		1:\"  Active  \"\n\
		2:\"Red-carded\"\n\
		3:\" Disabled \"\n\
		name:status\n\
\n\
EndScreen\n\
	name: End\n\
	PlainText\n\
		x:3\n\
		y:5\n\
		value:\"Minibot Success rating:\"\n\
	DropDown\n\
		x:1\n\
		y:6\n\
		1:\"   Does not have Minibot   \"\n\
		2:\"       No Deployment       \"\n\
		3:\"    Did not make to top    \"\n\
		4:\" Made to top AFTER TIME UP \"\n\
		5:\"  Made to top within time  \"\n\
		6:\"Other Bots Deployed Instead\"\n\
		name:minibotRating\n\
	DropDown\n\
		x:1\n\
		y:17\n\
		1:\"1st place\"\n\
		2:\"2nd place\"\n\
		3:\"3rd place\"\n\
		4:\"4th place\"\n\
		5:\"no finish\"\n\
		name:miniPlacement\n\
	PlainText\n\
		x:0\n\
		y:8\n\
		value:\"Deployment rating\"\n\
	PlainText\n\
		x:0\n\
		y:11\n\
		value:\"Aproximate secs to climb\"\n\
	Rating\n\
		x:0\n\
		y:9\n\
		name:deploySpeed\n\
	Timer\n\
		x:25\n\
		y:11\n\
		press:a\n\
		name:minibotSpeed\n\
	PlainText\n\
		x:0\n\
		y:14\n\
		value:\"Any minibot penalties\"\n\
	Checkbox\n\
		x:22\n\
		y:14\n\
		name:any_penalties\n\
	PlainText\n\
		x:1\n\
		y:16\n\
		value:\"Rank\"\n\
	Finish\n\
		x:24\n\
		y:20\n\
";




/**
 * Load the file into a character array.
 * Fill in the length into the len pointer.
 */
void loadFromFile(char** dest, int* len) {
  FILE* fp = fopen("config.txt", "r");
	
	if (fp == NULL) {
		*dest = result;
		*len = strlen(result);
		return;
	}

  /* Get the file size. */
  fseek(fp, 0L, SEEK_END);
  int size = ftell(fp);
  *len = size;
  fseek(fp, 0L, SEEK_SET);

  *dest = (char*)malloc(sizeof(char)*(size+1));
  char* buf = *dest;
  int chr = 0;
  int index = 0;
  /* Read a character. Make sure that we don't smash the heap. */
  while ((chr = fgetc(fp)) != EOF && index+1 < size) buf[index++] = chr;
  buf[index] = 0;

  fclose(fp);
}

/**
 * Print the configuration file out recursively.
 * Used for debugging.
 */
void printConf(Config* conf, int indent) {
  if (conf == NULL) return;
  printf("%d: %s\n", indent, conf->key);  
  KVPair* kv = conf->values;
  while (kv != NULL) {
    printf("[kv] %s: %s\n", kv->key, kv->value);
    kv = kv->next;
  }
  printConf(conf->children, indent+1);
  while (1) {
    conf = conf->next;
    if (conf == NULL) break;
    printConf(conf, indent);
  }
}

/**
 * Lookup the value for the given key in the linked list of key/value pairs.
 */
char* config_lookup_key(Config* conf, char* key) {
  KVPair* values = conf->values;
  while (values != NULL) {
		//printf("Comparing %s %s\n", key, values->key);
    if (strcmp(key, values->key) == 0) {
      return values->value;
    }
    values = values->next;
  }
  return NULL;
}

/**
 * Lookup the section for the given name in the linked list of children.
 */
Config* config_lookup_section(Config* conf, char* name) {
  Config* child = conf->children;
  while (child != NULL) {
    //printf("&%s/%s/%d*", name, child->key, strcmp(name, child->key));
    if (strcmp(name, child->key) == 0) {
      return child;
    }
    child = child->next;
  }
  return NULL;
}

/**
 * Do all the configuration. 
 * Load everything from the file, lex and parse the input, and then return the result.
 *
 * At the end of this function, there have been several calls to malloc:
 *  (1) The string representing the file has been allocated.
 *  (2) Each Token has been allocated.
 *  (3) Each Config struct and KV Pair has been allocated.
 * 	(a) Inside each config node, the name is a newly allocated string.
 *	(b) As well, in each KV Pair the key and value are allocated.
 *
 * These must be freed by the end of the program.
 */
Config* configure() {
  char* put;
  int len;
  loadFromFile(&put, &len);
  Token* tokenList = lex(put, len);
	
  Config* configFile = parse(tokenList, put);
  return configFile;
}
