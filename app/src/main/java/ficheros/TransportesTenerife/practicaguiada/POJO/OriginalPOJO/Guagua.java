package ficheros.TransportesTenerife.practicaguiada.POJO.OriginalPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Guagua implements Serializable
{

    @SerializedName("route_id")
    @Expose
    private String routeId;
    @SerializedName("agency_id")
    @Expose
    private String agencyId;
    @SerializedName("route_short_name")
    @Expose
    private String routeShortName;
    @SerializedName("route_long_name")
    @Expose
    private String routeLongName;
    @SerializedName("route_type")
    @Expose
    private String routeType;
    @SerializedName("route_color")
    @Expose
    private String routeColor;
    @SerializedName("route_url")
    @Expose
    private String routeUrl;
    private final static long serialVersionUID = -8968928114412995007L;

    /**
     * No args constructor for use in serialization
     *
     */
    public Guagua() {
    }

    /**
     *
     * @param routeColor
     * @param routeShortName
     * @param routeId
     * @param routeUrl
     * @param agencyId
     * @param routeType
     * @param routeLongName
     */
    public Guagua(String routeId, String agencyId, String routeShortName, String routeLongName, String routeType, String routeColor, String routeUrl) {
        super();
        this.routeId = routeId;
        this.agencyId = agencyId;
        this.routeShortName = routeShortName;
        this.routeLongName = routeLongName;
        this.routeType = routeType;
        this.routeColor = routeColor;
        this.routeUrl = routeUrl;
    }

    public Guagua(String routeShortName, String routeLongName){
        this.routeShortName = routeShortName;
        this.routeLongName = routeLongName;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public Guagua withRouteId(String routeId) {
        this.routeId = routeId;
        return this;
    }

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    public Guagua withAgencyId(String agencyId) {
        this.agencyId = agencyId;
        return this;
    }

    public String getRouteShortName() {
        return routeShortName;
    }

    public void setRouteShortName(String routeShortName) {
        this.routeShortName = routeShortName;
    }

    public Guagua withRouteShortName(String routeShortName) {
        this.routeShortName = routeShortName;
        return this;
    }

    public String getRouteLongName() {
        return routeLongName;
    }

    public void setRouteLongName(String routeLongName) {
        this.routeLongName = routeLongName;
    }

    public Guagua withRouteLongName(String routeLongName) {
        this.routeLongName = routeLongName;
        return this;
    }

    public String getRouteType() {
        return routeType;
    }

    public void setRouteType(String routeType) {
        this.routeType = routeType;
    }

    public Guagua withRouteType(String routeType) {
        this.routeType = routeType;
        return this;
    }

    public String getRouteColor() {
        return routeColor;
    }

    public void setRouteColor(String routeColor) {
        this.routeColor = routeColor;
    }

    public Guagua withRouteColor(String routeColor) {
        this.routeColor = routeColor;
        return this;
    }

    public String getRouteUrl() {
        return routeUrl;
    }

    public void setRouteUrl(String routeUrl) {
        this.routeUrl = routeUrl;
    }

    public Guagua withRouteUrl(String routeUrl) {
        this.routeUrl = routeUrl;
        return this;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.routeColor == null)? 0 :this.routeColor.hashCode()));
        result = ((result* 31)+((this.routeShortName == null)? 0 :this.routeShortName.hashCode()));
        result = ((result* 31)+((this.routeId == null)? 0 :this.routeId.hashCode()));
        result = ((result* 31)+((this.routeUrl == null)? 0 :this.routeUrl.hashCode()));
        result = ((result* 31)+((this.agencyId == null)? 0 :this.agencyId.hashCode()));
        result = ((result* 31)+((this.routeType == null)? 0 :this.routeType.hashCode()));
        result = ((result* 31)+((this.routeLongName == null)? 0 :this.routeLongName.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Guagua) == false) {
            return false;
        }
        Guagua rhs = ((Guagua) other);
        return ((((((((this.routeColor == rhs.routeColor)||((this.routeColor!= null)&&this.routeColor.equals(rhs.routeColor)))&&((this.routeShortName == rhs.routeShortName)||((this.routeShortName!= null)&&this.routeShortName.equals(rhs.routeShortName))))&&((this.routeId == rhs.routeId)||((this.routeId!= null)&&this.routeId.equals(rhs.routeId))))&&((this.routeUrl == rhs.routeUrl)||((this.routeUrl!= null)&&this.routeUrl.equals(rhs.routeUrl))))&&((this.agencyId == rhs.agencyId)||((this.agencyId!= null)&&this.agencyId.equals(rhs.agencyId))))&&((this.routeType == rhs.routeType)||((this.routeType!= null)&&this.routeType.equals(rhs.routeType))))&&((this.routeLongName == rhs.routeLongName)||((this.routeLongName!= null)&&this.routeLongName.equals(rhs.routeLongName))));
    }
}