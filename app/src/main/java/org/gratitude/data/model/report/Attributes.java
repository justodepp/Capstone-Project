
package org.gratitude.data.model.report;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Attributes {

    @SerializedName("xml:base")
    private String xmlBase;

    public String getXmlBase() {
        return xmlBase;
    }

    public static class Builder {

        private String xmlBase;

        public Attributes.Builder withXmlBase(String xmlBase) {
            this.xmlBase = xmlBase;
            return this;
        }

        public Attributes build() {
            Attributes attributes = new Attributes();
            attributes.xmlBase = xmlBase;
            return attributes;
        }

    }

}
