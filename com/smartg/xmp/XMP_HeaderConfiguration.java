/*
 * Copyright (c) Andrey Kuznetsov. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  o Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  o Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  o Neither the name of imagero Andrey Kuznetsov nor the names of
 *    its contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.smartg.xmp;

public class XMP_HeaderConfiguration {

    String namePrefix = "xmp";
    String shemas = "Shemas";
    String shema = "Shema";
    String property = "Property";
    String choice = "Choice";
    String valueTypes = "ValueTypes";
    String valueType = "ValueType";

    String url = "http://www.imagero.com/";

    public String createHeader() {

        String header = "<x:xmpmeta xmlns:x=\"adobe:ns:meta/\" x:xmptk=\"Adobe XMP Core 4.1.1\">"
    	    + " <rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"> <rdf:Description rdf:about=\"\"";

        StringBuffer sb = new StringBuffer(header);
        sb.append(" xmlns:");
        sb.append(namePrefix);
        sb.append(shemas);
        sb.append("=\"");
        sb.append(getUrl(shemas));
        sb.append("\"");

        sb.append(" xmlns:");
        sb.append(namePrefix);
        sb.append(shema);
        sb.append("=\"");
        sb.append(getUrl(shema + "#"));
        sb.append("\"");

        sb.append(" xmlns:");
        sb.append(namePrefix);
        sb.append(property);
        sb.append("=\"");
        sb.append(getUrl(property + "#"));
        sb.append("\"");

        sb.append(" xmlns:");
        sb.append(namePrefix);
        sb.append(choice);
        sb.append("=\"");
        sb.append(getUrl(choice + "#"));
        sb.append("\"");

        sb.append(" xmlns:");
        sb.append(namePrefix);
        sb.append(valueTypes);
        sb.append("=\"");
        sb.append(getUrl(valueTypes));
        sb.append("\"");

        sb.append(" xmlns:");
        sb.append(namePrefix);
        sb.append(valueType);
        sb.append("=\"");
        sb.append(getUrl(valueType + "#"));
        sb.append("\"");

        sb.append(">");

        return sb.toString();
    }

    public String getNamePrefix() {
        return namePrefix;
    }

    public void setNamePrefix(String namePrefix) {
        this.namePrefix = namePrefix;
    }

    public String getUrl(String name) {
        return url + "xmp/ns/" + name.toLowerCase();
    }

    public String getShemas() {
        return shemas;
    }

    public void setShemas(String xmpShemas) {
        this.shemas = xmpShemas;
    }

    public String getShema() {
        return shema;
    }

    public void setShema(String xmpShema) {
        this.shema = xmpShema;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String xmpProperty) {
        this.property = xmpProperty;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public String getValueTypes() {
        return valueTypes;
    }

    public void setValueTypes(String xmpValueTypes) {
        this.valueTypes = xmpValueTypes;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String xmpValueType) {
        this.valueType = xmpValueType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        if (!url.startsWith("http://")) {
    	if (url.startsWith("www.")) {
    	    url = "http://" + url;
    	} else {
    	    throw new IllegalArgumentException("URL must start with \"http://\".");
    	}
        }
        if (!url.endsWith("/")) {
    	url = url + "/";
        }
        this.url = url;
    }
}