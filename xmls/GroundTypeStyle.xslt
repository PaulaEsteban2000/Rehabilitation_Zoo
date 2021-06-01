<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" indent="yes" />

<xsl:template match="/">
         <html>
          
       		 <table border="1">
      			<th>Type</th>
      			<th>Habitat ID</th>
      			
        	 	<xsl:value-of select="type" />
        	 	<xsl:value-of select="habitat_id" />

        	 	
   			</table>
        </html>

</xsl:template>
</xsl:stylesheet>

