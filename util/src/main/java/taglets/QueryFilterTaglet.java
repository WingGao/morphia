/*
 * Copyright (c) 2008-2014 MongoDB, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package taglets;

import com.sun.source.doctree.DocTree;
import com.sun.source.doctree.UnknownBlockTagTree;

import java.util.List;

import javax.lang.model.element.Element;

/**
 * Provides a taglet for linking to the MongoDB manual pages
 *
 * @see <a href="http://docs.mongodb.org/manual/">the MongoDB manual</a>
 */
public class QueryFilterTaglet extends ManualTaglet {

    @Override
    public String getName() {
        return "query.filter";
    }

    @Override
    protected String getBaseDocURI() {
        return super.getBaseDocURI() + "reference/operator/query/";
    }

    @Override
    public String toString(List<? extends DocTree> tags, Element element) {
        if (tags.size() == 0) {
            return null;
        }

        StringBuilder buf = new StringBuilder(String.format("<dl><dt><span class=\"strong\">%s</span></dt>", getHeader()));
        for (DocTree tag : tags) {
            String text = ((UnknownBlockTagTree) tag).getContent().get(0).toString();
            buf.append("<dd>").append(genLink(text)).append("</dd>");
        }
        return buf.toString();
    }

    protected String genLink(String text) {
        return String.format("<a href='%s%s'>Query Filter:  %s</a>", getBaseDocURI(), text.replace("$", ""), text);
    }

}
