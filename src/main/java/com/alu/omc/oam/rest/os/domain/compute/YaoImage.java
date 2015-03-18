package com.alu.omc.oam.rest.os.domain.compute;

import java.io.Serializable;
import java.util.Date;

import org.openstack4j.model.image.ContainerFormat;
import org.openstack4j.model.image.DiskFormat;
import org.openstack4j.model.image.Image;
import org.openstack4j.model.image.StoreType;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
public class YaoImage implements Serializable {
    private String id;
    private String name;
    private String link;
    private String status;
    @JsonProperty("min_ram")
    private long minRam;
    @JsonProperty("min_disk")
    private long minDisk;
    @JsonProperty("created_at")
    private Date created;
    private long size;
    private String checkSum;
    @JsonProperty("container_format")
    private ContainerFormat containerFormat;
    @JsonProperty("disk_format")
    private DiskFormat diskFormat;
    private String copyFrom;
    private String location;
    private String owner;
    private StoreType storeTyper;
    @JsonProperty("updated_at")
    private Date updated;
    private boolean isProtected;
    private boolean isPublic;
    private boolean isSnapshot;
     
    public YaoImage(Image image) {
        setImage(image);
    }

	public void setImage(Image image) {
        this.name = image.getName();
        this.id   = image.getId();
        this.status = image.getStatus().name();
        this.checkSum = image.getChecksum();
        this.containerFormat = image.getContainerFormat();
        this.diskFormat = image.getDiskFormat();
        this.copyFrom = image.getCopyFrom();
        this.location = image.getLocation();
        this.owner = image.getOwner();
        this.storeTyper = image.getStoreType();
        this.updated = image.getUpdatedAt();        
        this.setMinDisk(image.getMinDisk());
        this.setCreated(image.getCreatedAt());
        this.setSize(image.getSize());
        this.isPublic = image.isPublic();
        this.isProtected = image.isProtected();
        this.setSnapshot(image.isSnapshot());
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the link
     */
    public String getLink() {
        return link;
    }

    /**
     * @param link the link to set
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the minRam
     */
    public long getMinRam() {
        return minRam;
    }

    /**
     * @param minRam the minRam to set
     */
    public void setMinRam(long minRam) {
        this.minRam = minRam;
    }

    /**
     * @return the minDisk
     */
    public long getMinDisk() {
        return minDisk;
    }

    /**
     * @param minDisk the minDisk to set
     */
    public void setMinDisk(long minDisk) {
        this.minDisk = minDisk;
    }

    /**
     * @return the created
     */
    public Date getCreated() {
        return created;
    }

    /**
     * @param created the created to set
     */
    public void setCreated(Date created) {
        this.created = created;
    }

    /**
     * @return the size
     */
    public long getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(long size) {
        this.size = size;
    }

    /**
     * @return the checkSum
     */
    public String getCheckSum() {
        return checkSum;
    }

    /**
     * @param checkSum the checkSum to set
     */
    public void setCheckSum(String checkSum) {
        this.checkSum = checkSum;
    }

    /**
     * @return the containerFormat
     */
    public ContainerFormat getContainerFormat() {
        return containerFormat;
    }

    /**
     * @param containerFormat the containerFormat to set
     */
    public void setContainerFormat(ContainerFormat containerFormat) {
        this.containerFormat = containerFormat;
    }

    /**
     * @return the diskFormat
     */
    public DiskFormat getDiskFormat() {
        return diskFormat;
    }

    /**
     * @param diskFormat the diskFormat to set
     */
    public void setDiskFormat(DiskFormat diskFormat) {
        this.diskFormat = diskFormat;
    }

    /**
     * @return the copyFrom
     */
    public String getCopyFrom() {
        return copyFrom;
    }

    /**
     * @param copyFrom the copyFrom to set
     */
    public void setCopyFrom(String copyFrom) {
        this.copyFrom = copyFrom;
    }

    /**
     * @return the localtion
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param localtion the localtion to set
     */
    public void setLocation(String localtion) {
        this.location = localtion;
    }

    /**
     * @return the owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * @param owner the owner to set
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * @return the storeTyper
     */
    public StoreType getStoreTyper() {
        return storeTyper;
    }

    /**
     * @param storeTyper the storeTyper to set
     */
    public void setStoreTyper(StoreType storeTyper) {
        this.storeTyper = storeTyper;
    }

    /**
     * @return the updated
     */
    public Date getUpdated() {
        return updated;
    }

    /**
     * @param updated the updated to set
     */
    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    /**
     * @return the isProtected
     */
    public boolean isProtected() {
        return isProtected;
    }

    /**
     * @param isProtected the isProtected to set
     */
    public void setProtected(boolean isProtected) {
        this.isProtected = isProtected;
    }

    /**
     * @return the isPublic
     */
    public boolean isPublic() {
        return isPublic;
    }

    /**
     * @param isPublic the isPublic to set
     */
    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    /**
     * @return the isSnapshot
     */
    public boolean isSnapshot() {
        return isSnapshot;
    }

    /**
     * @param isSnapshot the isSnapshot to set
     */
    public void setSnapshot(boolean isSnapshot) {
        this.isSnapshot = isSnapshot;
    }

}
