function fn() {
    var port = karate.properties['http.port'];
    var config = {
        baseUrl : "http://localhost:" + port
    };
    return config;
}