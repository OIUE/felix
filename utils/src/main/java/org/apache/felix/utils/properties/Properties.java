/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.felix.utils.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.AbstractMap;
<<<<<<< HEAD
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
=======
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.BundleContext;

/**
 * <p>
 * Enhancement of the standard <code>Properties</code>
 * managing the maintain of comments, etc.
 * </p>
 *
 * @author gnodet, jbonofre
 */
public class Properties extends AbstractMap<String, String> {

    /** Constant for the supported comment characters.*/
    private static final String COMMENT_CHARS = "#!";

    /** The list of possible key/value separators */
    private static final char[] SEPARATORS = new char[] {'=', ':'};

    /** The white space characters used as key/value separators. */
    private static final char[] WHITE_SPACE = new char[] {' ', '\t', '\f'};

    /**
     * The default encoding (ISO-8859-1 as specified by
     * http://java.sun.com/j2se/1.5.0/docs/api/java/util/Properties.html)
     */
<<<<<<< HEAD
    private static final String DEFAULT_ENCODING = "ISO-8859-1";
=======
    static final String DEFAULT_ENCODING = "ISO-8859-1";
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368

    /** Constant for the platform specific line separator.*/
    private static final String LINE_SEPARATOR = AccessController.doPrivileged(new PrivilegedAction<String>() {
            public String run() {
                return System.getProperty("line.separator");
            }
        });

    /** Constant for the radix of hex numbers.*/
    private static final int HEX_RADIX = 16;

    /** Constant for the length of a unicode literal.*/
    private static final int UNICODE_LEN = 4;

    private final Map<String,String> storage = new LinkedHashMap<String,String>();
    private final Map<String,Layout> layout = new LinkedHashMap<String,Layout>();
    private List<String> header;
    private List<String> footer;
    private File location;
<<<<<<< HEAD
    private BundleContext context;
=======
    private InterpolationHelper.SubstitutionCallback callback;
    boolean substitute = true;
    boolean typed;
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368

    public Properties() {
    }

    public Properties(File location) throws IOException {
<<<<<<< HEAD
      this(location, null);
    }

    public Properties(File location, BundleContext context) throws IOException {
        this.location = location;
        this.context = context;
=======
      this(location, (InterpolationHelper.SubstitutionCallback) null);
    }

    public Properties(File location, BundleContext context) throws IOException {
        this(location, new InterpolationHelper.BundleContextSubstitutionCallback(context));
    }

    public Properties(File location, InterpolationHelper.SubstitutionCallback callback) throws IOException {
        this.location = location;
        this.callback = callback;
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
        if(location.exists())
            load(location);
    }

<<<<<<< HEAD
=======
    public Properties(boolean substitute) {
        this.substitute = substitute;
    }

    public Properties(File location, boolean substitute) {
        this.location = location;
        this.substitute = substitute;
    }

>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
    public void load(File location) throws IOException {
        InputStream is = new FileInputStream(location);
        try {
            load(is);
        } finally {
            is.close();
        }
    }

    public void load(URL location) throws IOException {
        InputStream is = location.openStream();
        try {
            load(is);
        } finally {
            is.close();
        }
    }

    public void load(InputStream is) throws IOException {
        load(new InputStreamReader(is, DEFAULT_ENCODING));
    }

    public void load(Reader reader) throws IOException {
<<<<<<< HEAD
        loadLayout(reader);
=======
        loadLayout(reader, false);
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
    }

    public void save() throws IOException {
        save(this.location);
    }

    public void save(File location) throws IOException {
        OutputStream os = new FileOutputStream(location);
        try {
            save(os);
        } finally {
            os.close();
        }
    }

    public void save(OutputStream os) throws IOException {
        save(new OutputStreamWriter(os, DEFAULT_ENCODING));
    }

    public void save(Writer writer) throws IOException {
<<<<<<< HEAD
        saveLayout(writer);
=======
        saveLayout(writer, typed);
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
    }

    /**
     * Store a properties into a output stream, preserving comments, special character, etc.
     * This method is mainly to be compatible with the java.util.Properties class.
     *
     * @param os an output stream.
     * @param comment this parameter is ignored as this Properties
<<<<<<< HEAD
     * @throws IOException
=======
     * @throws IOException If storing fails
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
     */
    public void store(OutputStream os, String comment) throws IOException {
        this.save(os);
    }

