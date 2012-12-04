package cambridge.parser.expressions;

import cambridge.ExpressionEvaluationException;

import java.util.Map;

/**
 * @author Erdinc Yilmazel
 * Date: Nov 9, 2009
 * Time: 11:53:59 PM
 */
public abstract class FunctionRunner {
   public abstract Object eval(Map<String, Object> bindings, CambridgeExpression[] params) throws ExpressionEvaluationException;
}
