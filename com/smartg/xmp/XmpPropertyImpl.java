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

import java.util.ArrayList;
import java.util.Enumeration;

import com.smartg.java.util.EmptyEnumeration;
import com.smartg.java.util.SafeIterator;
import com.smartg.xmp.ValueType.ArrayType;
import com.smartg.xmp.ValueType.TypeV;

public class XmpPropertyImpl implements XmpProperty {
    XMPShema shema;
    String property;
    ValueType valueType;
    Category category;
    String description;

    ValueTypes.Structure memberOf;

    ArrayList<XmpProperty> qualifiers;

    String namespace;
    String prefix;
    
    public XmpPropertyImpl() {
	
    }

//    public XmpPropertyImpl(String namespace, String prefix) {
//	this.namespace = namespace;
//	this.prefix = prefix;
//    }
    
    public XmpPropertyImpl(String namespace, String prefix, String property, Category category, String description) {
	this.namespace = namespace;
	this.prefix = prefix;
	this.property = property;
	this.category = category;
	this.description = description;
    }

    public XmpPropertyImpl(String property, Category category, String description) {
	this(null, null, property, category, description);
    }

    public XmpProperty setValueType(ValueType type) {
	this.valueType = type;
	this.valueType.parent = this;
	return this;
    }

    public XmpProperty setValueType(TypeV type) {
	this.valueType = ValueTypeFactory.getInstance().create(this, type);
	return this;
    }

//    public XmpProperty setValueType(ArrayType arrayType, ChoiceType elementType) {
//	this.valueType = ValueTypeFactory.getInstance().create(this, arrayType, elementType);
//	return this;
//    }

    public XmpProperty setValueType(ArrayType arrayType, TypeV elementType) {
	this.valueType = ValueTypeFactory.getInstance().create(this, arrayType, elementType);
	return this;
    }
    
    public XmpProperty setValueType(ArrayType arrayType, ValueType elementType) {
	this.valueType = ValueTypeFactory.getInstance().create(this, arrayType, elementType);
	return this;
    }


    public String toXMP(XMP_HeaderConfiguration hc) {
	XMP_StringBuffer sb = new XMP_StringBuffer(hc);
	sb.append("<rdf:li rdf:parseType=\"Resource\">\n");

	sb.addTag(hc.property, "name", getProperty());

	sb.addTag(hc.property, "category", getCategory().toString());

	sb.addTag(hc.property, "description", getDescription());

	ValueType vt = getValueType();
	if (vt instanceof ValueTypes.Structure) {
	    ValueTypes.Structure s = (ValueTypes.Structure) vt;
	    String xmp = s.toXMP(hc);
	    sb.append(xmp);
	} else {
	    sb.addTag(hc.property, "valueType", vt.toString());
	}

	sb.append("</rdf:li>\n");

	return sb.toString();
    }
    
    void set(String name, TypeV vt, String desc) {
	set(name, Category.EXTERNAL, vt, desc);
    }
    
    void set(String name, ValueType vt, String desc) {
	set(name, Category.EXTERNAL, vt, desc);
    }
    
    void set(String name, Category cat, ValueType vt, String desc) {
	property = name;
	category = cat;
	setValueType(vt);
	description = desc;
    }

    
    void set(String name, Category cat, TypeV vt, String desc) {
	property = name;
	category = cat;
	setValueType(vt);
	description = desc;
    }

    void set(String name, ArrayType vt, TypeV et, String desc) {
	set(name, Category.EXTERNAL, vt, et, desc);

    }
    
//    void set(String name, Category cat, ArrayType vt, ChoiceType ct, String desc) {
//	property = name;
//	category = cat;
//	setValueType(vt, ct);
//	description = desc;
//    }
    
    void set(String name, Category cat, ArrayType vt, TypeV et, String desc) {
	property = name;
	category = cat;
	setValueType(vt, et);
	description = desc;
    }
    
    void set(String name, Category cat, ArrayType vt, ValueType et, String desc) {
	property = name;
	category = cat;
	setValueType(vt, et);
	description = desc;
    }

    public String getProperty() {
	return property;
    }

    public ValueType getValueType() {
	return valueType;
    }

    public Category getCategory() {
	return category;
    }

    public String getDescription() {
	return description;
    }

    public boolean isArray() {
	return valueType instanceof ValueTypes.Array;
    }

    public boolean isStructure() {
	return valueType instanceof ValueTypes.Structure;
    }

    public XMPShema getShema() {
	return shema;
    }

    public String getPrefix() {
	if (prefix != null) {
	    return prefix;
	}
	if (shema != null) {
	    return shema.prefix;
	}
	return "";
    }

    public String getNamespace() {
	if (namespace != null) {
	    return namespace;
	}
	if (shema != null) {
	    return shema.namespace;
	}
	return "";
    }

    public ValueTypes.Structure getMemberOf() {
	return memberOf;
    }

    public XmpProperty setMemberOf(ValueTypes.Structure memberOf) {
	this.memberOf = memberOf;
	return this;
    }

    public XmpProperty addQualifier(XmpProperty q) {
	if (qualifiers == null) {
	    qualifiers = new ArrayList<XmpProperty>();
	}
	qualifiers.add(q);
	return this;
    }

    public Enumeration<XmpProperty> qualifiers() {
	if (qualifiers != null) {
	    return new SafeIterator<XmpProperty>(qualifiers.iterator());
	}
	return new EmptyEnumeration<XmpProperty>();
    }

    public boolean hasQualifiers() {
	return qualifiers != null && qualifiers.size() > 0;
    }

    public PathElement getChildAt(int index) {
	return valueType;
    }

    public int getChildrenCount() {
	return 1;
    }

    public PathElement getParent() {
	return shema;
    }

    public void setShema(XMPShema shema) {
	this.shema = shema;
    }
}