package cambridge.behaviors;

import cambridge.BehaviorInstantiationException;
import cambridge.BehaviorProvider;
import cambridge.ExpressionEvaluationException;
import cambridge.ExpressionParsingException;
import cambridge.Expressions;
import cambridge.LoopingTagBehavior;
import cambridge.TemplateEvaluationException;
import cambridge.model.Attribute;
import cambridge.model.AttributeKey;
import cambridge.model.DynamicAttribute;
import cambridge.model.Expression;
import cambridge.model.TagNode;
import cambridge.runtime.Iter;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * @author Erdinc Yilmazel
 * Date: Nov 1, 2009
 * Time: 3:57:11 PM
 */
public class FromBehavior extends LoopingTagBehavior {
    private final Expression from;
    private final Expression to;

    public FromBehavior(Expression from, Expression to, int line, int col) {
        super(line, col);
        this.from = from;
        this.to = to;
    }

    @Override
    public void doExecute(Map<String, Object> bindings, TagNode tag, Writer out) throws TemplateEvaluationException, IOException {
        try {
            Iter iter = new Iter();
            for (int i = from.asInt(bindings); i <= to.asInt(bindings); i++) {
            	if (i == to.asInt(bindings))
            		iter.setLast();

                bindings.put(Expressions.CURRENT_OBJECT, i);
                bindings.put(Expressions.ITER_OBJECT, iter);
                tag.execute(bindings, out);
                iter.next();
            }
        } catch (ExpressionEvaluationException e) {
            throw new TemplateEvaluationException(e, "Could not execute the expression: " + e.getMessage() +
                    ", on line: " + tag.getBeginLine() + ", column: " + tag.getBeginColumn(),
                    tag.getBeginLine(), tag.getBeginColumn(), tag.getTagName());
        }
    }

    public static BehaviorProvider<FromBehavior> getProvider() {
        return new BehaviorProvider<FromBehavior>() {
            public FromBehavior get(DynamicAttribute keyAttribute, Map<AttributeKey, Attribute> attributes, int line, int col) throws ExpressionParsingException, BehaviorInstantiationException {
                Expression from = keyAttribute.getExpression();
                AttributeKey toKey = new AttributeKey(keyAttribute.getAttributeNameSpace(), "to");

                Attribute toAttribute = attributes.get(toKey);

                if (toAttribute == null || !(toAttribute instanceof DynamicAttribute)) {
                    throw new BehaviorInstantiationException("Required parameters to is not set", line, col);
                }

                Expression to = ((DynamicAttribute) toAttribute).getExpression();

                return new FromBehavior(from, to, line, col);
            }
        };
    }
}
