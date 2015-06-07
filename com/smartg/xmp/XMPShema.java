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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.adobe.xmp.XMPMetaFactory;
import com.adobe.xmp.XMPSchemaRegistry;
import com.smartg.java.util.HashBag;
import com.smartg.java.util.SafeIterator;

/**
 * @author Andrey Kuznetsov
 */
public abstract class XMPShema implements XMP_Shema {

    static String header0 = "<x:xmpmeta xmlns:x=\"adobe:ns:meta/\" x:xmptk=\"Adobe XMP Core 4.1.1\">"
	    + " <rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"> <rdf:Description rdf:about=\"\""
	    + " xmlns:xmpShemas=\"http://www.imagero.com/xmp/ns/shemas/\" xmlns:xmpSchema=\"http://www.imagero.com/xmp/ns/schema#\""
	    + " xmlns:xmpProperty=\"http://www.imagero.com/xmp/ns/property#\"" + " xmlns:choice=\"http://www.imagero.com/xmp/ns/choice#\""
	    + " xmlns:xmpValueTypes=\"http://www.imagero.com/xmp/ns/valueTypes\"" + " xmlns:xmpValueType=\"http://www.imagero.com/xmp/ns/valueType#\">";

    static String footer = " </rdf:Description> </rdf:RDF> </x:xmpmeta>";

    public static final Category INTERNAL = Category.INTERNAL;
    public static final Category EXTERNAL = Category.EXTERNAL;

    static final HashMap<String, XMPShema> shemas = new HashMap<String, XMPShema>();

    static final HashMap<String, String> nsMapping = new HashMap<String, String>();
    static final HashMap<String, XmpProperty> propMapping = new HashMap<String, XmpProperty>();

    static final HashBag<String, String> prefixMapping = new HashBag<String, String>();

    static boolean initDone;

    static XMPShema[] init() {
	XMPShema[] shemas0 = null;
	if (!initDone) {
	    initDone = true;

	    shemas0 = new XMPShema[] { BasicJobTicket.SHEMA, DublinCore.SHEMA, EXIFShema.SHEMA, Iptc4XmpCore.SHEMA, MediaManagement.SHEMA, PagedText.SHEMA,
		    PDFShema.PDF_SHEMA, PhotoshopShema.SHEMA, RightsManagement.SHEMA, TIFFShema.SHEMA, XMPBasicShema.SHEMA, XMPDynamicMediaSchema.SCHEMA,
		    CameraRawShema.SHEMA, XMP_AuxShema.SHEMA };
	}
	return shemas0;
    }

    public static String createDescription() {
	return createDescription(new XMP_HeaderConfiguration());
    }

    public static String createDescription(XMP_HeaderConfiguration hc) {
	XMP_StringBuffer sb = new XMP_StringBuffer(hc);

	String header = hc.createHeader();

	sb.append(header);

	String s0 = hc.namePrefix + hc.valueTypes + ":" + hc.valueTypes;

	sb.startTag(s0);
	sb.append(">\n <rdf:Bag>\n");

	ValueType.add(sb, hc);

	sb.append("</rdf:Bag>\n </" + s0 + ">\n");

	String s1 = hc.namePrefix + hc.shemas + ":" + hc.shemas;
	sb.startTag(s1);
	sb.append(">\n <rdf:Bag>\n");

	Enumeration<XMPShema> shemas = shemas();
	while (shemas.hasMoreElements()) {
	    XMPShema next = shemas.nextElement();
	    sb.append(next.toXMP(hc));
	}

	sb.append("</rdf:Bag> ");
	sb.endTag(s1);
	sb.append(">\n");
	sb.append(footer);

	return sb.toString();
    }

    public PathElement getChildAt(int index) {
	return properties()[index];
    }

    public int getChildrenCount() {
	return properties().length;
    }

    public PathElement getParent() {
	return null;
    }

    ArrayList<String> keys = new ArrayList<String>();

    final HashMap<String, XmpProperty> properties = new HashMap<String, XmpProperty>();

