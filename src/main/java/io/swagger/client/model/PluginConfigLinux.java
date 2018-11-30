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


package io.swagger.client.model;

import java.util.Objects;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.client.model.PluginDevice;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * PluginConfigLinux
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-11-29T20:08:06.738Z")
public class PluginConfigLinux {
  @SerializedName("Capabilities")
  private List<String> capabilities = new ArrayList<String>();

  @SerializedName("AllowAllDevices")
  private Boolean allowAllDevices = null;

  @SerializedName("Devices")
  private List<PluginDevice> devices = new ArrayList<PluginDevice>();

  public PluginConfigLinux capabilities(List<String> capabilities) {
    this.capabilities = capabilities;
    return this;
  }

  public PluginConfigLinux addCapabilitiesItem(String capabilitiesItem) {
    this.capabilities.add(capabilitiesItem);
    return this;
  }

   /**
   * Get capabilities
   * @return capabilities
  **/
  @ApiModelProperty(example = "[\"CAP_SYS_ADMIN\",\"CAP_SYSLOG\"]", required = true, value = "")
  public List<String> getCapabilities() {
    return capabilities;
  }

  public void setCapabilities(List<String> capabilities) {
    this.capabilities = capabilities;
  }

  public PluginConfigLinux allowAllDevices(Boolean allowAllDevices) {
    this.allowAllDevices = allowAllDevices;
    return this;
  }

   /**
   * Get allowAllDevices
   * @return allowAllDevices
  **/
  @ApiModelProperty(example = "false", required = true, value = "")
  public Boolean isAllowAllDevices() {
    return allowAllDevices;
  }

  public void setAllowAllDevices(Boolean allowAllDevices) {
    this.allowAllDevices = allowAllDevices;
  }

  public PluginConfigLinux devices(List<PluginDevice> devices) {
    this.devices = devices;
    return this;
  }

  public PluginConfigLinux addDevicesItem(PluginDevice devicesItem) {
    this.devices.add(devicesItem);
    return this;
  }

   /**
   * Get devices
   * @return devices
  **/
  @ApiModelProperty(required = true, value = "")
  public List<PluginDevice> getDevices() {
    return devices;
  }

  public void setDevices(List<PluginDevice> devices) {
    this.devices = devices;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PluginConfigLinux pluginConfigLinux = (PluginConfigLinux) o;
    return Objects.equals(this.capabilities, pluginConfigLinux.capabilities) &&
        Objects.equals(this.allowAllDevices, pluginConfigLinux.allowAllDevices) &&
        Objects.equals(this.devices, pluginConfigLinux.devices);
  }

  @Override
  public int hashCode() {
    return Objects.hash(capabilities, allowAllDevices, devices);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PluginConfigLinux {\n");
    
    sb.append("    capabilities: ").append(toIndentedString(capabilities)).append("\n");
    sb.append("    allowAllDevices: ").append(toIndentedString(allowAllDevices)).append("\n");
    sb.append("    devices: ").append(toIndentedString(devices)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

