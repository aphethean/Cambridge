package cambridge.runtime;

import java.util.Locale;

/**
 * @author Erdinc Yilmazel
 * Date: Nov 8, 2009
 * Time: 5:11:59 PM
 */
public interface Filter {
   public abstract void init(String parameters);
   public abstract Object doFilter(Object o, Locale locale);
}