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
import java.util.logging.Logger;

import com.adobe.xmp.XMPException;
import com.adobe.xmp.XMPMeta;
import com.adobe.xmp.XMPMetaFactory;
import com.adobe.xmp.options.PropertyOptions;
import com.adobe.xmp.properties.XMPProperty;
import com.smartg.xmp.ValueType.ArrayType;
import com.smartg.xmp.ValueTypes.Structure;

/**
 * Date: 08.10.2007
 * 
 * XMPCollection provides easy way to fill XMP tree. With only field name and
 * prefix (optional!) XMPCollection calls correct method from XMPMeta.
 * 
 * 
 * @author Andrey Kuznetsov
 */
public class XMPCollection {

    XMPMeta xmpMeta = XMPMetaFactory.create();
    String lastError;

    static {
	XMPShema.init();
    }

    public XMPCollection() {
	this(XMPMetaFactory.create());
    }

    public XMPCollection(XMPMeta xmpMeta) {
	this.xmpMeta = xmpMeta;
    }

    public boolean isLocalizedField(String prefix, String name) throws XMPException {
	String ns = XMPShema.getNameSpace(prefix, name);
	if (ns == null) {
	    String message = "Namespace not found for " + prefix + ":" + name;
	    throw new XMPException(message, 0);
	}
	XmpProperty property = XMPShema.getProperty(prefix, name);
	if (property == null) {
	    String message = "Property not found for " + prefix + ":" + name;
	    throw new XMPException(message, 0);
	}
	ValueType valueType = property.getValueType();
	if (valueType instanceof ValueTypes.Array) {
	    ValueTypes.Array array = (ValueTypes.Array) valueType;
	    if (array.arrayType == ArrayType.LangAlt) {
		return true;
	    }
	}
	return false;
    }

    public String getLastError() {
	return lastError;
    }

    /**
     * This method is used to add localized text and only works with type
     * "lang alt"
     * 
     * @param prefix
     *            namespace prefix (like "dc", "tiff", "exif", "photoshop"
     * @param name
     *            field name
     * @param lang
     *            language identifier
     * @param value
     *            field text
     * @return true on success, false if adding failed (in this case check
     *         lastError).
     */
    public boolean add(String prefix, String name, String lang, String value) {
	lastError = "";
	String ns = XMPShema.getNameSpace(prefix, name);
	if (ns == null) {
	    lastError = "Namespace not found for " + prefix + ":" + name;
	    return false;
	}
	XmpProperty property = XMPShema.getProperty(prefix, name);
	if (property == null) {
	    lastError = "Property not found for " + prefix + ":" + name;
	    return false;
	}
	String prop = property.getProperty();

	try {
	    ValueType valueType = property.getValueType();
	    if (valueType instanceof ValueTypes.Array) {
		ValueTypes.Array array = (ValueTypes.Array) valueType;
		switch (array.arrayType) {
		case Bag:
		case Seq:
		case Alt:
		    lastError = "Lang alt type expected";
		    return false;
		case LangAlt:
		    String genLang = "";
		    if (lang != null && lang.length() > 0) {
			if ("x-default".equalsIgnoreCase(lang)) {

			} else {
			    int k = lang.indexOf("-");
			    if (k > 0) {
				genLang = lang.substring(0, k - 1);
			    } else {
				genLang = lang;
			    }
			}
		    }
		    xmpMeta.setLocalizedText(ns, prop, genLang, lang, value);
		    break;
		default:
		    lastError = "Unsupported ArrayType: " + array.arrayType;
		    return false;
		}
	    } else {
		lastError = "Lang alt type expected";
		return false;
	    }
	} catch (XMPException ex) {
	    lastError = ex.getMessage();
	    return false;
	}
	return true;
    }

    public boolean findPrefixAndAdd(String name, String value) {
	String[] prefixes = XMPShema.getPrefixes(name);
	if (prefixes != null && prefixes.length > 0) {
	    return add(prefixes[0], name, value);
	}
	lastError = "Prefix for field '" + name + "' not found";
	return false;
    }

    public boolean findPrefixAndAdd(String name, String lang, String value) {
	String[] prefixes = XMPShema.getPrefixes(name);
	if (prefixes != null && prefixes.length > 0) {
	    return add(prefixes[0], name, lang, value);
	}
	lastError = "Prefix for field '" + name + "' not found";
	return false;
    }

