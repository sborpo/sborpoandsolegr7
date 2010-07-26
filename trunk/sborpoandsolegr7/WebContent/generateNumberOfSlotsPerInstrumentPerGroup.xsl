<?xml version="1.0" encoding="ISO-8859-1"?>
<!-- Edited by XMLSpy® -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:exsl="http://exslt.org/common">
	<xsl:key name="groupID" match="Results/Row[not(groupId=preceding::groupId)]/groupId" use="."/>
	<xsl:template match="/">
		<html>
		<link rel="stylesheet" type="text/css" href="defualtCss.css" />
			<body>
				<h2>Group By Group Type</h2>
				<xsl:for-each select="Results/Row[not(groupId=preceding::groupId)]/groupId">
					<xsl:variable name="GROUP" select="."/>
					<h3>
						<xsl:value-of select="."/>
					</h3>
					<table border="1">
						<xsl:for-each select="/Results/Row[1]/*">
							<xsl:if test="local-name() != 'groupId' ">
								<td>
									<xsl:value-of select="local-name()"/>
								</td>
							</xsl:if>
						</xsl:for-each>
						<xsl:for-each select="/Results/Row[groupId=$GROUP]">
							<tr>
								<xsl:for-each select="*">
									<xsl:if test="(local-name() != 'groupId' )">
										<td>
											<xsl:value-of select="."/>
										</td>
									</xsl:if>
								</xsl:for-each>
							</tr>
						</xsl:for-each>
					</table>
					
					<br/><!-- start count-->
					<xsl:variable name="tmpTotal">
						<total_amount>
							<xsl:for-each select="/Results/Row[groupId=$GROUP]">
								<item>
									<xsl:value-of select="slotend - slotbegin"/>
								</item>
							</xsl:for-each>
						</total_amount>
					</xsl:variable>
					<xsl:variable name="myTotal" select="exsl:node-set($tmpTotal)"/>
					<xsl:text>The total number of reserved slots for instrument </xsl:text>
					<xsl:value-of select="/Results/Row/instid"/>
					<xsl:text> for this group is: </xsl:text>
					<xsl:value-of select="sum($myTotal/total_amount/item)"/><!-- end count -->
				</xsl:for-each>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>