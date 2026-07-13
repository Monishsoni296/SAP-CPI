import com.sap.gateway.ip.core.customdev.util.Message
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

// Script to check and validate Cache response
def Message processData(Message message) {
    
    def headers = message.getHeaders()
    def body = message.getBody(String.class)
    def messageLog = messageLogFactory?.getMessageLog(message)
    def validPayload = "false"
    def cacheHit = "false"
    def cachePerformance = "MISS"

    // Cache HIT
    def bodyText = body?.toString()?.trim()
    if (bodyText) {
        validPayload = "true"
        cacheHit = "true"
        cachePerformance = "HIT"
    }

    message.setProperty("cacheHit", cacheHit)
    message.setProperty("validPayload", validPayload)
    message.setProperty("Cache_Performance", cachePerformance)

    if (messageLog != null) {
        messageLog.addCustomHeaderProperty("Cache_Performance", cachePerformance)
        messageLog.addCustomHeaderProperty("Cache_Hit", cacheHit)
    }
    return message
}