package cambridge;

import cambridge.model.Tag;
import cambridge.model.TemplateDocument;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Erdinc Yilmazel
 * Date: Nov 6, 2009
 * Time: 2:07:51 PM
 */
public class SelectorTest {
   @Test
   public void testSelect() {
      final DirectoryTemplateLoader loader = new DirectoryTemplateLoader(new File("."));
      try {
         TemplateFactory f = loader.newTemplateFactory("a.html", new TemplateModifier() {
            public void modifyTemplate(TemplateDocument doc) {
               Tag t = doc.locateTag("/html/body/div/div/span");

               assertNotNull(t);
               assertEquals("span", t.getTagName());
               assertEquals("text", t.getTextContents());

               t = doc.locateTag("/html/body/ul/li[2]/span");
               assertNotNull(t);
               assertEquals("span", t.getTagName());
               assertEquals("text", t.getTextContents());
            }
         });

         Template t = f.createTemplate();
         t.printTo(new OutputStreamWriter(System.out));
      } catch (TemplateLoadingException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (TemplateEvaluationException e) {
         e.printStackTrace();
      }
   }
}
