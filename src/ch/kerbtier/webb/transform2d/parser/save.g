eval : operation;
 
operation : VAR '.' command;

VAR : '$1' | '$2' | '$3' | '$4';
 
command : crop | border;

// Commands

crop : 'crop(' COORD ',' COORD ')';

border : 'border(' NUMBER | COORD ',' COLOR ')';

// DATATYPES

COLOR : '#' COL3 | COL6;

COL3 : HEX_DIGIT HEX_DIGIT HEX_DIGIT;

COL6 : HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT;

fragment HEX_DIGIT : ('0'..'9' | 'a'..'f' | 'A'..'F');

COORD : NUMBER '/' NUMBER;

NUMBER : ['0'-'9']+;