    /**
     * Searches for the property with the specified key in this property list.
     *
     * @param key the property key.
     * @return the value in this property list with the specified key value.
     */
    public String getProperty(String key) {
        return this.get(key);
    }

    /**
     * Searches for the property with the specified key in this property list. If the key is not found in this property
     * list, the default property list, and its defaults, recursively, are then checked. The method returns the default
     * value argument if the property is not found.
     *
     * @param key the property key.
     * @param defaultValue a default value.
<<<<<<< HEAD
     * @return
=======
     * @return The property value of the default value
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
     */
    public String getProperty(String key, String defaultValue) {
        if (this.get(key) != null)
            return this.get(key);
        return defaultValue;
    }

    @Override
    public Set<Entry<String, String>> entrySet() {
<<<<<<< HEAD
        return storage.entrySet();
=======
        return new AbstractSet<Entry<String, String>>() {
            @Override
            public Iterator<Entry<String, String>> iterator() {
                return new Iterator<Entry<String, String>>() {
                    final Iterator<Entry<String, String>> keyIterator = storage.entrySet().iterator();
                    public boolean hasNext() {
                        return keyIterator.hasNext();
                    }
                    public Entry<String, String> next() {
                        final Entry<String, String> entry = keyIterator.next();
                        return new Entry<String, String>() {
                            public String getKey() {
                                return entry.getKey();
                            }
                            public String getValue() {
                                return entry.getValue();
                            }
                            public String setValue(String value) {
                                String old = entry.setValue(value);
                                if (old == null || !old.equals(value)) {
                                    Layout l = layout.get(entry.getKey());
                                    if (l != null) {
                                        l.clearValue();
                                    }
                                }
                                return old;
                            }
                        };
                    }
                    public void remove() {
                        keyIterator.remove();
                    }
                };
            }

            @Override
            public int size() {
                return storage.size();
            }
        };
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
    }

    /**
     * Returns an enumeration of all the keys in this property list, including distinct keys in the default property
     * list if a key of the same name has not already been found from the main properties list.
     *
     * @return an enumeration of all the keys in this property list, including the keys in the default property list.
     */
    public Enumeration<?> propertyNames() {
        return Collections.enumeration(storage.keySet());
    }

    /**
     * Calls the map method put. Provided for parallelism with the getProperty method.
     * Enforces use of strings for property keys and values. The value returned is the result of the map call to put.
     *
     * @param key the key to be placed into this property list.
     * @param value the value corresponding to the key.
     * @return the previous value of the specified key in this property list, or null if it did not have one.
     */
    public Object setProperty(String key, String value) {
        return this.put(key, value);
    }

    @Override
    public String put(String key, String value) {
        String old = storage.put(key, value);
        if (old == null || !old.equals(value)) {
            Layout l = layout.get(key);
            if (l != null) {
                l.clearValue();
            }
        }
        return old;
    }

<<<<<<< HEAD
=======
    void putAllSubstituted(Map<? extends String, ? extends String> m) {
        storage.putAll(m);
    }

>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
    public String put(String key, List<String> commentLines, List<String> valueLines) {
        commentLines = new ArrayList<String>(commentLines);
        valueLines = new ArrayList<String>(valueLines);
        String escapedKey = escapeKey(key);
<<<<<<< HEAD
        int lastLine = valueLines.size() - 1;
        if (valueLines.isEmpty()) {
            valueLines.add(escapedKey + "=");
        } else if (!valueLines.get(0).trim().startsWith(escapedKey)) {
            valueLines.set(0, escapedKey + " = " + escapeJava(valueLines.get(0)) + (0 < lastLine? "\\": ""));
        }
        for (int i = 1; i < valueLines.size(); i++) {
            valueLines.set(i, escapeJava(valueLines.get(i)) + (i < lastLine? "\\": ""));
        }
        StringBuilder value = new StringBuilder();
        for (String line: valueLines) {
            value.append(line);
        }
        this.layout.put(key, new Layout(commentLines, valueLines));
        return storage.put(key, unescapeJava(value.toString()));
=======
        StringBuilder sb = new StringBuilder();
        int lastLine = valueLines.size() - 1;
        if (valueLines.isEmpty()) {
            valueLines.add(escapedKey + "=");
            sb.append(escapedKey).append("=");
        } else {
            String val0 = valueLines.get(0);
            String rv0 = typed ? val0 : escapeJava(val0);
            if (!val0.trim().startsWith(escapedKey)) {
                valueLines.set(0, escapedKey + " = " + rv0 /*+ (0 < lastLine? "\\": "")*/);
                sb.append(escapedKey).append(" = ").append(rv0);
            } else {
                valueLines.set(0, rv0 /*+ (0 < lastLine? "\\": "")*/);
                sb.append(rv0);
            }
        }
        for (int i = 1; i < valueLines.size(); i++) {
            String val = valueLines.get(i);
            valueLines.set(i, typed ? val : escapeJava(val) /*+ (i < lastLine? "\\": "")*/);
            while (val.length() > 0 && Character.isWhitespace(val.charAt(0))) {
                val = val.substring(1);
            }
            sb.append(val);
        }
        String[] property = PropertiesReader.parseProperty(sb.toString());
        this.layout.put(key, new Layout(commentLines, valueLines));
        return storage.put(key, property[1]);
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
    }

