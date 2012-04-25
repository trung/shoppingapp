/**
 *
 */
package org.telokers.service.utils;


import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;


@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
public class HTMLEncode
{

    /**
     * j2se 1.4 encode method, used by reflection if available.
     */
    private static Method encodeMethod14;

    static
    {
        try
        {
            Class urlEncoderClass = URLEncoder.class;
            encodeMethod14 = urlEncoderClass.getMethod("encode", new Class[]{String.class, String.class});
        }
        catch (Throwable ex)
        {
            // encodeMethod14 will be null if exception
        }
    }

    /**
     * Utility class, don't instantiate.
     */
    private HTMLEncode()
    {
        // unused
    }

    private static final String[] ENTITIES = {
        ">",
        "&gt;",
        "<",
        "&lt;",
        "&",
        "&amp;",
        "\"",
        "&quot;",
        "'",
        "&#039;",
        "\\",
        "&#092;",
        "\u00a9",
        "&copy;",
        "\u00ae",
        "&reg;"};

    private static Hashtable entityTableEncode = null;

    protected static synchronized void buildEntityTables()
    {
        entityTableEncode = new Hashtable(ENTITIES.length);

        for (int i = 0; i < ENTITIES.length; i += 2)
        {
            if (!entityTableEncode.containsKey(ENTITIES[i]))
            {
                entityTableEncode.put(ENTITIES[i], ENTITIES[i + 1]);
            }
        }
    }

    /**
     * Converts a String to HTML by converting all special characters to HTML-entities.
     */
    public final static String encode(String s)
    {
        return encode(s, "\n");
    }

    /**
     * Converts a String to HTML by converting all special characters to HTML-entities.
     */
    public final static String encode(String s, String cr)
    {
        if (entityTableEncode == null)
        {
            buildEntityTables();
        }
        if (s == null)
        {
            return "";
        }
        StringBuffer sb = new StringBuffer(s.length() * 2);
        char ch;
        for (int i = 0; i < s.length(); ++i)
        {
            ch = s.charAt(i);
            if ((ch >= 63 && ch <= 90) || (ch >= 97 && ch <= 122) || (ch == ' '))
            {
                sb.append(ch);
            }
            else if (ch == '\n')
            {
                sb.append(cr);
            }
            else
            {
                String chEnc = encodeSingleChar(String.valueOf(ch));
                if (chEnc != null)
                {
                    sb.append(chEnc);
                }
                else
                {
                    // Not 7 Bit use the unicode system
                    sb.append("&#");
                    sb.append(new Integer(ch).toString());
                    sb.append(';');
                }
            }
        }
        return sb.toString();
    }

    /**
     * Converts a single character to HTML
     */
    private static String encodeSingleChar(String ch)
    {
        return (String) entityTableEncode.get(ch);
    }

    /**
     * Converts a String to valid HTML HREF by converting all special characters to HTML-entities.
     * @param url url to be encoded
     * @return encoded url.
     */
	protected static String encodeHREFParam(String url)
    {
        if (encodeMethod14 != null)
        {
            Object[] methodArgs = new Object[2];
            methodArgs[0] = url;

            methodArgs[1] = "UTF8";

            try
            {
                return (String) encodeMethod14.invoke(null, methodArgs);
            }
            catch (Throwable e)
            {
                throw new RuntimeException("Error invoking 1.4 URLEncoder.encode with reflection: " + e.getMessage());
            }
        }

        // must use J2SE 1.3 version
        return URLEncoder.encode(url);

    }

    protected static String encodeHREFParamJava13(String value)
    {
        return URLEncoder.encode(value);
    }

    public static String encodeQuery(String url, String[] args)
    {
        return encodeHREFQuery(url, args, false);
    }

    public static String encodeHREFQuery(String url, String[] args)
    {
        return encodeHREFQuery(url, args, true);
    }

    public static String encodeHREFQuery(String url, String[] args, boolean forHtml)
    {
        StringBuffer out = new StringBuffer(128);
        out.append(url);

        if ((args != null) && (args.length > 0))
        {
            out.append("?");
            for (int i = 0; i < (args.length + 1) / 2; i++)
            {
                int k = i * 2;
                if (k != 0)
                {
                    if (forHtml)
                    {
                        out.append("&amp;");
                    }
                    else
                    {
                        out.append("&");
                    }
                }
                out.append(encodeHREFParam(args[k]));
                if (k + 1 < args.length)
                {
                    out.append("=");
                    out.append(encodeHREFParam(args[k + 1]));
                }
            }
        }
        return out.toString();
    }

	public static String encodeHREFQuery(String url, Map args, boolean forHtml)
    {
        StringBuffer out = new StringBuffer(128);
        out.append(url);

        if ((args != null) && (args.size() > 0))
        {
            out.append("?");
            int k = 0;
            for (Iterator i = args.keySet().iterator(); i.hasNext();)
            {
                if (k != 0)
                {
                    if (forHtml)
                    {
                        out.append("&amp;");
                    }
                    else
                    {
                        out.append("&");
                    }
                }
                String name = (String) i.next();
                out.append(encodeHREFParam(name));
                out.append("=");
                out.append(encodeHREFParam((String) args.get(name)));
                k++;
            }
        }
        return out.toString();
    }
}
