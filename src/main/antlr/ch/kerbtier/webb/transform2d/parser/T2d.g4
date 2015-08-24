grammar T2d;
 
operation : VAR ('.' command)*;

VAR : '$1' | '$2' | '$3' | '$4';
 
command : crop | border | diffMask | fit;

// Commands sdas

crop : 'crop(' rect ')';

border : 'border(' rect ',' color ')';

diffMask: 'diffMask(' operation ')';

fit: 'fit(' SIZE2 ')';

// DATATYPES

color : '#' (COL3 | COL6);

COL3 : HEX_DIGIT HEX_DIGIT HEX_DIGIT;

COL6 : HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT;

fragment HEX_DIGIT : ('0'..'9' | 'a'..'f' | 'A'..'F');

rect: rect_vecs 
    | rect_vec_size
    | rect_border;

rect_vecs: VEC2 VEC2;

rect_vec_size: VEC2 SIZE2;

rect_border: NUMBER (NUMBER (NUMBER NUMBER)?)?;

VEC2 : NUMBER '|' NUMBER;

SIZE2 : NUMBER 'x' NUMBER;

NUMBER : [0-9]+;

WS: [ \n\t\r]+ -> skip;

