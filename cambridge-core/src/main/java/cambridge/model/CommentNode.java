package cambridge.model;

import java.io.PrintStream;

/**
 * CommentNode class represents nodes within the documents which are valid
 * Html comments.
 * <p/>
 * Comment blocks start with "&lt;!--" characters and end with "--&gt;" characters.
 */
public class CommentNode extends TextNode {
   public String getSource() {
      return contents;
   }

   public void print(PrintStream out) {
      out.print(contents);
   }

   @Override
   void normalize(TemplateDocument doc, FragmentList f) {
      f.append(contents);
   }

   @Override
   boolean normalizeUntil(TemplateDocument doc, TemplateNode reference, FragmentList f, boolean inclusive) {
      if (reference == this) {
         if (inclusive) {
            f.append(contents);
         }
         return true;
      } else {
         f.append(contents);
         return false;
      }
   }

   @Override
   public TagNode getElementById(String id) {
      return null;
   }

   public String toString() {
      return "Comment node";
   }
}
