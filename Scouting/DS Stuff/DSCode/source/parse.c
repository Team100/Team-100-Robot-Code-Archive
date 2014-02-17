/* Copyright, Nicholas Carlini. All rights reserved. */

#include <stdio.h>
#include <stdlib.h>
#include <nds.h>

#include "config.h"
#include "lex.h"

/**
 * Create a new configuration node.
 */
Config* newNode(Config* parent) {
  Config* res = (Config*)malloc(sizeof(Config));
  res->children = NULL;
  res->parent = parent;
  res->next = NULL;
  res->key = NULL;
  res->values = NULL;
  res->objptr = 0;
  return res;
}

/**
 * Create a new configuration node and add it as a child to the given node.
 */
Config* addNewChild(Config* parent) {
  if (parent->children == NULL) {
    parent->children = newNode(parent);
    return parent->children;
  } else {
    Config* front = newNode(parent);
    front->next = parent->children;
    parent->children = front;
    return front;
  }
}

/**
 * Get the last KV pair so that we can add to the end of the list.
 */
KVPair* getLastKV(KVPair* element) {
  while (element->next != NULL)
    element = element->next;
  return element;
}

/**
 * Parse the input from a straem of tokens into Config structs.
 * The grammar is defined as follows:
 * File ::= Line File | Line
 * Line ::= Header NEWLINE | Tabs Head NEWLINE | Tabs KVP NEWLINE
 * Tabs ::= TAB Tabs | TAB
 * Head ::= IDENT
 * KVP  ::= IDENT CoSp IDENT | IDENT CoSp STRING
 * CoSP ::= COLON | COLON Spcs
 * Spcs ::= SPACE | Spcs SPACE
 *
 * Instead of a recursive decent parser, the parsing is done on a single while loop,
 * without backtracking. Lookahead is used to determine which rule to apply next.
 * 
 * On each pass of the while loop, the token stream will be at the beginning of a line.
 * First, the tabs are read in and counted, and for each tab read in the configuration
 * struct to be modified is determined. Then, it it is determined whether the line
 * contains a header or a key/value pair.
 *
 * At the end, a full struct is returned with all of the configuration data.
 */

/* TODO: Handle errors. */

Config* parse(Token* list, char* file) {
  Config* root = newNode(NULL);
  int line = 0;
  while (list != NULL) {
    line++;
    int indent = 0;
    while (list->token == TAB) {
      indent++;
      list = list->next;
    }
    Config* node = root;
    while (indent-- > 0) {
      node = node->children;
    }
    /* We're now at the correct config node. */

    if (list->token != IDENT) {
      printf("ERROR: Ident expected at start of line %d.\n", line);
      exit(1);
    }

    int size = list->end - list->start;
    char *val = (char*)malloc(size);
    strncpy(val, file+list->start, size);
    if (list->next->token == COLON) {
      /* This is a key/value pair. */
      list = list->next->next;
      while (list->token == SPACE
	     || list->token == TAB)
					list = list->next;

      KVPair* pair;
      if (node->values == NULL) {
				pair = (KVPair*)malloc(sizeof(KVPair));
				pair->key = val;
				node->values = pair;
      } else {
				pair = getLastKV(node->values);
				pair->next = (KVPair*)malloc(sizeof(KVPair));
				pair = pair->next;
				pair->key = val;
      }
      
      int size = list->end - list->start;
      char *next = (char*)malloc(size+1);
      strncpy(next, file+list->start, size);
      
      pair->value = next;
    } else if (list->next->token == NEWLINE) {
      /* This is a start of a struct. */
      addNewChild(node)->key = val;
    } else {
      printf("ERROR: Unexpected content at line %d: %d.\n", line, list->next->token);
      exit(1);
    }
    while (list != NULL && list->token != NEWLINE) {
      list = list->next;
    }
    
    while (list != NULL && list->token == NEWLINE) {
      list = list->next;
    }
  }
  return root;
}
