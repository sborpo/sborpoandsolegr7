<?xml version="1.0" encoding="ISO-8859-1"?>
<!-- Edited by XMLSpy® -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:exsl="http://exslt.org/common">
	<xsl:key name="groupID" match="Results/Row[not(groupId=preceding::groupId)]/groupId" use="."/>
	<xsl:template match="/">
		<html>
		<link rel="stylesheet" type="text/css" href="defualtCss.css" />
		<link href="/sborpoandsolegr7/css/tableCss.css" rel="stylesheet" type="text/css"/>
			<body>
				<h1>Group By Group Type</h1>
				<xsl:for-each select="Results/Row[not(groupid=preceding::groupid)]/groupid">
					<xsl:variable name="GROUP" select="."/>
					<h2>
						<xsl:text>Group name: </xsl:text><xsl:value-of select="."/>
					</h2>
					<table>
						<xsl:for-each select="/Results/Row[1]/*">
							<xsl:if test="local-name() != 'groupid' ">
								<th>
									<xsl:value-of select="local-name()"/>
								</th>
							</xsl:if>
						</xsl:for-each>
						<xsl:for-each select="/Results/Row[groupid=$GROUP]">
							<tr>
								<xsl:for-each select="*">
									<xsl:if test="(local-name() != 'groupid' )">
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
							<xsl:for-each select="/Results/Row[groupid=$GROUP]">
								<item>
									<xsl:value-of select="slotend - slotbegin"/>
								</item>
							</xsl:for-each>
						</total_amount>
					</xsl:variable>
					<xsl:variable name="myTotal" select="exsl:node-set($tmpTotal)"/>
					<div align="center"><xsl:text>The total number of reserved slots for instrument </xsl:text>
					<xsl:value-of select="/Results/Row/instid"/>
					<xsl:text> for this group is: </xsl:text>
					<xsl:value-of select="sum($myTotal/total_amount/item)"/><!-- end count --></div>
					<hr/>
				</xsl:for-each>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>