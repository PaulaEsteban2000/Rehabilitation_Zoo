<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" indent="yes" />

<xsl:template match="/">
         <html>
          
       		 <table border="1">
      			<th>Name</th>
      			<th>Last Cleaned</th>
      			<th>Last water Tank fill</th>
      			<th>Temperature</th>
      			<th>Light</th>
      			
        	 	<xsl:value-of select="@name" />
        	 	<xsl:value-of select="lastCleaned" />
        	 	<xsl:value-of select="waterTank" />
        	 	<xsl:value-of select="temperature" />
        	 	<xsl:value-of select="light" />

        	 	
   			</table>
        </html>

</xsl:template>
</xsl:stylesheet>