    public String put(String key, List<String> commentLines, String value) {
        commentLines = new ArrayList<String>(commentLines);
        this.layout.put(key, new Layout(commentLines, null));
        return storage.put(key, value);
    }

    public String put(String key, String comment, String value) {
        return put(key, Collections.singletonList(comment), value);
    }

<<<<<<< HEAD
=======
    public boolean update(Map<String, String> props) {
        Properties properties;
        if (props instanceof Properties) {
            properties = (Properties) props;
        } else {
            properties = new Properties();
            for (Map.Entry<? extends String, ? extends String> e : props.entrySet()) {
                properties.put(e.getKey(), e.getValue());
            }
        }
        return update(properties);
    }

    public boolean update(Properties properties) {
        boolean modified = false;
        // Remove "removed" properties from the cfg file
        for (String key : new ArrayList<String>(this.keySet())) {
            if (!properties.containsKey(key)) {
                this.remove(key);
                modified = true;
            }
        }
        // Update existing keys
        for (String key : properties.keySet()) {
            String v = this.get(key);
            List<String> comments = properties.getComments(key);
            List<String> value = properties.getRaw(key);
            if (v == null) {
                this.put(key, comments, value);
                modified = true;
            } else if (!v.equals(properties.get(key))) {
                if (comments.isEmpty()) {
                    comments = this.getComments(key);
                }
                this.put(key, comments, value);
                modified = true;
            }
        }
        return modified;
    }

>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
    public List<String> getRaw(String key) {
        if (layout.containsKey(key)) {
            if (layout.get(key).getValueLines() != null) {
                return new ArrayList<String>(layout.get(key).getValueLines());
            }
        }
        List<String> result = new ArrayList<String>();
        if (storage.containsKey(key)) {
            result.add(storage.get(key));
        }
        return result;
    }

<<<<<<< HEAD
=======
    public List<String> getComments(String key) {
        if (layout.containsKey(key)) {
            if (layout.get(key).getCommentLines() != null) {
                return new ArrayList<String>(layout.get(key).getCommentLines());
            }
        }
        return new ArrayList<String>();
    }

>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
    @Override
    public String remove(Object key) {
        Layout l = layout.get(key);
        if (l != null) {
            l.clearValue();
        }
        return storage.remove(key);
    }

    @Override
    public void clear() {
        for (Layout l : layout.values()) {
            l.clearValue();
        }
        storage.clear();
    }

    /**
     * Return the comment header.
     *
     * @return the comment header
     */
    public List<String> getHeader()
    {
        return header;
    }

    /**
     * Set the comment header.
     *
     * @param header the header to use
     */
    public void setHeader(List<String> header)
    {
        this.header = header;
    }

    /**
     * Return the comment footer.
     *
     * @return the comment footer
     */
    public List<String> getFooter()
    {
        return footer;
    }

    /**
     * Set the comment footer.
     *
     * @param footer the footer to use
     */
    public void setFooter(List<String> footer)
    {
        this.footer = footer;
    }

