<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">

<html>
  <head>
    <title>JPictureProspector - Suchergebnis</title>
  </head>
  
  <body>
    <div>
    <xsl:apply-templates />
    </div>
  </body>
</html>

</xsl:template>

<xsl:template match="BildDokument">
<div style="float: left; margin: 10px; padding: 10px; background-color: #CCEEEE">
  
  <p align="center">
    <xsl:element name="a">
      <xsl:attribute name="href"><xsl:value-of select="jpp.merkmale.DateipfadMerkmal"/></xsl:attribute>
    
      <xsl:element name="img">
        <xsl:attribute name="src"><xsl:value-of select="jpp.merkmale.ThumbnailMerkmal"/></xsl:attribute>
        <xsl:attribute name="border">0</xsl:attribute>        
        <!-- <xsl:attribute name="src">data:image/jpg;base64,<xsl:value-of select="ThumbnailMerkmal"/></xsl:attribute> -->
      </xsl:element>
      <p><xsl:value-of select="jpp.merkmale.DateinameMerkmal"/></p>
    </xsl:element>
  </p>
</div>

</xsl:template>

</xsl:stylesheet>

