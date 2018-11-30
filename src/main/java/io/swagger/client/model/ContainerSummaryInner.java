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
import io.swagger.client.model.ContainerSummaryInnerHostConfig;
import io.swagger.client.model.ContainerSummaryInnerNetworkSettings;
import io.swagger.client.model.Mount;
import io.swagger.client.model.Port;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ContainerSummaryInner
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-11-29T20:08:06.738Z")
public class ContainerSummaryInner {
  @SerializedName("Id")
  private String id = null;

  @SerializedName("Names")
  private List<String> names = null;

  @SerializedName("Image")
  private String image = null;

  @SerializedName("ImageID")
  private String imageID = null;

  @SerializedName("Command")
  private String command = null;

  @SerializedName("Created")
  private Long created = null;

  @SerializedName("Ports")
  private List<Port> ports = null;

  @SerializedName("SizeRw")
  private Long sizeRw = null;

  @SerializedName("SizeRootFs")
  private Long sizeRootFs = null;

  @SerializedName("Labels")
  private Map<String, String> labels = null;

  @SerializedName("State")
  private String state = null;

  @SerializedName("Status")
  private String status = null;

  @SerializedName("HostConfig")
  private ContainerSummaryInnerHostConfig hostConfig = null;

  @SerializedName("NetworkSettings")
  private ContainerSummaryInnerNetworkSettings networkSettings = null;

  @SerializedName("Mounts")
  private List<Mount> mounts = null;

  public ContainerSummaryInner id(String id) {
    this.id = id;
    return this;
  }

   /**
   * The ID of this container
   * @return id
  **/
  @ApiModelProperty(value = "The ID of this container")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public ContainerSummaryInner names(List<String> names) {
    this.names = names;
    return this;
  }

  public ContainerSummaryInner addNamesItem(String namesItem) {
    if (this.names == null) {
      this.names = new ArrayList<String>();
    }
    this.names.add(namesItem);
    return this;
  }

   /**
   * The names that this container has been given
   * @return names
  **/
  @ApiModelProperty(value = "The names that this container has been given")
  public List<String> getNames() {
    return names;
  }

  public void setNames(List<String> names) {
    this.names = names;
  }

  public ContainerSummaryInner image(String image) {
    this.image = image;
    return this;
  }

   /**
   * The name of the image used when creating this container
   * @return image
  **/
  @ApiModelProperty(value = "The name of the image used when creating this container")
  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public ContainerSummaryInner imageID(String imageID) {
    this.imageID = imageID;
    return this;
  }

   /**
   * The ID of the image that this container was created from
   * @return imageID
  **/
  @ApiModelProperty(value = "The ID of the image that this container was created from")
  public String getImageID() {
    return imageID;
  }

  public void setImageID(String imageID) {
    this.imageID = imageID;
  }

  public ContainerSummaryInner command(String command) {
    this.command = command;
    return this;
  }

   /**
   * Command to run when starting the container
   * @return command
  **/
  @ApiModelProperty(value = "Command to run when starting the container")
  public String getCommand() {
    return command;
  }

  public void setCommand(String command) {
    this.command = command;
  }

  public ContainerSummaryInner created(Long created) {
    this.created = created;
    return this;
  }

   /**
   * When the container was created
   * @return created
  **/
  @ApiModelProperty(value = "When the container was created")
  public Long getCreated() {
    return created;
  }

  public void setCreated(Long created) {
    this.created = created;
  }

  public ContainerSummaryInner ports(List<Port> ports) {
    this.ports = ports;
    return this;
  }

  public ContainerSummaryInner addPortsItem(Port portsItem) {
    if (this.ports == null) {
      this.ports = new ArrayList<Port>();
    }
    this.ports.add(portsItem);
    return this;
  }

   /**
   * The ports exposed by this container
   * @return ports
  **/
  @ApiModelProperty(value = "The ports exposed by this container")
  public List<Port> getPorts() {
    return ports;
  }

  public void setPorts(List<Port> ports) {
    this.ports = ports;
  }

  public ContainerSummaryInner sizeRw(Long sizeRw) {
    this.sizeRw = sizeRw;
    return this;
  }

   /**
   * The size of files that have been created or changed by this container
   * @return sizeRw
  **/
  @ApiModelProperty(value = "The size of files that have been created or changed by this container")
  public Long getSizeRw() {
    return sizeRw;
  }

  public void setSizeRw(Long sizeRw) {
    this.sizeRw = sizeRw;
  }

  public ContainerSummaryInner sizeRootFs(Long sizeRootFs) {
    this.sizeRootFs = sizeRootFs;
    return this;
  }

   /**
   * The total size of all the files in this container
   * @return sizeRootFs
  **/
  @ApiModelProperty(value = "The total size of all the files in this container")
  public Long getSizeRootFs() {
    return sizeRootFs;
  }

  public void setSizeRootFs(Long sizeRootFs) {
    this.sizeRootFs = sizeRootFs;
  }

  public ContainerSummaryInner labels(Map<String, String> labels) {
    this.labels = labels;
    return this;
  }