    /**
     * Reads a properties file and stores its internal structure. The found
     * properties will be added to the associated configuration object.
     *
     * @param in the reader to the properties file
     * @throws java.io.IOException if an error occurs
     */
<<<<<<< HEAD
    protected void loadLayout(Reader in) throws IOException
    {
        PropertiesReader reader = new PropertiesReader(in);
        while (reader.nextProperty())
        {
=======
    protected void loadLayout(Reader in, boolean maybeTyped) throws IOException
    {
        PropertiesReader reader = new PropertiesReader(in, maybeTyped);
        boolean hasProperty = false;
        while (reader.nextProperty())
        {
            hasProperty = true;
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
            storage.put(reader.getPropertyName(), reader.getPropertyValue());
            int idx = checkHeaderComment(reader.getCommentLines());
            layout.put(reader.getPropertyName(),
                    new Layout(idx < reader.getCommentLines().size() ?
                                    new ArrayList<String>(reader.getCommentLines().subList(idx, reader.getCommentLines().size())) :
                                    null,
                               new ArrayList<String>(reader.getValueLines())));
        }
<<<<<<< HEAD
        footer = new ArrayList<String>(reader.getCommentLines());
        if(context != null)
        {
            InterpolationHelper.performSubstitution(storage, context);
        }
        else {
            InterpolationHelper.performSubstitution(storage);
        }
=======
        typed = maybeTyped && reader.typed != null && reader.typed;
        if (!typed) {
            for (Map.Entry<String,String> e : storage.entrySet()) {
                e.setValue(unescapeJava(e.getValue()));
            }
        }
        if (hasProperty) {
            footer = new ArrayList<String>(reader.getCommentLines());
        } else {
            header = new ArrayList<String>(reader.getCommentLines());
        }
        if (substitute)
        {
            substitute();
        }
    }

    public void substitute()
    {
        substitute(callback);
    }

    public void substitute(InterpolationHelper.SubstitutionCallback callback)
    {
        if (callback == null)
        {
            callback = new InterpolationHelper.BundleContextSubstitutionCallback(null);
        }
        InterpolationHelper.performSubstitution(storage, callback);
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
    }

    /**
     * Writes the properties file to the given writer, preserving as much of its
     * structure as possible.
     *
     * @param out the writer
     * @throws java.io.IOException if an error occurs
     */
<<<<<<< HEAD
    protected void saveLayout(Writer out) throws IOException
    {
        PropertiesWriter writer = new PropertiesWriter(out);
=======
    protected void saveLayout(Writer out, boolean typed) throws IOException
    {
        PropertiesWriter writer = new PropertiesWriter(out, typed);
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
        if (header != null)
        {
            for (String s : header)
            {
                writer.writeln(s);
            }
        }

        for (String key : storage.keySet())
        {
            Layout l = layout.get(key);
            if (l != null && l.getCommentLines() != null)
            {
                for (String s : l.getCommentLines())
                {
                    writer.writeln(s);
                }
            }
            if (l != null && l.getValueLines() != null)
            {
<<<<<<< HEAD
                for (String s : l.getValueLines())
                {
                    writer.writeln(s);
=======
                for (int i = 0; i < l.getValueLines().size(); i++)
                {
                    String s = l.getValueLines().get(i);
                    if (i < l.getValueLines().size() - 1)
                    {
                        writer.writeln(s + "\\");
                    }
                    else
                    {
                        writer.writeln(s);
                    }
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
                }
            }
            else
            {
                writer.writeProperty(key, storage.get(key));
            }
        }
        if (footer != null)
        {
            for (String s : footer)
            {
                writer.writeln(s);
            }
        }
        writer.flush();
    }

    /**
     * Checks if parts of the passed in comment can be used as header comment.
     * This method checks whether a header comment can be defined (i.e. whether
     * this is the first comment in the loaded file). If this is the case, it is
     * searched for the lates blank line. This line will mark the end of the
     * header comment. The return value is the index of the first line in the
     * passed in list, which does not belong to the header comment.
     *
     * @param commentLines the comment lines
     * @return the index of the next line after the header comment
     */
    private int checkHeaderComment(List<String> commentLines)
    {
        if (getHeader() == null && layout.isEmpty())
        {
            // This is the first comment. Search for blank lines.
            int index = commentLines.size() - 1;
            while (index >= 0 && commentLines.get(index).length() > 0)
            {
                index--;
            }
            setHeader(new ArrayList<String>(commentLines.subList(0, index + 1)));
            return index + 1;
        }
        else
        {
            return 0;
        }
    }

    /**
     * Tests whether a line is a comment, i.e. whether it starts with a comment
     * character.
     *
     * @param line the line
     * @return a flag if this is a comment line
     */
    static boolean isCommentLine(String line) {
        String s = line.trim();
        // blank lines are also treated as comment lines
        return s.length() < 1 || COMMENT_CHARS.indexOf(s.charAt(0)) >= 0;
    }

    /**
     * <p>Unescapes any Java literals found in the <code>String</code> to a
     * <code>Writer</code>.</p> This is a slightly modified version of the
     * StringEscapeUtils.unescapeJava() function in commons-lang that doesn't
     * drop escaped separators (i.e '\,').
     *
     * @param str  the <code>String</code> to unescape, may be null
     * @return the processed string
     * @throws IllegalArgumentException if the Writer is <code>null</code>
     */
    protected static String unescapeJava(String str) {
        if (str == null) {
            return null;
        }
        int sz = str.length();
        StringBuffer out = new StringBuffer(sz);
        StringBuffer unicode = new StringBuffer(UNICODE_LEN);
        boolean hadSlash = false;
        boolean inUnicode = false;
        for (int i = 0; i < sz; i++) {
            char ch = str.charAt(i);
            if (inUnicode) {
                // if in unicode, then we're reading unicode
                // values in somehow
                unicode.append(ch);
                if (unicode.length() == UNICODE_LEN) {
                    // unicode now contains the four hex digits
                    // which represents our unicode character
                    try {
                        int value = Integer.parseInt(unicode.toString(), HEX_RADIX);
                        out.append((char) value);
                        unicode.setLength(0);
                        inUnicode = false;
                        hadSlash = false;
                    } catch (NumberFormatException nfe) {
                        throw new IllegalArgumentException("Unable to parse unicode value: " + unicode, nfe);
                    }
                }
                continue;
            }

            if (hadSlash) {
                // handle an escaped value
                hadSlash = false;
                switch (ch) {
                    case '\\' :
                        out.append('\\');
                        break;
                    case '\'' :
                        out.append('\'');
                        break;
                    case '\"' :
                        out.append('"');
                        break;
                    case 'r' :
                        out.append('\r');
                        break;
                    case 'f' :
                        out.append('\f');
                        break;
                    case 't' :
                        out.append('\t');
                        break;
                    case 'n' :
                        out.append('\n');
                        break;
                    case 'b' :
                        out.append('\b');
                        break;
                    case 'u' :
                        // uh-oh, we're in unicode country....
                        inUnicode = true;
                        break;
                    default :
                        out.append(ch);
                        break;
                }
                continue;
            } else if (ch == '\\') {
                hadSlash = true;
                continue;
            }
            out.append(ch);
        }

        if (hadSlash) {
            // then we're in the weird case of a \ at the end of the
            // string, let's output it anyway.
            out.append('\\');
        }

        return out.toString();
    }

    /**
     * <p>Escapes the characters in a <code>String</code> using Java String rules.</p>
     *
     * <p>Deals correctly with quotes and control-chars (tab, backslash, cr, ff, etc.) </p>
     *
     * <p>So a tab becomes the characters <code>'\\'</code> and
     * <code>'t'</code>.</p>
     *
     * <p>The only difference between Java strings and JavaScript strings
     * is that in JavaScript, a single quote must be escaped.</p>
     *
<<<<<<< HEAD
     * <p>Example:
=======
     * <p>Example:</p>
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
     * <pre>
     * input string: He didn't say, "Stop!"
     * output string: He didn't say, \"Stop!\"
     * </pre>
<<<<<<< HEAD
     * </p>
=======
     *
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
     *
     * @param str  String to escape values in, may be null
     * @return String with escaped values, <code>null</code> if null string input
     */
    protected static String escapeJava(String str) {
        if (str == null) {
            return null;
        }
        int sz = str.length();
        StringBuffer out = new StringBuffer(sz * 2);
        for (int i = 0; i < sz; i++) {
            char ch = str.charAt(i);
            // handle unicode
            if (ch > 0xfff) {
                out.append("\\u").append(hex(ch));
            } else if (ch > 0xff) {
                out.append("\\u0").append(hex(ch));
            } else if (ch > 0x7f) {
                out.append("\\u00").append(hex(ch));
            } else if (ch < 32) {
                switch (ch) {
                    case '\b' :
                        out.append('\\');
                        out.append('b');
                        break;
                    case '\n' :
                        out.append('\\');
                        out.append('n');
                        break;
                    case '\t' :
                        out.append('\\');
                        out.append('t');
                        break;
                    case '\f' :
                        out.append('\\');
                        out.append('f');
                        break;
                    case '\r' :
                        out.append('\\');
                        out.append('r');
                        break;
                    default :
                        if (ch > 0xf) {
                            out.append("\\u00").append(hex(ch));
                        } else {
                            out.append("\\u000").append(hex(ch));
                        }
                        break;
                }
            } else {
                switch (ch) {
                    case '"' :
                        out.append('\\');
                        out.append('"');
                        break;
                    case '\\' :
                        out.append('\\');
                        out.append('\\');
                        break;
                    default :
                        out.append(ch);
                        break;
                }
            }
        }
        return out.toString();
    }

    /**
     * <p>Returns an upper case hexadecimal <code>String</code> for the given
     * character.</p>
     *
     * @param ch The character to convert.
     * @return An upper case hexadecimal <code>String</code>
     */
    protected static String hex(char ch) {
        return Integer.toHexString(ch).toUpperCase(Locale.ENGLISH);
    }

    /**
     * <p>Checks if the value is in the given array.</p>
     *
     * <p>The method returns <code>false</code> if a <code>null</code> array is passed in.</p>
     *
     * @param array  the array to search through
     * @param valueToFind  the value to find
     * @return <code>true</code> if the array contains the object
     */
    public static boolean contains(char[] array, char valueToFind) {
        if (array == null) {
            return false;
        }
        for (int i = 0; i < array.length; i++) {
            if (valueToFind == array[i]) {
                return true;
            }
        }
        return false;
    }

    /**
     * Escape the separators in the key.
     *
     * @param key the key
     * @return the escaped key
     */
    private static String escapeKey(String key)
    {
        StringBuffer newkey = new StringBuffer();

        for (int i = 0; i < key.length(); i++)
        {
            char c = key.charAt(i);

            if (contains(SEPARATORS, c) || contains(WHITE_SPACE, c))
            {
                // escape the separator
                newkey.append('\\');
                newkey.append(c);
            }
            else
            {
                newkey.append(c);
            }
        }

        return newkey.toString();
    }

    /**
     * This class is used to read properties lines. These lines do
     * not terminate with new-line chars but rather when there is no
     * backslash sign a the end of the line.  This is used to
     * concatenate multiple lines for readability.
     */
    public static class PropertiesReader extends LineNumberReader
    {
        /** Stores the comment lines for the currently processed property.*/
        private final List<String> commentLines;

        /** Stores the value lines for the currently processed property.*/
        private final List<String> valueLines;

        /** Stores the name of the last read property.*/
        private String propertyName;

        /** Stores the value of the last read property.*/
        private String propertyValue;

<<<<<<< HEAD
=======
        private boolean maybeTyped;

        /** Stores if the properties are typed or not */
        Boolean typed;

>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
        /**
         * Creates a new instance of <code>PropertiesReader</code> and sets
         * the underlaying reader and the list delimiter.
         *
         * @param reader the reader
         */
<<<<<<< HEAD
        public PropertiesReader(Reader reader)
=======
        public PropertiesReader(Reader reader, boolean maybeTyped)
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
        {
            super(reader);
            commentLines = new ArrayList<String>();
            valueLines = new ArrayList<String>();
<<<<<<< HEAD
=======
            this.maybeTyped = maybeTyped;
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
        }

        /**
         * Reads a property line. Returns null if Stream is
         * at EOF. Concatenates lines ending with "\".
         * Skips lines beginning with "#" or "!" and empty lines.
         * The return value is a property definition (<code>&lt;name&gt;</code>
         * = <code>&lt;value&gt;</code>)
         *
         * @return A string containing a property value or null
         *
         * @throws java.io.IOException in case of an I/O error
         */
        public String readProperty() throws IOException
        {
            commentLines.clear();
            valueLines.clear();
            StringBuffer buffer = new StringBuffer();

            while (true)
            {
                String line = readLine();
                if (line == null)
                {
                    // EOF
                    return null;
                }

                if (isCommentLine(line))
                {
                    commentLines.add(line);
                    continue;
                }

<<<<<<< HEAD
=======
                boolean combine = checkCombineLines(line);
                if (combine)
                {
                    line = line.substring(0, line.length() - 1);
                }
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
                valueLines.add(line);
                while (line.length() > 0 && contains(WHITE_SPACE, line.charAt(0)))
                {
                    line = line.substring(1, line.length());
                }
<<<<<<< HEAD

                if (checkCombineLines(line))
                {
                    line = line.substring(0, line.length() - 1);
                    buffer.append(line);
                }
                else
                {
                    buffer.append(line);
=======
                buffer.append(line);
                if (!combine)
                {
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
                    break;
                }
            }
            return buffer.toString();
        }

        /**
         * Parses the next property from the input stream and stores the found
         * name and value in internal fields. These fields can be obtained using
         * the provided getter methods. The return value indicates whether EOF
         * was reached (<b>false</b>) or whether further properties are
         * available (<b>true</b>).
         *
         * @return a flag if further properties are available
         * @throws java.io.IOException if an error occurs
         */
        public boolean nextProperty() throws IOException
        {
            String line = readProperty();

            if (line == null)
            {
                return false; // EOF
            }

            // parse the line
            String[] property = parseProperty(line);
<<<<<<< HEAD
            propertyName = unescapeJava(property[0]);
            propertyValue = unescapeJava(property[1]);
=======
            boolean typed = false;
            if (maybeTyped && property[1].length() >= 2) {
                typed = property[1].matches("\\s*[TILFDXSCBilfdxscb]?(\\[[\\S\\s]*\\]|\\{[\\S\\s]*\\}|\"[\\S\\s]*\")\\s*");
            }
            if (this.typed == null) {
                this.typed = typed;
            } else {
                this.typed = this.typed & typed;
            }
            propertyName = unescapeJava(property[0]);
            propertyValue = property[1];
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
            return true;
        }

        /**
         * Returns the comment lines that have been read for the last property.
         *
         * @return the comment lines for the last property returned by
         * <code>readProperty()</code>
         */
        public List<String> getCommentLines()
        {
            return commentLines;
        }

        /**
         * Returns the value lines that have been read for the last property.
         *
         * @return the raw value lines for the last property returned by
         * <code>readProperty()</code>
         */
        public List<String> getValueLines()
        {
            return valueLines;
        }

        /**
         * Returns the name of the last read property. This method can be called
         * after <code>{@link #nextProperty()}</code> was invoked and its
         * return value was <b>true</b>.
         *
         * @return the name of the last read property
         */
        public String getPropertyName()
        {
            return propertyName;
        }

        /**
         * Returns the value of the last read property. This method can be
         * called after <code>{@link #nextProperty()}</code> was invoked and
         * its return value was <b>true</b>.
         *
         * @return the value of the last read property
         */
        public String getPropertyValue()
        {
            return propertyValue;
        }

        /**
         * Checks if the passed in line should be combined with the following.
         * This is true, if the line ends with an odd number of backslashes.
         *
         * @param line the line
         * @return a flag if the lines should be combined
         */
        private static boolean checkCombineLines(String line)
        {
            int bsCount = 0;
            for (int idx = line.length() - 1; idx >= 0 && line.charAt(idx) == '\\'; idx--)
            {
                bsCount++;
            }

            return bsCount % 2 != 0;
        }

        /**
         * Parse a property line and return the key and the value in an array.
         *
         * @param line the line to parse
         * @return an array with the property's key and value
         */
        private static String[] parseProperty(String line)
        {
            // sorry for this spaghetti code, please replace it as soon as
            // possible with a regexp when the Java 1.3 requirement is dropped

            String[] result = new String[2];
            StringBuffer key = new StringBuffer();
            StringBuffer value = new StringBuffer();

            // state of the automaton:
            // 0: key parsing
            // 1: antislash found while parsing the key
            // 2: separator crossing
            // 3: white spaces
            // 4: value parsing
            int state = 0;

            for (int pos = 0; pos < line.length(); pos++)
            {
                char c = line.charAt(pos);

                switch (state)
                {
                    case 0:
                        if (c == '\\')
                        {
                            state = 1;
                        }
                        else if (contains(WHITE_SPACE, c))
                        {
                            // switch to the separator crossing state
                            state = 2;
                        }
                        else if (contains(SEPARATORS, c))
                        {
                            // switch to the value parsing state
                            state = 3;
                        }
                        else
                        {
                            key.append(c);
                        }

                        break;

                    case 1:
                        if (contains(SEPARATORS, c) || contains(WHITE_SPACE, c))
                        {
                            // this is an escaped separator or white space
                            key.append(c);
                        }
                        else
                        {
                            // another escaped character, the '\' is preserved
                            key.append('\\');
                            key.append(c);
                        }

                        // return to the key parsing state
                        state = 0;

                        break;

                    case 2:
                        if (contains(WHITE_SPACE, c))
                        {
                            // do nothing, eat all white spaces
                            state = 2;
                        }
                        else if (contains(SEPARATORS, c))
                        {
                            // switch to the value parsing state
                            state = 3;
                        }
                        else
                        {
                            // any other character indicates we encoutered the beginning of the value
                            value.append(c);

                            // switch to the value parsing state
                            state = 4;
                        }

                        break;

                    case 3:
                        if (contains(WHITE_SPACE, c))
                        {
                            // do nothing, eat all white spaces
                            state = 3;
                        }
                        else
                        {
                            // any other character indicates we encoutered the beginning of the value
                            value.append(c);

                            // switch to the value parsing state
                            state = 4;
                        }

                        break;

                    case 4:
                        value.append(c);
                        break;
                }
            }

            result[0] = key.toString();
            result[1] = value.toString();

            return result;
        }
    } // class PropertiesReader

    /**
     * This class is used to write properties lines.
     */
    public static class PropertiesWriter extends FilterWriter
    {
<<<<<<< HEAD
=======
        private boolean typed;

>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
        /**
         * Constructor.
         *
         * @param writer a Writer object providing the underlying stream
         */
<<<<<<< HEAD
        public PropertiesWriter(Writer writer)
        {
            super(writer);
=======
        public PropertiesWriter(Writer writer, boolean typed)
        {
            super(writer);
            this.typed = typed;
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
        }

        /**
         * Writes the given property and its value.
         *
         * @param key the property key
         * @param value the property value
         * @throws java.io.IOException if an error occurs
         */
        public void writeProperty(String key, String value) throws IOException
        {
            write(escapeKey(key));
            write(" = ");
<<<<<<< HEAD
            write(escapeJava(value));
=======
            write(typed ? value : escapeJava(value));
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
            writeln(null);
        }

        /**
         * Helper method for writing a line with the platform specific line
         * ending.
         *
         * @param s the content of the line (may be <b>null</b>)
         * @throws java.io.IOException if an error occurs
         */
        public void writeln(String s) throws IOException
        {
            if (s != null)
            {
                write(s);
            }
            write(LINE_SEPARATOR);
        }

    } // class PropertiesWriter

    /**
     * TODO
     */
    protected static class Layout {

        private List<String> commentLines;
        private List<String> valueLines;

        public Layout() {
        }

        public Layout(List<String> commentLines, List<String> valueLines) {
            this.commentLines = commentLines;
            this.valueLines = valueLines;
        }

        public List<String> getCommentLines() {
            return commentLines;
        }

        public void setCommentLines(List<String> commentLines) {
            this.commentLines = commentLines;
        }

        public List<String> getValueLines() {
            return valueLines;
        }

        public void setValueLines(List<String> valueLines) {
            this.valueLines = valueLines;
        }

        public void clearValue() {
            this.valueLines = null;
        }

    } // class Layout

<<<<<<< HEAD
}
=======
}
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