    /**
     * get field values from xmp
     * 
     * @param prefix
     *            namespace prefix
     * @param name
     *            field name
     * @return field value(s) or null (check lastError in this case). IF field
     *         is array, then all fields in array are returned, if field is
     *         structure, then values of all structure fields are returned
     */
    public String[] get(String prefix, String name) {
	String[] res = {};
	lastError = "";
	String ns = XMPShema.getNameSpace(prefix, name);
	if (ns == null) {
	    lastError = "Namespace not found for " + prefix + ":" + name;
	    return res;
	}
	XmpProperty property = XMPShema.getProperty(prefix, name);
	if (property == null) {
	    lastError = "Property not found for " + prefix + ":" + name;
	    return res;
	}
	String prop = property.getProperty();
	ValueType valueType = property.getValueType();
	if (valueType instanceof ValueTypes.Array) {
	    // ValueTypes.Array array = (ValueTypes.Array) valueType;
	    try {
		int count = xmpMeta.countArrayItems(ns, prop);
		res = new String[count];
		for (int i = 0; i < count; i++) {
		    res[i] = xmpMeta.getArrayItem(ns, prop, i + 1).getValue().toString();
		}
	    } catch (XMPException ex) {
		lastError = ex.getMessage();
	    }
	} else if (valueType instanceof Structure) {
	    ValueTypes.Structure str = (Structure) valueType;
	    ArrayList<ValueType> types = str.types;
	    res = new String[types.size()];

	    for (int k = 0; k < types.size(); k++) {
		ValueType t = types.get(k);
		try {
		    XMPProperty structField = xmpMeta.getStructField(ns, str.name, ns, t.name);
		    if (structField != null) {
			Object value = structField.getValue();
			if (value != null) {
			    res[k] = String.valueOf(value);
			}
		    } else {
			// res[k] = t.name + ":";
		    }
		} catch (XMPException ex) {
		    lastError = ex.getMessage();
		    // res[k] = lastError;
		}
	    }
	} else {
	    res = new String[1];
	    try {
		res[0] = xmpMeta.getPropertyString(ns, prop);
	    } catch (XMPException ex) {
		lastError = ex.getMessage();
		return new String[0];
	    }
	}

	return res;
    }

    /**
     * Add field to xmp meta
     * 
     * @param prefix
     *            namespace prefix (like "dc", "tiff", "exif", "photoshop"
     * @param name
     *            field name
     * @param value
     *            field text
     * @return true on success, false if adding failed (in this case check
     *         lastError).
     */
    public boolean add(String prefix, String name, String value) {
	lastError = "";
	String ns = XMPShema.getNameSpace(prefix, name);
	if (ns == null) {
	    lastError = "Namespace not found for " + prefix + ":" + name;
	    return false;
	}
	XmpProperty property = XMPShema.getProperty(prefix, name);
	if (property == null) {
	    lastError = "Property not found for " + prefix + ":" + name;
	    return false;
	}
	String prop = property.getProperty();
	try {
	    ValueType valueType = property.getValueType();
	    if (valueType instanceof ValueTypes.Array) {
		ValueTypes.Array array = (ValueTypes.Array) valueType;
		switch (array.arrayType) {
		case Bag:
		    xmpMeta.appendArrayItem(ns, prop, new PropertyOptions().setArray(true), value, null);
		    break;
		case Seq:
		    xmpMeta.appendArrayItem(ns, prop, new PropertyOptions().setArrayOrdered(true), value, null);
		    break;
		case Alt:
		    xmpMeta.appendArrayItem(ns, prop, new PropertyOptions().setArrayAlternate(true), value, null);
		    break;
		case LangAlt:
		    xmpMeta.setLocalizedText(ns, prop, "", "x-default", value);
		    break;
		default:
		    lastError = "Unsupported ArrayType: " + array.arrayType;
		    return false;
		}
	    } else if (property.getMemberOf() != null) {
		ValueTypes.Structure s = property.getMemberOf();
		xmpMeta.setStructField(ns, s.name, ns, name, value);
		return true;
	    } else {
		xmpMeta.setProperty(ns, prop, value);
	    }
	} catch (XMPException ex) {
	    lastError = ex.getMessage();
	    return false;
	}
	return true;
    }

    // public static void setProperty(XMPMeta xmpMeta, XMPShema.Property
    // property, String value) throws XMPException {
    // xmpMeta.setProperty(property.getNamespace(), property.getProperty(),
    // value);
    // }

    /**
     * @param about
     * @return
     */
    public String toXMP(String about) {
	try {
	    return XMP.toString(xmpMeta, false, false);
	} catch (XMPException ex) {
	    Logger.getGlobal().throwing("XMPCollection", "toXMP", ex);
	    return null;
	}
    }

    /**
     * get XMPMeta (clone of current XMPMeta is returned)
     * 
     * @return XMPMeta
     */
    public XMPMeta getXmpMeta() {
	return (XMPMeta) xmpMeta.clone();
    }
}
