package cambridge;

import cambridge.model.TemplateDocument;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashSet;

/**
 * @author Erdinc Yilmazel
 * Date: Nov 3, 2009
 * Time: 3:20:20 PM
 */
public class FileTemplateLoader extends AbstractTemplateLoader {
   public FileTemplateLoader() {
      changeDetectionInterval = DefaultChangeDetectionInterval;
   }

   public FileTemplateLoader(int changeDetectionInterval) {
      this.changeDetectionInterval = changeDetectionInterval;
   }

   protected final int changeDetectionInterval;

   public TemplateFactory newTemplateFactory(File file) throws TemplateLoadingException {
      return newTemplateFactory(file, DefaultEncoding, null);
   }

   public TemplateFactory newTemplateFactory(File file, String encoding) throws TemplateLoadingException {
      return newTemplateFactory(file, encoding, null);
   }

   public TemplateFactory newTemplateFactory(File file, TemplateModifier modifier) throws TemplateLoadingException {
      return newTemplateFactory(file, DefaultEncoding, modifier);
   }

   public TemplateFactory newTemplateFactory(File file, String encoding, TemplateModifier modifier) throws TemplateLoadingException {
      TemplateDocument document = parseTemplate(file, encoding);
      if (modifier != null) {
         modifier.modifyTemplate(document);
      }

      try {
         if (document.getIncludes() != null) {
            return new FileTemplateFactory(this, document.normalize(), file, encoding, modifier, getFiles(document.getIncludes()), changeDetectionInterval);
         }

         return new FileTemplateFactory(this, document.normalize(), file, encoding, modifier, null, changeDetectionInterval);
      } catch (BehaviorInstantiationException e) {
         throw new TemplateLoadingException(e);
      }
   }

   public HashSet<File> getFiles(HashSet<String> fileNames) {
      HashSet<File> files = new HashSet<File>();
      for (String s : fileNames) {
         File f = new File(s);
         if (f.exists()) {
            files.add(f);
         }
      }

      if (files.size() != 0) {
         return files;
      }

      return null;
   }


   public TemplateDocument parseTemplate(File file) throws TemplateLoadingException {
      return parseTemplate(file, DefaultEncoding);
   }

   public TemplateDocument parseTemplate(File file, String encoding) throws TemplateLoadingException {
      try {
         return parseTemplate(new FileInputStream(file), encoding);
      } catch (FileNotFoundException e) {
         throw new TemplateLoadingException(e);
      }
   }

   public TemplateFactory newTemplateFactory(String template) throws TemplateLoadingException {
      return newTemplateFactory(new File(template));
   }

   public TemplateFactory newTemplateFactory(String template, String encoding) throws TemplateLoadingException {
      return newTemplateFactory(new File(template), encoding);
   }

   public TemplateFactory newTemplateFactory(String template, TemplateModifier modifier) throws TemplateLoadingException {
      return newTemplateFactory(new File(template), modifier);
   }

   public TemplateFactory newTemplateFactory(String template, String encoding, TemplateModifier modifier) throws TemplateLoadingException {
      return newTemplateFactory(new File(template), encoding, modifier);
   }

   public TemplateDocument parseTemplate(String templateFile) throws TemplateLoadingException {
      return parseTemplate(new File(templateFile));
   }

   public TemplateDocument parseTemplate(String templateFile, String encoding) throws TemplateLoadingException {
      return parseTemplate(new File(templateFile), encoding);
   }
}
