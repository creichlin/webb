// Generated from src/ch/kerbtier/webb/transform2d/parser/T2d.g4 by ANTLR 4.2
package ch.kerbtier.webb.transform2d.parser;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link T2dParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface T2dVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link T2dParser#border}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBorder(@NotNull T2dParser.BorderContext ctx);

	/**
	 * Visit a parse tree produced by {@link T2dParser#fit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFit(@NotNull T2dParser.FitContext ctx);

	/**
	 * Visit a parse tree produced by {@link T2dParser#rect}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRect(@NotNull T2dParser.RectContext ctx);

	/**
	 * Visit a parse tree produced by {@link T2dParser#diffMask}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDiffMask(@NotNull T2dParser.DiffMaskContext ctx);

	/**
	 * Visit a parse tree produced by {@link T2dParser#color}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColor(@NotNull T2dParser.ColorContext ctx);

	/**
	 * Visit a parse tree produced by {@link T2dParser#rect_vecs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRect_vecs(@NotNull T2dParser.Rect_vecsContext ctx);

	/**
	 * Visit a parse tree produced by {@link T2dParser#rect_vec_size}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRect_vec_size(@NotNull T2dParser.Rect_vec_sizeContext ctx);

	/**
	 * Visit a parse tree produced by {@link T2dParser#rect_border}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRect_border(@NotNull T2dParser.Rect_borderContext ctx);

	/**
	 * Visit a parse tree produced by {@link T2dParser#operation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperation(@NotNull T2dParser.OperationContext ctx);

	/**
	 * Visit a parse tree produced by {@link T2dParser#command}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommand(@NotNull T2dParser.CommandContext ctx);

	/**
	 * Visit a parse tree produced by {@link T2dParser#crop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCrop(@NotNull T2dParser.CropContext ctx);
}