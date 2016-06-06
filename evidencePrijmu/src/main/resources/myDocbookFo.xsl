<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : myDocbookFo.xsl
    Created on : Pondelok, 2016, júna 6, 11:59
    Author     : Miloš Šilhár
    Description:
        Set properties for fo docbook transformation.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" version="1.0">
    
    <xsl:import href="docbook-xsl/fo/docbook.xsl" />
    
    <xsl:param name="generate.toc">article nop</xsl:param>
    <xsl:param name="paper.type">A4</xsl:param>
    
    <xsl:attribute-set name="section.title.level1.properties">
        <xsl:attribute name="font-size">
            <xsl:value-of select="$body.font.master * 1.3"/>
            <xsl:text>pt</xsl:text>
        </xsl:attribute>
    </xsl:attribute-set>

</xsl:stylesheet>
