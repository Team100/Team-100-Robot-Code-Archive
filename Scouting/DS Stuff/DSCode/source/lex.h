/* Copyright, Nicholas Carlini. All rights reserved. */

#ifndef LEX_LOADED
#define LEX_LOADED 1

/** Chop a sequence of characters into a linked list of tokens. */

/**
 * Define the different token constants.
 */
typedef enum {
    UNDEFINED,
    TAB,
    SPACE,
    IDENT,
    NEWLINE,
    COLON,
    STRING,
} TokenType;

/**
 * A token is a group of characters which is to be parsed together.
 */
typedef struct token {
  TokenType token;
  int start;
  int end;
  struct token* next;
} Token;

/**
 * See function definition in lex.c for the below definition.
 */
Token* lex(char* input, int len);

#endif
