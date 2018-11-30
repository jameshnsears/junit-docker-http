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
import io.swagger.client.model.PluginInterfaceType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The interface between Docker and the plugin
 */
@ApiModel(description = "The interface between Docker and the plugin")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-11-29T20:08:06.738Z")
public class PluginConfigInterface {
  @SerializedName("Types")
  private List<PluginInterfaceType> types = new ArrayList<PluginInterfaceType>();

  @SerializedName("Socket")
  private String socket = null;

  /**
   * Protocol to use for clients connecting to the plugin.
   */
  @JsonAdapter(ProtocolSchemeEnum.Adapter.class)
  public enum ProtocolSchemeEnum {
    EMPTY(""),
    
    MOBY_PLUGINS_HTTP_V1("moby.plugins.http/v1");

    private String value;

    ProtocolSchemeEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static ProtocolSchemeEnum fromValue(String text) {
      for (ProtocolSchemeEnum b : ProtocolSchemeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }

    public static class Adapter extends TypeAdapter<ProtocolSchemeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final ProtocolSchemeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public ProtocolSchemeEnum read(final JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        return ProtocolSchemeEnum.fromValue(String.valueOf(value));
      }
    }
  }

  @SerializedName("ProtocolScheme")
  private ProtocolSchemeEnum protocolScheme = null;

  public PluginConfigInterface types(List<PluginInterfaceType> types) {
    this.types = types;
    return this;
  }

  public PluginConfigInterface addTypesItem(PluginInterfaceType typesItem) {
    this.types.add(typesItem);
    return this;
  }

   /**
   * Get types
   * @return types
  **/
  @ApiModelProperty(example = "[\"docker.volumedriver/1.0\"]", required = true, value = "")
  public List<PluginInterfaceType> getTypes() {
    return types;
  }

  public void setTypes(List<PluginInterfaceType> types) {
    this.types = types;
  }

  public PluginConfigInterface socket(String socket) {
    this.socket = socket;
    return this;
  }

   /**
   * Get socket
   * @return socket
  **/
  @ApiModelProperty(example = "plugins.sock", required = true, value = "")
  public String getSocket() {
    return socket;
  }

  public void setSocket(String socket) {
    this.socket = socket;
  }

  public PluginConfigInterface protocolScheme(ProtocolSchemeEnum protocolScheme) {
    this.protocolScheme = protocolScheme;
    return this;
  }

   /**
   * Protocol to use for clients connecting to the plugin.
   * @return protocolScheme
  **/
  @ApiModelProperty(example = "some.protocol/v1.0", value = "Protocol to use for clients connecting to the plugin.")
  public ProtocolSchemeEnum getProtocolScheme() {
    return protocolScheme;
  }

  public void setProtocolScheme(ProtocolSchemeEnum protocolScheme) {
    this.protocolScheme = protocolScheme;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PluginConfigInterface pluginConfigInterface = (PluginConfigInterface) o;
    return Objects.equals(this.types, pluginConfigInterface.types) &&
        Objects.equals(this.socket, pluginConfigInterface.socket) &&
        Objects.equals(this.protocolScheme, pluginConfigInterface.protocolScheme);
  }

  @Override
  public int hashCode() {
    return Objects.hash(types, socket, protocolScheme);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PluginConfigInterface {\n");
    
    sb.append("    types: ").append(toIndentedString(types)).append("\n");
    sb.append("    socket: ").append(toIndentedString(socket)).append("\n");
    sb.append("    protocolScheme: ").append(toIndentedString(protocolScheme)).append("\n");
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

