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
import io.swagger.client.model.EndpointPortConfig;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Properties that can be configured to access and load balance a service.
 */
@ApiModel(description = "Properties that can be configured to access and load balance a service.")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-11-29T20:08:06.738Z")
public class EndpointSpec {
  /**
   * The mode of resolution to use for internal load balancing between tasks.
   */
  @JsonAdapter(ModeEnum.Adapter.class)
  public enum ModeEnum {
    VIP("vip"),
    
    DNSRR("dnsrr");

    private String value;

    ModeEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static ModeEnum fromValue(String text) {
      for (ModeEnum b : ModeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }

    public static class Adapter extends TypeAdapter<ModeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final ModeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public ModeEnum read(final JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        return ModeEnum.fromValue(String.valueOf(value));
      }
    }
  }

  @SerializedName("Mode")
  private ModeEnum mode = ModeEnum.VIP;

  @SerializedName("Ports")
  private List<EndpointPortConfig> ports = null;

  public EndpointSpec mode(ModeEnum mode) {
    this.mode = mode;
    return this;
  }

   /**
   * The mode of resolution to use for internal load balancing between tasks.
   * @return mode
  **/
  @ApiModelProperty(value = "The mode of resolution to use for internal load balancing between tasks.")
  public ModeEnum getMode() {
    return mode;
  }

  public void setMode(ModeEnum mode) {
    this.mode = mode;
  }

  public EndpointSpec ports(List<EndpointPortConfig> ports) {
    this.ports = ports;
    return this;
  }

  public EndpointSpec addPortsItem(EndpointPortConfig portsItem) {
    if (this.ports == null) {
      this.ports = new ArrayList<EndpointPortConfig>();
    }
    this.ports.add(portsItem);
    return this;
  }

   /**
   * List of exposed ports that this service is accessible on from the outside. Ports can only be provided if &#x60;vip&#x60; resolution mode is used.
   * @return ports
  **/
  @ApiModelProperty(value = "List of exposed ports that this service is accessible on from the outside. Ports can only be provided if `vip` resolution mode is used.")
  public List<EndpointPortConfig> getPorts() {
    return ports;
  }

  public void setPorts(List<EndpointPortConfig> ports) {
    this.ports = ports;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EndpointSpec endpointSpec = (EndpointSpec) o;
    return Objects.equals(this.mode, endpointSpec.mode) &&
        Objects.equals(this.ports, endpointSpec.ports);
  }

  @Override
  public int hashCode() {
    return Objects.hash(mode, ports);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EndpointSpec {\n");
    
    sb.append("    mode: ").append(toIndentedString(mode)).append("\n");
    sb.append("    ports: ").append(toIndentedString(ports)).append("\n");
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

