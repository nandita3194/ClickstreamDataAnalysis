mapping {
    map clientTimestamp() onto 'clientTimestamp'
    map timestamp() onto 'timestamp'
    map partyId() onto 'partyId'
    map sessionId() onto 'sessionId'
    map eventType() onto 'eventType'
    map firstInSession() onto 'firstInSession'
    map corrupt() onto 'detectedCorruption'
    map duplicate() onto 'detectedDuplicate'
    map referer() onto 'referer'
    map location() onto 'location'
    def locationUr = parse location() to uri
    map locationUr.host() onto 'locationHostField'
    map locationUr.path() onto 'locationPathField'
    map locationUr.rawPath() onto 'locationPathFieldRaw'
    map locationUr.decodedFragment() onto 'locationFragment'
    map locationUr.port() onto 'locationPortField'
    map locationUr.decodedQueryString() onto 'locationQS'
    map locationUr.rawQueryString() onto 'locationQSRaw'
    def ua = userAgent()
    map ua.deviceCategory() onto 'userAgentDeviceCategory'
    map ua.family() onto 'userAgentFamily'
    map ua.osFamily() onto 'userAgentOsFamily'
    map userAgentString() onto 'userAgentString'
    map ua.type() onto 'userAgentType'
    def ip = header('X-Forwarded-For').first()
    def ip2 = header('X-Forwarded-For').last()
    def ga = ip2geo(ip)
    def ga2 = ip2geo(ip2)
    map ga.continentName() onto 'continentNameField'
    map ga.countryName() onto 'countryNameField'
    map ga2.mostSpecificSubdivisionName() onto 'mostSpecificSubdivisionNameField'
    map ga.cityName() onto 'cityNameField'
    map ga2.postalCode() onto 'postalCodeField'
    map ga.latitude() onto 'latitudeField'
    map ga.longitude() onto 'longitudeField'
}
