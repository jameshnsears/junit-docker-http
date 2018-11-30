/*
 * Docker Engine API
 * The Engine API is an HTTP API served by Docker Engine. It is the API the Docker client uses to communicate with the Engine, so everything the Docker client can do can be done with the API.  Most of the client's commands map directly to API endpoints (e.g. `docker ps` is `GET /containers/json`). The notable exception is running containers, which consists of several API calls.  # Errors  The API uses standard HTTP status codes to indicate the success or failure of the API call. The body of the response will be JSON in the following format:  ``` {   \"message\": \"page not found\" } ```  # Versioning  The API is usually changed in each release, so API calls are versioned to ensure that clients don't break. To lock to a specific version of the API, you prefix the URL with its version, for example, call `/v1.30/info` to use the v1.30 version of the `/info` endpoint. If the API version specified in the URL is not supported by the daemon, a HTTP `400 Bad Request` error message is returned.  If you omit the version-prefix, the current version of the API (v1.39) is used. For example, calling `/info` is the same as calling `/v1.39/info`. Using the API without a version-prefix is deprecated and will be removed in a future release.  Engine releases in the near future should support this version of the API, so your client will continue to work even if it is talking to a newer Engine.  The API uses an open schema model, which means server may add extra properties to responses. Likewise, the server will ignore any extra query parameters and request body properties. When you write clients, you need to ignore additional properties in responses to ensure they do not break when talking to newer daemons.   # Authentication  Authentication for registries is handled client side. The client has to send authentication details to various endpoints that need to communicate with registries, such as `POST /images/(name)/push`. These are sent as `X-Registry-Auth` header as a Base64 encoded (JSON) string with the following structure:  ``` {   \"username\": \"string\",   \"password\": \"string\",   \"email\": \"string\",   \"serveraddress\": \"string\" } ```  The `serveraddress` is a domain/IP without a protocol. Throughout this structure, double quotes are required.  If you have already got an identity token from the [`/auth` endpoint](#operation/SystemAuth), you can just pass this instead of credentials:  ``` {   \"identitytoken\": \"9cbaf023786cd7...\" } ``` 
 *
 * OpenAPI spec version: 1.39
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package io.swagger.client.api;

import io.swagger.client.ApiException;
import io.swagger.client.model.AuthConfig;
import io.swagger.client.model.ErrorResponse;
import io.swagger.client.model.SystemAuthResponse;
import io.swagger.client.model.SystemDataUsageResponse;
import io.swagger.client.model.SystemEventsResponse;
import io.swagger.client.model.SystemInfo;
import io.swagger.client.model.SystemVersionResponse;
import org.junit.Test;
import org.junit.Ignore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for SystemApi
 */
@Ignore
public class SystemApiTest {

    private final SystemApi api = new SystemApi();

    
    /**
     * Check auth configuration
     *
     * Validate credentials for a registry and, if available, get an identity token for accessing the registry without password.
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void systemAuthTest() throws ApiException {
        AuthConfig authConfig = null;
        SystemAuthResponse response = api.systemAuth(authConfig);

        // TODO: test validations
    }
    
    /**
     * Get data usage information
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void systemDataUsageTest() throws ApiException {
        SystemDataUsageResponse response = api.systemDataUsage();

        // TODO: test validations
    }
    
    /**
     * Monitor events
     *
     * Stream real-time events from the server.  Various objects within Docker report events when something happens to them.  Containers report these events: &#x60;attach&#x60;, &#x60;commit&#x60;, &#x60;copy&#x60;, &#x60;create&#x60;, &#x60;destroy&#x60;, &#x60;detach&#x60;, &#x60;die&#x60;, &#x60;exec_create&#x60;, &#x60;exec_detach&#x60;, &#x60;exec_start&#x60;, &#x60;exec_die&#x60;, &#x60;export&#x60;, &#x60;health_status&#x60;, &#x60;kill&#x60;, &#x60;oom&#x60;, &#x60;pause&#x60;, &#x60;rename&#x60;, &#x60;resize&#x60;, &#x60;restart&#x60;, &#x60;start&#x60;, &#x60;stop&#x60;, &#x60;top&#x60;, &#x60;unpause&#x60;, and &#x60;update&#x60;  Images report these events: &#x60;delete&#x60;, &#x60;import&#x60;, &#x60;load&#x60;, &#x60;pull&#x60;, &#x60;push&#x60;, &#x60;save&#x60;, &#x60;tag&#x60;, and &#x60;untag&#x60;  Volumes report these events: &#x60;create&#x60;, &#x60;mount&#x60;, &#x60;unmount&#x60;, and &#x60;destroy&#x60;  Networks report these events: &#x60;create&#x60;, &#x60;connect&#x60;, &#x60;disconnect&#x60;, &#x60;destroy&#x60;, &#x60;update&#x60;, and &#x60;remove&#x60;  The Docker daemon reports these events: &#x60;reload&#x60;  Services report these events: &#x60;create&#x60;, &#x60;update&#x60;, and &#x60;remove&#x60;  Nodes report these events: &#x60;create&#x60;, &#x60;update&#x60;, and &#x60;remove&#x60;  Secrets report these events: &#x60;create&#x60;, &#x60;update&#x60;, and &#x60;remove&#x60;  Configs report these events: &#x60;create&#x60;, &#x60;update&#x60;, and &#x60;remove&#x60; 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void systemEventsTest() throws ApiException {
        String since = null;
        String until = null;
        String filters = null;
        SystemEventsResponse response = api.systemEvents(since, until, filters);

        // TODO: test validations
    }
    
    /**
     * Get system information
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void systemInfoTest() throws ApiException {
        SystemInfo response = api.systemInfo();

        // TODO: test validations
    }
    
    /**
     * Ping
     *
     * This is a dummy endpoint you can use to test if the server is accessible.
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void systemPingTest() throws ApiException {
        String response = api.systemPing();

        // TODO: test validations
    }
    
    /**
     * Get version
     *
     * Returns the version of Docker that is running and various information about the system that Docker is running on.
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void systemVersionTest() throws ApiException {
        SystemVersionResponse response = api.systemVersion();

        // TODO: test validations
    }
    
}
