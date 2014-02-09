INTRO

This program is used to present simple questionaires on a Nintendo DS.

USAGE

Define the screens and programs in the configuration file "config".
Load the program and the config file onto the DS.
Retrieve the data from the DS. It is stored in the file "FOO".

INTERNALS

main.c is the main driver. It will load the configuration file, display the screens and questions, and call
each questions click handler when the question is clicked.

config.c reads the config file and looks up sections and values from names.

lex.c lexes the config file's text into tokens.

parse.c parses the config file's tokens into a Config object.

screen.c is a simple emulation of a Nintendo DS screen used for development/debugging when you don't have a DS.
