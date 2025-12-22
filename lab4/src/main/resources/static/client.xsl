<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="/client">
<html>
<body>
    <a href="/api/xml/clients">Все клиенты</a><br/>
    <a href="/api/xml/bookings">Все бронирования</a>

    <h2>Клиент: <xsl:value-of select="name"/></h2>
    <p><b>ID:</b> <xsl:value-of select="id"/></p>
    <p><b>Телефон:</b> <xsl:value-of select="phone"/></p>
    <p><b>Email:</b> <xsl:value-of select="email"/></p>
</body>
</html>
</xsl:template>
</xsl:stylesheet>
