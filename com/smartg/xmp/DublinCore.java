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

import java.util.Enumeration;

import com.smartg.xmp.ValueType.ArrayType;
import com.smartg.xmp.ValueType.TypeV;
import com.smartg.xmp.ValueTypes.OpenChoice;
import com.smartg.xmp.ValueTypes.Structure;

/**
 * @author Andrey Kuznetsov
 */
public class DublinCore extends XMPShema {

    public static final DublinCore SHEMA = new DublinCore();

    public static final String NAME = "DublinCore";

    private static final String ns = "http://purl.org/dc/elements/1.1/";
    private static final String prefix = "dc";

    private DublinCore() {
	super(ns, prefix, NAME);
    }

    
    @Override
    protected void initProperties() {
	Properties.CONTRIBUTOR.delegate.set("contributor", EXTERNAL, ArrayType.Bag, TypeV.ProperName, "Contributors to the resource (other than the authors).");
	Properties.COVERAGE.delegate.set("coverage", EXTERNAL, TypeV.Text, "The extent or scope of the resource.");
	Properties.CREATOR.delegate.set("creator", EXTERNAL, ArrayType.Seq, TypeV.ProperName,
		"The authors of the resource (listed in order of precedence, if significant).");
	Properties.DATE.delegate.set("date", EXTERNAL, ArrayType.Seq, TypeV.Date, "Date(s) that something interesting happened to the resource.");
	Properties.DESCRIPTION.delegate.set("description", EXTERNAL, ArrayType.LangAlt, TypeV.Text, "A textual description of the content of the resource. "
		+ "Multiple values may be present for different languages.");
	Properties.FORMAT.delegate.set("format", INTERNAL, TypeV.MIMEType, "The extent or scope of the resource.");
	Properties.IDENTIFIER.delegate.set("identifier", EXTERNAL, TypeV.Text, "Unique identifier of the resource.");
	Properties.LANGUAGE.delegate.set("language", EXTERNAL, ArrayType.Bag, TypeV.OpenChoice,
		"An unordered array specifying the languages used in the resource.");
	Properties.PUBLISHER.delegate.set("publisher", EXTERNAL, ArrayType.Bag, TypeV.ProperName, "Publishers");
	Properties.RELATION.delegate.set("relation", EXTERNAL, ArrayType.Bag, TypeV.Text, "Relationships to other documents.");
	Properties.RIGHTS.delegate.set("rights", EXTERNAL, ArrayType.LangAlt, TypeV.Text, "Informal rights statement, selected by language.");
	Properties.SOURCE.delegate.set("source", EXTERNAL, TypeV.Text, "Unique identifier of the work from which this resource was derived.");
	Properties.SUBJECT.delegate.set("subject", EXTERNAL, ArrayType.Bag, TypeV.Text, "An unordered array of descriptive phrases or keywords that specify "
		+ "the topic of the content of the resource.");
	Properties.TITLE.delegate.set("title", EXTERNAL, ArrayType.LangAlt, TypeV.Text, "The title of the document, or the name given to the resource. "
		+ "Typically, it will be a name by which the resource is formally known.");
	Properties.TYPE.delegate.set("type", EXTERNAL, ArrayType.Bag, new OpenChoice<ValueTypes.Text>(TypeV.Text), "A document type; for example, novel, poem, or working paper.");	
    }


    public XmpProperty[] properties() {
	return Properties.values();
    }

    public enum Properties implements XmpProperty {
	CONTRIBUTOR, COVERAGE, CREATOR, DATE, DESCRIPTION, FORMAT, IDENTIFIER, LANGUAGE, PUBLISHER, RELATION, RIGHTS, SOURCE, SUBJECT, TITLE, TYPE;

	XmpPropertyImpl delegate = new XmpPropertyImpl();

	private Properties() {

	}

	public XmpProperty addQualifier(XmpProperty q) {
	    return delegate.addQualifier(q);
	}

	public Category getCategory() {
	    return delegate.getCategory();
	}

	public String getDescription() {
	    return delegate.getDescription();
	}

	public Structure getMemberOf() {
	    return delegate.getMemberOf();
	}

	public String getNamespace() {
	    return delegate.getNamespace();
	}

	public String getPrefix() {
	    return delegate.getPrefix();
	}

	public String getProperty() {
	    return delegate.getProperty();
	}

	public XMPShema getShema() {
	    return delegate.getShema();
	}

	public ValueType getValueType() {
	    return delegate.getValueType();
	}

	public boolean hasQualifiers() {
	    return delegate.hasQualifiers();
	}

	public boolean isArray() {
	    return delegate.isArray();
	}

	public boolean isStructure() {
	    return delegate.isStructure();
	}

	public Enumeration<XmpProperty> qualifiers() {
	    return delegate.qualifiers();
	}

	public XmpProperty setMemberOf(Structure memberOf) {
	    return delegate.setMemberOf(memberOf);
	}

	public XmpProperty setValueType(ValueType type) {
	    return delegate.setValueType(type);
	}

	public XmpProperty setValueType(TypeV type) {
	    return delegate.setValueType(type);
	}

	public XmpProperty setValueType(ArrayType arrayType, TypeV elementType) {
	    return delegate.setValueType(arrayType, elementType);
	}

	public PathElement getChildAt(int index) {
	    return delegate.getChildAt(index);
	}

	public int getChildrenCount() {
	    return delegate.getChildrenCount();
	}

	public PathElement getParent() {
	    return delegate.getParent();
	}

	public void setShema(XMPShema shema) {
	    delegate.setShema(shema);
	}
    }
}
