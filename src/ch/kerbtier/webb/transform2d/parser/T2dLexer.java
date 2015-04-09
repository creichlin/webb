// Generated from src/ch/kerbtier/webb/transform2d/parser/T2d.g4 by ANTLR 4.2
package ch.kerbtier.webb.transform2d.parser;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class T2dLexer extends Lexer {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__7=1, T__6=2, T__5=3, T__4=4, T__3=5, T__2=6, T__1=7, T__0=8, VAR=9, 
		COL3=10, COL6=11, VEC2=12, SIZE2=13, NUMBER=14, WS=15;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"<INVALID>",
		"'border('", "'#'", "'crop('", "'diffMask('", "')'", "'fit('", "','", 
		"'.'", "VAR", "COL3", "COL6", "VEC2", "SIZE2", "NUMBER", "WS"
	};
	public static final String[] ruleNames = {
		"T__7", "T__6", "T__5", "T__4", "T__3", "T__2", "T__1", "T__0", "VAR", 
		"COL3", "COL6", "HEX_DIGIT", "VEC2", "SIZE2", "NUMBER", "WS"
	};


	public T2dLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "T2d.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\21s\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\3\2\3\2\3"+
		"\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5"+
		"\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\t\3\t\3"+
		"\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\5\nQ\n\n\3\13\3\13\3\13\3\13\3\f\3\f\3"+
		"\f\3\f\3\f\3\f\3\f\3\r\3\r\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\20"+
		"\6\20i\n\20\r\20\16\20j\3\21\6\21n\n\21\r\21\16\21o\3\21\3\21\2\2\22\3"+
		"\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\2\33\16\35\17\37"+
		"\20!\21\3\2\5\5\2\62;CHch\3\2\62;\5\2\13\f\17\17\"\"v\2\3\3\2\2\2\2\5"+
		"\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2"+
		"\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\33\3\2\2\2\2\35"+
		"\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\3#\3\2\2\2\5+\3\2\2\2\7-\3\2\2\2\t\63"+
		"\3\2\2\2\13=\3\2\2\2\r?\3\2\2\2\17D\3\2\2\2\21F\3\2\2\2\23P\3\2\2\2\25"+
		"R\3\2\2\2\27V\3\2\2\2\31]\3\2\2\2\33_\3\2\2\2\35c\3\2\2\2\37h\3\2\2\2"+
		"!m\3\2\2\2#$\7d\2\2$%\7q\2\2%&\7t\2\2&\'\7f\2\2\'(\7g\2\2()\7t\2\2)*\7"+
		"*\2\2*\4\3\2\2\2+,\7%\2\2,\6\3\2\2\2-.\7e\2\2./\7t\2\2/\60\7q\2\2\60\61"+
		"\7r\2\2\61\62\7*\2\2\62\b\3\2\2\2\63\64\7f\2\2\64\65\7k\2\2\65\66\7h\2"+
		"\2\66\67\7h\2\2\678\7O\2\289\7c\2\29:\7u\2\2:;\7m\2\2;<\7*\2\2<\n\3\2"+
		"\2\2=>\7+\2\2>\f\3\2\2\2?@\7h\2\2@A\7k\2\2AB\7v\2\2BC\7*\2\2C\16\3\2\2"+
		"\2DE\7.\2\2E\20\3\2\2\2FG\7\60\2\2G\22\3\2\2\2HI\7&\2\2IQ\7\63\2\2JK\7"+
		"&\2\2KQ\7\64\2\2LM\7&\2\2MQ\7\65\2\2NO\7&\2\2OQ\7\66\2\2PH\3\2\2\2PJ\3"+
		"\2\2\2PL\3\2\2\2PN\3\2\2\2Q\24\3\2\2\2RS\5\31\r\2ST\5\31\r\2TU\5\31\r"+
		"\2U\26\3\2\2\2VW\5\31\r\2WX\5\31\r\2XY\5\31\r\2YZ\5\31\r\2Z[\5\31\r\2"+
		"[\\\5\31\r\2\\\30\3\2\2\2]^\t\2\2\2^\32\3\2\2\2_`\5\37\20\2`a\7~\2\2a"+
		"b\5\37\20\2b\34\3\2\2\2cd\5\37\20\2de\7z\2\2ef\5\37\20\2f\36\3\2\2\2g"+
		"i\t\3\2\2hg\3\2\2\2ij\3\2\2\2jh\3\2\2\2jk\3\2\2\2k \3\2\2\2ln\t\4\2\2"+
		"ml\3\2\2\2no\3\2\2\2om\3\2\2\2op\3\2\2\2pq\3\2\2\2qr\b\21\2\2r\"\3\2\2"+
		"\2\6\2Pjo\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}