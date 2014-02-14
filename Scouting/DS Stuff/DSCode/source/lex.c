/* Copyright, Nicholas Carlini. All rights reserved. */

#include <stdio.h>
#include <stdlib.h>
#include <nds.h>

#include "config.h"
#include "lex.h"

/**
 * Create a new token which is of the given type.
 * The value of this token is on the range between of the next two args.
 */
Token* newToken(TokenType tokenType, int start, int end) {
	//printf("%d/", tokenType);
  Token* result = (Token*)malloc(sizeof(Token));
  result->token = tokenType;
  result->start = start;
  result->end = end;
  result->next = NULL;
  return result;
}

/**
 * Add a new token to the token list.
 */
void addNewToken(Token* prev, TokenType tokenType, int start, int end) {
  prev->next = newToken(tokenType, start, end);
}

int isSpace(char one) { return one == ' '; }
int isTab(char one) { return one == '	'; }
int isChar(char one) { 
  return (one >= 0x41 && one <= 0x5A) || (one >= 0x61 && one <= 0x7A)
    || (one >= 0x30 && one <= 0x39)
    || one == '.' || one == '_'; 
}
int isComment(char one) { return one == '#'; }
int isNewLine(char one) { return one == '\r' || one == '\n'; }
int isColon(char one) { return one == ':'; }
int isQuote(char one) { return one == '"'; }

/**
 * Lex the string into tokens, each of which has a given offset into the string.
 * Lexing is done by the following algorithm:
 *  (1) If the current character is a space, and if it is then check the next:
 *  	(a) If it is another space, then the token is a tab.
 *	(b) If it is some other character, the token is a space.
 *  (2) If the current character is a character (either upper or lower case), or a digit,
 *	 then continue until the first non-matching character and that is an ident.
 *  (3) If the current character is a #, then ignore everything until the end of the line.
 *  (4) If the current character is a newline, then the token is a newline.
 *  (5) If the current character is a colon, then the token is just a colon.
 *  (6) If the current character is a quote, then read until the endquote and
 *	 declare the string as the contents of the string.
 */
Token* lex(char* input, int len) {
  Token* first = newToken(0, 0, 0);
  Token* last = first;
  int index = 0;
  while (index < len-1) {
		//printf("*");
    int start = index;
    char cur = input[index];
    if (isSpace(cur)) {
      if (isSpace(input[index+1])) {
				index++;
				addNewToken(last, TAB, start, index);
      } else {
				addNewToken(last, SPACE, index, index);
      }
      index++;
		} else if (isTab(cur)) {
			index++;
			addNewToken(last, TAB, start, index);
    } else if (isChar(cur)) {
      while (isChar(input[++index]));
      addNewToken(last, IDENT, start, index);
    } else if (isComment(cur)) {
      while (!isNewLine(input[++index]));
    } else if (isNewLine(cur)) {
      index++;
      addNewToken(last, NEWLINE, index, index);
    } else if (isColon(cur)) {
      index++;
      addNewToken(last, COLON, index, index);
    } else if (isQuote(cur)) {
      while (!isQuote(input[++index]));
      addNewToken(last, STRING, start+1, index);
      index++; /* Pass by the end quote. */
    }
    if (last->next != NULL)
      last = last->next;
  }
  addNewToken(last, NEWLINE, index, index);

  return first->next;
}