    public static String getNameSpace(String prefix, String fieldName) {
	init();
	String s = nsMapping.get(prefix + ":" + fieldName);

	if (s == null) {
	    s = nsMapping.get(prefix);
	    if (s != null) {
		return s;
	    }
	    XMPSchemaRegistry registry = XMPMetaFactory.getSchemaRegistry();

	    Map<?, ?> map = registry.getPrefixes();
	    String ns = (String) map.get(prefix);

	    if (ns != null) {
		nsMapping.put(prefix, ns);
		return ns;
	    }
	}
	// if (s == null) {
	// Iterator<String> iterator = nsMapping.keySet().iterator();
	// while (iterator.hasNext()) {
	// String next = iterator.next();
	// if (next.equalsIgnoreCase(s)) {
	// Debug.println("Key found!");
	// } else if (next.startsWith(prefix)) {
	// Debug.println(next);
	// }
	// }
	// }
	return s;
    }

    public static XmpProperty getProperty(String prefix, String fieldName) {
	init();
	return propMapping.get(prefix + ":" + fieldName);
    }

    public static Enumeration<XMPShema> shemas() {
	init();
	return new ValueEnumeration<String, XMPShema>(shemas, shemas.keySet().iterator());
    }

    public static String[] getPrefixes(String name) {
	int count = prefixMapping.getCount(name);
	String[] res = new String[count];
	for (int i = 0; i < res.length; i++) {
	    res[i] = prefixMapping.get(name, i);
	}
	return res;
    }

    protected XMPShema(String namespace, String prefix, String name) {
	this.namespace = namespace;
	this.prefix = prefix;
	this.name = name;
	shemas.put(name, this);

	initProperties();
	registerProperties();
    }

    protected void registerProperties() {
	for (XmpProperty p : properties()) {
	    addProperty(p);
	}
    }

    protected abstract void initProperties();

    private String toXMP(XmpProperty p, XMP_HeaderConfiguration hc) {
	XMP_StringBuffer sb = new XMP_StringBuffer(hc);
	sb.append("<rdf:li rdf:parseType=\"Resource\">\n");

	sb.addTag(hc.property, "name", p.getProperty());

	Category category = p.getCategory();
	if (category == null) {
	    category = EXTERNAL;
	}
	sb.addTag(hc.property, "category", category.toString());

	sb.addTag(hc.property, "description", p.getDescription());

	ValueType vt = p.getValueType();
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

    public String toXMP(XMP_HeaderConfiguration hc) {
	XMP_StringBuffer sb = new XMP_StringBuffer(hc);
	sb.append("<rdf:li rdf:parseType=\"Resource\">\n");

	sb.addTag(hc.shema, "name", getName());

	sb.addTag(hc.shema, "prefix", getPrefix());

	sb.addTag(hc.shema, "namespaceURI", getNamespace());

	sb.addTag(hc.shema, "shema", getName());

	String s = hc.namePrefix + hc.shema;
	sb.startTag(s + ":property>");
	sb.append("<rdf:Seq>");

	// Enumeration<XmpProperty> properties = properties();
	for (XmpProperty next : properties()) {
	    // XmpProperty next = properties.nextElement();
	    if (next.getMemberOf() == null) {
		sb.append(toXMP(next, hc));
	    }
	}

	sb.append("</rdf:Seq>\n");
	sb.endTag(s + ":property>");
	sb.append("</rdf:li>\n");

	return sb.toString();
    }

    public String getName() {
	return name;
    }

    public String getNamespace() {
	return namespace;
    }

    public String getPrefix() {
	return prefix;
    }

    private String name;
    String namespace;
    String prefix;

    protected XmpProperty addProperty(XmpProperty property) {
	property.setShema(this);
	properties.put(property.getProperty(), property);
	keys.add(property.getProperty());
	nsMapping.put(prefix + ":" + property.getProperty(), namespace);
	nsMapping.put(prefix, namespace);
	propMapping.put(prefix + ":" + property.getProperty(), property);
	prefixMapping.put(property.getProperty(), prefix);
	return property;
    }

    public XmpProperty getProperty(String name) {
	return properties.get(name);
    }

    public Enumeration<String> propertyNames() {
	return new SafeIterator<String>(keys.iterator());
    }


    static class ValueEnumeration<K, V> implements Enumeration<V> {
	HashMap<K, V> map;
	Iterator<K> keys;

	public ValueEnumeration(HashMap<K, V> map, Iterator<K> keys) {
	    this.map = map;
	    this.keys = keys;
	}

	public boolean hasMoreElements() {
	    return keys.hasNext();
	}

	public V nextElement() {
	    return map.get(keys.next());
	}
    }

    public static XMPShema getShema(String name) {
	return shemas.get(name);
    }

}
