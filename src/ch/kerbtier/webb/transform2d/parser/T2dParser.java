// Generated from src/ch/kerbtier/webb/transform2d/parser/T2d.g4 by ANTLR 4.2
package ch.kerbtier.webb.transform2d.parser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class T2dParser extends Parser {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__7=1, T__6=2, T__5=3, T__4=4, T__3=5, T__2=6, T__1=7, T__0=8, VAR=9, 
		COL3=10, COL6=11, VEC2=12, SIZE2=13, NUMBER=14, WS=15;
	public static final String[] tokenNames = {
		"<INVALID>", "'border('", "'#'", "'crop('", "'diffMask('", "')'", "'fit('", 
		"','", "'.'", "VAR", "COL3", "COL6", "VEC2", "SIZE2", "NUMBER", "WS"
	};
	public static final int
		RULE_operation = 0, RULE_command = 1, RULE_crop = 2, RULE_border = 3, 
		RULE_diffMask = 4, RULE_fit = 5, RULE_color = 6, RULE_rect = 7, RULE_rect_vecs = 8, 
		RULE_rect_vec_size = 9, RULE_rect_border = 10;
	public static final String[] ruleNames = {
		"operation", "command", "crop", "border", "diffMask", "fit", "color", 
		"rect", "rect_vecs", "rect_vec_size", "rect_border"
	};

	@Override
	public String getGrammarFileName() { return "T2d.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public T2dParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class OperationContext extends ParserRuleContext {
		public TerminalNode VAR() { return getToken(T2dParser.VAR, 0); }
		public List<CommandContext> command() {
			return getRuleContexts(CommandContext.class);
		}
		public CommandContext command(int i) {
			return getRuleContext(CommandContext.class,i);
		}
		public OperationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operation; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof T2dVisitor ) return ((T2dVisitor<? extends T>)visitor).visitOperation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OperationContext operation() throws RecognitionException {
		OperationContext _localctx = new OperationContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_operation);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(22); match(VAR);
			setState(27);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==8) {
				{
				{
				setState(23); match(8);
				setState(24); command();
				}
				}
				setState(29);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CommandContext extends ParserRuleContext {
		public BorderContext border() {
			return getRuleContext(BorderContext.class,0);
		}
		public CropContext crop() {
			return getRuleContext(CropContext.class,0);
		}
		public DiffMaskContext diffMask() {
			return getRuleContext(DiffMaskContext.class,0);
		}
		public FitContext fit() {
			return getRuleContext(FitContext.class,0);
		}
		public CommandContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_command; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof T2dVisitor ) return ((T2dVisitor<? extends T>)visitor).visitCommand(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CommandContext command() throws RecognitionException {
		CommandContext _localctx = new CommandContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_command);
		try {
			setState(34);
			switch (_input.LA(1)) {
			case 3:
				enterOuterAlt(_localctx, 1);
				{
				setState(30); crop();
				}
				break;
			case 1:
				enterOuterAlt(_localctx, 2);
				{
				setState(31); border();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 3);
				{
				setState(32); diffMask();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 4);
				{
				setState(33); fit();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CropContext extends ParserRuleContext {
		public RectContext rect() {
			return getRuleContext(RectContext.class,0);
		}
		public CropContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_crop; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof T2dVisitor ) return ((T2dVisitor<? extends T>)visitor).visitCrop(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CropContext crop() throws RecognitionException {
		CropContext _localctx = new CropContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_crop);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(36); match(3);
			setState(37); rect();
			setState(38); match(5);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BorderContext extends ParserRuleContext {
		public RectContext rect() {
			return getRuleContext(RectContext.class,0);
		}
		public ColorContext color() {
			return getRuleContext(ColorContext.class,0);
		}
		public BorderContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_border; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof T2dVisitor ) return ((T2dVisitor<? extends T>)visitor).visitBorder(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BorderContext border() throws RecognitionException {
		BorderContext _localctx = new BorderContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_border);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(40); match(1);
			setState(41); rect();
			setState(42); match(7);
			setState(43); color();
			setState(44); match(5);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DiffMaskContext extends ParserRuleContext {
		public OperationContext operation() {
			return getRuleContext(OperationContext.class,0);
		}
		public DiffMaskContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_diffMask; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof T2dVisitor ) return ((T2dVisitor<? extends T>)visitor).visitDiffMask(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DiffMaskContext diffMask() throws RecognitionException {
		DiffMaskContext _localctx = new DiffMaskContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_diffMask);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(46); match(4);
			setState(47); operation();
			setState(48); match(5);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FitContext extends ParserRuleContext {
		public TerminalNode SIZE2() { return getToken(T2dParser.SIZE2, 0); }
		public FitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fit; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof T2dVisitor ) return ((T2dVisitor<? extends T>)visitor).visitFit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FitContext fit() throws RecognitionException {
		FitContext _localctx = new FitContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_fit);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(50); match(6);
			setState(51); match(SIZE2);
			setState(52); match(5);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ColorContext extends ParserRuleContext {
		public TerminalNode COL3() { return getToken(T2dParser.COL3, 0); }
		public TerminalNode COL6() { return getToken(T2dParser.COL6, 0); }
		public ColorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_color; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof T2dVisitor ) return ((T2dVisitor<? extends T>)visitor).visitColor(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ColorContext color() throws RecognitionException {
		ColorContext _localctx = new ColorContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_color);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(54); match(2);
			setState(55);
			_la = _input.LA(1);
			if ( !(_la==COL3 || _la==COL6) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RectContext extends ParserRuleContext {
		public Rect_vecsContext rect_vecs() {
			return getRuleContext(Rect_vecsContext.class,0);
		}
		public Rect_borderContext rect_border() {
			return getRuleContext(Rect_borderContext.class,0);
		}
		public Rect_vec_sizeContext rect_vec_size() {
			return getRuleContext(Rect_vec_sizeContext.class,0);
		}
		public RectContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rect; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof T2dVisitor ) return ((T2dVisitor<? extends T>)visitor).visitRect(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RectContext rect() throws RecognitionException {
		RectContext _localctx = new RectContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_rect);
		try {
			setState(60);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(57); rect_vecs();
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(58); rect_vec_size();
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(59); rect_border();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Rect_vecsContext extends ParserRuleContext {
		public List<TerminalNode> VEC2() { return getTokens(T2dParser.VEC2); }
		public TerminalNode VEC2(int i) {
			return getToken(T2dParser.VEC2, i);
		}
		public Rect_vecsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rect_vecs; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof T2dVisitor ) return ((T2dVisitor<? extends T>)visitor).visitRect_vecs(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Rect_vecsContext rect_vecs() throws RecognitionException {
		Rect_vecsContext _localctx = new Rect_vecsContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_rect_vecs);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(62); match(VEC2);
			setState(63); match(VEC2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Rect_vec_sizeContext extends ParserRuleContext {
		public TerminalNode VEC2() { return getToken(T2dParser.VEC2, 0); }
		public TerminalNode SIZE2() { return getToken(T2dParser.SIZE2, 0); }
		public Rect_vec_sizeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rect_vec_size; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof T2dVisitor ) return ((T2dVisitor<? extends T>)visitor).visitRect_vec_size(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Rect_vec_sizeContext rect_vec_size() throws RecognitionException {
		Rect_vec_sizeContext _localctx = new Rect_vec_sizeContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_rect_vec_size);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(65); match(VEC2);
			setState(66); match(SIZE2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Rect_borderContext extends ParserRuleContext {
		public TerminalNode NUMBER(int i) {
			return getToken(T2dParser.NUMBER, i);
		}
		public List<TerminalNode> NUMBER() { return getTokens(T2dParser.NUMBER); }
		public Rect_borderContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rect_border; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof T2dVisitor ) return ((T2dVisitor<? extends T>)visitor).visitRect_border(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Rect_borderContext rect_border() throws RecognitionException {
		Rect_borderContext _localctx = new Rect_borderContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_rect_border);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(68); match(NUMBER);
			setState(74);
			_la = _input.LA(1);
			if (_la==NUMBER) {
				{
				setState(69); match(NUMBER);
				setState(72);
				_la = _input.LA(1);
				if (_la==NUMBER) {
					{
					setState(70); match(NUMBER);
					setState(71); match(NUMBER);
					}
				}

				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\21O\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13\4"+
		"\f\t\f\3\2\3\2\3\2\7\2\34\n\2\f\2\16\2\37\13\2\3\3\3\3\3\3\3\3\5\3%\n"+
		"\3\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\7\3\7\3\7"+
		"\3\7\3\b\3\b\3\b\3\t\3\t\3\t\5\t?\n\t\3\n\3\n\3\n\3\13\3\13\3\13\3\f\3"+
		"\f\3\f\3\f\5\fK\n\f\5\fM\n\f\3\f\2\2\r\2\4\6\b\n\f\16\20\22\24\26\2\3"+
		"\3\2\f\rK\2\30\3\2\2\2\4$\3\2\2\2\6&\3\2\2\2\b*\3\2\2\2\n\60\3\2\2\2\f"+
		"\64\3\2\2\2\168\3\2\2\2\20>\3\2\2\2\22@\3\2\2\2\24C\3\2\2\2\26F\3\2\2"+
		"\2\30\35\7\13\2\2\31\32\7\n\2\2\32\34\5\4\3\2\33\31\3\2\2\2\34\37\3\2"+
		"\2\2\35\33\3\2\2\2\35\36\3\2\2\2\36\3\3\2\2\2\37\35\3\2\2\2 %\5\6\4\2"+
		"!%\5\b\5\2\"%\5\n\6\2#%\5\f\7\2$ \3\2\2\2$!\3\2\2\2$\"\3\2\2\2$#\3\2\2"+
		"\2%\5\3\2\2\2&\'\7\5\2\2\'(\5\20\t\2()\7\7\2\2)\7\3\2\2\2*+\7\3\2\2+,"+
		"\5\20\t\2,-\7\t\2\2-.\5\16\b\2./\7\7\2\2/\t\3\2\2\2\60\61\7\6\2\2\61\62"+
		"\5\2\2\2\62\63\7\7\2\2\63\13\3\2\2\2\64\65\7\b\2\2\65\66\7\17\2\2\66\67"+
		"\7\7\2\2\67\r\3\2\2\289\7\4\2\29:\t\2\2\2:\17\3\2\2\2;?\5\22\n\2<?\5\24"+
		"\13\2=?\5\26\f\2>;\3\2\2\2><\3\2\2\2>=\3\2\2\2?\21\3\2\2\2@A\7\16\2\2"+
		"AB\7\16\2\2B\23\3\2\2\2CD\7\16\2\2DE\7\17\2\2E\25\3\2\2\2FL\7\20\2\2G"+
		"J\7\20\2\2HI\7\20\2\2IK\7\20\2\2JH\3\2\2\2JK\3\2\2\2KM\3\2\2\2LG\3\2\2"+
		"\2LM\3\2\2\2M\27\3\2\2\2\7\35$>JL";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}