package com.thoughtworks.qdox.model;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class TypeTest extends TestCase{
    public TypeTest(String s) {
        super(s);
    }

    public void testResolvingWithFullyQualifiedImport() throws Exception{
        List imports = new ArrayList();
        imports.add("com.thoughtworks.qdox.model.Type");

        Type type = new Type(imports, "Type", new ArrayList(), "");
        assertTrue(type.isResolved());
        assertEquals("com.thoughtworks.qdox.model.Type", type.getValue());
    }

    public void testWithTwoImportsWithSimiliarNames() throws Exception {
        List imports = new ArrayList();
        imports.add("com.thoughtworks.qdox.model.Type");
        imports.add("fake.package.MyType");

        Type type = new Type(imports, "Type", new ArrayList(), "");
        assertTrue(type.isResolved());
        assertEquals("com.thoughtworks.qdox.model.Type", type.getValue());
    }

    public void testInclusiveImport() throws Exception {
        List imports = new ArrayList();
        imports.add("com.thoughtworks.qdox.model.Type");
        imports.add("com.thoughtworks.qdox.parser.*");
        imports.add("fake.package.MyType");

        List parsedClassNames = new ArrayList();

        Type jFlexLexerType = new Type(imports, "JFlexLexer", parsedClassNames, "");
        assertTrue(!jFlexLexerType.isResolved());
        assertEquals("JFlexLexer", jFlexLexerType.getValue());

        parsedClassNames.add("com.thoughtworks.qdox.parser.JFlexLexer");
        assertTrue(jFlexLexerType.isResolved());
        assertEquals("com.thoughtworks.qdox.parser.JFlexLexer", jFlexLexerType.getValue());
    }

    public void testInclusiveImportAddingAClassFromADifferentPackageWithSameName() throws Exception {
        List imports = new ArrayList();
        imports.add("com.thoughtworks.qdox.model.Type");
        imports.add("com.thoughtworks.qdox.parser.*");
        imports.add("fake.package.MyType");

        List parsedClassNames = new ArrayList();

        Type jFlexLexerType = new Type(imports, "JFlexLexer", parsedClassNames, "");
        assertTrue(!jFlexLexerType.isResolved());
        assertEquals("JFlexLexer", jFlexLexerType.getValue());

        parsedClassNames.add("fake.package.JFlexLexer");
        assertTrue(!jFlexLexerType.isResolved());
        assertEquals("JFlexLexer", jFlexLexerType.getValue());
    }

    public void testResolvingFileInSamePackage() throws Exception{
        List imports = new ArrayList();
        imports.add("com.thoughtworks.qdox.parser.*");

        List parsedClassNames = new ArrayList();
        String packge = "com.thoughtworks.qdox.model";

        Type type = new Type(imports, "ModelBuilder", parsedClassNames, packge);
        assertTrue(!type.isResolved());
        assertEquals("ModelBuilder", type.getValue());

        String modelBuilderClassName = packge + ".ModelBuilder";
        parsedClassNames.add(modelBuilderClassName);
        assertTrue(type.isResolved());
        assertEquals(modelBuilderClassName, type.getValue());
    }

    public void testFullyQualifiedType() throws Exception {
        List imports = new ArrayList();
        imports.add("com.thoughtworks.qdox.parser.ModelBuilder");

        List parsedClassNames = new ArrayList();
        String packge = "com.thoughtworks.qdox.model";

        Type type = new Type(imports, "fake.package.ModelBuilder", parsedClassNames, packge);
        assertTrue(type.isResolved());
        assertEquals("fake.package.ModelBuilder", type.getValue());

    }
}
