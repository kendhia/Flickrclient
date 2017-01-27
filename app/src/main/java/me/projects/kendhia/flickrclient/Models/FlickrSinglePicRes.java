package me.projects.kendhia.flickrclient.Models;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by KenDhia on 1/27/2017.
 */

public class FlickrSinglePicRes {
    private Sizes sizes;
    private String stat;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Sizes getSizes() {
        return sizes;
    }

    public void setSizes(Sizes sizes) {
        this.sizes = sizes;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
