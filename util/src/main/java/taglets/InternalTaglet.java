package taglets;

import com.sun.source.doctree.DocTree;

import java.util.List;
import java.util.Set;

import javax.lang.model.element.Element;

import static jdk.javadoc.doclet.Taglet.Location.CONSTRUCTOR;
import static jdk.javadoc.doclet.Taglet.Location.FIELD;
import static jdk.javadoc.doclet.Taglet.Location.METHOD;
import static jdk.javadoc.doclet.Taglet.Location.PACKAGE;
import static jdk.javadoc.doclet.Taglet.Location.TYPE;

public class InternalTaglet extends DocTaglet {
    @Override
    public Set<Location> getAllowedLocations() {
        return Set.of(METHOD, TYPE, PACKAGE, CONSTRUCTOR, FIELD);
    }

    @Override
    public String getName() {
        return "morphia.internal";
    }

    @Override
    public String toString(List<? extends DocTree> tags, Element element) {
        if (tags.isEmpty()) {
            return null;
        }

        String text = "<div class=\"deprecationBlock\"><span class=\"deprecatedLabel\">%s</span>"
                + " <div class=\"deprecationComment\">%s</div> </div>";

        return String.format(text, getHeader(), getMessage());
    }

    private String getMessage() {
        return "This is an internal item.  Its function and presence are subject to change without warning.  Its use is highly "
                + "discouraged.";
    }

    @Override
    protected String getHeader() {
        return "Developer note.";
    }

    @Override
    protected String getBaseDocURI() {
        return null;
    }
}
