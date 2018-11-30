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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * OK response to ContainerTop operation
 */
@ApiModel(description = "OK response to ContainerTop operation")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-11-29T20:08:06.738Z")
public class ContainerTopResponse {
  @SerializedName("Titles")
  private List<String> titles = null;

  @SerializedName("Processes")
  private List<List<String>> processes = null;

  public ContainerTopResponse titles(List<String> titles) {
    this.titles = titles;
    return this;
  }

  public ContainerTopResponse addTitlesItem(String titlesItem) {
    if (this.titles == null) {
      this.titles = new ArrayList<String>();
    }
    this.titles.add(titlesItem);
    return this;
  }

   /**
   * The ps column titles
   * @return titles
  **/
  @ApiModelProperty(value = "The ps column titles")
  public List<String> getTitles() {
    return titles;
  }

  public void setTitles(List<String> titles) {
    this.titles = titles;
  }

  public ContainerTopResponse processes(List<List<String>> processes) {
    this.processes = processes;
    return this;
  }

  public ContainerTopResponse addProcessesItem(List<String> processesItem) {
    if (this.processes == null) {
      this.processes = new ArrayList<List<String>>();
    }
    this.processes.add(processesItem);
    return this;
  }

   /**
   * Each process running in the container, where each is process is an array of values corresponding to the titles
   * @return processes
  **/
  @ApiModelProperty(value = "Each process running in the container, where each is process is an array of values corresponding to the titles")
  public List<List<String>> getProcesses() {
    return processes;
  }

  public void setProcesses(List<List<String>> processes) {
    this.processes = processes;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ContainerTopResponse containerTopResponse = (ContainerTopResponse) o;
    return Objects.equals(this.titles, containerTopResponse.titles) &&
        Objects.equals(this.processes, containerTopResponse.processes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(titles, processes);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ContainerTopResponse {\n");
    
    sb.append("    titles: ").append(toIndentedString(titles)).append("\n");
    sb.append("    processes: ").append(toIndentedString(processes)).append("\n");
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

