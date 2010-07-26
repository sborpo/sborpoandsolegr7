<?xml version="1.0" encoding="ISO-8859-1"?>
<!-- Edited by XMLSpy® -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:key name="instID" match="Results/Row[not(instid=preceding::instid)]/instid" use="."/>
	<xsl:template match="/">
		<html>
		<link rel="stylesheet" type="text/css" href="defualtCss.css" />
		<link href="/sborpoandsolegr7/css/tableCss.css" rel="stylesheet" type="text/css"/>
			<body>
				<h1>Group By Instrument Type</h1>
				<xsl:for-each select="Results/Row[not(instid=preceding::instid)]/instid">
					<xsl:variable name="INST" select="."/>
					<h3>
						<xsl:value-of select="."/>
					</h3>
					<table>
						<xsl:for-each select="/Results/Row[1]/*">
							<xsl:if test="local-name() != 'instid' and local-name() != 'groupId'">
								<th>
									<xsl:value-of select="local-name()"/>
								</th>
							</xsl:if>
						</xsl:for-each>
						<xsl:for-each select="/Results/Row[instid=$INST]">
							<tr>
								<xsl:for-each select="*">
									<xsl:if test="(local-name() != 'instid' and local-name() != 'groupId')">
										<td>
											<xsl:value-of select="."/>
										</td>
									</xsl:if>
								</xsl:for-each>
							</tr>
						</xsl:for-each>
					</table>
					<hr/>
					<div align="center"><xsl:text>Total number of reservation for this instrument: </xsl:text>
					<xsl:value-of select="count(/Results/Row[instid=$INST])"/></div>
					<br/>
				</xsl:for-each>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>
