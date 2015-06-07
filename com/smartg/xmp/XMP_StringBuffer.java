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


class XMP_StringBuffer {
    StringBuffer sb = new StringBuffer();
    XMP_HeaderConfiguration hc;

    public XMP_StringBuffer(XMP_HeaderConfiguration hc) {
        this.hc = hc;
    }

    void startTag(String s) {
        sb.append("<");
        sb.append(s);
    }

    void endTag(String s) {
        sb.append("</");
        sb.append(s);
    }

    void addTag(String prefix, String name, String value) {
        prefix = hc.namePrefix + prefix;
        startTag(prefix);
        sb.append(":");
        sb.append(name);
        sb.append(">");

        sb.append(value);

        endTag(prefix);
        sb.append(":");
        sb.append(name);
        sb.append(">\n");
    }

    void append(String s) {
        sb.append(s);
    }

    void appendListElement(String qualifier, String s) {
        if (qualifier == null) {
    	qualifier = "";
        }
        if (qualifier == "") {
    	sb.append("<rdf:li>");
        } else {
    	sb.append("<rdf:li ");
    	sb.append(qualifier);
    	sb.append(">");
        }

        sb.append(s);
        sb.append("</rdf:li>");
    }

    @Override
    public String toString() {
        return sb.toString();
    }
}