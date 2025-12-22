<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="/bookings">
<html>
<body>
    <a href="/api/xml/clients">Все клиенты</a>

    <h2>Бронирования</h2>
        <table border="1">
        <tr><th>ID</th><th>Клиент</th><th>Заезд</th><th>Выезд</th><th>Комната</th><th>Статус</th></tr>
        <xsl:for-each select="item">
            <tr><td><xsl:value-of select="id"/></td>
            <td>
                <a>
                    <xsl:attribute name="href">
                        <xsl:text>/api/xml/clients/</xsl:text>
                        <xsl:value-of select="client/id"/>
                    </xsl:attribute>
                    <xsl:value-of select="client/name"/>
                </a>
            </td>
            <td><xsl:value-of select="checkIn"/></td>
            <td><xsl:value-of select="checkOut"/></td>
            <td><xsl:value-of select="roomNumber"/></td>
            <td><xsl:value-of select="status"/></td></tr>
        </xsl:for-each>
    </table>
</body></html>
</xsl:template>
</xsl:stylesheet>