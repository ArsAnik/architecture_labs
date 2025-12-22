<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="/clients">
<html><body>
    <a href="/api/xml/bookings">Все бронирования</a>
    <h2>Клиенты</h2>
    <table border="1">
        <tr><th>ID</th><th>Имя</th><th>Телефон</th><th>Email</th></tr>
            <xsl:for-each select="item">
            <tr><td><xsl:value-of select="id"/></td>
            <td><xsl:value-of select="name"/></td>
            <td><xsl:value-of select="phone"/></td>
            <td><xsl:value-of select="email"/></td></tr>
        </xsl:for-each>
    </table>
</body></html>
</xsl:template>
</xsl:stylesheet>