  public ContainerSummaryInner putLabelsItem(String key, String labelsItem) {
    if (this.labels == null) {
      this.labels = new HashMap<String, String>();
    }
    this.labels.put(key, labelsItem);
    return this;
  }

   /**
   * User-defined key/value metadata.
   * @return labels
  **/
  @ApiModelProperty(value = "User-defined key/value metadata.")
  public Map<String, String> getLabels() {
    return labels;
  }

  public void setLabels(Map<String, String> labels) {
    this.labels = labels;
  }

  public ContainerSummaryInner state(String state) {
    this.state = state;
    return this;
  }

   /**
   * The state of this container (e.g. &#x60;Exited&#x60;)
   * @return state
  **/
  @ApiModelProperty(value = "The state of this container (e.g. `Exited`)")
  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public ContainerSummaryInner status(String status) {
    this.status = status;
    return this;
  }

   /**
   * Additional human-readable status of this container (e.g. &#x60;Exit 0&#x60;)
   * @return status
  **/
  @ApiModelProperty(value = "Additional human-readable status of this container (e.g. `Exit 0`)")
  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public ContainerSummaryInner hostConfig(ContainerSummaryInnerHostConfig hostConfig) {
    this.hostConfig = hostConfig;
    return this;
  }

   /**
   * Get hostConfig
   * @return hostConfig
  **/
  @ApiModelProperty(value = "")
  public ContainerSummaryInnerHostConfig getHostConfig() {
    return hostConfig;
  }

  public void setHostConfig(ContainerSummaryInnerHostConfig hostConfig) {
    this.hostConfig = hostConfig;
  }

  public ContainerSummaryInner networkSettings(ContainerSummaryInnerNetworkSettings networkSettings) {
    this.networkSettings = networkSettings;
    return this;
  }

   /**
   * Get networkSettings
   * @return networkSettings
  **/
  @ApiModelProperty(value = "")
  public ContainerSummaryInnerNetworkSettings getNetworkSettings() {
    return networkSettings;
  }

  public void setNetworkSettings(ContainerSummaryInnerNetworkSettings networkSettings) {
    this.networkSettings = networkSettings;
  }

  public ContainerSummaryInner mounts(List<Mount> mounts) {
    this.mounts = mounts;
    return this;
  }

  public ContainerSummaryInner addMountsItem(Mount mountsItem) {
    if (this.mounts == null) {
      this.mounts = new ArrayList<Mount>();
    }
    this.mounts.add(mountsItem);
    return this;
  }

   /**
   * Get mounts
   * @return mounts
  **/
  @ApiModelProperty(value = "")
  public List<Mount> getMounts() {
    return mounts;
  }

  public void setMounts(List<Mount> mounts) {
    this.mounts = mounts;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ContainerSummaryInner containerSummaryInner = (ContainerSummaryInner) o;
    return Objects.equals(this.id, containerSummaryInner.id) &&
        Objects.equals(this.names, containerSummaryInner.names) &&
        Objects.equals(this.image, containerSummaryInner.image) &&
        Objects.equals(this.imageID, containerSummaryInner.imageID) &&
        Objects.equals(this.command, containerSummaryInner.command) &&
        Objects.equals(this.created, containerSummaryInner.created) &&
        Objects.equals(this.ports, containerSummaryInner.ports) &&
        Objects.equals(this.sizeRw, containerSummaryInner.sizeRw) &&
        Objects.equals(this.sizeRootFs, containerSummaryInner.sizeRootFs) &&
        Objects.equals(this.labels, containerSummaryInner.labels) &&
        Objects.equals(this.state, containerSummaryInner.state) &&
        Objects.equals(this.status, containerSummaryInner.status) &&
        Objects.equals(this.hostConfig, containerSummaryInner.hostConfig) &&
        Objects.equals(this.networkSettings, containerSummaryInner.networkSettings) &&
        Objects.equals(this.mounts, containerSummaryInner.mounts);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, names, image, imageID, command, created, ports, sizeRw, sizeRootFs, labels, state, status, hostConfig, networkSettings, mounts);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ContainerSummaryInner {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    names: ").append(toIndentedString(names)).append("\n");
    sb.append("    image: ").append(toIndentedString(image)).append("\n");
    sb.append("    imageID: ").append(toIndentedString(imageID)).append("\n");
    sb.append("    command: ").append(toIndentedString(command)).append("\n");
    sb.append("    created: ").append(toIndentedString(created)).append("\n");
    sb.append("    ports: ").append(toIndentedString(ports)).append("\n");
    sb.append("    sizeRw: ").append(toIndentedString(sizeRw)).append("\n");
    sb.append("    sizeRootFs: ").append(toIndentedString(sizeRootFs)).append("\n");
    sb.append("    labels: ").append(toIndentedString(labels)).append("\n");
    sb.append("    state: ").append(toIndentedString(state)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    hostConfig: ").append(toIndentedString(hostConfig)).append("\n");
    sb.append("    networkSettings: ").append(toIndentedString(networkSettings)).append("\n");
    sb.append("    mounts: ").append(toIndentedString(mounts)).append("\n");
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

