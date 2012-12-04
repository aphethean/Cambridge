package cambridge.parser.expressions;

/**
 * @author Erdinc Yilmazel
 * Date: Oct 31, 2009
 * Time: 1:31:05 AM
 */
class MapVarProperty implements VarProperty {
   final CambridgeExpression expression;

   public MapVarProperty(CambridgeExpression expression) {
      this.expression = expression;
   }
}
